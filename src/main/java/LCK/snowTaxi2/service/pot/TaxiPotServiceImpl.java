package LCK.snowTaxi2.service.pot;

import LCK.snowTaxi2.domain.Member;
import LCK.snowTaxi2.domain.pot.Departure;
import LCK.snowTaxi2.domain.pot.TaxiPot;
import LCK.snowTaxi2.dto.pot.TaxiPotResponseDto;
import LCK.snowTaxi2.exception.NotFoundEntityException;
import LCK.snowTaxi2.repository.MemberRepository;
import LCK.snowTaxi2.repository.TaxiPotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaxiPotServiceImpl implements TaxiPotService {

    private final TaxiPotRepository taxiPotRepository;
    private final MemberRepository memberRepository;

    @Override
    public long create(String departure, LocalTime ridingTime) {
        TaxiPot taxiPot = TaxiPot.builder()
                .headCount(0)
                .departure(Departure.NAMYOUNG)
                .ridingDate(LocalDate.now())
                .ridingTime(ridingTime)
                .build();

        TaxiPot saveTaxiPot = taxiPotRepository.save(taxiPot);

        return saveTaxiPot.getId();
    }

    @Override
    public List<TaxiPotResponseDto> getTodayPots(String departure, Long memberId) {
        List<TaxiPot> pots = taxiPotRepository.getTaxiPotByRidingDateAndDeparture(LocalDate.now(), Departure.valueOf(departure));
        List<TaxiPotResponseDto> response = new ArrayList<>();

        Member member = memberRepository.findById(memberId).orElseThrow( () ->
                new NotFoundEntityException("member id:", memberId.toString())
        );

        for (TaxiPot pot : pots) {
            if (pot.getRidingTime().isBefore(LocalTime.now().minusMinutes(3))){
                response.add( TaxiPotResponseDto.builder()
                        .id(pot.getId())
                        .headCount(pot.getHeadCount())
                        .ridingTime(pot.getRidingTime())
                        .isParticipating(member.getParticipatingPotId() == pot.getId())
                        .build()
                );
            }
        }

        Collections.sort(response);
        return response;
    }

    @Override
    public TaxiPotResponseDto findParticipatingPot(Member member) {
        return null;
    }

    @Override
    public void changeHeadCount(int add, Long id) {
        TaxiPot taxiPot = taxiPotRepository.findById(id).orElseThrow( () ->
                new NotFoundEntityException("taxiPot id:", id.toString())
        );

        taxiPot.setHeadCount(taxiPot.getHeadCount() + add);
        taxiPotRepository.saveAndFlush(taxiPot);
    }

    @Override
    public void delete(Long id) {
        taxiPotRepository.deleteById(id);
    }

}

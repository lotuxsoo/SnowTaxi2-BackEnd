package LCK.snowTaxi2.service.participation;

import LCK.snowTaxi2.domain.Member;
import LCK.snowTaxi2.domain.Participation;
import LCK.snowTaxi2.domain.pot.TaxiPot;
import LCK.snowTaxi2.exception.NotFoundEntityException;
import LCK.snowTaxi2.repository.MemberRepository;
import LCK.snowTaxi2.repository.ParticipationRepository;
import LCK.snowTaxi2.repository.TaxiPotRepository;
import LCK.snowTaxi2.service.member.MemberService;
import LCK.snowTaxi2.service.pot.TaxiPotService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ParticipationServiceImpl implements ParticipationService {

    private final ParticipationRepository participationRepository;
    private final MemberRepository memberRepository;
    private final TaxiPotRepository taxiPotRepository;
    private final TaxiPotService taxiPotService;
    private final MemberService memberService;

    @Override
    @Transactional
    public boolean create(Long memberId, Long taxiPotId) {
        Member member = memberRepository.findById(memberId).orElseThrow( () ->
                new NotFoundEntityException("member id:", memberId.toString())
        );

        if (member.getParticipatingPotId() == 0) {
            TaxiPot taxiPot = taxiPotRepository.findById(taxiPotId).orElseThrow( () ->
                    new NotFoundEntityException("taxiPot id:", taxiPotId.toString())
            );

            Participation participation = Participation.builder()
                    .member(member)
                    .taxiPot(taxiPot)
                    .build();

            participationRepository.save(participation);
            taxiPotService.changeHeadCount(1, taxiPotId);
            memberService.setParticipatingPotId(memberId, taxiPotId);

            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional
    public void delete(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow( () ->
                new NotFoundEntityException("member id:", memberId.toString())
        );

        Long taxiPotId = member.getParticipatingPotId();

        TaxiPot taxiPot = taxiPotRepository.findById(taxiPotId).orElseThrow( () ->
                new NotFoundEntityException("taxiPot id:", taxiPotId.toString())
        );

        participationRepository.deleteParticipationByTaxiPotAndMember(taxiPot, member);
        memberService.setParticipatingPotId(memberId, 0);
        taxiPotService.changeHeadCount(-1, taxiPotId);

        if (taxiPot.getParticipations().size() == 0) {
            taxiPotService.delete(taxiPotId);
        }
    }
}

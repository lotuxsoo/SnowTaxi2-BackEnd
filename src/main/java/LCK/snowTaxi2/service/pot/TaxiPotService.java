package LCK.snowTaxi2.service.pot;

import LCK.snowTaxi2.domain.Member;
import LCK.snowTaxi2.domain.pot.TaxiPot;
import LCK.snowTaxi2.dto.pot.TaxiPotResponseDto;

import java.time.LocalDateTime;
import java.util.List;

public interface TaxiPotService {

    // 팟 생성하기
    TaxiPotResponseDto create(int departureIdx, LocalDateTime ridingTime, long hostId);

    // 택시 루트별로 오늘 모집중인 팟 정보들 모두 조회하기
    List<TaxiPotResponseDto> getTodayPots(int departureIdx, long userId);

    // 현재 참여중인 팟 정보 불러오기
    TaxiPotResponseDto findParticipatingPot(Member member);

    // 생성한 팟 삭제 (시간 안지난 팟만 삭제 가능하도록)
    void delete();

}

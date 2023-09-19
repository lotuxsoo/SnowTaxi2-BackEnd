package LCK.snowTaxi2.service.pot;

import LCK.snowTaxi2.domain.Member;
import LCK.snowTaxi2.dto.pot.TaxiPotResponseDto;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
public interface TaxiPotService {

    // 팟 생성하기 (컨트롤러에서 택시팟 생성하고 반환받은 id 값으로 participationService.create 실행)
    long create(String departure, LocalTime ridingTime);

    // 택시 루트별로 오늘 모집중인 팟 중 시간이 유효한 팟을 탑승시간을 기준으로 정렬해 리턴
    List<TaxiPotResponseDto> getTodayPots(int departureIdx, Long memberId);

    // 현재 참여중인 팟 정보 불러오기
    TaxiPotResponseDto findParticipatingPot(Member member);

    // 참여 기록 조회

    // 팟에 참여하고 있는 인원 변경 ( +add )
    void changeHeadCount(int add, Long id);

    // 생성한 팟 삭제 (시간 안지난 팟만 삭제 가능하도록)
    void delete(Long id);

}

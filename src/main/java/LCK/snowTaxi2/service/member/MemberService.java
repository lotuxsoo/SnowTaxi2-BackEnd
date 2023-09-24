package LCK.snowTaxi2.service.member;

import LCK.snowTaxi2.dto.member.MemberRequestDto;
import org.springframework.stereotype.Service;

@Service
public interface MemberService {
    public boolean checkNickname(String nickname);
    public boolean checkEmail(String email);
    public void createMember(MemberRequestDto dto);
    public int validationMember(MemberRequestDto dto);
    public void setParticipatingPotId(Long memberId, long taxiPotId);
    public long getParticipatingPotId(Long memberId);
}

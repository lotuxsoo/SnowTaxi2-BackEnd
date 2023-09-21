package LCK.snowTaxi2.service.member;

import LCK.snowTaxi2.dto.member.MemberRequestDto;
import org.springframework.stereotype.Service;

@Service
public interface MemberService {
    public String createMember(MemberRequestDto dto);
    public boolean validationMember(MemberRequestDto dto);
    public void setParticipatingPotId(Long memberId, long taxiPotId);
}

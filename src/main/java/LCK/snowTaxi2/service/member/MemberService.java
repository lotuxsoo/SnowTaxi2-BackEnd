package LCK.snowTaxi2.service.member;

import LCK.snowTaxi2.domain.Member;
import LCK.snowTaxi2.dto.member.MemberRequestDto;
import LCK.snowTaxi2.exception.NotFoundEntityException;
import LCK.snowTaxi2.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public String createMember(MemberRequestDto dto) {
        Member member = memberRepository.findByEmail(dto.getEmail());
        if (member != null) {
            return "해당 이메일의 유저가 존재합니다.";
        }

        member = Member.builder()
                .email(dto.getEmail())
                .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                .build();

        memberRepository.save(member);
        return "환영합니다!";
    }

    public boolean validationMember(MemberRequestDto dto) {
        Member loginMember = memberRepository.findByEmail(dto.getEmail());

        if(loginMember==null) {
            System.out.println("해당 이메일의 유저가 존재하지 않습니다.");
            return false;
        }

        if(!bCryptPasswordEncoder.matches(dto.getPassword(), loginMember.getPassword())) {
            System.out.println("비밀번호가 일치하지 않습니다.");
            return false;
        }

        return true;
    }

    // 현재 참여중인 택시 팟 아이디 변경
    public void setParticipatingPotId(Long memberId, long taxiPotId) {
        Member member = memberRepository.findById(memberId).orElseThrow( () ->
            new NotFoundEntityException("member Id:", memberId.toString())
        );
        member.setParticipatingPotId(taxiPotId);
        memberRepository.saveAndFlush(member);
    }

}

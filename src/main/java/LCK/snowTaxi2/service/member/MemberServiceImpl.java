package LCK.snowTaxi2.service.member;

import LCK.snowTaxi2.domain.Member;
import LCK.snowTaxi2.dto.member.LoginResponseDto;
import LCK.snowTaxi2.dto.member.MemberRequestDto;
import LCK.snowTaxi2.exception.NotFoundEntityException;
import LCK.snowTaxi2.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void createMember(MemberRequestDto dto) {
        Member member = Member.builder()
                .email(dto.getEmail())
                .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                .nickname(dto.getNickname())
                .build();
        memberRepository.save(member);
    }

    @Override
    public void setParticipatingPotId(Long memberId, long taxiPotId) {
        Member member = memberRepository.findById(memberId).orElseThrow( () ->
                new NotFoundEntityException("member Id:", memberId.toString())
        );
        member.setParticipatingPotId(taxiPotId);
        memberRepository.saveAndFlush(member);
    }

    @Override
    public long getParticipatingPotId(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow( () ->
                new NotFoundEntityException("member Id:", memberId.toString())
        );
        return member.getParticipatingPotId();
    }

    @Override
    public LoginResponseDto findMember(MemberRequestDto dto) {
        Member member = memberRepository.findByEmail(dto.getEmail());
        String message;
        int status;

        if (member == null) {
            status = HttpStatus.NOT_FOUND.value();
            message = "해당 이메일의 회원이 존재하지 않습니다.";
        } else if (!bCryptPasswordEncoder.matches(dto.getPassword(), member.getPassword())) {
            status = HttpStatus.CONFLICT.value();
            message = "비밀번호가 일치하지 않습니다.";
        } else {
            status = HttpStatus.OK.value();
            message = "로그인 성공하였습니다.";
        }

        return LoginResponseDto.builder()
                .loginStatus(status)
                .message(message)
                .member(member)
                .build();
    }

    @Override
    public boolean isValidNickname(String nickname) {
        Member member = memberRepository.findByNickname(nickname);
        return member == null;
    }

    @Override
    public boolean isValidEmail(String email) {
        Member member = memberRepository.findByEmail(email);
        return member == null;
    }

}
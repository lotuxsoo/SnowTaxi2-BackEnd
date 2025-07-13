package LCK.snowTaxi2.controller;

import LCK.snowTaxi2.domain.member.Member;
import LCK.snowTaxi2.domain.member.SessionUser;
import LCK.snowTaxi2.dto.ResultResponse;
import LCK.snowTaxi2.dto.member.LoginResponseDto;
import LCK.snowTaxi2.dto.member.MemberRequestDto;
import LCK.snowTaxi2.dto.member.MemberResponseDto;
import LCK.snowTaxi2.dto.pot.TaxiPotInfo;
import LCK.snowTaxi2.service.member.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/auth")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signUp")
    public ResultResponse signUp(@RequestBody MemberRequestDto memberRequest) {
        if (!memberService.isMemberEmail(memberRequest.getEmail())) {
            memberService.createMember(memberRequest);
            return ResultResponse.builder()
                    .code(HttpStatus.OK.value())
                    .message("회원가입에 성공했습니다.")
                    .build();
        } else {
            return ResultResponse.builder()
                    .code(HttpStatus.CONFLICT.value())
                    .message("회원가입에 실패했습니다.")
                    .build();
        }
    }

    @PostMapping("/login")
    public ResultResponse login(@RequestBody MemberRequestDto memberRequest, HttpServletRequest request) {
        // 회원 정보 조회
        LoginResponseDto loginResponseDto = memberService.findMember(memberRequest);
        MemberResponseDto memberResponseDto = null;

        // 로그인 성공
        if (loginResponseDto.getLoginStatus() == HttpStatus.OK.value()) {
            Member loginMember = loginResponseDto.getMember();
            SessionUser sessionUser = new SessionUser(
                    loginMember.getId(),
                    loginMember.getEmail(),
                    loginMember.getRole() // Member 엔티티에 getRole()이 있다고 가정
            );

            // 2. 새로운 세션을 생성하거나 기존 세션을 가져옴
            HttpSession session = request.getSession(true);

            // 3. 세션에 사용자 정보를 "user"라는 이름으로 저장
            session.setAttribute("user", sessionUser);

            // 4. 세션 타임아웃 시간 설정 (예: 30분)
            session.setMaxInactiveInterval(1800);

            memberResponseDto = MemberResponseDto.builder()
                    .potInfo(new TaxiPotInfo(loginMember.getParticipatingPotId(), loginMember.getRidingTime()))
                    .email(loginMember.getEmail())
                    .nickname(loginMember.getNickname())
                    .build();
        }

        return ResultResponse.builder()
                .code(loginResponseDto.getLoginStatus())
                .message(loginResponseDto.getMessage())
                .data(memberResponseDto)
                .build();
    }

    @GetMapping("/logout")
    public ResultResponse logout(HttpServletRequest request) {
        // 1. 현재 사용자의 세션을 가져옴 (없으면 null 반환)
        HttpSession session = request.getSession(false);

        // 2. 세션이 존재하면 해당 세션을 무효화시킴
        if (session != null) {
            session.invalidate();
        }

        return ResultResponse.builder()
                .code(HttpStatus.OK.value())
                .message("로그아웃 합니다.")
                .build();
    }

    @PostMapping("/nicknameCheck")
    public ResultResponse nicknameCheck(@RequestParam String nickname) {
        if (!memberService.isMemberNickname(nickname)) {
            return ResultResponse.builder()
                    .code(HttpStatus.OK.value())
                    .message("사용 가능한 닉네임입니다.")
                    .build();
        } else {
            return ResultResponse.builder()
                    .code(HttpStatus.CONFLICT.value())
                    .message("닉네임이 중복됩니다.")
                    .build();
        }
    }
}
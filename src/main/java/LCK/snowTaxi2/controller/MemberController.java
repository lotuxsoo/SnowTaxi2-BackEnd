package LCK.snowTaxi2.controller;

import LCK.snowTaxi2.dto.ResultResponse;
import LCK.snowTaxi2.dto.member.MemberRequestDto;
import LCK.snowTaxi2.jwt.TokenInfoVo;
import LCK.snowTaxi2.jwt.JwtService;
import LCK.snowTaxi2.service.member.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/auth")
public class MemberController {

    private final MemberService memberService;
    private final JwtService jwtService;

    @PostMapping("/signUp")
    public ResultResponse signUp(@RequestBody MemberRequestDto memberRequest) {
        int status = memberService.createMember(memberRequest);
        String msg = (status == HttpStatus.OK.value() ? "회원가입에 성공했습니다." : "해당 이메일의 유저가 존재합니다.");
        return ResultResponse.builder()
                .code(status)
                .message(msg)
                .build();
    }

    @PostMapping("/login")
    public ResultResponse login(@RequestBody MemberRequestDto memberRequest, HttpServletResponse response) {
        // 회원 정보 조회
        int status = memberService.validationMember(memberRequest);
        // 로그인 실패
        if (status != HttpStatus.OK.value()) {
            String msg = (status == HttpStatus.NOT_FOUND.value() ? "해당 이메일의 유저가 존재하지 않습니다." : "비밀번호가 일치하지 않습니다.");
            return ResultResponse.builder()
                    .code(status)
                    .message(msg)
                    .build();
        }
        // 로그인 성공
        TokenInfoVo tokenInfoVo = jwtService.setTokenInfo(memberRequest);
        String access_token = jwtService.createAccessToken(tokenInfoVo);
        jwtService.sendAccessToken(response, access_token);
        return ResultResponse.builder()
                .code(status)
                .message("로그인에 성공했습니다.")
                .build();
    }

    @GetMapping("/logout")
    public ResultResponse logout(HttpServletRequest request) {
        SecurityContextHolder.getContext().setAuthentication(null);

        return ResultResponse.builder()
                .code(HttpStatus.OK.value())
                .message("로그아웃 합니다.")
                .build();
    }

}
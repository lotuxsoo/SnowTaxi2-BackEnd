package LCK.snowTaxi2.controller;

import LCK.snowTaxi2.dto.MemberRequest;
import LCK.snowTaxi2.jwt.JwtService;
import LCK.snowTaxi2.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/auth")
public class MemberController {
    @Autowired
    private final MemberService memberService;
    @Autowired
    JwtService jwtService;

    @PostMapping("/signUp")
    public String signUp(@RequestBody MemberRequest memberRequest) {
        String msg = memberService.createMember(memberRequest);

        return msg;
    }

    @PostMapping("/login")
    public String login(@RequestBody MemberRequest memberRequest, HttpServletResponse response){
        // 회원 정보 조회
        boolean isMember = memberService.validationMember(memberRequest);
        if (isMember) {
            String access_token = jwtService.createAccessToken(1,"");
            jwtService.sendAccessToken(response, access_token);
            System.out.println("토큰을 헤더로 전송");
            return "home";
        }

        return "login 실패";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        SecurityContextHolder.getContext().setAuthentication(null);
        return "login";
    }
}

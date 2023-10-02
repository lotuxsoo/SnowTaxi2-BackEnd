package LCK.snowTaxi2;

import LCK.snowTaxi2.domain.Member;
import LCK.snowTaxi2.dto.ResultResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/")
    public String hello(){
        return "hello";
    }

    @GetMapping("/test")
    public String test(){
        return "test";
    }

    @GetMapping("/user")
    public Member geta() {
        Member member = Member.builder()
                .email("aa")
                .id(1)
                .password("32")
                .build();

        return member;

    }

    @GetMapping("/result")
    public ResultResponse g(){
        Member user = Member.builder()
                .email("aa")
                .id(1)
                .password("32")
                .build();

        return new ResultResponse (100, "회원가입이 ", user);
    }

}

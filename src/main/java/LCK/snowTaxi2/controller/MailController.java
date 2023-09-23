package LCK.snowTaxi2.controller;

import LCK.snowTaxi2.dto.ResultResponse;
import LCK.snowTaxi2.service.mail.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/email")
public class MailController {
    private final MailService mailService;

    @ResponseBody
    @GetMapping("/auth")
    public ResultResponse AuthMailSend(@RequestParam String mail) {

        int number = mailService.sendAuthMail(mail);
        String num = "" + number;
        return ResultResponse.builder()
                .code(HttpStatus.OK.value())
                .message("인증 번호를 메일로 보냈습니다.")
                .data(num)
                .build();
    }

    @ResponseBody
    @GetMapping("/password")
    public ResultResponse PasswordMailSend (@RequestParam String mail){

        String str = mailService.sendPasswordMail(mail);
        mailService.sendPasswordMail(mail);

        return ResultResponse.builder()
                .code(HttpStatus.OK.value())
                .message("변경된 비밀 번호를 메일로 보냈습니다.")
                .build();
    }

}
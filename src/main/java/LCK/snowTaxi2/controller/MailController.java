package LCK.snowTaxi2.controller;

import LCK.snowTaxi2.service.mail.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/email")
public class MailController {

    private final MailService mailService;

    @ResponseBody
    @GetMapping("/auth")
    public String AuthMailSend(@RequestParam String mail){

        int number = mailService.sendAuthMail(mail);
        String num = "" + number;

        return num;
    }

    @ResponseBody
    @GetMapping("/password")
    public String PasswordMailSend(@RequestParam String mail){

        String str = mailService.sendPasswordMail(mail);

        return str;
    }

}

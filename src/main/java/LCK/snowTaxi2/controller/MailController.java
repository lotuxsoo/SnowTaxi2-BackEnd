package LCK.snowTaxi2.controller;

import LCK.snowTaxi2.service.member.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/email")
public class MailController {
    @Autowired
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

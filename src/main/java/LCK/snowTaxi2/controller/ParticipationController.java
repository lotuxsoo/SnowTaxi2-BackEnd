package LCK.snowTaxi2.controller;

import LCK.snowTaxi2.domain.member.SessionUser;
import LCK.snowTaxi2.dto.ResultResponse;
import LCK.snowTaxi2.dto.chat.MessageRequestDto;
import LCK.snowTaxi2.jwt.JwtService;
import LCK.snowTaxi2.jwt.TokenInfoVo;
import LCK.snowTaxi2.service.chat.MessageService;
import LCK.snowTaxi2.service.participation.ParticipationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/participation")
public class ParticipationController {

    private final ParticipationService participationService;
    private final MessageService messageService;

    @PostMapping
    public ResultResponse participate(@SessionAttribute("user") SessionUser user, @RequestParam long potId) {
        boolean result = participationService.create(user.getUserId(), potId);

        if (result) {
            // sender 정보는 세션에서 가져온 사용자 닉네임 등을 사용
            messageService.send(MessageRequestDto.builder()
                    .roomId(potId)
                    .sender(user.getNickname())
                    .type("IN")
                    .build()
            );
        }
        return ResultResponse.builder()
                .code(HttpStatus.CREATED.value())
                .message("팟 참여하기")
                .data(result)
                .build();
    }

    @DeleteMapping
    public ResultResponse delete(@SessionAttribute("user") SessionUser user) {
        long potId = participationService.delete(user.getUserId());

        if (potId != 0) {
            messageService.send(MessageRequestDto.builder()
                    .roomId(potId)
                    .sender(user.getNickname())
                    .type("OUT")
                    .build()
            );
        }

        return ResultResponse.builder()
                .code(HttpStatus.CREATED.value())
                .message("팟 나가기")
                .build();
    }
}

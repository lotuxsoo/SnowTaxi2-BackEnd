package LCK.snowTaxi2.controller;

import LCK.snowTaxi2.dto.ResultResponse;
import LCK.snowTaxi2.dto.pot.TaxiPotRequestDto;
import LCK.snowTaxi2.jwt.JwtService;
import LCK.snowTaxi2.jwt.TokenInfoVo;
import LCK.snowTaxi2.service.member.MemberService;
import LCK.snowTaxi2.service.participation.ParticipationService;
import LCK.snowTaxi2.service.pot.TaxiPotService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/participation")
public class ParticipationController {

    private final TaxiPotService taxiPotService;
    private final ParticipationService participationService;
    private final MemberService memberService;
    private final JwtService jwtService;

    @PostMapping
    public ResultResponse participate(HttpServletRequest request, @RequestParam long potId) {
        String access_token = jwtService.extractAccessToken(request).orElseGet(() -> "");
        TokenInfoVo tokenInfoVo = jwtService.getTokenInfo(access_token);

        boolean result = participationService.create(tokenInfoVo.getMemberId(), potId);

        return ResultResponse.builder()
                .code(HttpStatus.CREATED.value())
                .message("팟 참여하기")
                .data(result)
                .build();
    }

    @DeleteMapping
    public ResultResponse delete(HttpServletRequest request) {
        String access_token = jwtService.extractAccessToken(request).orElseGet(() -> "");
        TokenInfoVo tokenInfoVo = jwtService.getTokenInfo(access_token);

        participationService.delete(tokenInfoVo.getMemberId());

        return ResultResponse.builder()
                .code(HttpStatus.CREATED.value())
                .message("팟 나가기")
                .build();
    }

}

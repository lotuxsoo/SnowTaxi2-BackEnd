package LCK.snowTaxi2.dto.member;

import LCK.snowTaxi2.domain.Member;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponseDto {

    private int loginStatus;

    private String message;

    private Member member;

}

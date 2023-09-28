package LCK.snowTaxi2.dto.member;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberResponseDto {

    private long participatingPotId;

    private String email;

    private String nickname;

}

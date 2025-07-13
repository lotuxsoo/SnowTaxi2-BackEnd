package LCK.snowTaxi2.domain.member;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SessionUser implements Serializable {
    private Long userId;
    private String email;
    private Role role;
}

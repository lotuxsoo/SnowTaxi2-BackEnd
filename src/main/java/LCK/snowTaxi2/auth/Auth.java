package LCK.snowTaxi2.auth;

import LCK.snowTaxi2.domain.member.Role;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD) // 이 어노테이션은 메서드에만 붙일 수 있음
@Retention(RetentionPolicy.RUNTIME) // 런타임까지 어노테이션 정보가 유지되어야 함
public @interface Auth {
    // 이 어노테이션은 어떤 역할을 필요로 하는지 명시
    // 기본값은 GUEST로, 로그인만 하면 접근 가능한 리소스를 의미
    Role role() default Role.GUEST;
}
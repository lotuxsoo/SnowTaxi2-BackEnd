package LCK.snowTaxi2.domain.pot;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Departure {
    SOOKMYUNG("숙대입구역"),
    HYOCHANG("효창공원역"),
    SEOUL("서울역"),
    NAMYOUNG("남영역");

    private final String name;
}

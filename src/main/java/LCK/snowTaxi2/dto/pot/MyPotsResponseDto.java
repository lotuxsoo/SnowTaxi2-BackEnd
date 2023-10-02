package LCK.snowTaxi2.dto.pot;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@Getter
public class MyPotsResponseDto {

    private long id;

    @JsonFormat(pattern = "yyyy.MM.dd")
    private LocalDate ridingDate;

    @JsonFormat(pattern = "a hh:mm")
    private LocalTime ridingTime;

    private int headCount;

}

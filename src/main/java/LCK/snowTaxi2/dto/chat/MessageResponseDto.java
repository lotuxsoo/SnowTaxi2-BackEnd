package LCK.snowTaxi2.dto.chat;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalTime;

@Getter
@Builder
public class MessageResponseDto {

    @JsonFormat(pattern = "a HH:mm")
    private LocalTime sentTime;

    private String sender;

    private String content;

}

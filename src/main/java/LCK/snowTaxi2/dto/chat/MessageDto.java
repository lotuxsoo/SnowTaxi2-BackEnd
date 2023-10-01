package LCK.snowTaxi2.dto.chat;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class MessageDto {

    private long roomId;

    private long senderId;

    private String sender;

    private String content;

    private String type;

    private String time;

}

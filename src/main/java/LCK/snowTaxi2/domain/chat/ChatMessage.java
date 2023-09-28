package LCK.snowTaxi2.domain.chat;

import LCK.snowTaxi2.domain.pot.TaxiPot;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "chat_message")
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_message_id")
    private Long id;

    @Enumerated(EnumType.ORDINAL)
    private MessageType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="taxi_pot_id")
    private ChatRoom chatRoom;

    private String sender;

    private String content;

    private LocalTime sentTime;

}

package LCK.snowTaxi2.service.chat;

import LCK.snowTaxi2.domain.chat.Message;
import LCK.snowTaxi2.domain.chat.MessageType;
import LCK.snowTaxi2.repository.MessageRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
@Transactional
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;

    public Message messageType(Message message) {
        if (MessageType.IN.equals(message.getType())) {
            message.setContent(message.getSender() + "님이 입장하였습니다.");
        } else if (MessageType.OUT.equals(message.getType())) {
            message.setContent(message.getSender() + "님이 나갔습니다.");
        }

        saveMessage(message.getType(), message.getRoomId(), message.getContent(), message.getSender());

        return message;
    }


    public void saveMessage(MessageType type, Long roomId, String content, String sender) {

        Message message = Message.builder()
                        .roomId(roomId)
                        .type(type)
                        .content(content)
                        .sender(sender)
                        .sentTime(LocalTime.now())
                        .build();

        messageRepository.save(message);
    }

}
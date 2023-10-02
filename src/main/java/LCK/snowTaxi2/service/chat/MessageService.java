package LCK.snowTaxi2.service.chat;

import LCK.snowTaxi2.domain.chat.Message;
import LCK.snowTaxi2.domain.chat.MessageType;
import LCK.snowTaxi2.domain.pot.TaxiPot;
import LCK.snowTaxi2.dto.chat.MessageRequestDto;
import LCK.snowTaxi2.dto.chat.MessageResponseDto;
import LCK.snowTaxi2.exception.NotFoundEntityException;
import LCK.snowTaxi2.repository.MessageRepository;
import LCK.snowTaxi2.repository.TaxiPotRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final TaxiPotRepository taxiPotRepository;
    private final SimpMessageSendingOperations sendingOperations;

    public void send(MessageRequestDto messageRequestDto) {
        String content = messageRequestDto.getContent();
        if (MessageType.valueOf(messageRequestDto.getType()).equals(MessageType.IN)) {
            content = messageRequestDto.getSender() + "님이 입장하였습니다.";
        } else if (MessageType.valueOf(messageRequestDto.getType()).equals(MessageType.OUT)) {
            content = messageRequestDto.getSender() + "님이 나갔습니다.";
        }

        saveMessage(messageRequestDto.getType(), messageRequestDto.getRoomId(), content, messageRequestDto.getSender());
        sendingOperations.convertAndSend("/sub/chatroom/" + messageRequestDto.getRoomId(), messageRequestDto);
    }


    public void saveMessage(String type, Long roomId, String content, String sender) {
        TaxiPot taxiPot = taxiPotRepository.findById(roomId).orElseThrow( () ->
                new NotFoundEntityException("taxiPot Id:", roomId.toString())
        );

        Message message = Message.builder()
                        .taxiPot(taxiPot)
                        .type(MessageType.valueOf(type))
                        .content(content)
                        .sender(sender)
                        .sentTime(LocalTime.now())
                        .build();

        messageRepository.save(message);
    }

    public List<MessageResponseDto> getChats(Long roomId) {
        TaxiPot taxiPot = taxiPotRepository.findById(roomId).orElseThrow( () ->
                new NotFoundEntityException("taxiPot Id:", roomId.toString())
        );

        List<MessageResponseDto> messages = new ArrayList<>();

        for (Message message: taxiPot.getChatMessages()) {
            messages.add( MessageResponseDto.builder()
                    .sentTime(message.getSentTime())
                    .content(message.getContent())
                    .sender(message.getSender())
                    .build()
            );
        }

        return messages;
    }

}
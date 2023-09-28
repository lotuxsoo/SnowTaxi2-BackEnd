package LCK.snowTaxi2.controller;

import LCK.snowTaxi2.domain.chat.ChatMessage;
import LCK.snowTaxi2.domain.chat.MessageType;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.messaging.handler.annotation.MessageMapping;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class MessageController {

    private final SimpMessageSendingOperations sendingOperations;

    @MessageMapping("/message")
    public void enter(ChatMessage message) {
        if (MessageType.ENTER.equals(message.getType())) {
            message.setContent(message.getSender()+"님이 입장하였습니다.");
        } else if (MessageType.OUT.equals(message.getType())) {
            message.setContent(message.getSender()+"님이 나갔습니다.");
        }

        sendingOperations.convertAndSend("/topic/chat/room/" + message.getChatRoom().getId(), message);
    }

}

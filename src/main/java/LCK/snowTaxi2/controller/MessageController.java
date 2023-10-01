package LCK.snowTaxi2.controller;

import LCK.snowTaxi2.domain.chat.MessageType;
import LCK.snowTaxi2.dto.ResultResponse;
import LCK.snowTaxi2.dto.chat.MessageDto;
import LCK.snowTaxi2.service.chat.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.messaging.handler.annotation.MessageMapping;

@RestController
@RequiredArgsConstructor
public class MessageController {

    private final SimpMessageSendingOperations sendingOperations;
    private final MessageService messageService;

    @MessageMapping("/chat")
    public void send(MessageDto messageDto) {
        System.out.println("chat {} send by {} to room number{}" + messageDto.getContent());
        messageService.saveMessage(MessageType.valueOf(messageDto.getType()), messageDto.getRoomId(), messageDto.getContent(), messageDto.getSender());
        sendingOperations.convertAndSend("/sub/chatroom/" + messageDto.getRoomId(), messageDto);
    }

//    @GetMapping("/chatroom/{id}")
//    public ResultResponse getChat(@RequestParam long id) {
//        return ResultResponse.builder().
//    }
}

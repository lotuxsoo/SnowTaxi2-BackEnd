package LCK.snowTaxi2.auth;

import LCK.snowTaxi2.domain.member.SessionUser;
import java.util.Optional;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.access.AccessDeniedException;

public class AuthChannelInterceptor implements ChannelInterceptor {

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        // WebSocket 연결(CONNECT) 또는 메시지 전송(SEND) 시에만 인증 검사
        if (StompCommand.CONNECT.equals(accessor.getCommand()) || StompCommand.SEND.equals(accessor.getCommand())) {

            // STOMP 세션 속성에서 사용자 정보(SessionUser)를 가져옴
            // 이 정보는 WebSocket 핸드셰이크 시 Http 세션에서 복사되어야 함
            SessionUser sessionUser = Optional.ofNullable((SessionUser) accessor.getSessionAttributes().get("user"))
                    .orElseThrow(() -> new AccessDeniedException("WebSocket: 유효한 사용자 정보가 없습니다."));
        }

        return message;
    }
}
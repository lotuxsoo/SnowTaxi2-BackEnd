package LCK.snowTaxi2.auth;

import LCK.snowTaxi2.domain.member.Role;
import LCK.snowTaxi2.domain.member.SessionUser;
import LCK.snowTaxi2.domain.pot.TaxiPot;
import LCK.snowTaxi2.repository.TaxiPotRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

@Component // Spring이 이 클래스를 Bean으로 관리하도록 함
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

    // DB 조회를 위해 TaxiPotRepository를 주입받음
    private final TaxiPotRepository taxiPotRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Auth authAnnotation = handlerMethod.getMethodAnnotation(Auth.class);

        if (authAnnotation == null) {
            return true;
        }

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "로그인이 필요합니다.");
            return false;
        }
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");
        Role userRole = sessionUser.getRole();

        // === 여기가 핵심 수정 부분: 동적 인가 로직 ===
        // 요청 URL에서 경로 변수(Path Variable)를 추출
        Map<String, String> pathVariables = (Map<String, String>) request.getAttribute(
                HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        String potIdStr = pathVariables.get("potId");

        // potId가 존재하는 경로의 요청일 경우 (예: /pots/{potId}/join)
        if (potIdStr != null) {
            Long potId = Long.parseLong(potIdStr);

            TaxiPot taxiPot = taxiPotRepository.findById(potId)
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 택시팟입니다."));

            // 수정된 부분: taxiPot에서 creator를 가져오고, creator의 역할을 조회합니다.
            Role creatorRole = taxiPot.getCreator().getRole(); // Member 엔티티에 getRole()이 있다고 가정

            if (creatorRole != userRole) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "해당 택시팟에 참여할 권한이 없습니다.");
                return false;
            }
        }

        // potId가 없는 다른 @Auth 요청은 기존의 정적 방식으로 처리
        else {
            Role requiredRole = authAnnotation.role();
            if (requiredRole == Role.STUDENT && userRole == Role.GUEST) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "학생만 접근 가능합니다.");
                return false;
            }
        }

        // 모든 검증을 통과하면 접근 허용
        return true;
    }
}

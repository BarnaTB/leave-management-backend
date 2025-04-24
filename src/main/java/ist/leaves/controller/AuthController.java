package ist.leaves.controller;

import java.util.Map;

import ist.leaves.security.CustomOAuth2User;
import ist.leaves.security.JwtTokenProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ist.leaves.entity.Employee;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final JwtTokenProvider jwtTokenProvider;

    public AuthController(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @GetMapping("/token")
    public Map<String, String> getToken(@AuthenticationPrincipal CustomOAuth2User customOAuth2User) {
        String token = jwtTokenProvider.generateToken(customOAuth2User.getEmail());
        return Map.of("token", token);
    }

    @GetMapping("/user")
    public ResponseEntity<Map<String, Object>> getCurrentUser(
            @AuthenticationPrincipal Employee employee) {

        return ResponseEntity.ok(Map.of(
                "id", employee.getId(),
                "name", employee.getName(),
                "email", employee.getEmail(),
                "avatarUrl", employee.getAvatarUrl(),
                "role", employee.getRole().name()
        ));
    }

//    @GetMapping("/token")
//    public Map<String, String> getToken(@AuthenticationPrincipal CustomOAuth2User customOAuth2User) {
//        String token = jwtTokenProvider.generateToken(customOAuth2User.getEmail());
//        return Map.of("token", token);
//    }
}

package poecbdx23.livecodingjwt.auth;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody RegisterRequest request, HttpServletRequest httpRequest) throws Exception {
        return ResponseEntity.ok(service.register(request, httpRequest));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthRequest request, HttpServletRequest httpRequest) {
    AuthResponse authenticationResponse = service.authenticate(request, httpRequest);
    return ResponseEntity.ok(authenticationResponse);



    }
}

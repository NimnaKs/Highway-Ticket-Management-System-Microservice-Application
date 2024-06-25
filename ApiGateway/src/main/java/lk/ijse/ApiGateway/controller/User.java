package lk.ijse.ApiGateway.controller;

import lk.ijse.ApiGateway.secureAndResponse.response.JwtAuthResponse;
import lk.ijse.ApiGateway.secureAndResponse.secure.SignIn;
import lk.ijse.ApiGateway.secureAndResponse.secure.SignUp;
import lk.ijse.ApiGateway.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class User {
    private final AuthenticationService authenticationService;

    @PostMapping("/signUp")
    public ResponseEntity<JwtAuthResponse> signup(@RequestBody SignUp signup){
        return ResponseEntity.ok(authenticationService.signUp(signup));
    }

    @PostMapping("/signIn")
    public ResponseEntity<JwtAuthResponse> signIn(@RequestBody SignIn signIn){
        return ResponseEntity.ok(authenticationService.signIn(signIn));
    }

    @GetMapping("/refresh")
    public ResponseEntity<JwtAuthResponse> refresh(
            @RequestParam ("refreshToken") String refreshToken
    ){
        return ResponseEntity.ok(authenticationService.refreshToken(refreshToken));
    }

}

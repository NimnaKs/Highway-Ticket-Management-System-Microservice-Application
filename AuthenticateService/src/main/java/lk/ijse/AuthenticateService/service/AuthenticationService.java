package lk.ijse.AuthenticateService.service;

import lk.ijse.AuthenticateService.secureAndResponse.response.JwtAuthResponse;
import lk.ijse.AuthenticateService.secureAndResponse.secure.SignIn;
import lk.ijse.AuthenticateService.secureAndResponse.secure.SignUp;

public interface AuthenticationService {
    JwtAuthResponse signUp(SignUp signup);
    JwtAuthResponse signIn(SignIn signIn);
    JwtAuthResponse refreshToken(String refreshToken);
}

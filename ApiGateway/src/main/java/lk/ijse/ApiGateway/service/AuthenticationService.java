package lk.ijse.ApiGateway.service;

import lk.ijse.ApiGateway.secureAndResponse.response.JwtAuthResponse;
import lk.ijse.ApiGateway.secureAndResponse.secure.SignIn;
import lk.ijse.ApiGateway.secureAndResponse.secure.SignUp;

public interface AuthenticationService {
    JwtAuthResponse signUp(SignUp signup);
    JwtAuthResponse signIn(SignIn signIn);
    JwtAuthResponse refreshToken(String refreshToken);
}

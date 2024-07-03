package lk.ijse.userservice.service;

import lk.ijse.userservice.dto.SignIn;
import lk.ijse.userservice.dto.SignUp;

public interface UserService {
    String registerUser(SignUp signUp) throws Exception;

    void updateUser(SignUp signUp, Long id) throws Exception;

    void verifyUser(SignIn signIn) throws Exception;

    boolean isUserExists(Long id);
}

package lk.ijse.userservice.service;

import lk.ijse.userservice.dto.SignUp;

public interface UserService {
    String registerUser(SignUp signUp) throws Exception;
}

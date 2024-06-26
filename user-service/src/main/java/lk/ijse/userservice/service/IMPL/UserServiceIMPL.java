package lk.ijse.userservice.service.IMPL;

import lk.ijse.userservice.conversion.ConversionData;
import lk.ijse.userservice.entity.UserEntity;
import lk.ijse.userservice.repository.UserRepository;
import lk.ijse.userservice.service.UserService;
import lk.ijse.userservice.dto.SignUp;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceIMPL implements UserService {
    private final UserRepository userRepository;
    private final ConversionData conversionData;
    @Override
    public String registerUser(SignUp signUp) throws Exception {
        try {
            UserEntity user = userRepository.save(conversionData.convertToUserEntity(signUp));
            return "Saved UserId : " + user.getId();
        }catch (DataIntegrityViolationException exception){
            throw new Exception("User Already Exist");
        }
    }
}

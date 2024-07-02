package lk.ijse.userservice.service.IMPL;

import lk.ijse.userservice.conversion.ConversionData;
import lk.ijse.userservice.dto.SignIn;
import lk.ijse.userservice.dto.SignUp;
import lk.ijse.userservice.entity.UserEntity;
import lk.ijse.userservice.exception.InvalidCredentialException;
import lk.ijse.userservice.exception.NotFoundException;
import lk.ijse.userservice.repository.UserRepository;
import lk.ijse.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceIMPL implements UserService {
    private final UserRepository userRepository;
    private final ConversionData conversionData;

    @Override
    public String registerUser(SignUp signUp) throws Exception {
        try {
            UserEntity user = userRepository.save(conversionData.convertToUserEntity(signUp));
            return "Saved UserId : " + user.getId();
        } catch (DataIntegrityViolationException exception) {
            throw new Exception("User Already Exist");
        }
    }

    @Override
    public void updateUser(SignUp signUp, Long id) throws Exception {
        try {
            Optional<UserEntity> user = userRepository.findById(id);
            if (user.isEmpty()) throw new NotFoundException("User Not Found");
            UserEntity entity = user.get();
            entity.setName(signUp.getName());
            entity.setEmail(signUp.getEmail());
            entity.setPassword(signUp.getPassword());
            entity.setRole(signUp.getRole());
        } catch (DataIntegrityViolationException exception) {
            throw new Exception("User Already Exist");
        }
    }

    @Override
    public void verifyUser(SignIn signIn) {

        Optional<UserEntity> user = userRepository.findByEmail(signIn.getEmail());
        if (user.isEmpty()) throw new NotFoundException("User Not Found");
        UserEntity userEntity = user.get();
        if (!signIn.getPassword().equals(userEntity.getPassword()))
            throw new InvalidCredentialException("User Credential Not Valid");

    }


}

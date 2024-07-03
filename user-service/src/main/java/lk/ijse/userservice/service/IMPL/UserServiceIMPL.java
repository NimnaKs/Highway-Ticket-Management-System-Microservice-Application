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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceIMPL implements UserService {

    private final UserRepository userRepository;
    private final ConversionData conversionData;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public String registerUser(SignUp signUp) throws Exception {
        try {
            UserEntity user = conversionData.convertToUserEntity(signUp);
            user.setPassword(bCryptPasswordEncoder.encode(signUp.getPassword())); // Hash the password
            UserEntity savedUser = userRepository.save(user);
            return "Saved UserId : " + savedUser.getId();
        } catch (DataIntegrityViolationException exception) {
            throw new Exception("User Already Exists");
        }
    }

    @Override
    public void updateUser(SignUp signUp, Long id) throws Exception {
        Optional<UserEntity> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) throw new NotFoundException("User Not Found");

        UserEntity user = userOptional.get();

        // Check if the email is changing
        if (!user.getEmail().equals(signUp.getEmail())) {
            // Check if the new email is already in use
            if (userRepository.existsByEmail(signUp.getEmail())) {
                throw new Exception("Email is already in use by another user");
            }
        }

        // Update the user's information
        user.setName(signUp.getName());
        user.setEmail(signUp.getEmail());

        // Hash and update the password if it's not null or empty
        if (signUp.getPassword() != null && !signUp.getPassword().isEmpty()) {
            user.setPassword(bCryptPasswordEncoder.encode(signUp.getPassword()));
        }

        user.setRole(signUp.getRole());

        try {
            userRepository.save(user);
        } catch (DataIntegrityViolationException exception) {
            throw new Exception("User Already Exists with this email");
        }
    }

    @Override
    public void verifyUser(SignIn signIn) {
        Optional<UserEntity> userOptional = userRepository.findByEmail(signIn.getEmail());
        if (userOptional.isEmpty()) throw new NotFoundException("User Not Found");

        UserEntity userEntity = userOptional.get();
        if (!bCryptPasswordEncoder.matches(signIn.getPassword(), userEntity.getPassword())) {
            throw new InvalidCredentialException("User Credential Not Valid");
        }
    }

    @Override
    public boolean isUserExists(Long id) {
        Optional<UserEntity> userOptional = userRepository.findById(id);
        return  (userOptional.isPresent()) ;
    }
}

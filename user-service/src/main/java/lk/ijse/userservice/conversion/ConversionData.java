package lk.ijse.userservice.conversion;

import lk.ijse.userservice.dto.SignUp;
import lk.ijse.userservice.entity.UserEntity;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class ConversionData {
    final private ModelMapper modelMapper;
    public UserEntity convertToUserEntity(SignUp signUp){
        return modelMapper.map(signUp, UserEntity.class);
    }
}

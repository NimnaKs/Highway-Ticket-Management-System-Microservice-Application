package lk.ijse.AuthenticateService.repository;
import lk.ijse.AuthenticateService.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserServiceDao extends JpaRepository<UserEntity,String> {
    Optional<UserEntity> findByEmail(String email);
}

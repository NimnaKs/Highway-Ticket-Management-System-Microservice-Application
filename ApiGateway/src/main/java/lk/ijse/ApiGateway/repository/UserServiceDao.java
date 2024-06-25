package lk.ijse.ApiGateway.repository;
import lk.ijse.ApiGateway.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserServiceDao extends JpaRepository<UserEntity,String> {
    Optional<UserEntity> findByEmail(String email);
}

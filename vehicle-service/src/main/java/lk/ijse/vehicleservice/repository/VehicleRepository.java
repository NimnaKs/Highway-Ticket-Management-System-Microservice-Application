package lk.ijse.vehicleservice.repository;

import lk.ijse.vehicleservice.entity.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends JpaRepository<VehicleEntity,Long> {
}

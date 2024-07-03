package lk.ijse.vehicleservice.service.IMPL;

import lk.ijse.vehicleservice.client.UserServiceClient;
import lk.ijse.vehicleservice.conversion.ConversionData;
import lk.ijse.vehicleservice.dto.VehicleReqRes;
import lk.ijse.vehicleservice.entity.VehicleEntity;
import lk.ijse.vehicleservice.exception.NotFoundException;
import lk.ijse.vehicleservice.repository.VehicleRepository;
import lk.ijse.vehicleservice.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class VehicleServiceIMPL implements VehicleService {

    private final UserServiceClient userServiceClient;
    private final VehicleRepository vehicleRepository;
    private final ConversionData conversionData;

    @Override
    public String registerVehicle(VehicleReqRes vehicleResponse) throws Exception {
        try {
            if (!userServiceClient.isUserExists(vehicleResponse.getUserId()))
                throw new NotFoundException("User Not Exists");
            VehicleEntity vehicleEntity = vehicleRepository.save(conversionData.convertToVehicleEntity(vehicleResponse));
            return "Vehicle Registered Successfully with ID: " + vehicleEntity.getId();
        } catch (DataIntegrityViolationException exception) {
            throw new Exception("User Already Exists");
        }
    }

    @Override
    public void updateVehicle(Long id,VehicleReqRes vehicleResponse) throws Exception {
        try {
            if (!userServiceClient.isUserExists(vehicleResponse.getUserId()))
                throw new NotFoundException("User Not Exists");
            Optional<VehicleEntity> vehicleEntity = vehicleRepository.findById(id);
            if (vehicleEntity.isEmpty())throw new NotFoundException("Vehicle Not Exists");
            VehicleEntity vehicle = vehicleEntity.get();
            vehicle.setLicensePlate(vehicleResponse.getLicensePlate());
            vehicle.setModel(vehicleResponse.getModel());
            vehicle.setBrand(vehicleResponse.getBrand());
            vehicle.setUserId(vehicleResponse.getUserId());
            vehicleRepository.save(vehicle);
        } catch (DataIntegrityViolationException exception) {
            throw new Exception("User Already Exists");
        }
    }

    @Override
    public List<VehicleReqRes> getAllVehicle() {
        return conversionData.convertToVehicleReqResList(vehicleRepository.findAll());
    }
}

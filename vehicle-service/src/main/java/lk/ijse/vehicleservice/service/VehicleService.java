package lk.ijse.vehicleservice.service;

import lk.ijse.vehicleservice.dto.VehicleReqRes;

import java.util.List;

public interface VehicleService {
    String registerVehicle(VehicleReqRes vehicleResponse) throws Exception;
    void updateVehicle(Long id,VehicleReqRes vehicleResponse) throws Exception;
    List<VehicleReqRes> getAllVehicle();
    boolean isVehicleExists(Long id);
}

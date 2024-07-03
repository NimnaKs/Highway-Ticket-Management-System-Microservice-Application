package lk.ijse.vehicleservice.conversion;

import lk.ijse.vehicleservice.dto.VehicleReqRes;
import lk.ijse.vehicleservice.entity.VehicleEntity;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class ConversionData {
    final private ModelMapper modelMapper;
    public VehicleEntity convertToVehicleEntity(VehicleReqRes vehicleResponse){
        return modelMapper.map(vehicleResponse, VehicleEntity.class);
    }
    public List<VehicleReqRes> convertToVehicleReqResList(List<VehicleEntity> vehicleEntities){
        return modelMapper.map(vehicleEntities, List.class);
    }
}

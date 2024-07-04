package lk.ijse.vehicleservice.controller;

import lk.ijse.vehicleservice.dto.VehicleReqRes;
import lk.ijse.vehicleservice.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/vehicle")
@RequiredArgsConstructor
public class Vehicle {

    private final VehicleService vehicleService;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registerVehicle(@Validated @RequestBody VehicleReqRes vehicleResponse, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                    body(bindingResult.getFieldErrors().get(0).getDefaultMessage());
        }
        try {
            return ResponseEntity.ok(vehicleService.registerVehicle(vehicleResponse));
        } catch (DataIntegrityViolationException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                    body(exception.getMessage());
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
                    body("Internal server error | Vehicle Details saved Unsuccessfully.\nMore Reason\n"
                            +exception.getMessage());
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping(value = "/{id}",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateVehicle(@Validated @RequestBody VehicleReqRes vehicleResponse,
                                           BindingResult bindingResult,
                                           @PathVariable ("id") Long id) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                    body(bindingResult.getFieldErrors().get(0).getDefaultMessage());
        }
        try {
            vehicleService.updateVehicle(id,vehicleResponse);
            return ResponseEntity.ok("Vehicle Details Updated Successfully.");
        } catch (DataIntegrityViolationException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                    body(exception.getMessage());
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
                    body("Internal server error | Vehicle Details Updated Unsuccessfully.\nMore Reason\n"
                            +exception.getMessage());
        }
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<?> getAllVehicles(){
        try {
            return ResponseEntity.ok(vehicleService.getAllVehicle());
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
                    body("Internal server error | Vehicle Details fetched Unsuccessfully.\nMore Reason\n"
                            +exception.getMessage());
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> isVehicleExists(@PathVariable ("id") Long id){
        try {
            return ResponseEntity.ok(vehicleService.isVehicleExists(id));
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
                    body("Internal server error | Vehicle Details fetched Unsuccessfully.\nMore Reason\n"
                            +exception.getMessage());
        }
    }
}

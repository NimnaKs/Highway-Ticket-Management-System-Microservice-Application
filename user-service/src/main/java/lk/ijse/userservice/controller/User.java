package lk.ijse.userservice.controller;

import lk.ijse.userservice.dto.SignIn;
import lk.ijse.userservice.exception.InvalidCredentialException;
import lk.ijse.userservice.exception.NotFoundException;
import lk.ijse.userservice.service.UserService;
import lk.ijse.userservice.dto.SignUp;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class User {
    private final UserService userService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signUp")
    public ResponseEntity<?> registerUser(@Validated @RequestBody SignUp signUp,BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                    body(bindingResult.getFieldErrors().get(0).getDefaultMessage());
        }
        try {
            return ResponseEntity.ok(userService.registerUser(signUp));
        } catch (DataIntegrityViolationException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                    body(exception.getMessage());
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
                    body("Internal server error | User Details Updated Unsuccessfully.\nMore Reason\n"
                            +exception.getMessage());
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping(value = "/{id}",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateUser(@Validated @RequestBody SignUp signUp,
                                                 BindingResult bindingResult,
                                                 @PathVariable ("id") Long id) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                    body(bindingResult.getFieldErrors().get(0).getDefaultMessage());
        }

        try {
            userService.updateUser(signUp,id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("User Details Updated Successfully.");
        } catch (NotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        } catch (DataIntegrityViolationException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                    body(exception.getMessage());
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
                    body("Internal server error | User Details Updated Unsuccessfully.\nMore Reason\n"
                            +exception.getMessage());
        }

    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signIn")
    public ResponseEntity<?> verifyUser(@Validated @RequestBody SignIn signIn,
                                            BindingResult bindingResult
                                        ){
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                    body(bindingResult.getFieldErrors().get(0).getDefaultMessage());
        }

        try {
            userService.verifyUser(signIn);
            return ResponseEntity.status(HttpStatus.CREATED).body("User Details Verify Successfully.");
        } catch (NotFoundException | InvalidCredentialException exception) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exception.getMessage());
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
                    body("Internal server error | User Details Updated Unsuccessfully.\nMore Reason\n"
                            +exception.getMessage());
        }

    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<?> isUserExists(@PathVariable ("id") Long id){
        try {
            return ResponseEntity.ok(userService.isUserExists(id));
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
                    body("Internal server error | User Details fetched Unsuccessfully.\nMore Reason\n"
                            +exception.getMessage());
        }
    }

    

}

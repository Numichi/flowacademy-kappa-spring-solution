package hu.flowacademy.band.controllers.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import hu.flowacademy.band.validation.NewUserEmail;
import hu.flowacademy.band.validation.Password;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RegistrationRequest {

    @Email
    @NewUserEmail
    @NotNull
    private String email;

    @Password(minUpper = 2, minDigit = 2, minLower = 6)
    @NotNull
    private String password;
}

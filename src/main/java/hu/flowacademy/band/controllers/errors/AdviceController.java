package hu.flowacademy.band.controllers.errors;

import hu.flowacademy.band.controllers.requests.LoginRequest;
import hu.flowacademy.band.exceptions.WrongEmailOrPasswordException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;
import java.util.Optional;

@RestControllerAdvice
public class AdviceController {

    /**
     * Jellemzően az alábbi dobálja, vagy mi manuálisan.
     * @see Optional#orElseThrow()
     */
    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void notFound() {}

    /**
     * Login dobhatja.
     * @see hu.flowacademy.band.controllers.http.AuthController#login(LoginRequest)
     */
    @ExceptionHandler(WrongEmailOrPasswordException.class)
    @ResponseStatus(HttpStatus.OK) // 200-al válaszolok vissza.
    public String failedLogin() {
        return "Hibás email vagy jelszó!";
    }
}

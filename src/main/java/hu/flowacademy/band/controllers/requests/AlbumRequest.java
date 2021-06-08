package hu.flowacademy.band.controllers.requests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import hu.flowacademy.band.validation.Date;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AlbumRequest {

    @NotNull
    @NotBlank
    private String name;

    /**
     * Persze megadható ide típusnak a "LocalDate"-t állítod be, de
     * akkor Jackson oldalon kell vagy be kell állítani a parsolást.
     * https://reflectoring.io/configuring-localdate-serialization-spring-boot/
     *
     * Én most írtam egy saját validatort és kézzel parsolom a service-ben.
     * Ha tudni akarod, miért nem írtam ide @NotNull-t nézd meg a validatort.
     */
    @Date
    private String release;
}

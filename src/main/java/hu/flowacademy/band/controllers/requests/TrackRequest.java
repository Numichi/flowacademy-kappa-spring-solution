package hu.flowacademy.band.controllers.requests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import hu.flowacademy.band.database.models.Album;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TrackRequest {

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    private Integer length;

    private Long price;
}

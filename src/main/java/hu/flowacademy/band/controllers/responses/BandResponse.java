package hu.flowacademy.band.controllers.responses;

import hu.flowacademy.band.enums.Genre;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BandResponse {
    private Integer uuid;
    private String name;
    private Genre genre;
}

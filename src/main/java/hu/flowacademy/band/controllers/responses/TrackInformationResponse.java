package hu.flowacademy.band.controllers.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TrackInformationResponse {
    private Integer id;
    private String name;
    private Integer length;
}

package hu.flowacademy.band.controllers.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlbumInformationResponse {
    private Integer id;
    private String name;
    private Integer seconds;
}

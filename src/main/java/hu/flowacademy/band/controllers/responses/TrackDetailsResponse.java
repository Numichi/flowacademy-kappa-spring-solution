package hu.flowacademy.band.controllers.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * Lehetett volna örököltetni is a TrackInformationResponse-ból is, de mi van akkor ha az az igény, hogy az változzon? (költői)
 */
@Data
@AllArgsConstructor
public class TrackDetailsResponse {
    private Integer id;
    private String name;
    private Integer length;
    private Long license;
    private List<String> radios;
}

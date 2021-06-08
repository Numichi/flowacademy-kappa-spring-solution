package hu.flowacademy.band.controllers.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProviderResponse {
    private int id;
    private String name;
}

package vlad.erofeev.layerservice.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {
    private String message;
    private long timestamp;

    public ErrorResponse(String message) {
        this.message = message;
        timestamp = System.currentTimeMillis();
    }
}

package guru.springframework.api.v1.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CustomerDTO {
    @Schema(description = "Customer Firstname")
    private String firstName;

    @Schema(description = "Customer Lastname")
    private String lastName;

    @Schema(description = "Customer URL")
    private String customerUrl;
}

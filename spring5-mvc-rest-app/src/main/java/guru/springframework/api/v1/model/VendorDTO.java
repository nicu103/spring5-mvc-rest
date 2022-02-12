package guru.springframework.api.v1.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class VendorDTO {
    @Schema(description = "Vendor Name")
    String name;

    @Schema(description = "Vendor URL")
    String vendorUrl;
}

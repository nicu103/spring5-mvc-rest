package guru.springfamework.api.v1.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CategoryDTO {
    @Schema(description = "Category ID")
    private Long id;

    @Schema(description = "Category Name")
    private String name;
}

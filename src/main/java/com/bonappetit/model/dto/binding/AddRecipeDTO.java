package com.bonappetit.model.dto.binding;

import com.bonappetit.model.enums.CategoryNameEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AddRecipeDTO {

    @NotBlank(message = "{not.blank}")
    @Size(min = 2, max = 40, message = "{name.length}")
    private String name;

    @NotBlank(message = "{not.blank}")
    @Size(min = 2, max = 40, message = "{ingredients.length}")
    private String ingredients;

    @NotNull(message = "{category_not_null}")
    private CategoryNameEnum category;
}

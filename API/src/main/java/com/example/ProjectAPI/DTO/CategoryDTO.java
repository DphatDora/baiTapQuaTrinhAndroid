package com.example.ProjectAPI.DTO;

import com.example.ProjectAPI.model.CategoryType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CategoryDTO {
    private Long id;

    private CategoryType type;

    private String imgCategory;
}

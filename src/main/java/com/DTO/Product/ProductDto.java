package com.DTO.Product;

import com.model.Product;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductDto {
    private Integer id;
    private @NotNull String name;
    private @NotNull String imageURL;
    private @NotNull int quantity;
    private @NotNull double price;
    private @NotNull String description;
    private @NotNull Integer categoryId;

    public ProductDto(Product product) {
        this.setId(product.getId());
        this.setName(product.getName());
        this.setImageURL(product.getImageURL());
        this.setDescription(product.getDescription());
        this.setPrice(product.getPrice());
        this.setPrice(product.getQuantity());
        this.setCategoryId(product.getCategory().getId());
    }

    // this one stays manual partial constructor - without ID
    public ProductDto(@NotNull String name, @NotNull String imageURL,
                      @NotNull double price, @NotNull String description,
                      @NotNull Integer categoryId, @NotNull int quantity) {
        this.name = name;
        this.imageURL = imageURL;
        this.price = price;
        this.description = description;
        this.quantity = quantity;
        this.categoryId = categoryId;
    }
}

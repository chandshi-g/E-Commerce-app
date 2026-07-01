package com.Controller;

import com.Config.ApiResponse;
import com.DTO.Product.ProductDto;
import com.Service.CategoryService;
import com.Service.ProductService;
import com.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createProduct(@RequestBody ProductDto productDto){
        Optional<Category> optCategory = categoryService.readId(productDto.getCategoryId());
        if(!optCategory.isPresent()){
            return new ResponseEntity<>(new ApiResponse(false,"category is invalid"), HttpStatus.CONFLICT);
        }
        Category category = optCategory.get();
        productService.addProduct(productDto,category);
        return new ResponseEntity<>(new ApiResponse(true,"product added"),HttpStatus.CREATED);
    }

    @GetMapping("/getAllProduct")
    public ResponseEntity<List<ProductDto>> allProduct(){
        return new ResponseEntity<>(productService.allProduct(),HttpStatus.ACCEPTED);
    }

    @GetMapping("/getAllProductOf{Category_id}")
    public ResponseEntity<List<ProductDto>> allProductOfCategory (@PathVariable Integer Category_id){
        return new ResponseEntity<>(productService.getList(Category_id),HttpStatus.ACCEPTED);
    }

    @PutMapping("/update/{product_id}")
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable Integer product_id,@RequestBody ProductDto productDto){
        Optional<Category> optCategory = categoryService.readId(productDto.getCategoryId());
        if(!optCategory.isPresent()){
            return new ResponseEntity<>(new ApiResponse(false,"product not found"),HttpStatus.CONFLICT);
        }

        Category category = optCategory.get();
        productService.update(category,product_id,productDto);
        return new ResponseEntity<>(new ApiResponse(true, "Product has been updated"), HttpStatus.OK);
    }


}

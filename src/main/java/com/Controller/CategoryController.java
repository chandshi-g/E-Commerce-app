package com.Controller;

import com.Config.ApiResponse;
import com.Service.CategoryService;
import com.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createCategory(@RequestBody Category category){
        if(Objects.nonNull(categoryService.readCategory(category.getName()))){//if not null(category exist) shows conflict
            return new ResponseEntity<>(new ApiResponse(false,"category already exist"), HttpStatus.CONFLICT);
        }
        categoryService.createCategory(category);
        return new ResponseEntity<>(new ApiResponse(true,"created the category"), HttpStatus.CREATED);
    }

    @GetMapping("/allCategory")
    public ResponseEntity<List<Category>> getAllCategory(){
        List<Category> list = categoryService.listCategory();
        return new ResponseEntity<>(list,HttpStatus.OK);
    }

    @PutMapping("/updateCategory/{id}")
    public ResponseEntity<ApiResponse> update(@PathVariable Integer id , @RequestBody Category category){
        if(categoryService.readId(id).isPresent()){
            categoryService.updateCategory(id,category);
            return new ResponseEntity<>(new ApiResponse(true, "updated the category"), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ApiResponse(false,"not exist"), HttpStatus.NOT_FOUND);
    }

}

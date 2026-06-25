package com.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "category")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "category_name")
    @NotBlank private String name;
    @NotBlank private String description;
    @NotBlank private String imageURL;

    public Category(String name, String description){//optional partial constructor when imageurl is not needed
        this.name = name;
        this.description = description;
    }

}

//    public Category(){//---empty constructor for JPA (creates object)
//    }
//    public Category(String name, String description){//--optional partial constructor when imageurl is not needed
//        this.name = name;
//        this.description = description;
//    }
//    public Category(String name, String description, String imageURL){//--fully parametrised constructor
//        this.name = name;
//        this.description = description;
//        this.imageURL = imageURL;
//    }

//    @Override
//    public String toString(){//---@Data will handle , its for getting readable object instance for debugging
//        return "Category{id" + ",name = " + name + ",description = " + description + ",imageURL = " + imageURL + "}";
//    }

//    public Integer getId(){
//        return id;
//    }
//    public void setId(Integer id){
//        this.id = id;
//    }
//
//    public String getName(){
//        return name;
//    }
//    public void setName(String name){
//        this.name = name;
//    }
//
//    public String getDescription(){
//        return description;
//    }
//    public void setDescription(String description){
//        this.description = description;
//    }
//
//    public String getImageURL(){
//        return imageURL;
//    }
//    public void setImageURL(String imageURL){
//        this.imageURL = imageURL;
//    }

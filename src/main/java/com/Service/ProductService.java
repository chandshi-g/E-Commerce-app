package com.Service;

import com.DTO.Product.ProductDto;
import com.Repository.CategoryRepository;
import com.Repository.ProductRepository;
import com.model.Category;
import com.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    public void addProduct(ProductDto productDto, Category category) {
        Product product = getProductFromDto(productDto,category);
        productRepository.save(product);
    }

    public Product getProductFromDto(ProductDto productDto, Category category) {
        Product product = new Product();
        product.setCategory(category);
        product.setDescription(productDto.getDescription());
        product.setImageURL(productDto.getImageURL());
        product.setPrice(productDto.getPrice());
        product.setName(productDto.getName());
        product.setQuantity(productDto.getQuantity());
        return product;
    }

    public List<ProductDto> allProduct() {
        List<Product> pro = productRepository.findAll();
        List<ProductDto> aLLPro  = new ArrayList<>();
        for(Product p : pro){
            aLLPro.add(new ProductDto(p));
        }
        return aLLPro;
    }

    public List<ProductDto> getList(Integer category_id) {
        List<Product> list = productRepository.findByCategoryId(category_id);
        //convert product to productDto
        List<ProductDto> aLLPro  = new ArrayList<>();
        for(Product p : list){
            aLLPro.add(new ProductDto(p));
        }
        return aLLPro;
    }

    public void update(Category category,Integer product_id,ProductDto productDto){
        Product product = getProductFromDto(productDto,category);
        product.setId(product_id);
        productRepository.save(product);
    }

}

package com.Controller;

import com.Config.ApiResponse;
import com.DTO.Product.ProductDto;
import com.Repository.ProductRepository;
import com.Service.AuthenticationService;
import com.Service.WishListService;
import com.exceptions.AuthenticationFailException;
import com.model.Product;
import com.model.User;
import com.model.WishList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/wishlist")
public class WishListController {
    @Autowired
    WishListService wishListService;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    private ProductRepository productRepository;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addWishList(@RequestBody ProductDto productDto, @RequestParam("token") String token) throws AuthenticationFailException{
        authenticationService.authenticate(token);
        User user = authenticationService.getUser(token);
        Product product = productRepository.getById(productDto.getId());
        WishList wishList = new WishList(user, product);
        wishListService.createWishList(wishList);
        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Added to wishlist"), HttpStatus.CREATED);
    }

    @GetMapping("/{token}")
    public ResponseEntity<List<ProductDto>> getWishList(@PathVariable("token") String token) throws AuthenticationFailException {
        // first authenticate if the token is valid
        authenticationService.authenticate(token);
        // then fetch the user linked to the token
        User user = authenticationService.getUser(token);
        // first retrieve the wishlist items
        List<WishList> wishLists = wishListService.readWishList(user);
        List<ProductDto> products = new ArrayList<>();
        for (WishList wishList : wishLists) {
            // change each product to product DTO
            products.add(new ProductDto(wishList.getProduct()));
        }
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

}

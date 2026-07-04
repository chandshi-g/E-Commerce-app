package com.Controller;

import com.Config.ApiResponse;
import com.DTO.AddToCartDto;
import com.DTO.CartDto;
import com.Service.AuthenticationService;
import com.Service.CartService;
import com.Service.ProductService;
import com.exceptions.AuthenticationFailException;
import com.exceptions.CartItemNotExistException;
import com.exceptions.ProductNotExistException;
import com.model.Product;
import com.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    CartService cartService;

    @Autowired
    ProductService productService;

    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addToCart(@RequestBody AddToCartDto addToCartDto, @RequestParam("token") String token)throws
        AuthenticationFailException, ProductNotExistException, AuthenticationFailException{
        User user = authenticationService.getUser(token);
        Product product = productService.getProductById(addToCartDto.getProductId());
        cartService.addToCart(addToCartDto, product, user);

        return new ResponseEntity<>(new ApiResponse(true, "Added to cart"), HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<CartDto> getCartItems(@RequestParam("token") String token) throws AuthenticationFailException {
        authenticationService.authenticate(token);
        User user = authenticationService.getUser(token);
        CartDto cartDto = cartService.listCartItems(user);
        return new ResponseEntity<CartDto>(cartDto,HttpStatus.OK);
    }

    @DeleteMapping("/delete/{cartItemId}")
    public ResponseEntity<ApiResponse> deleteCartItem(@PathVariable int cartItemId,@RequestParam String token) throws AuthenticationFailException, CartItemNotExistException {
        authenticationService.authenticate(token);
        User user = authenticationService.getUser(token);
        cartService.deletecartItem(cartItemId , user);
        return new ResponseEntity<>(new ApiResponse(true,"item has been removed"),HttpStatus.OK);
    }

}

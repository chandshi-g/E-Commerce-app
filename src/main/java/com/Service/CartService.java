package com.Service;

import com.DTO.AddToCartDto;
import com.DTO.CartDto;
import com.DTO.CartItemDto;
import com.Repository.CartRepository;
import com.exceptions.CartItemNotExistException;
import com.model.Cart;
import com.model.Product;
import com.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    @Autowired
    CartRepository cartRepository;


    public void addToCart(AddToCartDto addToCartDto, Product product, User user) {
        Cart cart = new Cart(product, addToCartDto.getQuantity(),user);
        cartRepository.save(cart);
    }

    public CartDto listCartItems(User user) {
        List<Cart> cartList = cartRepository.findAllByUser(user);

        List<CartItemDto> cartItems = new ArrayList<>();
        for (Cart cart:cartList){
            CartItemDto cartItemDto = new CartItemDto(cart);
            cartItems.add(cartItemDto);
        }
        double totalCost = 0;
        for (CartItemDto cartItemDto :cartItems){
            totalCost += cartItemDto.getProduct().getPrice() * cartItemDto.getQuantity();
        }
        return new CartDto(cartItems,totalCost);
    }

    public void deletecartItem(int cartItemId, User user) {
        Optional<Cart> cartId = cartRepository.findById(cartItemId);
        if(!cartId.isPresent()){
            throw new CartItemNotExistException("cartItemId not valid");
        }
        // next check if the cartItem belongs to the user
        Cart cart = cartId.get();
        if (cart.getUser() != user) {
            throw new CartItemNotExistException("cart item does not belong to user");
        }
        cartRepository.deleteById(cartItemId);
    }
}
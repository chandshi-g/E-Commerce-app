package com.Controller;

import com.Config.ApiResponse;
import com.DTO.CheckoutItemDto;
import com.DTO.Users.StripeResponseDto;
import com.Service.AuthenticationService;
import com.Service.OrderService;
import com.exceptions.AuthenticationFailException;
import com.exceptions.OrderNotFoundException;
import com.model.Order;
import com.model.User;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/checkout")
    public ResponseEntity<StripeResponseDto> checkoutList(@RequestBody List<CheckoutItemDto> checkoutItemDtoList) throws StripeException {//we r not using token here means authorization but we shouldauthenticate the checkout request too
        // create the stripe session
        Session session = orderService.createSession(checkoutItemDtoList);
        StripeResponseDto stripeResponse = new StripeResponseDto(session.getId());
        return new ResponseEntity<StripeResponseDto>(stripeResponse, HttpStatus.OK);
    }

    // place order after checkout
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> placeOrder(@RequestParam("token") String token, @RequestParam("sessionId") String sessionId)
            throws AuthenticationFailException {
        authenticationService.authenticate(token);// validate token
        User user = authenticationService.getUser(token);// retrieve user
        orderService.placeOrder(user, sessionId);// place the order
        return new ResponseEntity<>(new ApiResponse(true, "Order has been placed"), HttpStatus.CREATED);
    }


    @GetMapping("/")
    public ResponseEntity<List<Order>> getAllOrders(@RequestParam("token") String token) throws AuthenticationFailException {
        authenticationService.authenticate(token);
        User user = authenticationService.getUser(token);
        // get orders
        List<Order> orderDtoList = orderService.listOrders(user);
        return new ResponseEntity<>(orderDtoList, HttpStatus.OK);
    }

    // get order items for an order
    @GetMapping("/{id}")
    public ResponseEntity<Object> getOrderById(@PathVariable("id") Integer id, @RequestParam("token") String token) throws AuthenticationFailException, OrderNotFoundException {
            authenticationService.authenticate(token);
            User user = authenticationService.getUser(token);
            Order order = orderService.getOrder(id, user);
            return new ResponseEntity<>(order, HttpStatus.OK);
    }


}

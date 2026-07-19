package com.Service;

import com.DTO.CartDto;
import com.DTO.CartItemDto;
import com.DTO.CheckoutItemDto;

import com.Repository.OrderItemsRepository;
import com.Repository.OrderRepository;
import com.exceptions.OrderNotFoundException;
import com.model.Order;
import com.model.OrderItem;
import com.model.User;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;

import com.stripe.param.checkout.SessionCreateParams;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderService {
    @Autowired
    private CartService cartService;
    @Autowired
    private OrderItemsRepository orderItemsRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Value("${BASE_URL}")
    private String baseURL;

    @Value("${STRIPE_SECRET_KEY}")
    private String apiKey;

    // create total price and send productname as input
    SessionCreateParams.LineItem.PriceData createPriceData(CheckoutItemDto checkoutItemDto) {
        return SessionCreateParams.LineItem.PriceData.builder()
                .setCurrency("usd")
                .setUnitAmount( ((long) checkoutItemDto.getPrice()) * 100)
                .setProductData(
                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                .setName(checkoutItemDto.getProductName())
                                .build())
                .build();
    }

    // build each product in the stripe checkout page
    SessionCreateParams.LineItem createSessionLineItem(CheckoutItemDto checkoutItemDto) {
        return SessionCreateParams.LineItem.builder()
                // set price for each product
                .setPriceData(createPriceData(checkoutItemDto))
                // set quantity for each product
                .setQuantity(Long.parseLong(String.valueOf(checkoutItemDto.getQuantity())))
                .build();
    }

    // create session from list of checkout items
    public Session createSession(List<CheckoutItemDto> checkoutItemDtoList) throws StripeException {
        System.out.println("========== STRIPE DEBUG START ==========");

        // supply success and failure url for stripe
        String successURL = baseURL + "payment/success";
        String failedURL = baseURL + "payment/failed";

        System.out.println("Base URL      : " + baseURL);
        System.out.println("Success.vue URL   : " + successURL);
        System.out.println("Cancel URL    : " + failedURL);

        // Secret Key
        System.out.println("Stripe Key Loaded : " + (apiKey != null));
        System.out.println("Stripe Key Prefix : " +
                (apiKey != null ? apiKey.substring(0, Math.min(10, apiKey.length())) : "NULL"));

        // set the private key
        Stripe.apiKey = apiKey;

        List<SessionCreateParams.LineItem> sessionItemsList = new ArrayList<>();

        // for each product compute SessionCreateParams.LineItem
        for (CheckoutItemDto checkoutItemDto : checkoutItemDtoList) {
            sessionItemsList.add(createSessionLineItem(checkoutItemDto));
        }



        // build the session param
        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setCancelUrl(failedURL)
                .addAllLineItem(sessionItemsList)
                .setSuccessUrl(successURL)
                .build();

        System.out.println("Session Params Built Successfully");
        System.out.println("Calling Stripe API...");

       // return Session.create(params);
        try {

            Session session = Session.create(params);

            System.out.println("Stripe Session Created Successfully!");
            System.out.println("Session ID : " + session.getId());

            return session;

        } catch (Exception e) {

            System.out.println("========== STRIPE ERROR ==========");
            e.printStackTrace();
            throw e;
        }
    }

    public void placeOrder(User user, String sessionId) {
        // first let get cart items for the user
        CartDto cartDto = cartService.listCartItems(user);

        List<CartItemDto> cartItemDtoList = cartDto.getcartItems();

        // create the order and save it
        Order newOrder = new Order();
        newOrder.setCreatedDate(new Date());
        newOrder.setSessionId(sessionId);
        newOrder.setUser(user);
        newOrder.setTotalPrice(cartDto.getTotalCost());
        orderRepository.save(newOrder);


        for (CartItemDto cartItemDto : cartItemDtoList) {
            // create orderItem and save each one
            OrderItem orderItem = new OrderItem();
            orderItem.setCreatedDate(new Date());
            orderItem.setPrice((int)cartItemDto.getProduct().getPrice());
            orderItem.setProduct(cartItemDto.getProduct());
            orderItem.setQuantity(cartItemDto.getQuantity());
            orderItem.setOrder(newOrder);
            // add to order item list
            orderItemsRepository.save(orderItem);
        }

        cartService.deleteUserCartItems(user);//we placed the card oder now we don't need it
    }

    public List<Order> listOrders(User user) {
        return orderRepository.findAllByUserOrderByCreatedDateDesc(user);
    }

    public Order getOrder(Integer orderId, User user) throws OrderNotFoundException {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if(optionalOrder.isEmpty()){
            throw new OrderNotFoundException("order is not valid");
        }
        Order order = optionalOrder.get();
        if(order.getUser() != user) {
            throw  new OrderNotFoundException("order does not belong to user");
        }
        return  order;
    }
}

package com.Service;

import com.Repository.WishListRepository;
import com.model.User;
import com.model.WishList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishListService {
    @Autowired
    private WishListRepository wishListRepository;

    public void createWishList(WishList wishList){
        wishListRepository.save(wishList);
    }

    public List<WishList> readWishList(User user){
        return wishListRepository.findByUser(user);
    }
}

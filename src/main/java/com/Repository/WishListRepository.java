package com.Repository;

import com.model.User;
import com.model.WishList;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishListRepository extends JpaRepository<WishList,Integer> {
    List<WishList> findByUser(User user);
}

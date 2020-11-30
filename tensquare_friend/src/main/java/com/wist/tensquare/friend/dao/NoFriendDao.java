package com.wist.tensquare.friend.dao;

import com.wist.tensquare.friend.entity.Friend;
import com.wist.tensquare.friend.entity.Nofriend;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author simptx
 */
public interface NoFriendDao extends JpaRepository<Nofriend, String>{
    Nofriend findByUseridAndFriendid(String userId, String friendId);
}

package com.wist.tensquare.friend.dao;

import com.wist.tensquare.friend.entity.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @author simptx
 */
public interface FriendDao extends JpaRepository<Friend, String>{
    Friend findByUseridAndFriendid(String userId, String friendId);

    @Modifying
    @Query("update Friend set islike = ?1 where userid = ?2 and friendid = ?3")
    void updateIslike(String islike, String userId, String friendId);

    @Modifying
    @Query("delete from Friend where  userid = ?1 and friendid = ?2")
    void deleteFriend(String userId, String friendId);
}

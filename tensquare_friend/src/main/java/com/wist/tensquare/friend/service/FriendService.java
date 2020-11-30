package com.wist.tensquare.friend.service;

import com.wist.tensquare.friend.dao.FriendDao;
import com.wist.tensquare.friend.dao.NoFriendDao;
import com.wist.tensquare.friend.entity.Friend;
import com.wist.tensquare.friend.entity.Nofriend;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class FriendService {
    @Resource
    private FriendDao friendDao;
    @Resource
    private NoFriendDao noFriendDao;
    public int addFriend(String userId, String friendId, String type) {
        Friend friend = friendDao.findByUseridAndFriendid(userId, friendId);
        if (friend!=null){
            return 0;
        }
        //直接添加好友，状态为零
        friend = new Friend();
        friend.setUserid(userId);
        friend.setFriendid(friendId);
        friend.setIslike("0");
        friendDao.save(friend);
        friend = friendDao.findByUseridAndFriendid(friendId, userId);
        if (friend!=null){
            friendDao.updateIslike("1",userId,friendId);
            friendDao.updateIslike("1",friendId,userId);
        }
        return 1;
    }

    public int addNofriend(String userId, String friendId) {
        Nofriend nofriend = noFriendDao.findByUseridAndFriendid(userId, friendId);
        if (nofriend!=null) {
            return 0;
        }
        nofriend=new Nofriend();
        nofriend.setUserid(userId);
        nofriend.setFriendid(friendId);
        noFriendDao.save(nofriend);
        return 1;
    }

    public void deleteFriend(String userId, String friendId) {
        friendDao.deleteFriend(userId,friendId);
        friendDao.updateIslike("0",friendId,userId);
        Nofriend nofriend = new Nofriend();
        nofriend.setUserid(userId);
        nofriend.setFriendid(friendId);
        noFriendDao.save(nofriend);
    }
}

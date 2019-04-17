package com.tacuso.buyer.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.tacuso.buyer.entity.ClothingInfo;
import com.tacuso.buyer.entity.Group;
import com.tacuso.buyer.entity.User;
import com.tacuso.buyer.vo.PageData;
import com.tacuso.buyer.vo.PayOrderVo;

public interface UserService extends IService<User> {

    User getUserByUid(Integer uid);
    
    User getUserByUno(String uno);

    Integer setUserToMember(Integer uid);

	Map<String, String> getClothingInfoByUid(Integer uid);

	void updateClothingInfo(ClothingInfo info);
	
    Integer clearUserByUid(Integer uid);

    Integer setUserQuestionFinish(Integer uid);
    
    Integer setUserCouponFinish(Integer uid);

	Group getCurrentyRegisterGroup();

	Integer setUserGroup(Integer uid, Integer group_id);

	Group getNextGroupByDeliveryTime(String delivery_time);

	LocalDate getNextBoxDate(Integer uid);

	Group getCurrentyGroup(Integer uid);
	
	List<User> findMemberJoinUserList();
	
	List<User> findMemberInfoCompleteList();
	
    void updateUserData(Integer uid);

    PageData getIndexUser(Integer uid);
    
    String getHeadImg(Integer uid);
    
    PageData validateInviteCode(String inviteCode);
  
    PageData getUserByShopCode(String shopCode,Integer uid);
}

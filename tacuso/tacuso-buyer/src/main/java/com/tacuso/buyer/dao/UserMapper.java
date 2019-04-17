package com.tacuso.buyer.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.tacuso.buyer.common.SuperMapper;
import com.tacuso.buyer.entity.ClothingInfo;
import com.tacuso.buyer.entity.Group;
import com.tacuso.buyer.entity.User;
import com.tacuso.buyer.vo.PageData;

public interface UserMapper extends SuperMapper<User> {

    /**
     * 用户注册
     * @param uid
     * @param bindphone
     * @param area
     * @return
     */
    public Integer registerUser(@Param("uid") Integer uid, @Param("bindphone") String bindphone, @Param("area") String area, @Param("inviteCode") String inviteCode);

    public Integer createUser(@Param("user") User user);

    public User getUserByBindphone(@Param("bindphone") String bindphone);

    public User getUserByUid(@Param("uid") Integer uid);

    public User userLogin(@Param("bindphone") String bindphone);

    public Integer updateMember(@Param("uid") Integer uid);

	public Map<String,String> getUserClothingInfoByUid(@Param("uid") Integer uid);

	public Map<String, String> getClothingInfoByUid(@Param("uid") Integer uid);

	public void updateClothingInfo(@Param("info") ClothingInfo info);

    public Integer changeBindphone(@Param("uid") Integer uid , @Param("bindphone") String bindphone);

    public Integer clearUserByUid(@Param("uid") Integer uid);

    public Integer updateQuestionFinish(@Param("uid") Integer uid);

    public Integer updateCouponFinish(@Param("uid") Integer uid);
    
    public Group getCurrentyRegisterGroup();
	
    public Integer setUserGroup(@Param("uid") Integer uid, @Param("group_id") Integer group_id);

	public Integer updateGroupUserNum(@Param("group_id") Integer group_id);

	public Group getNextGroupByDeliveryTime(@Param("delivery_time") String delivery_time);

	public List<Group> getUserGroupList(@Param("uid") Integer uid);

	public Group getCurrentyGroup(@Param("uid") Integer uid);
	
	public List<User> findMemberJoinUserList();
	
	public List<User> findMemberInfoCompleteList();

	public String getUserNo();
	
	public Integer updateUserData(@Param("uid") Integer uid);
	
	public PageData  getIndexUser(@Param("uid") Integer uid);
	
	public String  getHeadImg(@Param("uid") Integer uid);
	
	public PageData  validateInviteCode(@Param("inviteCode") String inviteCode);

	public PageData  getUserByShopCode(@Param("shopCode") String shopCode,@Param("uid") Integer uid);
		
}
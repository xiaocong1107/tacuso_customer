package com.tacuso.buyer.service.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.tacuso.buyer.dao.UserMapper;
import com.tacuso.buyer.entity.ClothingInfo;
import com.tacuso.buyer.entity.Group;
import com.tacuso.buyer.entity.User;
import com.tacuso.buyer.service.UserService;
import com.tacuso.buyer.vo.PageData;


@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUserByUid(Integer uid) {

        if(uid==null ){
            return null;
        }

        List<User> userList =  userMapper.selectList(
                new EntityWrapper<User>().eq("uid",uid).setSqlSelect("`uid`, wx_uid, bindphone, is_verify, nickname, email, username, is_employee, is_member,is_question_finish, province, growth, score, level_id, credit_level_id ,uno")
        );
        User user;
        if(!userList.isEmpty() && userList.size()>0){
            user = userList.get(0);
        }else{
            user = null;
        }

        return user;
    }
    
    
    @Override
    public User getUserByUno(String uno) {

        if(uno==null ){
            return null;
        }

        List<User> userList =  userMapper.selectList(
                new EntityWrapper<User>().eq("uno",uno).setSqlSelect("`uid`, wx_uid, bindphone, is_verify, nickname, email, username, is_employee, is_member,is_question_finish, province, growth, score, level_id, credit_level_id ,uno")
        );
        User user;
        if(!userList.isEmpty() && userList.size()>0){
            user = userList.get(0);
        }else{
            user = null;
        }

        return user;
    }

    
    @Override
    public Integer setUserToMember(Integer uid) {
       Integer count= userMapper.updateMember(uid);
       return count;
    }
    
	@Override
	public Map<String,String> getClothingInfoByUid(Integer uid) {
		Map<String,String> info = userMapper.getClothingInfoByUid(uid);
		return info;
	}

	@Override
	public void updateClothingInfo(ClothingInfo info) {
		userMapper.updateClothingInfo(info);
		int uid = info.getUid();
		System.out.println(" info.getUid()==="+ info.getUid());
		userMapper.updateUserData(uid);
	}
	
    @Override
    public Integer clearUserByUid(Integer uid) {

        Integer count = userMapper.clearUserByUid(uid);

        return  count;
    }

    @Override
    public Integer setUserQuestionFinish(Integer uid) {
        Integer count = userMapper.updateQuestionFinish(uid);

        return  count;
    }

    @Override
    public Integer setUserCouponFinish(Integer uid) {
        Integer count = userMapper.updateCouponFinish(uid);

        return  count;
    }

	@Override
	public Group getCurrentyRegisterGroup() {
		return userMapper.getCurrentyRegisterGroup();
	}	

	@Override
	@Transactional
	public Integer setUserGroup(Integer uid, Integer group_id) {
		Integer count = userMapper.setUserGroup(uid, group_id);
		userMapper.updateGroupUserNum(group_id);
		return count;
	}

	@Override
	public Group getNextGroupByDeliveryTime(String delivery_time) {
		return userMapper.getNextGroupByDeliveryTime(delivery_time);
	}

	@Override
	public LocalDate getNextBoxDate(Integer uid) {
		List<Group> groupList = userMapper.getUserGroupList(uid);
//		for (Group group : groupList) {
//
//		}
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDate boxDate = LocalDate.parse(groupList.get(0).getBox_time(), df);
		return boxDate;
	}

	@Override
	public Group getCurrentyGroup(Integer uid) {
		// TODO Auto-generated method stub
		return userMapper.getCurrentyGroup(uid);
	}
	
	@Override
	public List<User> findMemberJoinUserList(){		
		return userMapper.findMemberJoinUserList();
	}
	
	@Override
	public List<User> findMemberInfoCompleteList(){		
		return userMapper.findMemberInfoCompleteList();
	}
	
	@Override
	public void updateUserData(Integer uid) {
		userMapper.updateUserData(uid);
	}
	
	@Override
	public PageData getIndexUser(Integer uid) {
		return userMapper.getIndexUser(uid);
	}
	
	@Override
	public String getHeadImg(Integer uid) {
		return userMapper.getHeadImg(uid);
	}
	
	@Override
	public PageData validateInviteCode(String inviteCode) {
		return userMapper.validateInviteCode(inviteCode);
	}
	
	@Override
	public PageData getUserByShopCode(String shopCode,Integer uid) {
		return userMapper.getUserByShopCode(shopCode,uid);
	}
	
	
}

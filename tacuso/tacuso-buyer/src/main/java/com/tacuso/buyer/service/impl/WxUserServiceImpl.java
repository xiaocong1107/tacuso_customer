package com.tacuso.buyer.service.impl;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.tacuso.buyer.dao.UserMapper;
import com.tacuso.buyer.dao.WxUserMapper;
import com.tacuso.buyer.entity.User;
import com.tacuso.buyer.entity.WxUser;
import com.tacuso.buyer.result.JsonResult;
import com.tacuso.buyer.result.JsonResultCode;
import com.tacuso.buyer.service.WxUserService;
import com.tacuso.buyer.utils.EmojiFilter;
import com.xiaoleilu.hutool.crypto.SecureUtil;
import com.xiaoleilu.hutool.util.ObjectUtil;
import com.xiaoleilu.hutool.util.RandomUtil;

import me.chanjar.weixin.mp.bean.result.WxMpUser;

@Service
public class WxUserServiceImpl extends ServiceImpl<WxUserMapper,WxUser> implements WxUserService {

    public static final Logger logger =  LoggerFactory.getLogger(WxUserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private WxUserMapper wxUserMapper;


    /**
     * 微信用户初步注册
     * @param session
     * @param wxMpUser
     * @return
     */
    @Override
    public User RegisterWxUser(HttpSession session, WxMpUser wxMpUser) {

    	System.out.println("wxMpUser======"+wxMpUser);
    	
    	if(wxMpUser==null){
    		return null;
    	}
    	
        saveWxUserSessionData(session,wxMpUser);

        String open_id = wxMpUser.getOpenId();

        WxUser wxUser = this.wxUserMapper.getWxUserByOpenId(open_id);

        WxUser insertWxUser = new WxUser();
        insertWxUser.setWx_openid(wxMpUser.getOpenId());
        String nickname = wxMpUser.getNickname();
        if(nickname!=null){
	    	nickname = EmojiFilter.filterEmoji(nickname);
	    	nickname = EmojiFilter.removeFourChar(nickname);
        }
        if(wxMpUser.getHeadImgUrl()!=null||wxMpUser.getNickname()!=null){
            insertWxUser.setWx_nickname(nickname);
            insertWxUser.setWx_sex( wxMpUser.getSex()==1?"男":"女" );
            insertWxUser.setWx_city(wxMpUser.getCity());
            insertWxUser.setWx_province(wxMpUser.getProvince());
            insertWxUser.setWx_country(wxMpUser.getCountry());
            insertWxUser.setWx_headimgurl(wxMpUser.getHeadImgUrl());
            insertWxUser.setWx_unionid(wxMpUser.getUnionId());
            insertWxUser.setWx_subscribe_time(wxMpUser.getSubscribeTime());
            insertWxUser.setWx_tagid_list( JSON.toJSONString(wxMpUser.getTagIds()));
            insertWxUser.setWx_subscribe_scene(wxMpUser.getSubscribeScene());
        }

        //如果是新用户
        if( ObjectUtil.isNull(wxUser) ){
            //新建wxUser
            Integer count = this.wxUserMapper.insert(insertWxUser);

            Integer wx_uid = insertWxUser.getWx_uid();

            //新建User
            User user = new User();
            user.setWx_uid(wx_uid);
//            user.setBindphone(RandomUtil.randomNumbers(13));
            user.setBindphone("");
            if(insertWxUser.getWx_nickname()!=null){
                user.setNickname(insertWxUser.getWx_nickname());
            }else{
                user.setNickname("有衣宅送用户"+RandomUtil.randomString(6));
            }

            user.setEmail("");
            if(insertWxUser.getWx_nickname()!=null){
                user.setUsername(nickname);
            }else{
                user.setUsername("有衣宅送用户"+RandomUtil.randomString(6));
            }
            user.setSalt(RandomUtil.randomString(10));
            user.setPassword( SecureUtil.md5((RandomUtil.randomString(10)+user.getSalt()) ));
            user.setIs_member(0);
            user.setIs_verify(0);

            user.setWxUser(insertWxUser);
            user.setUno(this.userMapper.getUserNo());
            //插入新用户到用户表
            count = this.userMapper.createUser(user);
            logger.info("用户首次SESSION信息：{}",JSON.toJSONString(user));
            saveUserSessionData(session,user);
            return user;

        }else{
            insertWxUser.setWx_uid(wxUser.getWx_uid());
            Integer count = this.wxUserMapper.update(insertWxUser, new EntityWrapper<WxUser>().where("wx_uid={0}",wxUser.getWx_uid()));
            //获取User
            List<User> userList =  this.userMapper.selectList(
                    new EntityWrapper<User>().eq("wx_uid",wxUser.getWx_uid()).setSqlSelect("uid, wx_uid, bindphone, is_verify, nickname, email, username, is_employee, is_member, province, growth, score, level_id, credit_level_id")
            );
            logger.info("用户登录session信息1：{}",JSON.toJSONString(userList));
            if(!userList.isEmpty()){
                User currUser = userList.get(0);
                logger.info("用户登录session信息2：{}",JSON.toJSONString(currUser));
                saveUserSessionData(session,currUser);
                return currUser;
            }
        }
        return null;
    }


    /**
     * 保存用户信息到 session
     * @param session
     * @param wxMpUser
     */
    private void saveWxUserSessionData(HttpSession session , WxMpUser wxMpUser){
        //用户保存至数据库即可
        session.setMaxInactiveInterval(12*60*60);//12小时超时
        session.setAttribute("openId",wxMpUser.getOpenId());
        session.setAttribute("wxUser", JSON.toJSONString(wxMpUser));
    }

    /**
     * 保存用户信息到 session
     * @param session
     * @param user
     */
    private void saveUserSessionData(HttpSession session , User user){
        //用户保存至user
        session.setMaxInactiveInterval(12*60*60);//12小时超时
        session.setAttribute("uid",user.getUid());
        session.setAttribute("wx_uid",user.getWx_uid());
        session.setAttribute("user",JSON.toJSONString(user));
    }


}

package com.tacuso.buyer.quartz;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import com.tacuso.buyer.entity.Order;
import com.tacuso.buyer.entity.Quartz;
import com.tacuso.buyer.entity.SendWechatLog;
import com.tacuso.buyer.entity.User;
import com.tacuso.buyer.entity.UserInfo;
import com.tacuso.buyer.entity.WxUser;
import com.tacuso.buyer.service.OrderService;
import com.tacuso.buyer.service.SendWechatLogService;
import com.tacuso.buyer.service.UserInfoService;
import com.tacuso.buyer.service.UserService;
import com.tacuso.buyer.service.WxUserService;
import com.tacuso.buyer.utils.wechat.WeixinUtils;

import net.sf.json.JSONObject;

@Component
@EnableScheduling
public class OrderJob {

    @Autowired
    private UserService userService;
    @Autowired
    private WxUserService wxUserService;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private SendWechatLogService sendWechatLogService;
    @Autowired
    private OrderService orderService;
    
    //会员加入提醒
    public void joinAlert() throws Exception{
            System.out.println("check token expire");
            Quartz quartz = new Quartz();
            quartz.setType("joinAlert");
            quartz = sendWechatLogService.getQuartz(quartz);
            if(quartz.getStatus()==1){//启用定时器
            	
	    		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    		String addtime = sf.format(new Date());	
	    		JSONObject jsonObject =null;
	    		String 	access_token = WeixinUtils.getAtoken();//重新获取token
	    		
	    		
	    		 /*监测会员加入提醒  begin*/
	    		List<User>  userMemberJoinList = userService.findMemberJoinUserList();
	    		
	    		for(User MemberJoinUser : userMemberJoinList) {
	    			WxUser wxUser = wxUserService.selectById(MemberJoinUser.getWx_uid());
	    			UserInfo userInfo =  userInfoService.getUserInfoByUid(MemberJoinUser.getWx_uid());
	    			SendWechatLog sendWechatLog = new SendWechatLog();
	    			sendWechatLog.setUid(MemberJoinUser.getUid());
	    			sendWechatLog.setType(1);
	    			sendWechatLog.setType_name("会员加入提醒");
	    			sendWechatLog.setCreatetime(addtime);
	    			SendWechatLog  getLogPd = sendWechatLogService.findSendWechatLog(sendWechatLog);
	    			if(getLogPd==null) {
	    	 			jsonObject=	WeixinUtils.memberJoin(access_token,wxUser.getWx_openid(),userInfo.getNickname(),addtime,MemberJoinUser.getBindphone());
	    	 			sendWechatLog.setRemark(jsonObject.toString());
	    	 			sendWechatLogService.createSendWechatLog(sendWechatLog);
	    			}
	    		}
	    		 /*监测会员加入提醒  end*/
	    		
        }	
    }
    
    //资料完善通知
    public void perfectData() throws Exception{
      System.out.println("check token expire");
      Quartz quartz = new Quartz();
      quartz.setType("perfectData");
      quartz = sendWechatLogService.getQuartz(quartz);
      if(quartz.getStatus()==1){//启用定时器
      	
  		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  		String addtime = sf.format(new Date());	
  		JSONObject jsonObject =null;
  		String 	access_token = WeixinUtils.getAtoken();//重新获取token
 
  		
  		 /*资料完善通知  begin*/
  		List<User>  MemberInfoCompleteList = userService.findMemberInfoCompleteList();
  		for(User MemberInfoCompleteUser : MemberInfoCompleteList) {
  			WxUser wxUser = wxUserService.selectById(MemberInfoCompleteUser.getWx_uid());
			UserInfo userInfo =  userInfoService.getUserInfoByUid(MemberInfoCompleteUser.getWx_uid());
  			SendWechatLog sendWechatLog = new SendWechatLog();
  			sendWechatLog.setUid(MemberInfoCompleteUser.getUid());
  			sendWechatLog.setType(2);
  			sendWechatLog.setType_name("资料完善通知");
  			sendWechatLog.setCreatetime(addtime);
  			SendWechatLog  getLogPd = sendWechatLogService.findSendWechatLog(sendWechatLog);
  			if(getLogPd==null) {
  	 			jsonObject=	WeixinUtils.memberInfoComplete(access_token,wxUser.getWx_openid(),userInfo.getNickname(),addtime);
  	 			sendWechatLog.setRemark(jsonObject.toString());
  	 			sendWechatLogService.createSendWechatLog(sendWechatLog);
  			}
  		}
  		 /*资料完善通知  end*/
  		
  }	
}
    
    //服务到期提醒
    public void expire() throws Exception{
      System.out.println("check token expire");
      Quartz quartz = new Quartz();
      quartz.setType("expire");
      quartz = sendWechatLogService.getQuartz(quartz);
      if(quartz.getStatus()==1){//启用定时器
      	
  		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  		String addtime = sf.format(new Date());	
  		JSONObject jsonObject =null;
  		String 	access_token = WeixinUtils.getAtoken();//重新获取token
		
  		 /*服务到期提醒  begin*/
  		List<Order>  orderExpireList = orderService.findserviceExpireList();
  		for(Order  order : orderExpireList) {
  			User user = userService.getUserByUid(order.getUid());
  			if(user.getWx_uid()!=null){
	    			WxUser wxUser = wxUserService.selectById(user.getWx_uid());
	    			UserInfo userInfo =  userInfoService.getUserInfoByUid(user.getWx_uid());
	    			SendWechatLog sendWechatLog = new SendWechatLog();
	    			sendWechatLog.setUid(order.getUid());
	    			sendWechatLog.setType(3);
	    			sendWechatLog.setType_name("服务到期提醒");
	    			sendWechatLog.setCreatetime(addtime);
	    			SendWechatLog  getLogPd = sendWechatLogService.findSendWechatLog(sendWechatLog);
	    			if(getLogPd==null) {
	    	 			jsonObject=	WeixinUtils.serviceExpire(access_token,wxUser.getWx_openid(),userInfo.getNickname(),order.getOrder_no());
	    	 			sendWechatLog.setRemark(jsonObject.toString());
	    	 			sendWechatLogService.createSendWechatLog(sendWechatLog);
	    			}
  			}
  		}
  		 /*服务到期提醒  end*/
  }	
}
    
    
	//新增门店结算
	    public void insertShopAccounts() throws Exception{
	      System.out.println("check insert_shop_accounts");
	      Quartz quartz = new Quartz();
	      quartz.setType("insert_shop_accounts");
	      quartz = sendWechatLogService.getQuartz(quartz);
	      if(quartz.getStatus()==1){//启用定时器
	    	  
	  		 /*更新门店结算  begin*/
	    	  orderService.callInsertShopAccounts();
	  		 /*更新门店结算  end*/
	      }
	    }
	    
	    
		//更新门店结算
	    public void updateShopAccounts() throws Exception{
	      System.out.println("check insert_shop_accounts");
	      Quartz quartz = new Quartz();
	      quartz.setType("update_shop_accounts");
	      quartz = sendWechatLogService.getQuartz(quartz);
	      if(quartz.getStatus()==1){//启用定时器
	  		 /*更新门店结算  begin*/
	    	  orderService.callUpdateShopAccounts();
	  		 /*更新门店结算  end*/
	      }
	    }
		    

}
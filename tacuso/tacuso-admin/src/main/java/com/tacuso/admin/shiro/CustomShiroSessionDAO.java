package com.tacuso.admin.shiro;

import com.tacuso.admin.common.utils.LoggerUtils;
import com.tacuso.admin.shiro.session.ShiroSessionRepository;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;

import java.io.Serializable;
import java.util.Collection;

/**
 * 
 * 开发公司：SOJSON在线工具 <p>
 * 版权所有：© www.sojson.com<p>
 * 博客地址：http://www.sojson.com/blog/  <p>
 * <p>
 * 
 * Session 操作
 * 
 * <p>
 * 
 * 区分　责任人　日期　　　　说明<br/>
 * 创建　区明海　2016年6月2日 　<br/>
 *
 * @author ouminghai
 * @email  so@sojson.com
 * @version 1.0,2016年6月2日 <br/>
 * 
 */
public class CustomShiroSessionDAO extends AbstractSessionDAO{ 
	
    private ShiroSessionRepository shiroSessionRepository;  
  
    public ShiroSessionRepository getShiroSessionRepository() {  
        return shiroSessionRepository;  
    }  
  
    public void setShiroSessionRepository(  
            ShiroSessionRepository shiroSessionRepository) {  
        this.shiroSessionRepository = shiroSessionRepository;  
    }

    /**
     * 删除session
     * @param session
     * @throws UnknownSessionException
     */
    @Override  
    public void update(Session session) throws UnknownSessionException {  
        getShiroSessionRepository().saveSession(session);  
    }


    /**
     * 删除session
     * @param session
     */
    @Override  
    public void delete(Session session) {  
        if (session == null) {  
        	LoggerUtils.error(getClass(), "Session 不能为null");
            return;  
        }  
        Serializable id = session.getId();  
        if (id != null)  
            getShiroSessionRepository().deleteSession(id);  
    }

    /**
     * 获取所有在线的所有session
     * @return
     */
    @Override  
    public Collection<Session> getActiveSessions() {  
        return getShiroSessionRepository().getAllSessions();  
    }

    /**
     * 创建session
     * @param session
     * @return
     */
    @Override  
    protected Serializable doCreate(Session session) {  
        Serializable sessionId = this.generateSessionId(session);  
        this.assignSessionId(session, sessionId);  
        getShiroSessionRepository().saveSession(session);  
        return sessionId;  
    }

    /**
     * 获取某个session的信息
     * @param sessionId
     * @return
     */
    @Override  
    protected Session doReadSession(Serializable sessionId) {  
        return getShiroSessionRepository().getSession(sessionId);  
    } }

package com.tacuso.buyer.tags;


import com.tacuso.buyer.freemarker.utils.FreemarkerTagUtil;
import freemarker.core.Environment;
import freemarker.template.*;

import java.io.IOException;
import java.util.Map;


/**
 * 
 * 开发公司：WENYIFAN.NET <br/>
 * 版权：WENYIFAN.NET <br/>
 * <p>
 * 基础标签类
 * <p>
 * 
 * 区分　责任人　日期　　　　说明<br/>
 * 创建　区明海　2014年4月28日 　<br/>
 * <p>
 * *******
 * <p>
 * @author ouminghai
 * 
 * @version 1.0,2014年4月28日 <br/>
 * 
 */
@SuppressWarnings("unchecked")
public abstract class WYFTemplateModel implements TemplateDirectiveModel{

	
	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		
		
		/**
		 * 模版方法模式，把变化委派下去，交给子类实现！
		 */
		Map<String, TemplateModel> paramWrap = putValue(params);
		
		
		Map<String, TemplateModel> origMap = FreemarkerTagUtil.convertToTemplateModel(env, paramWrap);
		body.render(env.getOut());
		FreemarkerTagUtil.clearTempleModel(env, paramWrap, origMap);
	}

	/**
	 * 子类实现
	 * @param params
	 * @return
	 * @throws TemplateModelException
	 */
	protected abstract Map<String, TemplateModel> putValue(Map params) throws TemplateModelException;
}

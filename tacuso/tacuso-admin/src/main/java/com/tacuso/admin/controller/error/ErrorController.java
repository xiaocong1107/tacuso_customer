package com.tacuso.admin.controller.error;

import com.tacuso.admin.result.JsonResult;
import com.tacuso.admin.result.JsonResultCode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 错误统一处理
 * @author Administrator
 */

@RestController
public class ErrorController {

	/**
	 * 请求异常404
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/error_404", method = { RequestMethod.GET, RequestMethod.POST })
	public JsonResult error_404() throws Exception
	{
		return new JsonResult(JsonResultCode.FAILURE, "404请求找不到请求", "");
	}

	/**
	 * 服务器异常
	 * @return JsonResult
	 */
	@RequestMapping(value = "/error_500", method = { RequestMethod.GET, RequestMethod.POST })
	public JsonResult error_500()
	{
		return new JsonResult(JsonResultCode.FAILURE, "500服务器内部错误", "");
	}
}
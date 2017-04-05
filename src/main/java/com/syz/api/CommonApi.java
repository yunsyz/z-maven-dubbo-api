package com.syz.api;

import java.io.UnsupportedEncodingException;

import javax.annotation.Resource;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.syz.dubbo.DubboBaseService;
import com.syz.dubbo.bean.DubboInParamBean;
import com.syz.dubbo.bean.DubboOutParamBean;

@Controller
@RequestMapping("/api")
public class CommonApi {
	private static final Logger logger = LoggerFactory.getLogger(CommonApi.class);

	@Resource
	private DubboBaseService dubboBaseService;

	private DubboInParamBean dubboInParamBean;

	private DubboOutParamBean dubboOutPamamBean;

	/**
	 * 公共接口入口，反射调用
	 * 
	 * @Title: common
	 * @Description: TODO
	 * @param request
	 * @param body
	 * @return
	 * @return: String
	 */
	@RequestMapping(value = { "", "/" })
	@ResponseBody
	public String common(HttpServletRequest request, @RequestParam(value = "body", required = false) String body) {
		if (body == null) {
			try {
				body = getBody(request);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		String txid = "";
		dubboInParamBean = new DubboInParamBean();
		dubboOutPamamBean = new DubboOutParamBean();
		if (StringUtils.isEmpty(request.getCharacterEncoding())) {
			try {
				request.setCharacterEncoding("UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		txid = request.getParameter("txid");
		logger.info("txid:" + txid);
		logger.info("body:" + body);
		dubboInParamBean.setParam(body);
		dubboInParamBean.setTxid(txid);
		// 接口调用
		try {
			// 执行dopost方法
			dubboOutPamamBean = dubboBaseService.doPost(dubboInParamBean);
		} catch (Exception e) {
			logger.error("接口调用失败！" + e.getMessage());
			e.printStackTrace();
		}
		String ret = dubboOutPamamBean.getParam();
		System.out.println("ret:" + ret);
		if (ret == null)
			ret = "";
		return ret;
	}

	private String getBody(HttpServletRequest request) throws Exception {
		ServletInputStream in = request.getInputStream();
		byte[] buf = new byte[8 * 1024];
		StringBuffer sbuf = new StringBuffer();
		int result;
		do {
			result = in.readLine(buf, 0, buf.length); // does +=
			if (result != -1) {
				sbuf.append(new String(buf, 0, result, "UTF-8"));
			}
		} while (result == buf.length); // loop only if the buffer was filled
		if (sbuf.length() == 0) {
			return null; // nothing read, must be at the end of stream
		}
		return sbuf.toString();
	}
}
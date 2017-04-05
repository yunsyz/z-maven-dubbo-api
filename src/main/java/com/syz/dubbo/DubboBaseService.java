package com.syz.dubbo;

import com.syz.dubbo.bean.DubboInParamBean;
import com.syz.dubbo.bean.DubboOutParamBean;

public interface DubboBaseService {
	DubboOutParamBean doPost(DubboInParamBean dubboInParamBean);
}
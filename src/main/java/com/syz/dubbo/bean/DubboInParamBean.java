package com.syz.dubbo.bean;

import java.io.Serializable;

public class DubboInParamBean implements Serializable {
	/**
	 * @fieldName: serialVersionUID
	 * @fieldType: long
	 * @Description: TODO
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 业务ID
	 */
	private String txid;

	/**
	 * 返回参数
	 */
	private String param;

	public String getTxid() {
		return txid;
	}

	public void setTxid(String txid) {
		this.txid = txid;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

}
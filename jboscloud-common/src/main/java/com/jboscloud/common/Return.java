package com.jboscloud.common;
import java.util.HashMap;
import java.util.Map;

/**
 * 返回数据
 * @author youfu.wang
 * @date 2019-01-29
 */
public class Return extends HashMap<String, Object> {
	private static final long serialVersionUID = 1L;
	public final static String RETCODE_SUCCESS="0000";
	public final static String RETCODE_FAILURE="9999";
	
	public Return() {
		put("retCode", Return.RETCODE_SUCCESS);
		put("retMsg", "success");
	}
	
	public static Return error() {
		return error(Return.RETCODE_FAILURE, "未知异常，请联系管理员");
	}
	
	public static Return error(String retMsg) {
		return error(Return.RETCODE_FAILURE, retMsg);
	}
	
	public static Return error(String retCode, String retMsg) {
		Return r = new Return();
		r.put("retCode", retCode);
		r.put("retMsg", retMsg);
		return r;
	}

	public static Return ok(String retMsg) {
		Return r = new Return();
		r.put("retMsg", retMsg);
		return r;
	}
	
	public static Return ok(Map<String, Object> map) {
		Return r = new Return();
		r.putAll(map);
		return r;
	}
	
	public static Return ok() {
		return new Return();
	}

	@Override
	public Return put(String key, Object value) {
		super.put(key, value);
		return this;
	}
}
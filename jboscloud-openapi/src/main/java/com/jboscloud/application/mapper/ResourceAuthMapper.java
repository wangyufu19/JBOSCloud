package com.jboscloud.application.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * UriAuthMapper
 * @author youfu.wang
 * @date 2021-01-31
 */
public interface ResourceAuthMapper {
	/**
	 * 得到资源的客户端授权
	 * @param parameterObject
	 * @return
	 */
	public Map<String,Object> getUriAuthInfoByClientId(Map<String, Object> parameterObject);
}

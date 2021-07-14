package com.jboscloud.application.service;

import java.util.Map;

/**
 * ResourceAuthService
 * @author youfu.wang
 * @date 2021-01-31
 */
public interface ResourceAuthService {
	/**
	 * 验证资源的客户端授权
	 * @param clientId
	 * @param resourceId
	 * @return
	 */
	public boolean validateResourceAuth(String clientId,String resourceId);
}

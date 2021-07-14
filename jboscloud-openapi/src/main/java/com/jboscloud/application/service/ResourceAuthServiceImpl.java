package com.jboscloud.application.service;

import com.jboscloud.application.mapper.ResourceAuthMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.ws.ServiceMode;
import java.util.HashMap;
import java.util.Map;

/**
 * ResourceAuthServiceImpl
 * @author youfu.wang
 * @date 2021-01-31
 */
@Service
public class ResourceAuthServiceImpl implements ResourceAuthService{
	@Autowired
	private ResourceAuthMapper resourceAuthMapper;

	/**
	 * 得到资源的客户端授权
	 * @param clientId
	 * @param resourceId
	 * @return
	 */
	public boolean validateResourceAuth(String clientId, String resourceId){
		boolean bool=false;
		Map<String,Object> parameterObject=new HashMap<String,Object>();
		parameterObject.put("clientId",clientId);
		parameterObject.put("resourceId",resourceId);
		Map<String,Object> resourceAuthMap=resourceAuthMapper.getUriAuthInfoByClientId(parameterObject);
		if(resourceAuthMap!=null){
			bool=true;
		}
		return bool;
	}
}

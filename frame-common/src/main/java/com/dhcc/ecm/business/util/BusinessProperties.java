package com.dhcc.ecm.business.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
/**
 * @ClassName BusinessProperties
 * @Description 配置文件模型类
 * @author wangaobing wangaobing001@dhcc.com.cn
 * @date 2016-07-08
 */
@ConfigurationProperties(prefix = "bus",locations = "classpath:/business.properties")  
public class BusinessProperties {
	private String ldapSearch;
	private String erjinzhiUrl;
	private String masterServiceUrl;
	private String userAuthorTree;
	private String filePath;
	private String systemId;
	private String port;
	private String host;
	private String password;
	
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getSystemId() {
		return systemId;
	}
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}
	public String getFilePath() {
		return filePath;
	}
	public String getLdapSearch() {
		return ldapSearch;
	}
	public void setLdapSearch(String ldapSearch) {
		this.ldapSearch = ldapSearch;
	}
	public String getErjinzhiUrl() {
		return erjinzhiUrl;
	}
	public void setErjinzhiUrl(String erjinzhiUrl) {
		this.erjinzhiUrl = erjinzhiUrl;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getMasterServiceUrl() {
		return masterServiceUrl;
	}
	public void setMasterServiceUrl(String masterServiceUrl) {
		this.masterServiceUrl = masterServiceUrl;
	}
	public String getUserAuthorTree() {
		return userAuthorTree;
	}
	public void setUserAuthorTree(String userAuthorTree) {
		this.userAuthorTree = userAuthorTree;
	}
}

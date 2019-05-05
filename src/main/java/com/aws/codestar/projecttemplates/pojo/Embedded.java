/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aws.codestar.projecttemplates.pojo;

import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author lian
 */
public class Embedded {

	private HomePage _embedded;

	/**
	 * 登入用户数据
	 */
	private HttpSessionUser httpSessionUser;

	/**
	 * @return the _embedded
	 */
	public HomePage getEmbedded() {
		return _embedded;
	}

	/**
	 * @param _embedded the _embedded to set
	 */
	public void setEmbedded(HomePage _embedded) {
		this._embedded = _embedded;
	}

	/**
	 * @return the httpSessionUser
	 */
	@XmlElement(name = "httpSessionUser")
	public HttpSessionUser getHttpSessionUser() {
		return httpSessionUser;
	}

	/**
	 * @param httpSessionUser the httpSessionUser to set
	 */
	public void setHttpSessionUser(HttpSessionUser httpSessionUser) {
		this.httpSessionUser = httpSessionUser;
	}

	@Override
	public String toString() {
		return "Embedded{" + "_embedded=" + _embedded + ", httpSessionUser=" + httpSessionUser + '}';
	}

}

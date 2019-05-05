/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aws.codestar.projecttemplates.pojo;

import javax.xml.bind.annotation.XmlAttribute;

/**
 *
 * @author lian
 */
public class HttpSessionUser {

	private String me;

	private String id;

	private String personnelHref;

	private String thirdParty;

	private String nickname;

	/**
	 * @return the me
	 */
	@XmlAttribute(name = "me")
	public String getMe() {
		return me;
	}

	/**
	 * @param me the me to set
	 */
	public void setMe(String me) {
		this.me = me;
	}

	/**
	 * @return the id
	 */
	@XmlAttribute(name = "id")
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the personnelHref
	 */
	@XmlAttribute(name = "personnelHref")
	public String getPersonnelHref() {
		return personnelHref;
	}

	/**
	 * @param personnelHref the personnelHref to set
	 */
	public void setPersonnelHref(String personnelHref) {
		this.personnelHref = personnelHref;
	}

	/**
	 * @return the thirdParty
	 */
	@XmlAttribute(name = "thirdParty")
	public String getThirdParty() {
		return thirdParty;
	}

	/**
	 * @param thirdParty the thirdParty to set
	 */
	public void setThirdParty(String thirdParty) {
		this.thirdParty = thirdParty;
	}

	/**
	 * @return the nickname
	 */
	@XmlAttribute(name = "nickname")
	public String getNickname() {
		return nickname;
	}

	/**
	 * @param nickname the nickname to set
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	@Override
	public String toString() {
		return "HttpSessionUser{" + "me=" + me + ", id=" + id + ", personnelHref=" + personnelHref + ", thirdParty=" + thirdParty + ", nickname=" + nickname + '}';
	}

}

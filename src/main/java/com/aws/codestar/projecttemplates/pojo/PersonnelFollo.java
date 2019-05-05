/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aws.codestar.projecttemplates.pojo;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 * 跟踪者或跟随者
 *
 * @author lian
 */
public class PersonnelFollo {

	/**
	 * 状态
	 */
	private String status;

	/**
	 * 跟踪者数据
	 */
	private Personnel relatingUser;

	/**
	 * 跟随者数据
	 */
	private Personnel relatedUser;

	/**
	 * @return the status
	 */
	@XmlAttribute(name = "status")
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the relatingUser
	 */
	@XmlElement(name = "relatingUser")
	public Personnel getRelatingUser() {
		return relatingUser;
	}

	/**
	 * @param relatingUser the relatingUser to set
	 */
	public void setRelatingUser(Personnel relatingUser) {
		this.relatingUser = relatingUser;
	}

	/**
	 * @return the relatedUser
	 */
	@XmlElement(name = "relatedUser")
	public Personnel getRelatedUser() {
		return relatedUser;
	}

	/**
	 * @param relatedUser the relatedUser to set
	 */
	public void setRelatedUser(Personnel relatedUser) {
		this.relatedUser = relatedUser;
	}

	@Override
	public String toString() {
		return "PersonnelFollo{" + "status=" + status + ", relatingUser=" + relatingUser + ", relatedUser=" + relatedUser + '}';
	}

}

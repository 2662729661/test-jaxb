/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aws.codestar.projecttemplates.jaxbXml;

import com.aws.codestar.projecttemplates.pojo.Personnel;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 主页数据 对象转xml
 *
 * @author lian
 */
@XmlRootElement(name = "document")
public class PersonnelJAXB {

	private Personnel personnel1Homepage;

	/**
	 * @return the personnels
	 */
	@XmlElement(name = "list")
	public Personnel getPersonnel1Homepage() {
		return personnel1Homepage;
	}

	/**
	 * @param personnels the personnels to set
	 */
	public void setPersonnel1Homepage(Personnel personnel1Homepage) {
		this.personnel1Homepage = personnel1Homepage;
	}

}

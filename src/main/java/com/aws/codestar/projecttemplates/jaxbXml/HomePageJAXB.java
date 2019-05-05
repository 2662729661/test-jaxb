/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aws.codestar.projecttemplates.jaxbXml;

import com.aws.codestar.projecttemplates.pojo.HomePage;
import com.aws.codestar.projecttemplates.pojo.Embedded;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 主页数据 对象转xml
 *
 * @author lian
 */
@XmlRootElement(name = "document")
public class HomePageJAXB {

	private Embedded homePage;

	/**
	 * @return the homePage
	 */
	@XmlElement(name = "list")
	public Embedded getHomePage() {
		return homePage;
	}

	/**
	 * @param homePage the homePage to set
	 */
	public void setHomePage(Embedded homePage) {
		this.homePage = homePage;
	}

}

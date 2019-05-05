/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aws.codestar.projecttemplates.pojo;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

/**
 * 首页数据
 *
 * @author lian
 */
public class HomePage {

	/**
	 * 文章数据
	 */
	private List<HomePageStorie> stories;

	/**
	 * @return the stories
	 */
	@XmlElementWrapper(name = "stories")
	@XmlElement(name = "story")
	public List<HomePageStorie> getStories() {
		return stories;
	}

	/**
	 * @param stories the stories to set
	 */
	public void setStories(List<HomePageStorie> stories) {
		this.stories = stories;
	}

	@Override
	public String toString() {
		return "HomePage{" + "stories=" + stories + '}';
	}

}

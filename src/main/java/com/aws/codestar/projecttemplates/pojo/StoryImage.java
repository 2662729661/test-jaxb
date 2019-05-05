/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aws.codestar.projecttemplates.pojo;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 * 文章图片 api
 *
 * @author lian
 */
public class StoryImage {

	/**
	 * 图片url
	 */
	private String imgUrl;

	/**
	 *
	 */
	private String content;

	private String count;

	private String mode;

	/**
	 * @return the imgUrl
	 */
	@XmlAttribute(name = "imgUrl")
	public String getImgUrl() {
		return imgUrl;
	}

	/**
	 * @param imgUrl the imgUrl to set
	 */
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	/**
	 * @return the content
	 */
	@XmlAttribute(name = "content")
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the count
	 */
	@XmlAttribute(name = "count")
	public String getCount() {
		return count;
	}

	/**
	 * @param count the count to set
	 */
	public void setCount(String count) {
		this.count = count;
	}

	/**
	 * @return the mode
	 */
	@XmlAttribute(name = "mode")
	public String getMode() {
		return mode;
	}

	/**
	 * @param mode the mode to set
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}

	@Override
	public String toString() {
		return "StoryImage{" + "imgUrl=" + imgUrl + ", content=" + content + ", count=" + count + ", mode=" + mode + '}';
	}

}

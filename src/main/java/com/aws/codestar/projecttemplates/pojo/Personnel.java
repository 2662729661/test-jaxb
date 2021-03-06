/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aws.codestar.projecttemplates.pojo;

import java.util.List;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

/**
 * 个人主页数据
 *
 * @author lian
 */
public class Personnel {

	/**
	 * id
	 */
	private String id;

	/**
	 * 跟随者数量
	 */
	private String followerCount;

	/**
	 * 跟踪者数量
	 */
	private String followingCount;

	/**
	 *
	 */
	private String universallyUniqueIdentifier;

	/**
	 * 发布的文章
	 */
	private List<Storie> userStory;

	/**
	 * 背景
	 */
	private String coverImgUrl;

	/**
	 * 头像
	 */
	private String profileImgUrl;

	/**
	 * 个人简介
	 */
	private String profileText;

	/**
	 * 用户名
	 */
	private String nickname;

	/**
	 * 发布文章数量
	 */
	private String userStoryCount;

	/**
	 * 跟随者数据
	 */
	private List<PersonnelFollo> followers;

	/**
	 * 跟踪者数据
	 */
	private List<PersonnelFollo> following;

	/**
	 * 登入用户信息
	 */
	private HttpSessionUser httpSessionUser;

	/**
	 * 判断该主页可否添加好友
	 */
	private String isAddFriend;

	/**
	 * 判断是否显示好友列表
	 */
	private String isFriendList;

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
	 * @return the followerCount
	 */
	@XmlElement(name = "followerCount")
	public String getFollowerCount() {
		return followerCount;
	}

	/**
	 * @param followerCount the followerCount to set
	 */
	public void setFollowerCount(String followerCount) {
		this.followerCount = followerCount;
	}

	/**
	 * @return the followingCount
	 */
	@XmlElement(name = "followingCount")
	public String getFollowingCount() {
		return followingCount;
	}

	/**
	 * @param followingCount the followingCount to set
	 */
	public void setFollowingCount(String followingCount) {
		this.followingCount = followingCount;
	}

	/**
	 * @return the universallyUniqueIdentifier
	 */
	@XmlAttribute(name = "universallyUniqueIdentifier")
	public String getUniversallyUniqueIdentifier() {
		return universallyUniqueIdentifier;
	}

	/**
	 * @param universallyUniqueIdentifier the universallyUniqueIdentifier to
	 * set
	 */
	public void setUniversallyUniqueIdentifier(String universallyUniqueIdentifier) {
		this.universallyUniqueIdentifier = universallyUniqueIdentifier;
	}

	/**
	 * @return the userStory
	 */
	@XmlElementWrapper(name = "userStorys")
	@XmlElement(name = "userStory")
	public List<Storie> getUserStory() {
		return userStory;
	}

	/**
	 * @param userStory the userStory to set
	 */
	public void setUserStory(List<Storie> userStory) {
		this.userStory = userStory;
	}

	/**
	 * @return the coverImgUrl
	 */
	@XmlAttribute(name = "coverImgUrl")
	public String getCoverImgUrl() {
		return coverImgUrl;
	}

	/**
	 * @param coverImgUrl the coverImgUrl to set
	 */
	public void setCoverImgUrl(String coverImgUrl) {
		this.coverImgUrl = coverImgUrl;
	}

	/**
	 * @return the profileImgUrl
	 */
	@XmlAttribute(name = "profileImgUrl")
	public String getProfileImgUrl() {
		return profileImgUrl;
	}

	/**
	 * @param profileImgUrl the profileImgUrl to set
	 */
	public void setProfileImgUrl(String profileImgUrl) {
		this.profileImgUrl = profileImgUrl;
	}

	/**
	 * @return the profileText
	 */
	@XmlElement(name = "profileText")
	public String getProfileText() {
		return profileText;
	}

	/**
	 * @param profileText the profileText to set
	 */
	public void setProfileText(String profileText) {
		this.profileText = profileText;
	}

	/**
	 * @return the nickname
	 */
	@XmlElement(name = "nickname")
	public String getNickname() {
		return nickname;
	}

	/**
	 * @param nickname the nickname to set
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	/**
	 * @return the userStoryCount
	 */
	@XmlElement(name = "userStoryCount")
	public String getUserStoryCount() {
		return userStoryCount;
	}

	/**
	 * @param userStoryCount the userStoryCount to set
	 */
	public void setUserStoryCount(String userStoryCount) {
		this.userStoryCount = userStoryCount;
	}

	/**
	 * @return the followers
	 */
	@XmlElementWrapper(name = "followerss")
	@XmlElement(name = "followers")
	public List<PersonnelFollo> getFollowers() {
		return followers;
	}

	/**
	 * @param followers the followers to set
	 */
	public void setFollowers(List<PersonnelFollo> followers) {
		this.followers = followers;
	}

	/**
	 * @return the following
	 */
	@XmlElementWrapper(name = "followings")
	@XmlElement(name = "following")
	public List<PersonnelFollo> getFollowing() {
		return following;
	}

	/**
	 * @param following the following to set
	 */
	public void setFollowing(List<PersonnelFollo> following) {
		this.following = following;
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

	/**
	 * @return the isFriend
	 */
	@XmlAttribute(name = "isAddFriend")
	public String getIsAddFriend() {
		return isAddFriend;
	}

	/**
	 * @param isFriend the isFriend to set
	 */
	public void setIsAddFriend(String isAddFriend) {
		this.isAddFriend = isAddFriend;
	}

	/**
	 * @return the isFriendList
	 */
	@XmlAttribute(name = "isFriendList")
	public String getIsFriendList() {
		return isFriendList;
	}

	/**
	 * @param isFriendList the isFriendList to set
	 */
	public void setIsFriendList(String isFriendList) {
		this.isFriendList = isFriendList;
	}

	@Override
	public String toString() {
		return "Personnel{" + "id=" + id + ", followerCount=" + followerCount + ", followingCount=" + followingCount + ", universallyUniqueIdentifier=" + universallyUniqueIdentifier + ", userStory=" + userStory + ", coverImgUrl=" + coverImgUrl + ", profileImgUrl=" + profileImgUrl + ", profileText=" + profileText + ", nickname=" + nickname + ", userStoryCount=" + userStoryCount + ", followers=" + followers + ", following=" + following + ", httpSessionUser=" + httpSessionUser + ", isAddFriend=" + isAddFriend + ", isFriendList=" + isFriendList + '}';
	}

}

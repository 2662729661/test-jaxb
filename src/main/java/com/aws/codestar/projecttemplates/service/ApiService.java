package com.aws.codestar.projecttemplates.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import javax.servlet.http.HttpSession;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.aws.codestar.projecttemplates.configuration.AuthHttpClient;
import com.aws.codestar.projecttemplates.jaxbXml.HomePageJAXB;
import com.aws.codestar.projecttemplates.jaxbXml.PersonnelJAXB;
import com.aws.codestar.projecttemplates.model.Emotion;
import com.aws.codestar.projecttemplates.pojo.HttpSessionUser;
import com.aws.codestar.projecttemplates.pojo.Personnel;
import com.aws.codestar.projecttemplates.pojo.PersonnelFollo;
import com.aws.codestar.projecttemplates.pojo.Embedded;
import com.aws.codestar.projecttemplates.pojo.HomePageStorie;
import com.aws.codestar.projecttemplates.pojo.StoryImage;
import com.aws.codestar.projecttemplates.suppot.BeanToXml;
import java.util.List;
import javax.xml.bind.JAXBException;

@Component
public class ApiService {

	@Autowired
	private RestTemplate restTemplate;

	String resourceUrl = "https://redan-api.herokuapp.com/";

	/**
	 * 返回首页数据
	 *
	 * @param httpSession
	 * @return
	 * @throws ParserConfigurationException
	 * @throws UnsupportedEncodingException
	 * @throws JSONException
	 * @throws TransformerException
	 * @throws IOException
	 */
	public Embedded getStory(HttpSession httpSession) throws ParserConfigurationException, UnsupportedEncodingException, JSONException, TransformerException, IOException {

		ResponseEntity<Embedded> response = restTemplate.getForEntity(resourceUrl + "stories/", Embedded.class);
		Embedded body = response.getBody();
		getStoryStoryImage(body);
		//封装的登入数据
		if (httpSession.getAttribute("id") != null) {
			body.setHttpSessionUser(getHttpSessionUser(httpSession));
		}

		return body;
	}

	//图片数据
	public void getStoryStoryImage(Embedded body) {
		List<HomePageStorie> stories = body.getEmbedded().getStories();
		for (int i = 0; i < stories.size(); i++) {
			HomePageStorie homePageStorie = stories.get(i);
			List<StoryImage> storyImages = homePageStorie.getStoryImage();
			String mode = "";
			for (int j = 0; j < storyImages.size(); j++) {
				if (j == 0) {
					mode = "carousel-item active";
				} else {
					mode = "carousel-item";
				}
				String count = String.valueOf(j);
				StoryImage storyImage = storyImages.get(j);
				storyImage.setMode(mode);
				storyImage.setCount(count);
			}
		}
	}

	/**
	 * 返回首页数据 Xml格式的
	 *
	 * @param httpSession
	 * @return
	 * @throws ParserConfigurationException
	 * @throws UnsupportedEncodingException
	 * @throws JSONException
	 * @throws TransformerException
	 * @throws IOException
	 */
	public Document getStoryXml(HttpSession httpSession) throws ParserConfigurationException, JSONException, TransformerException, IOException, JAXBException {
		Embedded story = getStory(httpSession);

		HomePageJAXB homePageJAXB = new HomePageJAXB();
		homePageJAXB.setHomePage(story);
		Document beanToXml = BeanToXml.beanToXml(homePageJAXB, HomePageJAXB.class);

		return beanToXml;
	}

	/**
	 * 获取个人主页数据 Xml数据格式
	 *
	 * @param id 参数
	 * @return
	 */
	public Document getPersonnelsXml(String id, HttpSession httpSession) throws ParserConfigurationException, IOException, JAXBException {
		Personnel personnel = getPersonnels(id, httpSession);

		PersonnelJAXB personnelJAXB = new PersonnelJAXB();
		personnelJAXB.setPersonnel1Homepage(personnel);
		Document beanToXml = BeanToXml.beanToXml(personnelJAXB, PersonnelJAXB.class);

		return beanToXml;
	}

	/**
	 * 获取个人主页数据 personnels/search/findOneById 路径
	 *
	 * @param id 参数
	 * @return
	 */
	public Personnel getPersonnels(String id, HttpSession httpSession) throws ParserConfigurationException, IOException {

		ResponseEntity<Personnel> response = restTemplate.getForEntity(resourceUrl + "personnels/search/findOneById?id=" + id, Personnel.class);

		Personnel personnel1Homepage = response.getBody();

		if (httpSession.getAttribute("id") != null) {

			personnel1Homepage.setHttpSessionUser(getHttpSessionUser(httpSession));

			//判断该主页是否属于该用户
			if (id.equals(httpSession.getAttribute("id").toString())) {
				//按键置灰
				personnel1Homepage.setIsAddFriend("true");

				//显示好友列表
				personnel1Homepage.setIsFriendList("true");

			} else {
				//判断是否已经添加过该用户
				List<PersonnelFollo> following = personnel1Homepage.getFollowing();
				for (int i = 0; i < following.size(); i++) {
					//获取已添加好友id
					String followingId = following.get(i).getRelatedUser().getId();
					if (followingId.equals(httpSession.getAttribute("id"))) {
						//已添加按键置灰
						personnel1Homepage.setIsAddFriend("true");
					}
				}
			}
		}
		return personnel1Homepage;
	}

	/**
	 * 封装HttpSessionUser数据
	 *
	 * @param httpSession
	 * @return
	 */
	public HttpSessionUser getHttpSessionUser(HttpSession httpSession) {
		HttpSessionUser httpSessionUser = new HttpSessionUser();
		httpSessionUser.setId(httpSession.getAttribute("id").toString());
		httpSessionUser.setMe(httpSession.getAttribute("me").toString());
		httpSessionUser.setNickname(httpSession.getAttribute("nickname").toString());
		httpSessionUser.setPersonnelHref(httpSession.getAttribute("personnelHref").toString());
		httpSessionUser.setThirdParty(httpSession.getAttribute("thirdParty").toString());
		return httpSessionUser;
	}

	/**
	 * 查询该用户所有好友
	 *
	 * @param id
	 */
	public Personnel findFriendAll(HttpSession httpSession) throws IOException, ParserConfigurationException {

		//获取该用户所有数据不包括登入数据
		ResponseEntity<Personnel> response = restTemplate.getForEntity(resourceUrl + "personnels/search/findOneById?id=" + httpSession.getAttribute("id").toString(), Personnel.class);

		Personnel personnel = response.getBody();

		System.out.println("查询该用户所有好友--" + personnel.toString());

		if (personnel != null) {
			return personnel;
		}
		return null;
	}

	/**
	 * 呼叫 ../stories/" + id + "/storyComment/.. 取得某故事之最新留言
	 *
	 * @param storyId
	 * @return DOM 字串
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws TransformerConfigurationException
	 * @throws TransformerException
	 */
	public JSONArray getStoryComment(String storyId) throws ParserConfigurationException, IOException, TransformerConfigurationException, TransformerException {
		String stringUri = "stories/" + storyId + "/storyComment/";
		HttpGet httpGet = new AuthHttpClient().bulidHttpGet(stringUri);
		JSONArray jSONArrayOfstoryComments = new JSONArray();

		CloseableHttpResponse closeableHttpResponse = HttpClients.createDefault().execute(httpGet);
		try {
			HttpEntity httpEntity = closeableHttpResponse.getEntity();
			if (httpEntity != null) {
				String stringOfEntity = EntityUtils.toString(httpEntity, "UTF-8");
				JSONObject jSONObjectOfEntity = new JSONObject(stringOfEntity);
				String stringOfEmbedded = jSONObjectOfEntity.get("_embedded").toString();
				jSONArrayOfstoryComments = new JSONObject(stringOfEmbedded).getJSONArray("storyComments");
			}
		} finally {
			closeableHttpResponse.close();
		}
		return jSONArrayOfstoryComments;
	}

	/**
	 * 呼叫 ../personnels/search/.. 尋找帳號是否存在, 存在傳回 JSONObject; 反之回傳null
	 *
	 * @param thirdParty
	 * @param userId
	 * @return 帳號相關資訊之JSONObject
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public JSONObject findOneByThirdPartyId(String thirdParty, String userId) throws ClientProtocolException, IOException, URISyntaxException {
		String key = thirdParty + "Id";
		String url = "";
		switch (thirdParty) {
			case ("facebook"):
				url = "findOneByFacebookId";
				break;
			case ("google"):
				url = "findOneByGoogleId";
				break;
			default:
				url = "findOneByLineId";
				break;
		}

		ArrayList<NameValuePair> pairList = new ArrayList();
		pairList.add(new BasicNameValuePair(key, userId));

		URIBuilder builder = new URIBuilder(new AuthHttpClient().getHost() + "personnels/search/" + url);
		builder.setParameters(pairList);
		HttpGet httpGet = new AuthHttpClient().bulidHttpViaURI(builder.build());

		CloseableHttpResponse closeableHttpResponse = HttpClients.createDefault().execute(httpGet);
		HttpEntity httpEntity = closeableHttpResponse.getEntity();
		if (httpEntity == null) {
//			Logger.getGlobal().info("發生錯誤, Method: ApiService." + url);
			System.out.println("發生錯誤, Method: ApiService." + url + "()");
			return null;
		}

		JSONObject jSONObjectOfResult = null;
		String result = EntityUtils.toString(httpEntity, "UTF-8");

		if (result != null && result.length() > 0) {

			System.out.println("httpEntity: " + result);
			jSONObjectOfResult = new JSONObject(result);
			System.out.println("nickname: " + jSONObjectOfResult.get("nickname").toString());
		}

		return jSONObjectOfResult;
	}

	/**
	 * 呼叫 ../personnels/search/findOneByEmail 尋找帳號是否存在, 存在傳回 JSONObject;
	 * 反之回傳null
	 *
	 * @param email
	 * @return
	 * @throws URISyntaxException
	 * @throws IOException
	 */
	public JSONObject findOneByEmail(String email) throws URISyntaxException, IOException {
		ArrayList<NameValuePair> pairList = new ArrayList();
		pairList.add(new BasicNameValuePair("email", email));

		URIBuilder uRIBuilder = new URIBuilder(new AuthHttpClient().getHost() + "personnels/search/findOneByEmail");
		uRIBuilder.setParameters(pairList);
		HttpGet httpGet = new AuthHttpClient().bulidHttpViaURI(uRIBuilder.build());

		CloseableHttpResponse closeableHttpResponse = HttpClients.createDefault().execute(httpGet);
		HttpEntity httpEntity = closeableHttpResponse.getEntity();
		if (httpEntity == null) {
			System.out.println("發生錯誤, Method: ApiService.findOneByEmail()");
			return null;
		}

		JSONObject jSONObjectOfResult = null;
		String result = EntityUtils.toString(httpEntity, "UTF-8");
		if (result != null && result.length() > 0) {

			System.out.println("httpEntity: " + result);
			jSONObjectOfResult = new JSONObject(result);
			System.out.println("nickname: " + jSONObjectOfResult.get("nickname").toString());
		}

		return jSONObjectOfResult;
	}

	/**
	 *
	 * @param nickname
	 * @param facebookId
	 * @param googleId
	 * @param lineId
	 * @param email
	 * @param lastname
	 * @param firstname
	 * @param birth
	 * @param gender
	 * @param storeName
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public JSONObject registerUser(String nickname, String facebookId, String googleId, String lineId, String email, String lastname, String firstname, String birth, String gender, String storeName) throws ClientProtocolException, IOException {
		CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
		HttpPost httpPost = new AuthHttpClient().bulidHttpPost("personnels");

		JSONObject jSONObjectOfParams = new JSONObject();
		jSONObjectOfParams.put("nickname", nickname);
		jSONObjectOfParams.put("facebookId", facebookId);
		jSONObjectOfParams.put("googleId", googleId);
		jSONObjectOfParams.put("lineId", lineId);
		jSONObjectOfParams.put("email", email);
		jSONObjectOfParams.put("lastname", lastname);
		jSONObjectOfParams.put("firstname", firstname);
		jSONObjectOfParams.put("birth", birth);
		jSONObjectOfParams.put("gender", gender);
		jSONObjectOfParams.put("storeName", storeName);
		System.out.println("jSONObjectOfParams: " + jSONObjectOfParams.toString());

		StringEntity stringEntityOfPersonnel = new StringEntity(jSONObjectOfParams.toString(), "UTF-8");
		System.out.println("stringEntityOfPersonnel: " + stringEntityOfPersonnel);

		httpPost.setEntity(stringEntityOfPersonnel);
		httpPost.setHeader("Content-type", "application/json");
		CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(httpPost);

		JSONObject jSONObjectOfResult = cover2Json(closeableHttpResponse);

		closeableHttpResponse.close();
		closeableHttpClient.close();
		return jSONObjectOfResult;

	}

	/*
		  {
		 	"content": "this is a story content.",		 
		 	"imgUrls":{"URL","URL","URL"}
		    "Author": "http://localhost:8080/personnels/1" 
		  }
	 */
	public String postStory(String content, String personnelsUri, String[] imgUrls) throws Exception {
		CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
		HttpPost httpPost = new AuthHttpClient().bulidHttpPost("stories");

		JSONObject jSONObjectOfParams = new JSONObject();
		jSONObjectOfParams.put("content", content);
//		jSONObjectOfParams.put("imgUrls", new JSONArray(Arrays.asList(imgUrls)));
		jSONObjectOfParams.put("author", "https://redan-api.herokuapp.com/personnels/" + personnelsUri);

		System.out.println("jSONObjectOfParams: " + jSONObjectOfParams.toString());

		StringEntity stringEntityOfPersonnel = new StringEntity(jSONObjectOfParams.toString(), "UTF-8");

		httpPost.setEntity(stringEntityOfPersonnel);
		httpPost.setHeader("Content-type", "application/hal+json;charset=UTF-8");
		CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(httpPost);

		System.out.println("----------------------------------------");
		System.out.println(closeableHttpResponse.getStatusLine());

		JSONObject storyInfo = new JSONObject(
			EntityUtils.toString(closeableHttpResponse.getEntity()));
		String storyUrl = storyInfo.getJSONObject("_links").getJSONObject("self").getString("href");

		Arrays.asList(imgUrls).forEach(imgUrl -> {
			try {
				System.out.println(postImgUrl(imgUrl, null, storyUrl));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		closeableHttpResponse.close();
		closeableHttpClient.close();
		return "";

	}

	/*	
		{
			"story":"http://localhost:8080/stories/1",
			"who":"http://localhost:8080/personnels/1",
			"content":"oka"
		}
	 */
	public String postComments(String content, String personnelsUri, String storyUri) throws ClientProtocolException, IOException {
		CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
		HttpPost httpPost = new AuthHttpClient().bulidHttpPost("storyComments");

		JSONObject jSONObjectOfParams = new JSONObject();
		jSONObjectOfParams.put("story", storyUri);
		jSONObjectOfParams.put("who", personnelsUri);
		jSONObjectOfParams.put("content", content);

		System.out.println("jSONObjectOfParams: " + jSONObjectOfParams.toString());

		StringEntity stringEntityOfPersonnel = new StringEntity(jSONObjectOfParams.toString(), "UTF-8");

		httpPost.setEntity(stringEntityOfPersonnel);
		httpPost.setHeader("Content-type", "application/json");
		CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(httpPost);

		closeableHttpResponse.close();
		closeableHttpClient.close();
		return "留言成功";
	}

	/*
	{	
		"url":"http://localhost:8080/img1",
		"content":"oka",
		"story":"http://localhost:8080/stories/1"
	}
	 */
	public String postImgUrl(String imgUrl, String content, String storyUrl) throws ClientProtocolException, IOException {
		CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
		HttpPost httpPost = new AuthHttpClient().bulidHttpPost("storyImages");

		JSONObject jSONObjectOfParams = new JSONObject();
		jSONObjectOfParams.put("url", imgUrl);
		jSONObjectOfParams.put("content", "redan");
		jSONObjectOfParams.put("story", storyUrl);

		System.out.println("postImgUrl: " + jSONObjectOfParams.toString());

		StringEntity stringEntityOfPersonnel = new StringEntity(jSONObjectOfParams.toString(), "UTF-8");

		httpPost.setEntity(stringEntityOfPersonnel);
		httpPost.setHeader("Content-type", "application/hal+json;charset=UTF-8");
		CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(httpPost);
		System.out.println(EntityUtils.toString(closeableHttpResponse.getEntity()));
		closeableHttpResponse.close();
		closeableHttpClient.close();
		return "上傳成功";
	}

	/*
	发送好友请求
	
		{
			"relatedUser":"https://redan-api.herokuapp.com/personnels/3"
			"relatingUser":"https://redan-api.herokuapp.com/personnels/1"
			"status":0
		 }
	 */
	public void postFriend(String relatedId, String relatingId) throws IOException {
		JSONObject jSONFriend = new JSONObject();

		//获取请求发送者者
		jSONFriend.put("relatedUser", "https://redan-api.herokuapp.com/personnels/" + relatedId);

		//获取被请求者
		jSONFriend.put("relatingUser", "https://redan-api.herokuapp.com/personnels/" + relatingId);

		//状态
		jSONFriend.put("status", "0");

		//创建连接
		HttpPost httpPost = new AuthHttpClient().bulidHttpPost("userRelationships");

		//添加请求头
		httpPost.setHeader("Content-type", "application/hal+json;charset=UTF-8");

		//封装数据
		StringEntity stringEntityGoodFriend = new StringEntity(jSONFriend.toString(), "UTF-8");

		//添加请求体
		httpPost.setEntity(stringEntityGoodFriend);

		//发送请求
		CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
		CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(httpPost);

		//查看结果
		String storyUrl = new JSONObject(EntityUtils.toString(closeableHttpResponse.getEntity())).toString();
		System.out.println("添加成功---------------" + storyUrl);

		closeableHttpResponse.close();
		closeableHttpClient.close();

	}

	/**
	 * 会员数据
	 *
	 * @param id
	 * @throws ParserConfigurationException
	 * @throws IOException
	 */
	public Document memberCenterXml(String id) throws ParserConfigurationException, IOException {
		//创建文档对象
		DocumentBuilderFactory newInstance = DocumentBuilderFactory.newInstance();
		DocumentBuilder newDocumentBuilder = newInstance.newDocumentBuilder();
		Document doc = newDocumentBuilder.newDocument();

		//生成根元素
		Element documentElement = doc.createElement("document");
		doc.appendChild(documentElement);

		//创建连接
		HttpGet httpGet = new AuthHttpClient().bulidHttpGet("personnels/" + id);

		//获取请求体
		CloseableHttpResponse execute = HttpClients.createDefault().execute(httpGet);
		HttpEntity entity = execute.getEntity();

		if (entity != null) {
			String string = EntityUtils.toString(entity, "UTF-8");

			JSONObject jsonObject = new JSONObject(string);

			//universallyUniqueIdentifier-唯一标识符
			Element universallyUniqueIdentifierElement = doc.createElement("universallyUniqueIdentifier");
			universallyUniqueIdentifierElement.appendChild(doc.createTextNode(jsonObject.get("universallyUniqueIdentifier").toString()));
			documentElement.appendChild(universallyUniqueIdentifierElement);

			//nickname-名字
			Element nicknameElement = doc.createElement("nickname");
			nicknameElement.appendChild(doc.createTextNode(jsonObject.get("nickname").toString()));
			documentElement.appendChild(nicknameElement);

			//facebookId-脸谱网id
			Element facebookIdElement = doc.createElement("facebookId");
			facebookIdElement.appendChild(doc.createTextNode(jsonObject.get("facebookId").toString()));
			documentElement.appendChild(facebookIdElement);

			//googleId-谷歌id
			Element googleIdElement = doc.createElement("googleId");
			googleIdElement.appendChild(doc.createTextNode(jsonObject.get("googleId").toString()));
			documentElement.appendChild(googleIdElement);

			//lineId-
			Element lineIdElement = doc.createElement("lineId");
			lineIdElement.appendChild(doc.createTextNode(jsonObject.get("lineId").toString()));
			documentElement.appendChild(lineIdElement);

			//email-邮箱/账号
			Element emailElement = doc.createElement("email");
			emailElement.appendChild(doc.createTextNode(jsonObject.get("email").toString()));
			documentElement.appendChild(emailElement);

			//lastname-姓
			Element lastnameElement = doc.createElement("lastname");
			lastnameElement.appendChild(doc.createTextNode(jsonObject.get("lastname").toString()));
			documentElement.appendChild(lastnameElement);

			//firstname-初始名称
			Element firstnameElement = doc.createElement("firstname");
			firstnameElement.appendChild(doc.createTextNode(jsonObject.get("firstname").toString()));
			documentElement.appendChild(firstnameElement);

			//birth-生日
			Element birthElement = doc.createElement("birth");
			birthElement.appendChild(doc.createTextNode(jsonObject.get("birth").toString()));
			documentElement.appendChild(birthElement);

			//gender-性别
			Element genderElement = doc.createElement("gender");
			genderElement.appendChild(doc.createTextNode(jsonObject.get("gender").toString()));
			documentElement.appendChild(genderElement);

			//storeName-商铺名称
			Element storeNameElement = doc.createElement("storeName");
			storeNameElement.appendChild(doc.createTextNode(jsonObject.get("storeName").toString()));
			documentElement.appendChild(storeNameElement);

			//openedAt-账号成立时间
			Element openedAtElement = doc.createElement("openedAt");
			openedAtElement.appendChild(doc.createTextNode(jsonObject.get("openedAt").toString()));
			documentElement.appendChild(openedAtElement);

			//coverImgUrl-主页背景图
			Element coverImgUrlElement = doc.createElement("coverImgUrl");
			coverImgUrlElement.appendChild(doc.createTextNode(jsonObject.get("coverImgUrl").toString()));
			documentElement.appendChild(coverImgUrlElement);

			//profileImgUrl-头像
			Element profileImgUrlElement = doc.createElement("profileImgUrl");
			profileImgUrlElement.appendChild(doc.createTextNode(jsonObject.get("profileImgUrl").toString()));
			documentElement.appendChild(profileImgUrlElement);

			//profileText-个人简介
			Element profileTextElement = doc.createElement("profileText");
			profileTextElement.appendChild(doc.createTextNode(jsonObject.get("profileText").toString()));
			documentElement.appendChild(profileTextElement);

			return doc;
		}
		return null;
	}

	private JSONObject cover2Json(CloseableHttpResponse closeableHttpResponse) {
		HttpEntity httpEntity = closeableHttpResponse.getEntity();
		if (httpEntity == null) {
			System.out.println("發生錯誤, Method: cover2Json()");
			return null;
		}
		JSONObject jSONObjectOfResult = null;
		String result = null;
		try {
			result = EntityUtils.toString(httpEntity, "UTF-8");
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (result != null && result.length() > 0) {

			System.out.println("httpEntity: " + result);
			jSONObjectOfResult = new JSONObject(result);
			System.out.println("nickname: " + jSONObjectOfResult.get("nickname").toString());
		}

		return jSONObjectOfResult;
	}

	//判断是否收藏
	public boolean isBookmark(Emotion emotion) throws IOException {
		//生成路径https://redan-api.herokuapp.com/bookmarks/search/findByMackeeper

		//获取请求体
		return true;
	}

	/**
	 * 收藏文章
	 *
	 * @param emotion
	 * @param httpSession
	 *
	 * {
	 * mackeeper:"http://localhost:8080/personnels/1"
	 * story:"http://localhost:8080/storys/1" }
	 */
	public void bookmark(Emotion emotion, HttpSession httpSession) throws IOException {
		JSONObject jSONFriend = new JSONObject();

		//收藏者
		jSONFriend.put("mackeeper", "https://redan-api.herokuapp.com/personnels/" + httpSession.getAttribute("id"));

		//收藏的文章
		jSONFriend.put("targetStory", "https://redan-api.herokuapp.com/stories/" + emotion.getStory());

		//创建连接
		HttpPost httpPost = new AuthHttpClient().bulidHttpPost("bookmarks");

		//添加请求头
		httpPost.setHeader("Content-type", "application/hal+json;charset=UTF-8");

		//封装数据
		StringEntity stringEntityGoodFriend = new StringEntity(jSONFriend.toString(), "UTF-8");

		//添加请求体
		httpPost.setEntity(stringEntityGoodFriend);

		//发送请求
		CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
		CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(httpPost);

		//查看结果
		String storyUrl = new JSONObject(EntityUtils.toString(closeableHttpResponse.getEntity())).toString();
		System.out.println("添加成功---------------" + storyUrl);

		closeableHttpResponse.close();
		closeableHttpClient.close();
	}
}

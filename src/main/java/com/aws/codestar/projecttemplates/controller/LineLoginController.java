package com.aws.codestar.projecttemplates.controller;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ResolvableType;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class LineLoginController {

	private static final String authorizationRequestBaseUri = "oauth2/authorize-client";
	Map<String, String> oauth2AuthenticationUrls = new HashMap<>();

	@Autowired
	private ClientRegistrationRepository clientRegistrationRepository;
	@Autowired
	private OAuth2AuthorizedClientService authorizedClientService;

	@GetMapping("/oauth_login")
	public String getLoginPage(Model model) {
		Iterable<ClientRegistration> clientRegistrations = null;
		ResolvableType type = ResolvableType.forInstance(clientRegistrationRepository).as(Iterable.class);
		if (type != ResolvableType.NONE && ClientRegistration.class.isAssignableFrom(type.resolveGenerics()[0])) {
			clientRegistrations = (Iterable<ClientRegistration>) clientRegistrationRepository;
		}

		clientRegistrations.forEach(registration -> oauth2AuthenticationUrls.put(registration.getClientName(),
				authorizationRequestBaseUri + "/" + registration.getRegistrationId()));

		model.addAttribute("urls", oauth2AuthenticationUrls);

		return "oauth_login<br>" + oauth2AuthenticationUrls;
	}

	@GetMapping("/loginFailure")
	public String loginFailure() throws UnknownHostException, SocketException {
		String ip = null;
		try(final DatagramSocket socket = new DatagramSocket()){
			  socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
			   ip = socket.getLocalAddress().getHostAddress();
			}
		return InetAddress.getLocalHost().getHostName() +" is loginFailure<br> your outbound IP is"+ip;
	}

	@GetMapping("/loginSuccess")
	public String getLoginInfo(Model model, OAuth2AuthenticationToken authentication) {

		OAuth2AuthorizedClient client = authorizedClientService
				.loadAuthorizedClient(authentication.getAuthorizedClientRegistrationId(), authentication.getName());
		System.out.println(authentication.getAuthorizedClientRegistrationId());
		System.out.println(authentication.getName());
		System.out.println(client);

		String userInfoEndpointUri = client.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUri();
		String res = null;
		if (!StringUtils.isEmpty(userInfoEndpointUri)) {
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + client.getAccessToken().getTokenValue());

			HttpEntity<String> entity = new HttpEntity<String>("", headers);

			ResponseEntity<Map> response = restTemplate.exchange(userInfoEndpointUri, HttpMethod.GET, entity,
					Map.class);
			Map userAttributes = response.getBody();
			model.addAttribute("name", userAttributes.get("name"));
			res = userAttributes.toString();
		}

		return "loginSuccess:userAttributes<br>"+res;
	}
}

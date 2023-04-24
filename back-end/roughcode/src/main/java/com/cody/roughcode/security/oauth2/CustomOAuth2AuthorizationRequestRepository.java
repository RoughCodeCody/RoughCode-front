package com.cody.roughcode.security.oauth2;

import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomOAuth2AuthorizationRequestRepository<T extends OAuth2AuthorizationRequest> implements
        AuthorizationRequestRepository<OAuth2AuthorizationRequest> {

    //AuthorizationRequestRepository는 인가 요청을 시작한 시점부터 인가 요청을 받는 시점까지 OAuth2AuthorizationRequest를 유지해줌
    //default는 HttpSession에 저장하는 HttpSessionOAuth2AuthorizationRequestRepository이다
    //HttpSessionOAuth2AuthorizationRequestRepository는 세션을 이용해서 저장을 하는데
    //우리는 프론트로부터 Authorization code를 받기 때문에, 이 과정이 필요없다
    //즉, remove과정만 진행하게 되므로 remove에서 올바른 OAuth2AuthorizationRequest를 반환해주면 된다
    private final ClientRegistrationRepository clientRegistrationRepository;

    public CustomOAuth2AuthorizationRequestRepository(
            ClientRegistrationRepository clientRegistrationRepository) {
        this.clientRegistrationRepository = clientRegistrationRepository;
    }

    //client registration 설정을 가지고
    //RequestResolver의 로직을 따라간다
    private static String expandRedirectUri(HttpServletRequest request, ClientRegistration clientRegistration) {
        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("registrationId", clientRegistration.getRegistrationId());
        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(
                        UrlUtils.buildFullRequestUrl(request))
                .replacePath(request.getContextPath())
                .replaceQuery(null)
                .fragment(null)
                .build();

        String scheme = uriComponents.getScheme();
        uriVariables.put("baseScheme", (scheme != null) ? scheme : "");
        String host = uriComponents.getHost();
        uriVariables.put("baseHost", (host != null) ? host : "");
        // following logic is based on HierarchicalUriComponents#toUriString()
        int port = uriComponents.getPort();
        uriVariables.put("basePort", (port == -1) ? "" : ":" + port);
        String path = uriComponents.getPath();
        if (org.springframework.util.StringUtils.hasLength(path)) {
            if (path.charAt(0) != '/') {
                path = '/' + path;
            }
        }
        uriVariables.put("basePath", (path != null) ? path : "");
        uriVariables.put("baseUrl", uriComponents.toUriString());
        uriVariables.put("action", "login");
        return UriComponentsBuilder.fromUriString(clientRegistration.getRedirectUri())
                .buildAndExpand(uriVariables)
                .toUriString();
    }

    @Override
    public OAuth2AuthorizationRequest loadAuthorizationRequest(HttpServletRequest request) {
        return null;
    }

    @Override
    public void saveAuthorizationRequest(OAuth2AuthorizationRequest authorizationRequest, HttpServletRequest request, HttpServletResponse response) {
    }

    @Override
    public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest request) {
        Assert.notNull(request, "request cannot be null");

        OAuth2AuthorizationRequest originalRequest;

        //state에 google과 kakao를 구분할 수 있는 string을 넣어놓았다 올바른 방법이 아니므로 다른 방법을 찾아봐야 한다
        String registrationId = request.getParameter("state");
        ClientRegistration clientRegistration = clientRegistrationRepository.findByRegistrationId(registrationId);
        if (clientRegistration == null) {
            throw new IllegalArgumentException("Invalid Client Registration with Id: " + registrationId);
        }
        OAuth2AuthorizationRequest.Builder builder = OAuth2AuthorizationRequest.authorizationCode()
                .attributes((attrs) ->
                        attrs.put(OAuth2ParameterNames.REGISTRATION_ID,
                                clientRegistration.getRegistrationId()));

        String redirectUriStr = expandRedirectUri(request, clientRegistration);

        builder.clientId(clientRegistration.getClientId())
                .authorizationUri(clientRegistration.getProviderDetails().getAuthorizationUri())
                .redirectUri(redirectUriStr)
                .scopes(clientRegistration.getScopes())
                .state(request.getParameter("state"));

        originalRequest = builder.build();

        return originalRequest;
    }

}

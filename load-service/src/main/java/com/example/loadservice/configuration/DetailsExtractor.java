package com.example.loadservice.configuration;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.DefaultOAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.security.oauth2.server.resource.introspection.SpringOpaqueTokenIntrospector;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DetailsExtractor implements OpaqueTokenIntrospector {
    private final OpaqueTokenIntrospector delegate = new SpringOpaqueTokenIntrospector("http://localhost:8082/oauth2/introspect",
            "resource", "secret");

    @Override
    public OAuth2AuthenticatedPrincipal introspect(String token) {
        OAuth2AuthenticatedPrincipal oAuth2AuthenticatedPrincipal = delegate.introspect(token);
        List<String> auth = oAuth2AuthenticatedPrincipal.getAttribute("authorities");
        Integer id = oAuth2AuthenticatedPrincipal.getAttribute("id");

        Map<String, Object> map = new HashMap<>();
        map.put("id", Long.valueOf(id.longValue()));

        List<GrantedAuthority> grantedAuthorities = new LinkedList<>();
        auth.forEach(name -> {
            grantedAuthorities.add(new SimpleGrantedAuthority(name));
        });
        return new DefaultOAuth2AuthenticatedPrincipal(oAuth2AuthenticatedPrincipal.getName(),
                map,
                grantedAuthorities);
    }

}

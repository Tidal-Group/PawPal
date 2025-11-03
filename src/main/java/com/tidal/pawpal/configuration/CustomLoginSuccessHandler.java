package com.tidal.pawpal.configuration;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(
        HttpServletRequest request,
        HttpServletResponse response,
        Authentication authentication
    ) throws IOException, ServletException {

        String redirectUrl = request.getParameter("redirectUrl");
        if (redirectUrl != null && !redirectUrl.isEmpty()) {
            UriComponents uriComponents= UriComponentsBuilder.fromUriString(redirectUrl).build();
            Map<String, List<String>> queryParams = uriComponents.getQueryParams();
            boolean hasLoginModal = queryParams.get("modal").get(0).equals("loginModal");

            if(hasLoginModal) {
                UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(redirectUrl);
                uriBuilder.replaceQueryParam("modal", (Object[]) null);
                redirectUrl = uriBuilder.build().toUriString();
            }

            response.sendRedirect(redirectUrl);
            return;
        }
        
        boolean isAdmin = authentication.getAuthorities().stream()
            .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (isAdmin) {
            response.sendRedirect("/admin/dashboard");
        } else {
            response.sendRedirect("/");
        }

    }
    
}

package com.tidal.pawpal.utils;

import java.util.Map;

import org.springframework.web.util.UriComponentsBuilder;

public final class ControllerUtils {

    private ControllerUtils() {
        throw new AssertionError("No " + this.getClass().getName() + " instances for you!");
    }

    public static final String redirectToSame(String redirectUrl) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(redirectUrl);
        uriBuilder.replaceQueryParam("modal", (Object[]) null);
        return "redirect:" + uriBuilder.build().toUriString();
    }

    public static final String redirectToModal(String redirectUrl, String modalId) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(redirectUrl);
        uriBuilder.replaceQueryParam("modal", modalId);
        return "redirect:" + uriBuilder.build().toUriString();
    }

    public static final String redirectToView(String redirectUrl, String viewId) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(redirectUrl);
        uriBuilder.replaceQueryParam("view", viewId);
        return "redirect:" + uriBuilder.build().toUriString();
    }

    public static final String redirectToPathView(String redirectUrl, String path, String viewId) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(redirectUrl);
        uriBuilder.path(path);
        uriBuilder.replaceQueryParam("view", viewId);
        return "redirect:" + uriBuilder.build().toUriString();
    }

    public static final String redirectToQueryParams(String redirectUrl, Map<String, String> queryParams) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(redirectUrl);
        queryParams.entrySet().forEach((entry) -> uriBuilder.replaceQueryParam(entry.getKey(), entry.getValue()));
        return "redirect:" + uriBuilder.build().toUriString();
    }

    public static final String redirectToPathQueryParams(String redirectUrl, String path, Map<String, String> queryParams) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(redirectUrl);
        uriBuilder.path(path);
        queryParams.entrySet().forEach((entry) -> uriBuilder.replaceQueryParam(entry.getKey(), entry.getValue()));
        return "redirect:" + uriBuilder.build().toUriString();
    }

    public static final String redirectToFragment(String redirectUrl, String fragment) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(redirectUrl);
        uriBuilder.fragment(fragment);
        return "redirect:" + uriBuilder.build().toUriString();
    }

}

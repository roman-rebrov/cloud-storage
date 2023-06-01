package com.cloudstorage.storage;

import org.springframework.http.HttpHeaders;
import org.springframework.util.MultiValueMap;

import java.net.URI;
import java.net.URISyntaxException;


public class HttpData {

    private final String host = "http://localhost:";
    private final String token = "12345678-123456-781234-5678-12345678";
    private final MultiValueMap<String, String> headers = new HttpHeaders();
    private final MultiValueMap<String, String> emptyHeaders = new HttpHeaders();

    public HttpData() {
        this.headers.add("auth-token", token);
        this.emptyHeaders.add("auth-token", "");
    }

    public String getToken() {
        return token;
    }

    public MultiValueMap<String, String> getHeaders() {
        return headers;
    }

    public MultiValueMap<String, String> getEmptyHeaders() {
        return emptyHeaders;
    }

    public URI uriLogout(int port) throws URISyntaxException {
        return new URI(this.host + port + "/cloud/logout");
    }

    public URI uriLogin(int port) throws URISyntaxException {
        return new URI(this.host + port + "/cloud/login");
    }

    public URI uriFileWithName(int port) throws URISyntaxException {
        return new URI(this.host + port + "/cloud/file" + "?filename=test");
    }

    public URI uriFile(int port) throws URISyntaxException {
        return new URI(this.host + port + "/cloud/file");
    }

    public URI uriList(int port) throws URISyntaxException {
        return new URI(this.host + port + "/cloud/list" + "?limit=3");
    }
}

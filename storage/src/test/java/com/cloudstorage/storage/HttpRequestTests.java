package com.cloudstorage.storage;

import com.cloudstorage.storage.entity.Account;
import com.cloudstorage.storage.entity.Login;
import com.cloudstorage.storage.util.ValidationUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HttpRequestTests {

    private String host = "http://localhost:";
    private ObjectMapper objectMapper = new ObjectMapper();
    private String token = "12345678-123456-781234-5678-12345678";

    @Value(value = "${local.server.port}")
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void loginTest() throws JsonProcessingException {

        final HttpEntity<Account> entity = new HttpEntity(new Account("user", "123"));
        final String URI = this.host + this.port + "/cloud/login";

        ResponseEntity res = this.restTemplate.exchange(URI, HttpMethod.POST, entity, String.class);
        Login login = this.objectMapper.readValue(res.getBody().toString(), Login.class);

        assertThat(ValidationUtils.tokenValidation(login.getAuthToken())).isEqualTo(true);
        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void logoutTest() {

        final MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add("auth-token", token);
        final HttpEntity<Account> entity = new HttpEntity(headers);

        final String URI = this.host + this.port + "/cloud/logout";

        ResponseEntity res = this.restTemplate.exchange(URI, HttpMethod.POST, entity, String.class);

        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}

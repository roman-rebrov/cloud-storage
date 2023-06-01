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

import java.net.URISyntaxException;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HttpRequestTests {

    private ObjectMapper objectMapper = new ObjectMapper();
    private HttpData data = new HttpData();

    @Value(value = "${local.server.port}")
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void loginTest() throws JsonProcessingException, URISyntaxException {

        final HttpEntity<Account> entity = new HttpEntity(new Account("user", "123"));

        ResponseEntity res = this.restTemplate.exchange(this.data.uriLogin(this.port), HttpMethod.POST, entity, String.class);
        Login login = this.objectMapper.readValue(res.getBody().toString(), Login.class);

        assertThat(ValidationUtils.tokenValidation(login.getAuthToken())).isEqualTo(true);
        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void logoutTest() throws URISyntaxException {

        final HttpEntity<Account> entity = new HttpEntity(this.data.getHeaders());

        ResponseEntity res = this.restTemplate.exchange(this.data.uriLogout(this.port), HttpMethod.POST, entity, String.class);

        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}

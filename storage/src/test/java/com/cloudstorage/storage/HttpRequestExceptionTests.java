package com.cloudstorage.storage;

import com.cloudstorage.storage.entity.Account;
import com.cloudstorage.storage.entity.FileEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HttpRequestExceptionTests {

    private String host = "http://localhost:";
    private String token = "12345678-123456-781234-5678-12345678";

    @Value(value = "${local.server.port}")
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void badRequestLoginTest() {

        final HttpEntity<Account> badEntity = new HttpEntity(new Account("", ""));
        final String URI = this.host + this.port + "/cloud/login";

        ResponseEntity badResult = this.restTemplate.exchange(URI, HttpMethod.POST, badEntity, String.class);

        assertThat(badResult.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void badRequestLogoutTest() {

        final MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add("auth-token", "");
        final HttpEntity entity = new HttpEntity(headers);

        final String URI = this.host + this.port + "/cloud/logout";

        ResponseEntity res = this.restTemplate.exchange(URI, HttpMethod.POST, entity, String.class);

        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void badRequestFileGetMethodTest() {

        final MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add("auth-token", "");
        final HttpEntity entity = new HttpEntity(headers);

        final String URI = this.host + this.port + "/cloud/file" + "?filename=test";

        ResponseEntity res = this.restTemplate.exchange(URI, HttpMethod.GET, entity, String.class);

        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void unauthorizedFileGetMethodTest() {

        final MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add("auth-token", token);
        final HttpEntity entity = new HttpEntity(headers);

        final String URI = this.host + this.port + "/cloud/file" + "?filename=test";

        ResponseEntity res = this.restTemplate.exchange(URI, HttpMethod.GET, entity, String.class);

        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }


    @Test
    void badRequestFileDeleteMethodTest() {

        final MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add("auth-token", "");
        final HttpEntity entity = new HttpEntity(headers);

        final String URI = this.host + this.port + "/cloud/file" + "?filename=test";

        ResponseEntity res = this.restTemplate.exchange(URI, HttpMethod.DELETE, entity, String.class);

        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void unauthorizedFileDeleteMethodTest() {

        final MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add("auth-token", token);
        final HttpEntity entity = new HttpEntity(headers);

        final String URI = this.host + this.port + "/cloud/file" + "?filename=test";

        ResponseEntity res = this.restTemplate.exchange(URI, HttpMethod.DELETE, entity, String.class);

        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    void badRequestFilePutMethodTest() {

        final FileEntity newFilename = new FileEntity("test", 100L);
        final MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add("auth-token", "");
        final HttpEntity<FileEntity> entity = new HttpEntity(newFilename, headers);

        final String URI = this.host + this.port + "/cloud/file" + "?filename=test";

        ResponseEntity res = this.restTemplate.exchange(URI, HttpMethod.PUT, entity, String.class);

        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void badRequestFilePostMethodTest() {

        final MultiValueMap<String, File> body = new LinkedMultiValueMap();
        body.add("test", new File("test"));

        final MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add("auth-token", "");
        final HttpEntity<MultiValueMap<String, File>> entity = new HttpEntity(body, headers);

        final String URI = this.host + this.port + "/cloud/file";

        ResponseEntity res = this.restTemplate.exchange(URI, HttpMethod.POST, entity, String.class);

        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void badRequestListTest() throws URISyntaxException {

        final MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add("auth-token", "");
        final HttpEntity entity = new HttpEntity(headers);
        final URI uri = new URI(host + port + "/cloud/list" + "?limit=3");

        ResponseEntity result = this.restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void unauthorizedListTest() throws URISyntaxException {

        final MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add("auth-token", token);
        final HttpEntity entity = new HttpEntity(headers);
        final URI uri = new URI(host + port + "/cloud/list" + "?limit=3");

        ResponseEntity result = this.restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }
}

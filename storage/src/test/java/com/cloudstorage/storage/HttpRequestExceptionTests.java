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
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HttpRequestExceptionTests {

    private HttpData data = new HttpData();

    @Value(value = "${local.server.port}")
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    void badRequestLoginTest() throws URISyntaxException {

        final HttpEntity<Account> badEntity = new HttpEntity(new Account("", ""));

        ResponseEntity badResult = this.restTemplate.exchange(this.data.uriLogin(this.port), HttpMethod.POST, badEntity, String.class);

        assertThat(badResult.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void badRequestLogoutTest() throws URISyntaxException {

        final HttpEntity entity = new HttpEntity(this.data.getEmptyHeaders());

        ResponseEntity res = this.restTemplate.exchange(this.data.uriLogout(this.port), HttpMethod.POST, entity, String.class);

        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void badRequestFileGetMethodTest() throws URISyntaxException {

        final HttpEntity entity = new HttpEntity(this.data.getEmptyHeaders());

        ResponseEntity res = this.restTemplate.exchange(this.data.uriFileWithName(this.port), HttpMethod.GET, entity, String.class);

        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void unauthorizedFileGetMethodTest() throws URISyntaxException {

        final HttpEntity entity = new HttpEntity(this.data.getHeaders());

        ResponseEntity res = this.restTemplate.exchange(this.data.uriFileWithName(this.port), HttpMethod.GET, entity, String.class);

        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }


    @Test
    void badRequestFileDeleteMethodTest() throws URISyntaxException {

        final HttpEntity entity = new HttpEntity(this.data.getEmptyHeaders());

        ResponseEntity res = this.restTemplate.exchange(this.data.uriFileWithName(this.port), HttpMethod.DELETE, entity, String.class);

        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void unauthorizedFileDeleteMethodTest() throws URISyntaxException {

        final HttpEntity entity = new HttpEntity(this.data.getHeaders());

        ResponseEntity res = this.restTemplate.exchange(this.data.uriFileWithName(this.port), HttpMethod.DELETE, entity, String.class);

        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    void badRequestFilePutMethodTest() throws URISyntaxException {

        final FileEntity newFilename = new FileEntity("test", 100L);
        final HttpEntity<FileEntity> entity = new HttpEntity(newFilename, this.data.getEmptyHeaders());

        ResponseEntity res = this.restTemplate.exchange(this.data.uriFileWithName(this.port), HttpMethod.PUT, entity, String.class);

        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void badRequestFilePostMethodTest() throws URISyntaxException {

        final MultiValueMap<String, File> body = new LinkedMultiValueMap();
        body.add("test", new File("test"));

        final HttpEntity<MultiValueMap<String, File>> entity = new HttpEntity(body, this.data.getEmptyHeaders());

        ResponseEntity res = this.restTemplate.exchange(this.data.uriFile(this.port), HttpMethod.POST, entity, String.class);

        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void badRequestListTest() throws URISyntaxException {

        final HttpEntity entity = new HttpEntity(this.data.getEmptyHeaders());

        ResponseEntity result = this.restTemplate.exchange(this.data.uriList(this.port), HttpMethod.GET, entity, String.class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void unauthorizedListTest() throws URISyntaxException {

        final HttpEntity entity = new HttpEntity(this.data.getHeaders());

        ResponseEntity result = this.restTemplate.exchange(this.data.uriList(this.port), HttpMethod.GET, entity, String.class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }
}
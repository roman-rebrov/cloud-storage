package com.cloudstorage.storage;

import com.cloudstorage.storage.entity.Account;
import com.cloudstorage.storage.entity.FileEntity;
import com.cloudstorage.storage.repository.StorageRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HttpRequestMockTests {

    @Value(value = "${local.server.port}")
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private StorageRepository repository;

    private ObjectMapper objectMapper = new ObjectMapper();
    private String host = "http://localhost:";
    private String token = "12345678-123456-781234-5678-12345678";

    @BeforeEach
    void beforeEach() {
        Mockito.when(this.repository.auth(token)).thenReturn(true);
    }

    @Test
    void getListTest() throws URISyntaxException {

        Mockito.when(this.repository.getFileList(3)).thenReturn(List.of());

        final MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add("auth-token", token);
        final HttpEntity<Account> entity = new HttpEntity(headers);
        final URI uri = new URI(host + port + "/cloud/list" + "?limit=3");


        ResponseEntity res = this.restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);

        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void putFileTest() throws URISyntaxException {

        Mockito.when(repository.updateFile("test", "test")).thenReturn(true);

        final FileEntity newFilename = new FileEntity("test", 100L);
        final MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add("auth-token", token);
        final HttpEntity<FileEntity> entity = new HttpEntity(newFilename, headers);
        final URI uri = new URI(host + port + "/cloud/file" + "?filename=test");

        ResponseEntity res = this.restTemplate.exchange(uri, HttpMethod.PUT, entity, String.class);

        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void saveFileTestWithMock() throws URISyntaxException {

        final MultipartFile file = new MockMultipartFile("test", new byte[0]);
        Mockito.when(this.repository.saveFile(file)).thenReturn(true);

        final MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add("auth-token", token);
        final HttpEntity entity = new HttpEntity(headers);
        final URI uri = new URI(host + port + "/cloud/file");


        ResponseEntity res = this.restTemplate.exchange(uri, HttpMethod.POST, entity, String.class);

        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(res.getBody()).isEqualTo("OK");
    }

    @Test
    void deleteFileTestWithMock() throws URISyntaxException {

        Mockito.when(this.repository.deleteFile("test")).thenReturn(true);

        final MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add("auth-token", token);
        final HttpEntity entity = new HttpEntity(headers);
        final URI uri = new URI(host + port + "/cloud/file" + "?filename=test");

        ResponseEntity res = this.restTemplate.exchange(uri, HttpMethod.DELETE, entity, String.class);

        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void getFileTestWithMock() throws URISyntaxException, IOException {

        Mockito.when(this.repository.getFile("test")).thenReturn(new byte[0]);

        final MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add("auth-token", token);
        final HttpEntity entity = new HttpEntity(headers);
        final URI uri = new URI(host + port + "/cloud/file" + "?filename=test");

        ResponseEntity res = this.restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);

        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

}

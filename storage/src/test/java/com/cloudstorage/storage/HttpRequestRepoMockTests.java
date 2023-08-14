package com.cloudstorage.storage;

import com.cloudstorage.storage.entity.Account;
import com.cloudstorage.storage.entity.FileEntity;
import com.cloudstorage.storage.repository.ClientRepository;
import com.cloudstorage.storage.repository.FilesJpaRepository;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HttpRequestRepoMockTests {


    private ObjectMapper objectMapper = new ObjectMapper();
    private HttpData data = new HttpData();

    @Value(value = "${local.server.port}")
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private StorageRepository repository;
    @MockBean
    private ClientRepository clientRepository;
    @MockBean
    private FilesJpaRepository DBfileRepository;

    @BeforeEach
    void beforeEach() {
        Mockito.when(this.clientRepository.auth(this.data.getToken())).thenReturn(true);
    }

    @Test
    void getListTest() throws URISyntaxException {

        Mockito.when(this.DBfileRepository.findByDirname(null, 3)).thenReturn(List.of());

        final HttpEntity<Account> entity = new HttpEntity(this.data.getHeaders());

        ResponseEntity res = this.restTemplate.exchange(this.data.uriList(this.port), HttpMethod.GET, entity, String.class);

        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void putFileTest() throws URISyntaxException {

        Mockito.when(repository.updateFile("test", "", "test")).thenReturn(true);

        final FileEntity newFilename = new FileEntity("test", 100L);
        final HttpEntity<FileEntity> entity = new HttpEntity(newFilename, this.data.getHeaders());

        ResponseEntity res = this.restTemplate.exchange(this.data.uriFileWithName(this.port), HttpMethod.PUT, entity, String.class);

        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void saveFileTestWithMock() throws URISyntaxException {

        final MultipartFile file = new MockMultipartFile("test", new byte[0]);
        Mockito.when(this.repository.saveFile("", file)).thenReturn(true);

        final HttpEntity entity = new HttpEntity(this.data.getHeaders());

        ResponseEntity res = this.restTemplate.exchange(this.data.uriFile(this.port), HttpMethod.POST, entity, String.class);

        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(res.getBody()).isEqualTo("OK");
    }

    @Test
    void deleteFileTestWithMock() throws URISyntaxException {

        Mockito.when(this.repository.deleteFile("", "test")).thenReturn(true);

        final HttpEntity entity = new HttpEntity(this.data.getHeaders());

        ResponseEntity res = this.restTemplate.exchange(this.data.uriFileWithName(this.port), HttpMethod.DELETE, entity, String.class);

        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void getFileTestWithMock() throws URISyntaxException, IOException {

        Mockito.when(this.repository.getFile("", "test")).thenReturn(new byte[0]);

        final HttpEntity entity = new HttpEntity(this.data.getHeaders());

        ResponseEntity res = this.restTemplate.exchange(this.data.uriFileWithName(this.port), HttpMethod.GET, entity, String.class);

        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

}

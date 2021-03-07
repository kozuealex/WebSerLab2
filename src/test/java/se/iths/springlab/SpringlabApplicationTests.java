package se.iths.springlab;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import se.iths.springlab.dtos.MonkeyDto;
import se.iths.springlab.services.Service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class SpringlabApplicationTests {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @LocalServerPort
    int port;

    @Autowired
    private TestRestTemplate testClient;

    @MockBean
    private Service testService;

    @Test
    public void callingReplaceReturnsNewMonkey() throws Exception {
        var monkeyDto = new MonkeyDto(1, "TestMonkey", "TestType", 1, "F");
        when(testService.createMonkey(any(MonkeyDto.class))).thenReturn(monkeyDto);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(objectMapper.writeValueAsString(monkeyDto), headers);
        ResponseEntity<String> response = testClient
                .exchange("/animals/1", HttpMethod.PUT, entity, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        // JSONAssert.assertEquals(objectMapper.writeValueAsString(monkeyDto), response.getBody(), false);
    }

    @Test
    public void callingUpdatePatchesOneMonkey() {
        when(testService.createMonkey(any(MonkeyDto.class))).thenReturn(new MonkeyDto());
        String patchText = "{\"name\":\"ChangeMonkey\"}";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(patchText, headers);
        ResponseEntity<String> response = testClient
                .exchange("/animals/1", HttpMethod.PUT, entity, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

//    @Test
//    void contextLoads() {
//        var result = testClient.getForEntity
//                ("http://localhost:" + port + "/animals/", MonkeyDto[].class);
//        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(result.getBody().length).isGreaterThan(0);
//    }
//
//    @Test
//    void postSomethingToServiceAndGetById() {
//        MonkeyDto monkeyDto = new MonkeyDto(1, "TestMonkey", "Type", 1, "M");
//        var result = testClient.postForEntity
//                ("http://localhost:" + port + "/animals/", monkeyDto, MonkeyDto.class);
//        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
//        assertThat(result.getBody().getId()).isEqualTo(1);
//
//        var getResult = testClient.getForEntity
//                ("http://localhost:" + port + "/animals/{id}", MonkeyDto.class, result.getBody().getId());
//        assertThat(getResult.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(getResult.getBody().getName()).isEqualTo("TestMonkey");
//    }

}

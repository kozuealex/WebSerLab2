package se.iths.springlab;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import se.iths.springlab.dtos.MonkeyDto;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SpringlabApplicationTests {

    @LocalServerPort
    int port;

    @Autowired
    TestRestTemplate testClient;


    @Test
    void contextLoads() {
        var result = testClient.getForEntity
                ("http://localhost:" + port + "/animals/", MonkeyDto[].class);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody().length).isGreaterThan(0);
    }

    @Test
    void postSomethingToServiceAndGetById() {
        MonkeyDto monkeyDto = new MonkeyDto(1, "TestMonkey", "Type", 1, "M");
        var result = testClient.postForEntity
                ("http://localhost:" + port + "/animals/", monkeyDto, MonkeyDto.class);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(result.getBody().getId()).isEqualTo(1);

        var getResult = testClient.getForEntity
                ("http://localhost:" + port + "/animals/{id}", MonkeyDto.class, result.getBody().getId());
        assertThat(getResult.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResult.getBody().getName()).isEqualTo("TestMonkey");
    }

}

package at.schulgong;

import at.schulgong.controller.RingToneController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BackendApplicationTest {

  private HttpClient httpClient = HttpClient.newHttpClient();

  @Autowired
  private RingToneController controller;

  @Value(value = "${local.server.port}")
  private int port;

  private String deleteId = "";

  @Test
  public void getAllRingTones() throws Exception {
    HttpRequest request = HttpRequest.newBuilder()
      .GET()
      .uri(new URI("http://localhost:" + this.port + "/periods"))
      .build();
    HttpResponse<String> response = this.httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    System.out.println(response.body());
    assertThat(response.body().equals("Token is valid"));
    assertThat(response.toString()).contains("201");
  }

}

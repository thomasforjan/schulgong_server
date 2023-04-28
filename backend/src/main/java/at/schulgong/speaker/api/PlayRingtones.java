package at.schulgong.speaker.api;

import at.schulgong.BackendApplication;
import org.springframework.boot.SpringApplication;

import java.util.ArrayList;
import java.util.List;

public class PlayRingtones {

  public static void main(String[] args) throws Exception {

    String[] argsList = {"get_volume"};
    SpeakerApi.runSpeakerApi(argsList);

  }

}

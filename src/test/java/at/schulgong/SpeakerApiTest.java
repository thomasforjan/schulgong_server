package at.schulgong;

import at.schulgong.speaker.api.SpeakerApi;
import at.schulgong.speaker.util.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import at.schulgong.speaker.util.speakerconfig.ReadSpeakerConfigFile;
import at.schulgong.speaker.util.speakerconfig.Speaker;
import at.schulgong.speaker.util.speakerconfig.SpeakerObjects;
import at.schulgong.speaker.util.speakerconfig.WriteSpeakerConfigFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Thomas Forjan, Philipp Wildzeiss, Martin Kral
 * @version 0.1
 * @implNote practical project
 * @since March 2023
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SpeakerApiTest {

  /*private final String SONG_PATH_ONE = "audiofiles/01_-_attack.mp3";
  private final String SONG_PATH_TWO = "audiofiles/02_-_dreaming.mp3";
  private final String SONG_PATH_THREE = "audiofiles/03_-_kill_rock_n_roll.mp3";
  private final String SONG_PATH_FOUR = "audiofiles/04_-_hypnotize.mp3";

  private final String SONG_NAME_ONE = "01_-_attack.mp3";
  private final String SONG_NAME_TWO = "02_-_dreaming.mp3";
  private final String SONG_NAME_THREE = "03_-_kill_rock_n_roll.mp3";
  private final String SONG_NAME_FOUR = "04_-_hypnotize.mp3";


  *//**
   * Test if the all speakers are discovered by name.
   *
   * @throws IOException if method fails
   *//*
  @Test
  void testDiscoverByName() throws IOException {
    String[] args = new String[]{SpeakerCommand.DISCOVER_BY_NAME.getCommand()};
    SpeakerActionStatus speakerActionStatus = SpeakerApi.runSpeakerApi(args);
    SpeakerObjects speakerObjects = ReadSpeakerConfigFile.getSpeakerObjectListFromConfigFile();
    StringBuilder speakerList = new StringBuilder();
    int i = 0;
    for (Speaker speaker : speakerObjects.getSpeakerObjects()) {
      speakerList.append(speaker.getName());
      if(i < speakerObjects.getSpeakerObjects().size() - 1) {
        speakerList.append(", ");
      }
      i++;
    }
    assert speakerActionStatus != null;
    assertEquals(SpeakerCommand.DISCOVER_BY_NAME, speakerActionStatus.getSpeakerCommand());
    assertEquals(0, speakerActionStatus.getExitCode());
    assertNull(speakerActionStatus.getException());
    assertNull(speakerActionStatus.getInformation());
    assertEquals(speakerList.toString(), speakerActionStatus.getSpeakerList());
  }

  *//**
   * Test if an exception returns if all speaker names are wrong.
   *
   * @throws IOException if method fails
   *//*
  @Test
  void testDiscoverByNameWithWrongNames() throws IOException {
    SpeakerObjects currentSpeakerObjects = ReadSpeakerConfigFile.getSpeakerObjectListFromConfigFile();
    List<Speaker> speakers = new ArrayList<>();
    speakers.add(new Speaker("Gang4"));
    speakers.add(new Speaker("Gang5"));
    speakers.add(new Speaker("Gang6"));
    SpeakerObjects speakerObjects = new SpeakerObjects(speakers);
    WriteSpeakerConfigFile.writeSpeakerObjectListToConfigFile(speakerObjects);
    String[] args = new String[]{SpeakerCommand.DISCOVER_BY_NAME.getCommand()};
    SpeakerActionStatus speakerActionStatus = SpeakerApi.runSpeakerApi(args);
    WriteSpeakerConfigFile.writeSpeakerObjectListToConfigFile(currentSpeakerObjects);
    assert speakerActionStatus != null;
    assertEquals(SpeakerCommand.DISCOVER_BY_NAME, speakerActionStatus.getSpeakerCommand());
    assertEquals(0, speakerActionStatus.getExitCode());
    assertEquals("No speaker was found", speakerActionStatus.getException());
    assertNull(speakerActionStatus.getInformation());
    assertNull(speakerActionStatus.getSpeakerList());
  }

  *//**
   * Test if an only a speaker list returns with one speaker fewer.
   *
   * @throws IOException if method fails
   *//*
  @Test
  void testDiscoverByNameWithOneWrongName() throws IOException {
    SpeakerObjects currentSpeakerObjects = ReadSpeakerConfigFile.getSpeakerObjectListFromConfigFile();
    List<Speaker> speakers = new ArrayList<>();
    speakers.add(new Speaker("Gang1"));
    speakers.add(new Speaker("Gang2"));
    speakers.add(new Speaker("Gang6"));
    SpeakerObjects speakerObjects = new SpeakerObjects(speakers);
    WriteSpeakerConfigFile.writeSpeakerObjectListToConfigFile(speakerObjects);
    String[] args = new String[]{SpeakerCommand.DISCOVER_BY_NAME.getCommand()};
    SpeakerActionStatus speakerActionStatus = SpeakerApi.runSpeakerApi(args);
    WriteSpeakerConfigFile.writeSpeakerObjectListToConfigFile(currentSpeakerObjects);
    String speakerList = "Gang1, Gang2";
    assert speakerActionStatus != null;
    assertEquals(SpeakerCommand.DISCOVER_BY_NAME, speakerActionStatus.getSpeakerCommand());
    assertEquals(0, speakerActionStatus.getExitCode());
    assertNull(speakerActionStatus.getException());
    assertNull(speakerActionStatus.getInformation());
    assertEquals(speakerList, speakerActionStatus.getSpeakerList());
  }

  *//**
   * Test if the all speakers are discovered by discover_all funciton.
   *
   * @throws IOException if method fails
   *//*
  @Test
  void testDiscoverAll() throws IOException {
    SpeakerObjects currentSpeakerObjects = ReadSpeakerConfigFile.getSpeakerObjectListFromConfigFile();
    SpeakerObjects speakerObjects = new SpeakerObjects(new ArrayList<>());
    WriteSpeakerConfigFile.writeSpeakerObjectListToConfigFile(speakerObjects);
    String[] args = new String[]{SpeakerCommand.DISCOVER_ALL.getCommand()};
    SpeakerActionStatus speakerActionStatus = SpeakerApi.runSpeakerApi(args);
    WriteSpeakerConfigFile.writeSpeakerObjectListToConfigFile(currentSpeakerObjects);
    assert speakerActionStatus != null;
    assertEquals(SpeakerCommand.DISCOVER_ALL, speakerActionStatus.getSpeakerCommand());
    assertEquals(0, speakerActionStatus.getExitCode());
    assertNull(speakerActionStatus.getException());
    assertNull(speakerActionStatus.getInformation());
    for (Speaker speaker : currentSpeakerObjects.getSpeakerObjects()) {
      assertThat("Error, don't contains " + speaker.getName(), speakerActionStatus.getSpeakerList().contains(speaker.getName()));
    }

  }

  *//**
   * Test if all speakers ar discovered by discover function, if speaker are defined in the config file.
   *
   * @throws IOException if method fails
   *//*
  @Test
  void testDiscoverWithSpeakersInConfigFile() throws IOException {
    SpeakerObjects currentSpeakerObjects = ReadSpeakerConfigFile.getSpeakerObjectListFromConfigFile();
    String[] args = new String[]{SpeakerCommand.DISCOVER.getCommand()};
    SpeakerActionStatus speakerActionStatus = SpeakerApi.runSpeakerApi(args);
    WriteSpeakerConfigFile.writeSpeakerObjectListToConfigFile(currentSpeakerObjects);
    StringBuilder speakerList = new StringBuilder();
    int i = 0;
    for (Speaker speaker : currentSpeakerObjects.getSpeakerObjects()) {
      speakerList.append(speaker.getName());
      if(i < currentSpeakerObjects.getSpeakerObjects().size() - 1) {
        speakerList.append(", ");
      }
      i++;
    }
    assert speakerActionStatus != null;
    assertEquals(SpeakerCommand.DISCOVER_BY_NAME, speakerActionStatus.getSpeakerCommand());
    assertEquals(0, speakerActionStatus.getExitCode());
    assertNull(speakerActionStatus.getException());
    assertNull(speakerActionStatus.getInformation());
    assertEquals(speakerList.toString(), speakerActionStatus.getSpeakerList());
  }

  *//**
   * Test if all speakers ar discovered by discover function, if no speaker are defined in the config file.
   *
   * @throws IOException if method fails
   *//*
  @Test
  void testDiscoverWithoutSpeakersInConfigFile() throws IOException {
    SpeakerObjects currentSpeakerObjects = ReadSpeakerConfigFile.getSpeakerObjectListFromConfigFile();
    SpeakerObjects speakerObjects = new SpeakerObjects(new ArrayList<>());
    WriteSpeakerConfigFile.writeSpeakerObjectListToConfigFile(speakerObjects);
    String[] args = new String[]{SpeakerCommand.DISCOVER.getCommand()};
    SpeakerActionStatus speakerActionStatus = SpeakerApi.runSpeakerApi(args);
    WriteSpeakerConfigFile.writeSpeakerObjectListToConfigFile(currentSpeakerObjects);
    StringBuilder speakerList = new StringBuilder();
    int i = 0;
    for (Speaker speaker : currentSpeakerObjects.getSpeakerObjects()) {
      speakerList.append(speaker.getName());
      if(i < currentSpeakerObjects.getSpeakerObjects().size() - 1) {
        speakerList.append(", ");
      }
      i++;
    }
    assert speakerActionStatus != null;
    assertEquals(SpeakerCommand.DISCOVER_ALL, speakerActionStatus.getSpeakerCommand());
    assertEquals(0, speakerActionStatus.getExitCode());
    assertNull(speakerActionStatus.getException());
    assertNull(speakerActionStatus.getInformation());
    for (Speaker speaker : currentSpeakerObjects.getSpeakerObjects()) {
      assertThat("Error, don't contains " + speaker.getName(), speakerActionStatus.getSpeakerList().contains(speaker.getName()));
    }
  }

  *//**
   * Test if song played with play_uri function.
   *
   * @throws IOException if method fails
   *//*
  @Test
  void testPlayUri() throws IOException {
    String[] args = new String[]{SpeakerCommand.PLAY_URI.getCommand(), SONG_PATH_ONE};
    SpeakerActionStatus speakerActionStatus = SpeakerApi.runSpeakerApi(args);
    SpeakerObjects speakerObjects = ReadSpeakerConfigFile.getSpeakerObjectListFromConfigFile();
    if(speakerObjects == null || speakerObjects.getSpeakerObjects() == null || speakerObjects.getSpeakerObjects().size() == 0) {
      List<Speaker> speakers = new ArrayList<>();
      speakers.add(new Speaker("Gang1"));
      speakers.add(new Speaker("Gang2"));
      speakers.add(new Speaker("Gang3"));
      speakerObjects = new SpeakerObjects(speakers);
      WriteSpeakerConfigFile.writeSpeakerObjectListToConfigFile(speakerObjects);
    }

    assert speakerActionStatus != null;
    assertEquals(SpeakerCommand.PLAY_URI, speakerActionStatus.getSpeakerCommand());
    assertEquals(0, speakerActionStatus.getExitCode());
    assertNull(speakerActionStatus.getException());
    assertNull(speakerActionStatus.getInformation());
    for (Speaker speaker : speakerObjects.getSpeakerObjects()) {
      assertThat("Error, don't contains " + speaker.getName(), speakerActionStatus.getSpeakerList().contains(speaker.getName()));
    }
  }

  *//**
   * Test if song play with play function.
   *
   * @throws IOException if method fails
   *//*
  @Test
  void testPlay() throws IOException {
    String[] args = new String[]{SpeakerCommand.PLAY.getCommand()};
    SpeakerActionStatus speakerActionStatus = SpeakerApi.runSpeakerApi(args);
    SpeakerObjects speakerObjects = ReadSpeakerConfigFile.getSpeakerObjectListFromConfigFile();
    if(speakerObjects == null || speakerObjects.getSpeakerObjects() == null || speakerObjects.getSpeakerObjects().size() == 0) {
      List<Speaker> speakers = new ArrayList<>();
      speakers.add(new Speaker("Gang1"));
      speakers.add(new Speaker("Gang2"));
      speakers.add(new Speaker("Gang3"));
      speakerObjects = new SpeakerObjects(speakers);
      WriteSpeakerConfigFile.writeSpeakerObjectListToConfigFile(speakerObjects);
    }

    assert speakerActionStatus != null;
    assertEquals(SpeakerCommand.PLAY, speakerActionStatus.getSpeakerCommand());
    assertEquals(0, speakerActionStatus.getExitCode());
    assertNull(speakerActionStatus.getException());
    assertNull(speakerActionStatus.getInformation());
    for (Speaker speaker : speakerObjects.getSpeakerObjects()) {
      assertThat("Error, don't contains " + speaker.getName(), speakerActionStatus.getSpeakerList().contains(speaker.getName()));
    }
  }

  *//**
   * Test if song is stopped after stop function.
   *
   * @throws IOException if method fails
   *//*
  @Test
  void testStop() throws IOException {
    String[] args = new String[]{SpeakerCommand.STOP.getCommand()};
    SpeakerActionStatus speakerActionStatus = SpeakerApi.runSpeakerApi(args);
    SpeakerObjects speakerObjects = ReadSpeakerConfigFile.getSpeakerObjectListFromConfigFile();
    if(speakerObjects == null || speakerObjects.getSpeakerObjects() == null || speakerObjects.getSpeakerObjects().size() == 0) {
      List<Speaker> speakers = new ArrayList<>();
      speakers.add(new Speaker("Gang1"));
      speakers.add(new Speaker("Gang2"));
      speakers.add(new Speaker("Gang3"));
      speakerObjects = new SpeakerObjects(speakers);
      WriteSpeakerConfigFile.writeSpeakerObjectListToConfigFile(speakerObjects);
    }

    assert speakerActionStatus != null;
    assertEquals(SpeakerCommand.STOP, speakerActionStatus.getSpeakerCommand());
    assertEquals(0, speakerActionStatus.getExitCode());
    assertNull(speakerActionStatus.getException());
    assertNull(speakerActionStatus.getInformation());
    for (Speaker speaker : speakerObjects.getSpeakerObjects()) {
      assertThat("Error, don't contains " + speaker.getName(), speakerActionStatus.getSpeakerList().contains(speaker.getName()));
    }
  }

  *//**
   * Test if song is paused with pause function.
   *
   * @throws IOException if method fails
   *//*
  @Test
  void testPause() throws IOException {
    String[] args = new String[]{SpeakerCommand.PAUSE.getCommand()};
    SpeakerActionStatus speakerActionStatus = SpeakerApi.runSpeakerApi(args);
    SpeakerObjects speakerObjects = ReadSpeakerConfigFile.getSpeakerObjectListFromConfigFile();
    if(speakerObjects == null || speakerObjects.getSpeakerObjects() == null || speakerObjects.getSpeakerObjects().size() == 0) {
      List<Speaker> speakers = new ArrayList<>();
      speakers.add(new Speaker("Gang1"));
      speakers.add(new Speaker("Gang2"));
      speakers.add(new Speaker("Gang3"));
      speakerObjects = new SpeakerObjects(speakers);
      WriteSpeakerConfigFile.writeSpeakerObjectListToConfigFile(speakerObjects);
    }

    assert speakerActionStatus != null;
    assertEquals(SpeakerCommand.PAUSE, speakerActionStatus.getSpeakerCommand());
    assertEquals(0, speakerActionStatus.getExitCode());
    assertNull(speakerActionStatus.getException());
    assertNull(speakerActionStatus.getInformation());
    for (Speaker speaker : speakerObjects.getSpeakerObjects()) {
      assertThat("Error, don't contains " + speaker.getName(), speakerActionStatus.getSpeakerList().contains(speaker.getName()));
    }
  }

  *//**
   * Test if song is muted with mute function.
   *
   * @throws IOException if method fails
   *//*
  @Test
  void testMute() throws IOException {
    String[] args = new String[]{SpeakerCommand.MUTE.getCommand(), "True"};
    SpeakerActionStatus speakerActionStatus = SpeakerApi.runSpeakerApi(args);
    
    SpeakerObjects speakerObjects = ReadSpeakerConfigFile.getSpeakerObjectListFromConfigFile();
    if(speakerObjects == null || speakerObjects.getSpeakerObjects() == null || speakerObjects.getSpeakerObjects().size() == 0) {
      List<Speaker> speakers = new ArrayList<>();
      speakers.add(new Speaker("Gang1"));
      speakers.add(new Speaker("Gang2"));
      speakers.add(new Speaker("Gang3"));
      speakerObjects = new SpeakerObjects(speakers);
      WriteSpeakerConfigFile.writeSpeakerObjectListToConfigFile(speakerObjects);
    }

    assert speakerActionStatus != null;
    assertEquals(SpeakerCommand.MUTE, speakerActionStatus.getSpeakerCommand());
    assertEquals(0, speakerActionStatus.getExitCode());
    assertNull(speakerActionStatus.getException());
    assertNull(speakerActionStatus.getInformation());
    for (Speaker speaker : speakerObjects.getSpeakerObjects()) {
      assertThat("Error, don't contains " + speaker.getName(), speakerActionStatus.getSpeakerList().contains(speaker.getName()));
    }
  }

  *//**
   * Test if song is not muted with mute function.
   *
   * @throws IOException if method fails
   *//*
  @Test
  void testUnMute() throws IOException {
    String[] args = new String[]{SpeakerCommand.MUTE.getCommand(), "False"};
    SpeakerActionStatus speakerActionStatus = SpeakerApi.runSpeakerApi(args);
    
    SpeakerObjects speakerObjects = ReadSpeakerConfigFile.getSpeakerObjectListFromConfigFile();
    if(speakerObjects == null || speakerObjects.getSpeakerObjects() == null || speakerObjects.getSpeakerObjects().size() == 0) {
      List<Speaker> speakers = new ArrayList<>();
      speakers.add(new Speaker("Gang1"));
      speakers.add(new Speaker("Gang2"));
      speakers.add(new Speaker("Gang3"));
      speakerObjects = new SpeakerObjects(speakers);
      WriteSpeakerConfigFile.writeSpeakerObjectListToConfigFile(speakerObjects);
    }

    assert speakerActionStatus != null;
    assertEquals(SpeakerCommand.MUTE, speakerActionStatus.getSpeakerCommand());
    assertEquals(0, speakerActionStatus.getExitCode());
    assertNull(speakerActionStatus.getException());
    assertNull(speakerActionStatus.getInformation());
    for (Speaker speaker : speakerObjects.getSpeakerObjects()) {
      assertThat("Error, don't contains " + speaker.getName(), speakerActionStatus.getSpeakerList().contains(speaker.getName()));
    }
  }

  *//**
   * Test if mute state is True after set mute.
   *
   * @throws IOException if method fails
   *//*
  @Test
  void testGetMuteStateTrue() throws IOException {
    String[] argsSetMute = new String[]{SpeakerCommand.MUTE.getCommand(), "True"};
    SpeakerApi.runSpeakerApi(argsSetMute);
    String[] args = new String[]{SpeakerCommand.GET_MUTE_STATE.getCommand()};
    SpeakerActionStatus speakerActionStatus = SpeakerApi.runSpeakerApi(args);
    argsSetMute = new String[]{SpeakerCommand.MUTE.getCommand(), "False"};
    SpeakerApi.runSpeakerApi(argsSetMute);
    
    SpeakerObjects speakerObjects = ReadSpeakerConfigFile.getSpeakerObjectListFromConfigFile();
    if(speakerObjects == null || speakerObjects.getSpeakerObjects() == null || speakerObjects.getSpeakerObjects().size() == 0) {
      List<Speaker> speakers = new ArrayList<>();
      speakers.add(new Speaker("Gang1"));
      speakers.add(new Speaker("Gang2"));
      speakers.add(new Speaker("Gang3"));
      speakerObjects = new SpeakerObjects(speakers);
      WriteSpeakerConfigFile.writeSpeakerObjectListToConfigFile(speakerObjects);
    }

    assert speakerActionStatus != null;
    assertEquals(SpeakerCommand.GET_MUTE_STATE, speakerActionStatus.getSpeakerCommand());
    assertEquals(0, speakerActionStatus.getExitCode());
    assertNull(speakerActionStatus.getException());
    assertEquals("True", speakerActionStatus.getInformation());
    for (Speaker speaker : speakerObjects.getSpeakerObjects()) {
      assertThat("Error, don't contains " + speaker.getName(), speakerActionStatus.getSpeakerList().contains(speaker.getName()));
    }
  }

  *//**
   * Test if mute state is False after set not muted.
   *
   * @throws IOException if method fails
   *//*
  @Test
  void testGetMuteStateFalse() throws IOException {
    String[] argsSetMute = new String[]{SpeakerCommand.MUTE.getCommand(), "False"};
    SpeakerApi.runSpeakerApi(argsSetMute);
    String[] args = new String[]{SpeakerCommand.GET_MUTE_STATE.getCommand()};
    SpeakerActionStatus speakerActionStatus = SpeakerApi.runSpeakerApi(args);
    
    SpeakerObjects speakerObjects = ReadSpeakerConfigFile.getSpeakerObjectListFromConfigFile();
    if(speakerObjects == null || speakerObjects.getSpeakerObjects() == null || speakerObjects.getSpeakerObjects().size() == 0) {
      List<Speaker> speakers = new ArrayList<>();
      speakers.add(new Speaker("Gang1"));
      speakers.add(new Speaker("Gang2"));
      speakers.add(new Speaker("Gang3"));
      speakerObjects = new SpeakerObjects(speakers);
      WriteSpeakerConfigFile.writeSpeakerObjectListToConfigFile(speakerObjects);
    }

    assert speakerActionStatus != null;
    assertEquals(SpeakerCommand.GET_MUTE_STATE, speakerActionStatus.getSpeakerCommand());
    assertEquals(0, speakerActionStatus.getExitCode());
    assertNull(speakerActionStatus.getException());
    assertEquals("False", speakerActionStatus.getInformation());
    for (Speaker speaker : speakerObjects.getSpeakerObjects()) {
      assertThat("Error, don't contains " + speaker.getName(), speakerActionStatus.getSpeakerList().contains(speaker.getName()));
    }
  }

  *//**
   * Test to add a song to the queue.
   *
   * @throws IOException if method fails
   *//*
  @Test
  void testAddUriToQueue() throws IOException {
    String[] args = new String[]{SpeakerCommand.ADD_URI_TO_QUEUE.getCommand(), SONG_PATH_ONE};
    SpeakerActionStatus speakerActionStatus = SpeakerApi.runSpeakerApi(args);
    
    SpeakerObjects speakerObjects = ReadSpeakerConfigFile.getSpeakerObjectListFromConfigFile();
    if(speakerObjects == null || speakerObjects.getSpeakerObjects() == null || speakerObjects.getSpeakerObjects().size() == 0) {
      List<Speaker> speakers = new ArrayList<>();
      speakers.add(new Speaker("Gang1"));
      speakers.add(new Speaker("Gang2"));
      speakers.add(new Speaker("Gang3"));
      speakerObjects = new SpeakerObjects(speakers);
      WriteSpeakerConfigFile.writeSpeakerObjectListToConfigFile(speakerObjects);
    }

    assert speakerActionStatus != null;
    assertEquals(SpeakerCommand.ADD_URI_TO_QUEUE, speakerActionStatus.getSpeakerCommand());
    assertEquals(0, speakerActionStatus.getExitCode());
    assertNull(speakerActionStatus.getException());
    assertNull(speakerActionStatus.getInformation());
    for (Speaker speaker : speakerObjects.getSpeakerObjects()) {
      assertThat("Error, don't contains " + speaker.getName(), speakerActionStatus.getSpeakerList().contains(speaker.getName()));
    }
  }

  *//**
   * Test to clear the queue.
   *
   * @throws IOException if method fails
   *//*
  @Test
  void testClearQueue() throws IOException {
    String[] args = new String[]{SpeakerCommand.CLEAR_QUEUE.getCommand()};
    SpeakerActionStatus speakerActionStatus = SpeakerApi.runSpeakerApi(args);
    
    SpeakerObjects speakerObjects = ReadSpeakerConfigFile.getSpeakerObjectListFromConfigFile();
    if(speakerObjects == null || speakerObjects.getSpeakerObjects() == null || speakerObjects.getSpeakerObjects().size() == 0) {
      List<Speaker> speakers = new ArrayList<>();
      speakers.add(new Speaker("Gang1"));
      speakers.add(new Speaker("Gang2"));
      speakers.add(new Speaker("Gang3"));
      speakerObjects = new SpeakerObjects(speakers);
      WriteSpeakerConfigFile.writeSpeakerObjectListToConfigFile(speakerObjects);
    }

    assert speakerActionStatus != null;
    assertEquals(SpeakerCommand.CLEAR_QUEUE, speakerActionStatus.getSpeakerCommand());
    assertEquals(0, speakerActionStatus.getExitCode());
    assertNull(speakerActionStatus.getException());
    assertNull(speakerActionStatus.getInformation());
    for (Speaker speaker : speakerObjects.getSpeakerObjects()) {
      assertThat("Error, don't contains " + speaker.getName(), speakerActionStatus.getSpeakerList().contains(speaker.getName()));
    }
  }

  *//**
   * Test to get a list of all songs in the queue.
   *
   * @throws IOException if method fails
   *//*
  @Test
  void testGetQueue() throws IOException {
    String[] argsList = new String[]{SpeakerCommand.CLEAR_QUEUE.getCommand()};
    SpeakerApi.runSpeakerApi(argsList);
    argsList = new String[]{SpeakerCommand.ADD_URI_TO_QUEUE.getCommand(), SONG_PATH_ONE};
    SpeakerApi.runSpeakerApi(argsList);
    argsList = new String[]{SpeakerCommand.ADD_URI_TO_QUEUE.getCommand(), SONG_PATH_TWO};
    SpeakerApi.runSpeakerApi(argsList);
    argsList = new String[]{SpeakerCommand.ADD_URI_TO_QUEUE.getCommand(), SONG_PATH_THREE};
    SpeakerApi.runSpeakerApi(argsList);
    argsList = new String[]{SpeakerCommand.ADD_URI_TO_QUEUE.getCommand(), SONG_PATH_FOUR};
    SpeakerApi.runSpeakerApi(argsList);
    String[] args = new String[]{SpeakerCommand.GET_QUEUE.getCommand()};
    SpeakerActionStatus speakerActionStatus = SpeakerApi.runSpeakerApi(args);
    
    SpeakerObjects speakerObjects = ReadSpeakerConfigFile.getSpeakerObjectListFromConfigFile();
    if(speakerObjects == null || speakerObjects.getSpeakerObjects() == null || speakerObjects.getSpeakerObjects().size() == 0) {
      List<Speaker> speakers = new ArrayList<>();
      speakers.add(new Speaker("Gang1"));
      speakers.add(new Speaker("Gang2"));
      speakers.add(new Speaker("Gang3"));
      speakerObjects = new SpeakerObjects(speakers);
      WriteSpeakerConfigFile.writeSpeakerObjectListToConfigFile(speakerObjects);
    }
    assert speakerActionStatus != null;
    int count = Collections.frequency(Arrays.asList(speakerActionStatus.getInformation().split(" ")), "at");
    argsList = new String[]{SpeakerCommand.CLEAR_QUEUE.getCommand()};
    SpeakerApi.runSpeakerApi(argsList);

    assertEquals(SpeakerCommand.GET_QUEUE, speakerActionStatus.getSpeakerCommand());
    assertEquals(0, speakerActionStatus.getExitCode());
    assertNull(speakerActionStatus.getException());
    assertEquals(4,count);
    assertTrue(speakerActionStatus.getInformation().contains(SONG_NAME_ONE));
    assertTrue(speakerActionStatus.getInformation().contains(SONG_NAME_TWO));
    assertTrue(speakerActionStatus.getInformation().contains(SONG_NAME_THREE));
    assertTrue(speakerActionStatus.getInformation().contains(SONG_NAME_FOUR));
    for (Speaker speaker : speakerObjects.getSpeakerObjects()) {
      assertThat("Error, don't contains " + speaker.getName(), speakerActionStatus.getSpeakerList().contains(speaker.getName()));
    }
  }

  *//**
   * Test to play a song from the queue.
   *
   * @throws IOException if method fails
   *//*
  @Test
  void testPlayFromQueue() throws IOException {
    String[] argsList = new String[]{SpeakerCommand.CLEAR_QUEUE.getCommand()};
    SpeakerApi.runSpeakerApi(argsList);
    argsList = new String[]{SpeakerCommand.ADD_URI_TO_QUEUE.getCommand(), SONG_PATH_ONE};
    SpeakerApi.runSpeakerApi(argsList);
    String[] args = new String[]{SpeakerCommand.PLAY_FROM_QUEUE.getCommand(), "0"};
    SpeakerActionStatus speakerActionStatus = SpeakerApi.runSpeakerApi(args);
    
    SpeakerObjects speakerObjects = ReadSpeakerConfigFile.getSpeakerObjectListFromConfigFile();
    if(speakerObjects == null || speakerObjects.getSpeakerObjects() == null || speakerObjects.getSpeakerObjects().size() == 0) {
      List<Speaker> speakers = new ArrayList<>();
      speakers.add(new Speaker("Gang1"));
      speakers.add(new Speaker("Gang2"));
      speakers.add(new Speaker("Gang3"));
      speakerObjects = new SpeakerObjects(speakers);
      WriteSpeakerConfigFile.writeSpeakerObjectListToConfigFile(speakerObjects);
    }
    argsList = new String[]{SpeakerCommand.CLEAR_QUEUE.getCommand()};
    SpeakerApi.runSpeakerApi(argsList);

    assert speakerActionStatus != null;
    assertEquals(SpeakerCommand.PLAY_FROM_QUEUE, speakerActionStatus.getSpeakerCommand());
    assertEquals(0, speakerActionStatus.getExitCode());
    assertNull(speakerActionStatus.getException());
    assertNull(speakerActionStatus.getInformation());
    for (Speaker speaker : speakerObjects.getSpeakerObjects()) {
      assertThat("Error, don't contains " + speaker.getName(), speakerActionStatus.getSpeakerList().contains(speaker.getName()));
    }
  }

  *//**
   * Test if an exception occurs with a wrong start number.
   *
   * @throws IOException if method fails
   *//*
  @Test
  void testPlayFromQueueWithWrongStartNumber() throws IOException {
    String[] argsList = new String[]{SpeakerCommand.CLEAR_QUEUE.getCommand()};
    SpeakerApi.runSpeakerApi(argsList);
    argsList = new String[]{SpeakerCommand.ADD_URI_TO_QUEUE.getCommand(), SONG_PATH_ONE};
    SpeakerApi.runSpeakerApi(argsList);
    String[] args = new String[]{SpeakerCommand.PLAY_FROM_QUEUE.getCommand(), "1"};
    SpeakerActionStatus speakerActionStatus = SpeakerApi.runSpeakerApi(args);
    
    SpeakerObjects speakerObjects = ReadSpeakerConfigFile.getSpeakerObjectListFromConfigFile();
    if(speakerObjects == null || speakerObjects.getSpeakerObjects() == null || speakerObjects.getSpeakerObjects().size() == 0) {
      List<Speaker> speakers = new ArrayList<>();
      speakers.add(new Speaker("Gang1"));
      speakers.add(new Speaker("Gang2"));
      speakers.add(new Speaker("Gang3"));
      speakerObjects = new SpeakerObjects(speakers);
      WriteSpeakerConfigFile.writeSpeakerObjectListToConfigFile(speakerObjects);
    }
    argsList = new String[]{SpeakerCommand.CLEAR_QUEUE.getCommand()};
    SpeakerApi.runSpeakerApi(argsList);

    assert speakerActionStatus != null;
    assertEquals(SpeakerCommand.PLAY_FROM_QUEUE, speakerActionStatus.getSpeakerCommand());
    assertEquals(0, speakerActionStatus.getExitCode());
    assertEquals("An exception occurred in the method play_from_queue(index)", speakerActionStatus.getException());
    assertNull(speakerActionStatus.getInformation());
    for (Speaker speaker : speakerObjects.getSpeakerObjects()) {
      assertThat("Error, don't contains " + speaker.getName(), speakerActionStatus.getSpeakerList().contains(speaker.getName()));
    }
  }

  *//**
   * Test to play the next song in the queue.
   *
   * @throws IOException if method fails
   *//*
  @Test
  void testPlayNextSongFromQueue() throws IOException {
    String[] argsList = new String[]{SpeakerCommand.CLEAR_QUEUE.getCommand()};
    SpeakerApi.runSpeakerApi(argsList);
    argsList = new String[]{SpeakerCommand.ADD_URI_TO_QUEUE.getCommand(), SONG_PATH_ONE};
    SpeakerApi.runSpeakerApi(argsList);
    argsList = new String[]{SpeakerCommand.ADD_URI_TO_QUEUE.getCommand(), SONG_PATH_TWO};
    SpeakerApi.runSpeakerApi(argsList);
    argsList = new String[]{SpeakerCommand.PLAY_FROM_QUEUE.getCommand(), "0"};
    SpeakerApi.runSpeakerApi(argsList);
    String[] args = new String[]{SpeakerCommand.NEXT_SONG_QUEUE.getCommand()};
    SpeakerActionStatus speakerActionStatus = SpeakerApi.runSpeakerApi(args);
    
    SpeakerObjects speakerObjects = ReadSpeakerConfigFile.getSpeakerObjectListFromConfigFile();
    if(speakerObjects == null || speakerObjects.getSpeakerObjects() == null || speakerObjects.getSpeakerObjects().size() == 0) {
      List<Speaker> speakers = new ArrayList<>();
      speakers.add(new Speaker("Gang1"));
      speakers.add(new Speaker("Gang2"));
      speakers.add(new Speaker("Gang3"));
      speakerObjects = new SpeakerObjects(speakers);
      WriteSpeakerConfigFile.writeSpeakerObjectListToConfigFile(speakerObjects);
    }
    argsList = new String[]{SpeakerCommand.CLEAR_QUEUE.getCommand()};
    SpeakerApi.runSpeakerApi(argsList);

    assert speakerActionStatus != null;
    assertEquals(SpeakerCommand.NEXT_SONG_QUEUE, speakerActionStatus.getSpeakerCommand());
    assertEquals(0, speakerActionStatus.getExitCode());
    assertNull(speakerActionStatus.getException());
    assertNull(speakerActionStatus.getInformation());
    for (Speaker speaker : speakerObjects.getSpeakerObjects()) {
      assertThat("Error, don't contains " + speaker.getName(), speakerActionStatus.getSpeakerList().contains(speaker.getName()));
    }
  }

  *//**
   * Test if the first song of the queue is played after the next song from the queue when the last song is currently played.
   *
   * @throws IOException if method fails
   *//*
  @Test
  void testPlayNextSongFromQueueSkipFromLastToFirstSong() throws IOException {
    String[] argsList = new String[]{SpeakerCommand.CLEAR_QUEUE.getCommand()};
    SpeakerApi.runSpeakerApi(argsList);
    argsList = new String[]{SpeakerCommand.ADD_URI_TO_QUEUE.getCommand(), SONG_PATH_ONE};
    SpeakerApi.runSpeakerApi(argsList);
    argsList = new String[]{SpeakerCommand.ADD_URI_TO_QUEUE.getCommand(), SONG_PATH_TWO};
    SpeakerApi.runSpeakerApi(argsList);
    argsList = new String[]{SpeakerCommand.PLAY_FROM_QUEUE.getCommand(), "1"};
    SpeakerApi.runSpeakerApi(argsList);
    String[] args = new String[]{SpeakerCommand.NEXT_SONG_QUEUE.getCommand()};
    SpeakerActionStatus speakerActionStatus = SpeakerApi.runSpeakerApi(args);
    
    SpeakerObjects speakerObjects = ReadSpeakerConfigFile.getSpeakerObjectListFromConfigFile();
    if(speakerObjects == null || speakerObjects.getSpeakerObjects() == null || speakerObjects.getSpeakerObjects().size() == 0) {
      List<Speaker> speakers = new ArrayList<>();
      speakers.add(new Speaker("Gang1"));
      speakers.add(new Speaker("Gang2"));
      speakers.add(new Speaker("Gang3"));
      speakerObjects = new SpeakerObjects(speakers);
      WriteSpeakerConfigFile.writeSpeakerObjectListToConfigFile(speakerObjects);
    }
    argsList = new String[]{SpeakerCommand.CLEAR_QUEUE.getCommand()};
    SpeakerApi.runSpeakerApi(argsList);

    assert speakerActionStatus != null;
    assertEquals(SpeakerCommand.NEXT_SONG_QUEUE, speakerActionStatus.getSpeakerCommand());
    assertEquals(0, speakerActionStatus.getExitCode());
    assertNull(speakerActionStatus.getException());
    assertNull(speakerActionStatus.getInformation());
    for (Speaker speaker : speakerObjects.getSpeakerObjects()) {
      assertThat("Error, don't contains " + speaker.getName(), speakerActionStatus.getSpeakerList().contains(speaker.getName()));
    }
  }

  *//**
   * Test if exception occurs by execute next_song_queue if the queue is empty
   *
   * @throws IOException if method fails
   *//*
  @Test
  void testPlayNextSongFromQueueWithEmptyQueue() throws IOException {
    String[] argsList = new String[]{SpeakerCommand.CLEAR_QUEUE.getCommand()};
    SpeakerApi.runSpeakerApi(argsList);
    argsList = new String[]{SpeakerCommand.PLAY_URI.getCommand(), SONG_PATH_ONE};
    SpeakerApi.runSpeakerApi(argsList);
    argsList = new String[]{SpeakerCommand.STOP.getCommand()};
    SpeakerApi.runSpeakerApi(argsList);
    String[] args = new String[]{SpeakerCommand.NEXT_SONG_QUEUE.getCommand()};
    SpeakerActionStatus speakerActionStatus = SpeakerApi.runSpeakerApi(args);
    
    SpeakerObjects speakerObjects = ReadSpeakerConfigFile.getSpeakerObjectListFromConfigFile();
    if(speakerObjects == null || speakerObjects.getSpeakerObjects() == null || speakerObjects.getSpeakerObjects().size() == 0) {
      List<Speaker> speakers = new ArrayList<>();
      speakers.add(new Speaker("Gang1"));
      speakers.add(new Speaker("Gang2"));
      speakers.add(new Speaker("Gang3"));
      speakerObjects = new SpeakerObjects(speakers);
      WriteSpeakerConfigFile.writeSpeakerObjectListToConfigFile(speakerObjects);
    }
    argsList = new String[]{SpeakerCommand.CLEAR_QUEUE.getCommand()};
    SpeakerApi.runSpeakerApi(argsList);

    assert speakerActionStatus != null;
    assertEquals(SpeakerCommand.NEXT_SONG_QUEUE, speakerActionStatus.getSpeakerCommand());
    assertEquals(0, speakerActionStatus.getExitCode());
    assertEquals("An exception occurred in the method next_song_queue", speakerActionStatus.getException());
    assertNull(speakerActionStatus.getInformation());
    for (Speaker speaker : speakerObjects.getSpeakerObjects()) {
      assertThat("Error, don't contains " + speaker.getName(), speakerActionStatus.getSpeakerList().contains(speaker.getName()));
    }
  }

  *//**
   * Test to play the previous song in the queue.
   *
   * @throws IOException if method fails
   *//*
  @Test
  void testPlayPreviousSongFromQueue() throws IOException {
    String[] argsList = new String[]{SpeakerCommand.CLEAR_QUEUE.getCommand()};
    SpeakerApi.runSpeakerApi(argsList);
    argsList = new String[]{SpeakerCommand.ADD_URI_TO_QUEUE.getCommand(), SONG_PATH_ONE};
    SpeakerApi.runSpeakerApi(argsList);
    argsList = new String[]{SpeakerCommand.ADD_URI_TO_QUEUE.getCommand(), SONG_PATH_TWO};
    SpeakerApi.runSpeakerApi(argsList);
    argsList = new String[]{SpeakerCommand.PLAY_FROM_QUEUE.getCommand(), "1"};
    SpeakerApi.runSpeakerApi(argsList);
    String[] args = new String[]{SpeakerCommand.PREVIOUS_SONG_QUEUE.getCommand()};
    SpeakerActionStatus speakerActionStatus = SpeakerApi.runSpeakerApi(args);
    
    SpeakerObjects speakerObjects = ReadSpeakerConfigFile.getSpeakerObjectListFromConfigFile();
    if(speakerObjects == null || speakerObjects.getSpeakerObjects() == null || speakerObjects.getSpeakerObjects().size() == 0) {
      List<Speaker> speakers = new ArrayList<>();
      speakers.add(new Speaker("Gang1"));
      speakers.add(new Speaker("Gang2"));
      speakers.add(new Speaker("Gang3"));
      speakerObjects = new SpeakerObjects(speakers);
      WriteSpeakerConfigFile.writeSpeakerObjectListToConfigFile(speakerObjects);
    }
    argsList = new String[]{SpeakerCommand.CLEAR_QUEUE.getCommand()};
    SpeakerApi.runSpeakerApi(argsList);

    assert speakerActionStatus != null;
    assertEquals(SpeakerCommand.PREVIOUS_SONG_QUEUE, speakerActionStatus.getSpeakerCommand());
    assertEquals(0, speakerActionStatus.getExitCode());
    assertNull(speakerActionStatus.getException());
    assertNull(speakerActionStatus.getInformation());
    for (Speaker speaker : speakerObjects.getSpeakerObjects()) {
      assertThat("Error, don't contains " + speaker.getName(), speakerActionStatus.getSpeakerList().contains(speaker.getName()));
    }
  }

  *//**
   * Test if the last song of the queue is played after the previous song from the queue when the first song is currently played.
   *
   * @throws IOException if method fails
   *//*
  @Test
  void testPlayPreviousSongFromQueueSkipFromLastToFirstSong() throws IOException {
    String[] argsList = new String[]{SpeakerCommand.CLEAR_QUEUE.getCommand()};
    SpeakerApi.runSpeakerApi(argsList);
    argsList = new String[]{SpeakerCommand.ADD_URI_TO_QUEUE.getCommand(), SONG_PATH_ONE};
    SpeakerApi.runSpeakerApi(argsList);
    argsList = new String[]{SpeakerCommand.ADD_URI_TO_QUEUE.getCommand(), SONG_PATH_TWO};
    SpeakerApi.runSpeakerApi(argsList);
    argsList = new String[]{SpeakerCommand.PLAY_FROM_QUEUE.getCommand(), "0"};
    SpeakerApi.runSpeakerApi(argsList);
    String[] args = new String[]{SpeakerCommand.PREVIOUS_SONG_QUEUE.getCommand()};
    SpeakerActionStatus speakerActionStatus = SpeakerApi.runSpeakerApi(args);
    
    SpeakerObjects speakerObjects = ReadSpeakerConfigFile.getSpeakerObjectListFromConfigFile();
    if(speakerObjects == null || speakerObjects.getSpeakerObjects() == null || speakerObjects.getSpeakerObjects().size() == 0) {
      List<Speaker> speakers = new ArrayList<>();
      speakers.add(new Speaker("Gang1"));
      speakers.add(new Speaker("Gang2"));
      speakers.add(new Speaker("Gang3"));
      speakerObjects = new SpeakerObjects(speakers);
      WriteSpeakerConfigFile.writeSpeakerObjectListToConfigFile(speakerObjects);
    }
    argsList = new String[]{SpeakerCommand.CLEAR_QUEUE.getCommand()};
    SpeakerApi.runSpeakerApi(argsList);

    assert speakerActionStatus != null;
    assertEquals(SpeakerCommand.PREVIOUS_SONG_QUEUE, speakerActionStatus.getSpeakerCommand());
    assertEquals(0, speakerActionStatus.getExitCode());
    assertNull(speakerActionStatus.getException());
    assertNull(speakerActionStatus.getInformation());
    for (Speaker speaker : speakerObjects.getSpeakerObjects()) {
      assertThat("Error, don't contains " + speaker.getName(), speakerActionStatus.getSpeakerList().contains(speaker.getName()));
    }
  }

  *//**
   * Test if exception occurs by execute previous_song_queue if the queue is empty
   *
   * @throws IOException if method fails
   *//*
  @Test
  void testPlayPreviousSongFromQueueWithEmptyQueue() throws IOException {
    String[] argsList = new String[]{SpeakerCommand.CLEAR_QUEUE.getCommand()};
    SpeakerApi.runSpeakerApi(argsList);
    argsList = new String[]{SpeakerCommand.PLAY_URI.getCommand(), SONG_PATH_ONE};
    SpeakerApi.runSpeakerApi(argsList);
    argsList = new String[]{SpeakerCommand.STOP.getCommand()};
    SpeakerApi.runSpeakerApi(argsList);
    String[] args = new String[]{SpeakerCommand.PREVIOUS_SONG_QUEUE.getCommand()};
    SpeakerActionStatus speakerActionStatus = SpeakerApi.runSpeakerApi(args);
    
    SpeakerObjects speakerObjects = ReadSpeakerConfigFile.getSpeakerObjectListFromConfigFile();
    if(speakerObjects == null || speakerObjects.getSpeakerObjects() == null || speakerObjects.getSpeakerObjects().size() == 0) {
      List<Speaker> speakers = new ArrayList<>();
      speakers.add(new Speaker("Gang1"));
      speakers.add(new Speaker("Gang2"));
      speakers.add(new Speaker("Gang3"));
      speakerObjects = new SpeakerObjects(speakers);
      WriteSpeakerConfigFile.writeSpeakerObjectListToConfigFile(speakerObjects);
    }
    argsList = new String[]{SpeakerCommand.CLEAR_QUEUE.getCommand()};
    SpeakerApi.runSpeakerApi(argsList);

    assert speakerActionStatus != null;
    assertEquals(SpeakerCommand.PREVIOUS_SONG_QUEUE, speakerActionStatus.getSpeakerCommand());
    assertEquals(0, speakerActionStatus.getExitCode());
    assertEquals("An exception occurred in the method previous_song_queue", speakerActionStatus.getException());
    assertNull(speakerActionStatus.getInformation());
    for (Speaker speaker : speakerObjects.getSpeakerObjects()) {
      assertThat("Error, don't contains " + speaker.getName(), speakerActionStatus.getSpeakerList().contains(speaker.getName()));
    }
  }

  *//**
   * Test if a song can be removed from the queue
   *
   * @throws IOException if method fails
   *//*
  @Test
  void testRemoveFromQueue() throws IOException {
    String[] argsList = new String[]{SpeakerCommand.CLEAR_QUEUE.getCommand()};
    SpeakerApi.runSpeakerApi(argsList);
    argsList = new String[]{SpeakerCommand.ADD_URI_TO_QUEUE.getCommand(), SONG_PATH_ONE};
    SpeakerApi.runSpeakerApi(argsList);
    String[] args = new String[]{SpeakerCommand.REMOVE_FROM_QUEUE.getCommand(), "0"};
    SpeakerActionStatus speakerActionStatus = SpeakerApi.runSpeakerApi(args);
    
    SpeakerObjects speakerObjects = ReadSpeakerConfigFile.getSpeakerObjectListFromConfigFile();
    if(speakerObjects == null || speakerObjects.getSpeakerObjects() == null || speakerObjects.getSpeakerObjects().size() == 0) {
      List<Speaker> speakers = new ArrayList<>();
      speakers.add(new Speaker("Gang1"));
      speakers.add(new Speaker("Gang2"));
      speakers.add(new Speaker("Gang3"));
      speakerObjects = new SpeakerObjects(speakers);
      WriteSpeakerConfigFile.writeSpeakerObjectListToConfigFile(speakerObjects);
    }
    argsList = new String[]{SpeakerCommand.CLEAR_QUEUE.getCommand()};
    SpeakerApi.runSpeakerApi(argsList);

    assert speakerActionStatus != null;
    assertEquals(SpeakerCommand.REMOVE_FROM_QUEUE, speakerActionStatus.getSpeakerCommand());
    assertEquals(0, speakerActionStatus.getExitCode());
    assertNull(speakerActionStatus.getException());
    assertNull(speakerActionStatus.getInformation());
    for (Speaker speaker : speakerObjects.getSpeakerObjects()) {
      assertThat("Error, don't contains " + speaker.getName(), speakerActionStatus.getSpeakerList().contains(speaker.getName()));
    }
  }

  *//**
   * Test if an exception occurs if a song will be removed that doesn't exist in the queue
   *
   * @throws IOException if method fails
   *//*
  @Test
  void testRemoveFromQueueThatNotExistsInQueue() throws IOException {
    String[] argsList = new String[]{SpeakerCommand.CLEAR_QUEUE.getCommand()};
    SpeakerApi.runSpeakerApi(argsList);
    String[] args = new String[]{SpeakerCommand.REMOVE_FROM_QUEUE.getCommand(), "0"};
    SpeakerActionStatus speakerActionStatus = SpeakerApi.runSpeakerApi(args);
    
    SpeakerObjects speakerObjects = ReadSpeakerConfigFile.getSpeakerObjectListFromConfigFile();
    if(speakerObjects == null || speakerObjects.getSpeakerObjects() == null || speakerObjects.getSpeakerObjects().size() == 0) {
      List<Speaker> speakers = new ArrayList<>();
      speakers.add(new Speaker("Gang1"));
      speakers.add(new Speaker("Gang2"));
      speakers.add(new Speaker("Gang3"));
      speakerObjects = new SpeakerObjects(speakers);
      WriteSpeakerConfigFile.writeSpeakerObjectListToConfigFile(speakerObjects);
    }

    assert speakerActionStatus != null;
    assertEquals(SpeakerCommand.REMOVE_FROM_QUEUE, speakerActionStatus.getSpeakerCommand());
    assertEquals(0, speakerActionStatus.getExitCode());
    assertEquals("The given index is to low or high or not a number", speakerActionStatus.getException());
    assertNull(speakerActionStatus.getInformation());
    for (Speaker speaker : speakerObjects.getSpeakerObjects()) {
      assertThat("Error, don't contains " + speaker.getName(), speakerActionStatus.getSpeakerList().contains(speaker.getName()));
    }
  }

  *//**
   * Test to set the volume of the speakers
   *
   * @throws IOException if method fails
   *//*
  @Test
  void testSetVolume() throws IOException {
    String[] args = new String[]{SpeakerCommand.SET_VOLUME.getCommand(), "10"};
    SpeakerActionStatus speakerActionStatus = SpeakerApi.runSpeakerApi(args);
    
    SpeakerObjects speakerObjects = ReadSpeakerConfigFile.getSpeakerObjectListFromConfigFile();
    if(speakerObjects == null || speakerObjects.getSpeakerObjects() == null || speakerObjects.getSpeakerObjects().size() == 0) {
      List<Speaker> speakers = new ArrayList<>();
      speakers.add(new Speaker("Gang1"));
      speakers.add(new Speaker("Gang2"));
      speakers.add(new Speaker("Gang3"));
      speakerObjects = new SpeakerObjects(speakers);
      WriteSpeakerConfigFile.writeSpeakerObjectListToConfigFile(speakerObjects);
    }

    assert speakerActionStatus != null;
    assertEquals(SpeakerCommand.SET_VOLUME, speakerActionStatus.getSpeakerCommand());
    assertEquals(0, speakerActionStatus.getExitCode());
    assertNull(speakerActionStatus.getException());
    assertNull(speakerActionStatus.getInformation());
    for (Speaker speaker : speakerObjects.getSpeakerObjects()) {
      assertThat("Error, don't contains " + speaker.getName(), speakerActionStatus.getSpeakerList().contains(speaker.getName()));
    }
  }

  *//**
   * Test to set the volume of the speakers with a negative volume input
   *
   * @throws IOException if method fails
   *//*
  @Test
  void testSetVolumeWithNegativeVolumeAsInput() throws IOException {
    String[] args = new String[]{SpeakerCommand.SET_VOLUME.getCommand(), "-1"};
    SpeakerActionStatus speakerActionStatus = SpeakerApi.runSpeakerApi(args);
    
    SpeakerObjects speakerObjects = ReadSpeakerConfigFile.getSpeakerObjectListFromConfigFile();
    if(speakerObjects == null || speakerObjects.getSpeakerObjects() == null || speakerObjects.getSpeakerObjects().size() == 0) {
      List<Speaker> speakers = new ArrayList<>();
      speakers.add(new Speaker("Gang1"));
      speakers.add(new Speaker("Gang2"));
      speakers.add(new Speaker("Gang3"));
      speakerObjects = new SpeakerObjects(speakers);
      WriteSpeakerConfigFile.writeSpeakerObjectListToConfigFile(speakerObjects);
    }

    assert speakerActionStatus != null;
    assertEquals(SpeakerCommand.SET_VOLUME, speakerActionStatus.getSpeakerCommand());
    assertEquals(0, speakerActionStatus.getExitCode());
    assertEquals("Please enter a number between 0 and 100 as argument", speakerActionStatus.getException());
    assertNull(speakerActionStatus.getInformation());
    assertNull(speakerActionStatus.getSpeakerList());
  }

  *//**
   * Test to set the volume of the speakers with a too high volume input
   *
   * @throws IOException if method fails
   *//*
  @Test
  void testSetVolumeWithTooHighVolumeAsInput() throws IOException {
    String[] args = new String[]{SpeakerCommand.SET_VOLUME.getCommand(), "105"};
    SpeakerActionStatus speakerActionStatus = SpeakerApi.runSpeakerApi(args);
    
    SpeakerObjects speakerObjects = ReadSpeakerConfigFile.getSpeakerObjectListFromConfigFile();
    if(speakerObjects == null || speakerObjects.getSpeakerObjects() == null || speakerObjects.getSpeakerObjects().size() == 0) {
      List<Speaker> speakers = new ArrayList<>();
      speakers.add(new Speaker("Gang1"));
      speakers.add(new Speaker("Gang2"));
      speakers.add(new Speaker("Gang3"));
      speakerObjects = new SpeakerObjects(speakers);
      WriteSpeakerConfigFile.writeSpeakerObjectListToConfigFile(speakerObjects);
    }

    assert speakerActionStatus != null;
    assertEquals(SpeakerCommand.SET_VOLUME, speakerActionStatus.getSpeakerCommand());
    assertEquals(0, speakerActionStatus.getExitCode());
    assertEquals("Please enter a number between 0 and 100 as argument", speakerActionStatus.getException());
    assertNull(speakerActionStatus.getInformation());
    assertNull(speakerActionStatus.getSpeakerList());
  }

  *//**
   * Test to set the volume of the speakers with a volume input of a
   *
   * @throws IOException if method fails
   *//*
  @Test
  void testSetVolumeWithCharAsInput() throws IOException {
    String[] args = new String[]{SpeakerCommand.SET_VOLUME.getCommand(), "a"};
    SpeakerActionStatus speakerActionStatus = SpeakerApi.runSpeakerApi(args);
    
    SpeakerObjects speakerObjects = ReadSpeakerConfigFile.getSpeakerObjectListFromConfigFile();
    if(speakerObjects == null || speakerObjects.getSpeakerObjects() == null || speakerObjects.getSpeakerObjects().size() == 0) {
      List<Speaker> speakers = new ArrayList<>();
      speakers.add(new Speaker("Gang1"));
      speakers.add(new Speaker("Gang2"));
      speakers.add(new Speaker("Gang3"));
      speakerObjects = new SpeakerObjects(speakers);
      WriteSpeakerConfigFile.writeSpeakerObjectListToConfigFile(speakerObjects);
    }

    assert speakerActionStatus != null;
    assertNull(speakerActionStatus.getSpeakerCommand());
    assertEquals(2, speakerActionStatus.getExitCode());
    assertNull(speakerActionStatus.getException());
    assertNull(speakerActionStatus.getInformation());
    assertNull(speakerActionStatus.getSpeakerList());
  }

  *//**
   * Test to get the volume of the speakers
   *
   * @throws IOException if method fails
   *//*
  @Test
  void testGetVolume() throws IOException {
    String[] argsSetVolume = new String[]{SpeakerCommand.SET_VOLUME.getCommand(), "5"};
    SpeakerApi.runSpeakerApi(argsSetVolume);
    String[] args = new String[]{SpeakerCommand.GET_VOLUME.getCommand()};
    SpeakerActionStatus speakerActionStatus = SpeakerApi.runSpeakerApi(args);
    
    SpeakerObjects speakerObjects = ReadSpeakerConfigFile.getSpeakerObjectListFromConfigFile();
    if(speakerObjects == null || speakerObjects.getSpeakerObjects() == null || speakerObjects.getSpeakerObjects().size() == 0) {
      List<Speaker> speakers = new ArrayList<>();
      speakers.add(new Speaker("Gang1"));
      speakers.add(new Speaker("Gang2"));
      speakers.add(new Speaker("Gang3"));
      speakerObjects = new SpeakerObjects(speakers);
      WriteSpeakerConfigFile.writeSpeakerObjectListToConfigFile(speakerObjects);
    }

    assert speakerActionStatus != null;
    assertEquals(SpeakerCommand.GET_VOLUME, speakerActionStatus.getSpeakerCommand());
    assertEquals(0, speakerActionStatus.getExitCode());
    assertNull(speakerActionStatus.getException());
    assertEquals("5", speakerActionStatus.getInformation());
    for (Speaker speaker : speakerObjects.getSpeakerObjects()) {
      assertThat("Error, don't contains " + speaker.getName(), speakerActionStatus.getSpeakerList().contains(speaker.getName()));
    }
  }

  *//**
   * Test if speaker state is PLAYING after execute play_uri function
   *
   * @throws IOException if method fails
   *//*
  @Test
  void testGetPlayingStatePLAYING() throws IOException {
    String[] argsStop = new String[]{SpeakerCommand.STOP.getCommand()};
    SpeakerApi.runSpeakerApi(argsStop);
    String[] argsPlayUri = new String[]{SpeakerCommand.PLAY_URI.getCommand(), SONG_PATH_ONE};
    SpeakerApi.runSpeakerApi(argsPlayUri);
    String[] args = new String[]{SpeakerCommand.GET_PLAYING_STATE.getCommand()};
    SpeakerActionStatus speakerActionStatus = SpeakerApi.runSpeakerApi(args);
    
    SpeakerObjects speakerObjects = ReadSpeakerConfigFile.getSpeakerObjectListFromConfigFile();
    if(speakerObjects == null || speakerObjects.getSpeakerObjects() == null || speakerObjects.getSpeakerObjects().size() == 0) {
      List<Speaker> speakers = new ArrayList<>();
      speakers.add(new Speaker("Gang1"));
      speakers.add(new Speaker("Gang2"));
      speakers.add(new Speaker("Gang3"));
      speakerObjects = new SpeakerObjects(speakers);
      WriteSpeakerConfigFile.writeSpeakerObjectListToConfigFile(speakerObjects);
    }

    assert speakerActionStatus != null;
    assertEquals(SpeakerCommand.GET_PLAYING_STATE, speakerActionStatus.getSpeakerCommand());
    assertEquals(0, speakerActionStatus.getExitCode());
    assertNull(speakerActionStatus.getException());
    assertEquals(SpeakerState.PLAYING.getState(), speakerActionStatus.getInformation());
    for (Speaker speaker : speakerObjects.getSpeakerObjects()) {
      assertThat("Error, don't contains " + speaker.getName(), speakerActionStatus.getSpeakerList().contains(speaker.getName()));
    }
  }

  *//**
   * Test if speaker state is PAUSED_PLAYBACK after execute pause function
   *
   * @throws IOException if method fails
   *//*
  @Test
  void testGetPlayingStatePause() throws IOException {
    String[] argsStop = new String[]{SpeakerCommand.STOP.getCommand()};
    SpeakerApi.runSpeakerApi(argsStop);
    String[] argsPlayUri = new String[]{SpeakerCommand.PLAY_URI.getCommand(), SONG_PATH_ONE};
    SpeakerApi.runSpeakerApi(argsPlayUri);
    String[] argsPause = new String[]{SpeakerCommand.PAUSE.getCommand()};
    SpeakerApi.runSpeakerApi(argsPause);
    String[] args = new String[]{SpeakerCommand.GET_PLAYING_STATE.getCommand()};
    SpeakerActionStatus speakerActionStatus = SpeakerApi.runSpeakerApi(args);
    
    SpeakerObjects speakerObjects = ReadSpeakerConfigFile.getSpeakerObjectListFromConfigFile();
    if(speakerObjects == null || speakerObjects.getSpeakerObjects() == null || speakerObjects.getSpeakerObjects().size() == 0) {
      List<Speaker> speakers = new ArrayList<>();
      speakers.add(new Speaker("Gang1"));
      speakers.add(new Speaker("Gang2"));
      speakers.add(new Speaker("Gang3"));
      speakerObjects = new SpeakerObjects(speakers);
      WriteSpeakerConfigFile.writeSpeakerObjectListToConfigFile(speakerObjects);
    }

    assert speakerActionStatus != null;
    assertEquals(SpeakerCommand.GET_PLAYING_STATE, speakerActionStatus.getSpeakerCommand());
    assertEquals(0, speakerActionStatus.getExitCode());
    assertNull(speakerActionStatus.getException());
    assertEquals(SpeakerState.PAUSED_PLAYBACK.getState(), speakerActionStatus.getInformation());
    for (Speaker speaker : speakerObjects.getSpeakerObjects()) {
      assertThat("Error, don't contains " + speaker.getName(), speakerActionStatus.getSpeakerList().contains(speaker.getName()));
    }
  }

  *//**
   * Test if speaker state is STOPPED after execute stop function
   *
   * @throws IOException if method fails
   *//*
  @Test
  void testGetPlayingStateStopped() throws IOException {
    String[] argsStop = new String[]{SpeakerCommand.STOP.getCommand()};
    SpeakerApi.runSpeakerApi(argsStop);
    String[] argsPlayUri = new String[]{SpeakerCommand.PLAY_URI.getCommand(), SONG_PATH_ONE};
    SpeakerApi.runSpeakerApi(argsPlayUri);
    String[] argsPause = new String[]{SpeakerCommand.STOP.getCommand()};
    SpeakerApi.runSpeakerApi(argsPause);
    String[] args = new String[]{SpeakerCommand.GET_PLAYING_STATE.getCommand()};
    SpeakerActionStatus speakerActionStatus = SpeakerApi.runSpeakerApi(args);
    
    SpeakerObjects speakerObjects = ReadSpeakerConfigFile.getSpeakerObjectListFromConfigFile();
    if(speakerObjects == null || speakerObjects.getSpeakerObjects() == null || speakerObjects.getSpeakerObjects().size() == 0) {
      List<Speaker> speakers = new ArrayList<>();
      speakers.add(new Speaker("Gang1"));
      speakers.add(new Speaker("Gang2"));
      speakers.add(new Speaker("Gang3"));
      speakerObjects = new SpeakerObjects(speakers);
      WriteSpeakerConfigFile.writeSpeakerObjectListToConfigFile(speakerObjects);
    }

    assert speakerActionStatus != null;
    assertEquals(SpeakerCommand.GET_PLAYING_STATE, speakerActionStatus.getSpeakerCommand());
    assertEquals(0, speakerActionStatus.getExitCode());
    assertNull(speakerActionStatus.getException());
    assertEquals(SpeakerState.STOPPED.getState(), speakerActionStatus.getInformation());
    for (Speaker speaker : speakerObjects.getSpeakerObjects()) {
      assertThat("Error, don't contains " + speaker.getName(), speakerActionStatus.getSpeakerList().contains(speaker.getName()));
    }
  }
*/
}

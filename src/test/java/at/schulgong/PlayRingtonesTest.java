package at.schulgong;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

/**
 * Tests for testing playing ringtones on the sonos speakers
 *
 * @author Thomas Forjan, Philipp Wildzeiss, Martin Kral
 * @version 0.1
 * @since March 2023
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PlayRingtonesTest {

    /*private final String SONG_PATH_ONE = "audiofiles/01_-_attack.mp3";
    private final String SONG_PATH_TWO = "audiofiles/02_-_dreaming.mp3";
    private final String SONG_PATH_THREE = "audiofiles/03_-_kill_rock_n_roll.mp3";
    private final String SONG_PATH_FOUR = "audiofiles/04_-_hypnotize.mp3";

    private final String SONG_NAME_ONE = "01_-_attack.mp3";
    private final String SONG_NAME_TWO = "02_-_dreaming.mp3";
    private final String SONG_NAME_THREE = "03_-_kill_rock_n_roll.mp3";
    private final String SONG_NAME_FOUR = "04_-_hypnotize.mp3";

    private List<PlaylistSongDTO> playlistSongDTOList;
    private PlayRingtones playRingtones;

    @BeforeAll
    public void initialize() {
      playRingtones = new PlayRingtones();
      playlistSongDTOList = new ArrayList<>();
      PlaylistSongDTO playlistSongOne = new PlaylistSongDTO();
      playlistSongOne.setId(1);
      playlistSongOne.setIndex(1);
      playlistSongOne.setName(SONG_NAME_ONE);
      playlistSongOne.setFilePath(SONG_PATH_ONE);
      PlaylistSongDTO playlistSongTwo = new PlaylistSongDTO();
      playlistSongTwo.setId(2);
      playlistSongTwo.setIndex(2);
      playlistSongTwo.setName(SONG_NAME_TWO);
      playlistSongTwo.setFilePath(SONG_PATH_TWO);
      PlaylistSongDTO playlistSongThree = new PlaylistSongDTO();
      playlistSongThree.setId(3);
      playlistSongThree.setIndex(3);
      playlistSongThree.setName(SONG_NAME_THREE);
      playlistSongThree.setFilePath(SONG_PATH_THREE);
      PlaylistSongDTO playlistSongFour = new PlaylistSongDTO();
      playlistSongFour.setId(4);
      playlistSongFour.setIndex(4);
      playlistSongFour.setName(SONG_NAME_FOUR);
      playlistSongFour.setFilePath(SONG_PATH_FOUR);
      playlistSongDTOList.add(playlistSongOne);
      playlistSongDTOList.add(playlistSongTwo);
      playlistSongDTOList.add(playlistSongThree);
      playlistSongDTOList.add(playlistSongFour);
    }

    @BeforeEach
    public void setQueue() {
      String[] argsList = new String[]{SpeakerCommand.STOP.getCommand()};
      SpeakerApi.runSpeakerApi(argsList);
      argsList = new String[]{SpeakerCommand.CLEAR_QUEUE.getCommand()};
      SpeakerApi.runSpeakerApi(argsList);
      argsList = new String[]{SpeakerCommand.ADD_URI_TO_QUEUE.getCommand(), SONG_PATH_ONE};
      SpeakerApi.runSpeakerApi(argsList);
      argsList = new String[]{SpeakerCommand.ADD_URI_TO_QUEUE.getCommand(), SONG_PATH_TWO};
      SpeakerApi.runSpeakerApi(argsList);
      argsList = new String[]{SpeakerCommand.ADD_URI_TO_QUEUE.getCommand(), SONG_PATH_THREE};
      SpeakerApi.runSpeakerApi(argsList);
      argsList = new String[]{SpeakerCommand.ADD_URI_TO_QUEUE.getCommand(), SONG_PATH_FOUR};
      SpeakerApi.runSpeakerApi(argsList);
    }

    @AfterEach
    public void stopPlayingMusic() {
      String[] argsList = new String[]{SpeakerCommand.STOP.getCommand()};
      SpeakerApi.runSpeakerApi(argsList);
      argsList = new String[]{SpeakerCommand.CLEAR_QUEUE.getCommand()};
      SpeakerApi.runSpeakerApi(argsList);
    }

    */
    /**
     * Test if you get the correct playlist infos back from the network speaker volume: 5, mute:
     * false, position: 0, speakerState: PLAYING
     */
    /*
    @Test
    void testGetPlaylistInfoOne() {
      int volume = 5;
      boolean mute = false;
      int position = 0;
      String[] argsList = new String[]{SpeakerCommand.SET_VOLUME.getCommand(), String.valueOf(volume)};
      SpeakerApi.runSpeakerApi(argsList);
      argsList = new String[]{SpeakerCommand.MUTE.getCommand(), Boolean.toString(mute)};
      SpeakerApi.runSpeakerApi(argsList);
      argsList = new String[]{SpeakerCommand.PLAY_FROM_QUEUE.getCommand(), String.valueOf(position)};
      SpeakerApi.runSpeakerApi(argsList);

      PlaylistDTO playlistDTO = playRingtones.getPlaylistInfo(playlistSongDTOList);

      assertEquals(SpeakerState.PLAYING, playlistDTO.getSpeakerState());
      assertEquals(volume, playlistDTO.getVolume());
      assertEquals(mute, playlistDTO.isMute());
      assertEquals(playlistSongDTOList.get(position), playlistDTO.getActualSong());
      assertEquals(playlistSongDTOList, playlistDTO.getSongDTOList());

    }

    */
    /**
     * Test if you get the correct playlist infos back from the network speaker volume: 1, mute:
     * true, position: 2, speakerState: PLAYING
     */
    /*
    @Test
    void testGetPlaylistInfoTwo() {
      int volume = 1;
      boolean mute = true;
      int position = 2;
      String[] argsList = new String[]{SpeakerCommand.SET_VOLUME.getCommand(), String.valueOf(volume)};
      SpeakerApi.runSpeakerApi(argsList);
      argsList = new String[]{SpeakerCommand.MUTE.getCommand(), Boolean.toString(mute)};
      SpeakerApi.runSpeakerApi(argsList);
      argsList = new String[]{SpeakerCommand.PLAY_FROM_QUEUE.getCommand(), String.valueOf(position)};
      SpeakerApi.runSpeakerApi(argsList);

      PlaylistDTO playlistDTO = playRingtones.getPlaylistInfo(playlistSongDTOList);

      assertEquals(SpeakerState.PLAYING, playlistDTO.getSpeakerState());
      assertEquals(volume, playlistDTO.getVolume());
      assertEquals(mute, playlistDTO.isMute());
      assertEquals(playlistSongDTOList.get(position), playlistDTO.getActualSong());
      assertEquals(playlistSongDTOList, playlistDTO.getSongDTOList());

    }

    */
    /**
     * Test if you get the correct playlist infos back from the network speaker volume: 1, mute:
     * false, position: 3, speakerState: STOPPED
     */
    /*
    @Test
    void testGetPlaylistInfoThree() {
      int volume = 1;
      boolean mute = false;
      int position = 3;
      String[] argsList = new String[]{SpeakerCommand.SET_VOLUME.getCommand(), String.valueOf(volume)};
      SpeakerApi.runSpeakerApi(argsList);
      argsList = new String[]{SpeakerCommand.MUTE.getCommand(), Boolean.toString(mute)};
      SpeakerApi.runSpeakerApi(argsList);

      PlaylistDTO playlistDTO = playRingtones.getPlaylistInfo(playlistSongDTOList);

      assertEquals(SpeakerState.STOPPED, playlistDTO.getSpeakerState());
      assertEquals(volume, playlistDTO.getVolume());
      assertEquals(mute, playlistDTO.isMute());
      assertEquals(playlistSongDTOList.get(position), playlistDTO.getActualSong());
      assertEquals(playlistSongDTOList, playlistDTO.getSongDTOList());

    }

    */
    /**
     * Test if you get the correct playlist infos back from the network speaker volume: 4, mute:
     * false, position: 1, speakerState: PAUSED_PLAYBACK
     */
    /*
    @Test
    void testGetPlaylistInfoFour() {
      int volume = 4;
      boolean mute = false;
      int position = 1;
      String[] argsList = new String[]{SpeakerCommand.SET_VOLUME.getCommand(), String.valueOf(volume)};
      SpeakerApi.runSpeakerApi(argsList);
      argsList = new String[]{SpeakerCommand.MUTE.getCommand(), Boolean.toString(mute)};
      SpeakerApi.runSpeakerApi(argsList);
      argsList = new String[]{SpeakerCommand.PLAY_FROM_QUEUE.getCommand(), String.valueOf(position)};
      SpeakerApi.runSpeakerApi(argsList);
      argsList = new String[]{SpeakerCommand.PAUSE.getCommand()};
      SpeakerApi.runSpeakerApi(argsList);

      PlaylistDTO playlistDTO = playRingtones.getPlaylistInfo(playlistSongDTOList);

      assertEquals(SpeakerState.PAUSED_PLAYBACK, playlistDTO.getSpeakerState());
      assertEquals(volume, playlistDTO.getVolume());
      assertEquals(mute, playlistDTO.isMute());
      assertEquals(playlistSongDTOList.get(position), playlistDTO.getActualSong());
      assertEquals(playlistSongDTOList, playlistDTO.getSongDTOList());
    }

    */
    /**
     * Test to set the playlist on the network speaker
     *
     * @throws IOException if method fails
     */
    /*
    @Test
    void testSetPlaylist() throws IOException {
      String[] argsList = new String[]{SpeakerCommand.STOP.getCommand()};
      SpeakerApi.runSpeakerApi(argsList);
      argsList = new String[]{SpeakerCommand.CLEAR_QUEUE.getCommand()};
      SpeakerApi.runSpeakerApi(argsList);

      playRingtones.setPlaylist(playlistSongDTOList);

      String[] args = new String[]{SpeakerCommand.GET_QUEUE.getCommand()};
      SpeakerActionStatus speakerActionStatus = SpeakerApi.runSpeakerApi(args);
      assert speakerActionStatus != null;
      System.out.println(speakerActionStatus.toString());
      SpeakerObjects speakerObjects = ReadSpeakerConfigFile.getSpeakerObjectListFromConfigFile();
      if(speakerObjects == null || speakerObjects.getSpeakerObjects() == null || speakerObjects.getSpeakerObjects().size() == 0) {
        List<Speaker> speakers = new ArrayList<>();
        speakers.add(new Speaker("Gang1"));
        speakers.add(new Speaker("Gang2"));
        speakers.add(new Speaker("Gang3"));
        speakerObjects = new SpeakerObjects(speakers);
        WriteSpeakerConfigFile.writeSpeakerObjectListToConfigFile(speakerObjects);
      }
      int count = Collections.frequency(Arrays.asList(speakerActionStatus.getInformation().split(" ")), "at");

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
    }*/

}

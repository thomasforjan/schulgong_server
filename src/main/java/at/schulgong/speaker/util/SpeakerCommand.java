package at.schulgong.speaker.util;

/**
 * Commands for controlling network speaker
 *
 * @author Thomas Forjan, Philipp Wildzeiss, Martin Kral
 * @version 0.1
 * @since May 2023
 */
public enum SpeakerCommand {
  DISCOVER("discover"),
  DISCOVER_BY_NAME("discover_by_name"),
  DISCOVER_ALL("discover_all"),
  GET_PLAYING_STATE("get_playing_state"),
  PLAY("play"), STOP("stop"),
  PAUSE("pause"), MUTE("mute"),
  GET_MUTE_STATE("get_mute_state"),
  PLAY_URI("play_uri"),
  ADD_URI_TO_QUEUE("add_uri_to_queue"),
  PLAY_FROM_QUEUE("play_from_queue"),
  GET_PLAYLIST_POSITION("get_playlist_position"),
  GET_POSITION("get_position"),
  GET_QUEUE("get_queue"),
  NEXT_SONG_QUEUE("next_song_queue"),
  PREVIOUS_SONG_QUEUE("previous_song_queue"),
  CLEAR_QUEUE("clear_queue"),
  REMOVE_FROM_QUEUE("remove_from_queue"),
  SET_VOLUME("set_volume"),
  GET_VOLUME("get_volume"),
  SEEK("seek"),
  SET_PLAY_MODE("set_play_mode"),
  GET_SPEAKER_INFO("get_speaker_info"),
  GET_CURRENT_MEDIA_INFO("get_current_media_info"),
  GET_PLAYLIST_INFO("get_playlist_info"),
  PLAY_URI_VOLUME_MUTE("play_uri_volume_mute"),
  PLAY_FROM_QUEUE_AFTER_ANNOUNCEMENT("play_from_queue_after_announcement");;
  private final String command;

    SpeakerCommand(String command) {
        this.command = command;
    }

    public static SpeakerCommand fromCommand(String command) throws IllegalArgumentException {
        if (command != null) {
            for (SpeakerCommand speakerCommand : SpeakerCommand.values()) {
                if (command.equals(speakerCommand.command)) {
                    return speakerCommand;
                }
            }
        }
        throw new IllegalArgumentException("Name [" + command + "] not supported.");
    }

    public String getCommand() {
        return this.command;
    }
}

package at.schulgong.speaker.util;

/**
 * @author Thomas Forjan, Philipp Wildzeiss, Martin Kral
 * @version 0.2
 * @implNote Enum for state of speakers
 * @since May 2023
 */
public enum SpeakerState {
  PLAYING("PLAYING"),

  TRANSITIONING("TRANSITIONING"),

  PAUSED_PLAYBACK("PAUSED_PLAYBACK"),

  STOPPED("STOPPED");

  private final String state;

  SpeakerState(String state) {
    this.state = state;
  }

  public static SpeakerState fromState(String state) throws IllegalArgumentException {
    if (state != null) {
      for (SpeakerState speakerState : SpeakerState.values()) {
        if (state.equals(speakerState.state)) {
          return speakerState;
        }
      }
    }
    throw new IllegalArgumentException("Name [" + state + "] not supported.");
  }

  public String getState() {
    return this.state;
  }
}

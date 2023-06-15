package at.schulgong.speaker.util;

import lombok.*;

/**
 * Output from the speaker scripts
 *
 * @author Thomas Forjan, Philipp Wildzeiss, Martin Kral
 * @version 0.1
 * @since May 2023
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SpeakerActionStatus {

    private SpeakerCommand speakerCommand;
    private int exitCode;
    private String exception;
    private String information;
    private String speakerList;

    @Override
    public String toString() {
        if (speakerCommand != null) {
            return "SpeakerActionStatus{"
                    + "speakerCommand="
                    + this.speakerCommand.name()
                    + ", exitCode="
                    + exitCode
                    + ", exception='"
                    + exception
                    + '\''
                    + ", information='"
                    + information
                    + '\''
                    + ", speakerList='"
                    + speakerList
                    + '\''
                    + '}';
        } else {
            return "SpeakerActionStatus{"
                    + "speakerCommand="
                    + ", exitCode="
                    + exitCode
                    + ", exception='"
                    + exception
                    + '\''
                    + ", information='"
                    + information
                    + '\''
                    + ", speakerList='"
                    + speakerList
                    + '\''
                    + '}';
        }
    }
}

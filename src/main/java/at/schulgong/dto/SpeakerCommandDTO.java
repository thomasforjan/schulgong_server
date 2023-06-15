package at.schulgong.dto;

import lombok.*;

/**
 * DTO of Speaker Command object
 *
 * @author Thomas Forjan, Philipp Wildzeiss, Martin Kral
 * @version 0.2
 * @since May 2023
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SpeakerCommandDTO {
    private String command;
    private String parameter;
}

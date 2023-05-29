package at.schulgong.speaker.api;

import at.schulgong.speaker.util.ReadSettingFile;
import at.schulgong.speaker.util.SpeakerActionStatus;
import at.schulgong.speaker.util.SpeakerCommand;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Thomas Forjan, Philipp Wildzeiss, Martin Kral
 * @version 0.1
 * @implNote Api for running scripts to control network speaker
 * @since May 2023
 */
public class SpeakerApi {
  private static final String PYTHON_STRING = "python";
  /**
   * Run specified script for control network speaker
   *
   * @param args Array containing speaker command inclusive arguments
   * @return Output from the executed script in form of SpeakerActionStatus
   */
  public static SpeakerActionStatus runSpeakerApi(String[] args) {
    switch (ReadSettingFile.getSettingFromConfigFile().getType().toLowerCase()) {
      case PYTHON_STRING:
        return runPythonScript(args);
      case "java":
        return runJarFile(args);
      default:
        return null;
    }
  }

  /**
   * Run python script to control network speaker
   *
   * @param args Array containing speaker command inclusive arguments
   * @return Output from the executed script in form of SpeakerActionStatus
   */
  private static SpeakerActionStatus runPythonScript(String[] args) {
    SpeakerActionStatus speakerActionStatus = null;
    try {
      String filePath = ReadSettingFile.getSettingFromConfigFile().getExecutedFilePath();
      ProcessBuilder processBuilder = null;
      if (args != null) {
        if (args.length > 1) {
          processBuilder = new ProcessBuilder(PYTHON_STRING, filePath, args[0], args[1]);
        } else if (args.length > 0) {
          processBuilder = new ProcessBuilder(PYTHON_STRING, filePath, args[0]);
        } else {
          processBuilder = new ProcessBuilder(PYTHON_STRING, filePath);
        }
      }
      if (processBuilder != null) {
        processBuilder.redirectErrorStream(true);

        Process process = processBuilder.start();
        BufferedReader bfr =
          new BufferedReader(new InputStreamReader(process.getInputStream()));
        int exitCode = process.waitFor();
        speakerActionStatus = SpeakerActionStatus.builder().exitCode(exitCode).build();
        System.out.println("Exit Code : " + exitCode);
        readScriptOutput(bfr, speakerActionStatus);
      }

    } catch (InterruptedException | IOException e) {
      e.printStackTrace();
    }
    return speakerActionStatus;
  }

  /**
   * Run jar file to control network speaker
   *
   * @param args Array containing speaker command inclusive arguments
   * @return Output from the executed script in form of SpeakerActionStatus
   */
  private static SpeakerActionStatus runJarFile(String[] args) {
    return null;
  }

  /**
   * Read output from running scripts
   *
   * @param bfr                 Buffered Raader
   * @param speakerActionStatus Output from script in form of SpeakerActionStatus
   * @throws IOException throws ioException
   */
  private static void readScriptOutput(
    BufferedReader bfr, SpeakerActionStatus speakerActionStatus) throws IOException {
    String line = "";
    System.out.println("Running Python starts: " + line);
    if (bfr != null) {
      while ((line = bfr.readLine()) != null) {
        String[] pythonOutput = line.split("=");
        if (pythonOutput.length > 1) {
          switch (pythonOutput[0]) {
            case "command":
              try {
                if (speakerActionStatus.getSpeakerCommand() == null) {
                  speakerActionStatus.setSpeakerCommand(
                    SpeakerCommand.fromCommand(pythonOutput[1].trim()));
                }
              } catch (IllegalArgumentException e) {
                speakerActionStatus.setSpeakerCommand(null);
              }
              break;
            case "information":
              speakerActionStatus.setInformation(getFullOutputLine(pythonOutput));
              break;
            case "exception":
              speakerActionStatus.setException(getFullOutputLine(pythonOutput));
              break;
            case "speakerList":
              speakerActionStatus.setSpeakerList(getFullOutputLine(pythonOutput));
              break;
            default:
              break;
          }
        }
        System.out.println("Python Output: " + line);
      }
    }
  }

  /**
   * Get output line of script output in one string
   *
   * @param pythonOutput Array of the script output
   * @return output in one string
   */
  private static String getFullOutputLine(String[] pythonOutput) {
    StringBuilder fullOutPutLine = new StringBuilder(pythonOutput[1].trim());
    if (pythonOutput.length > 2) {
      for (int i = 2; i < pythonOutput.length; i++) {
        fullOutPutLine.append(pythonOutput[i]);
      }
    }
    return fullOutPutLine.toString();
  }
}

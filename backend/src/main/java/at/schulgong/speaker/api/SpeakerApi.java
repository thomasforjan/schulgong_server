package at.schulgong.speaker.api;

import at.schulgong.speaker.util.ReadSettingFile;
import at.schulgong.speaker.util.SpeakerActionStatus;
import at.schulgong.speaker.util.SpeakerCommand;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SpeakerApi {

    public static SpeakerActionStatus runSpeakerApi(String[] args) {
        switch (ReadSettingFile.getSettingFromConfigFile().getType().toLowerCase()) {
            case "python":
                return runPythonScript(args);
            case "java":
                return runJarFile();
            case "bash":
                return runBashScript();
            case "powershell":
                return runPowerShellScript();
            default:
                return null;
        }
    }

    private static SpeakerActionStatus runPythonScript(String[] args) {
        SpeakerActionStatus speakerActionStatus = null;
        try {
            String filePath = ReadSettingFile.getSettingFromConfigFile().getExecutedFilePath();
            ProcessBuilder processBuilder = null;
            if (args != null) {
                if (args.length > 1) {
                    processBuilder = new ProcessBuilder("python", filePath, args[0], args[1]);
                } else if (args.length > 0) {
                    processBuilder = new ProcessBuilder("python", filePath, args[0]);
                } else {
                    processBuilder = new ProcessBuilder("python", filePath);
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

        } catch (Exception e) {
        }
        return speakerActionStatus;
    }

    private static SpeakerActionStatus runJarFile() {
        SpeakerActionStatus speakerActionStatus = null;
        try {
            ProcessBuilder processBuilder =
                    new ProcessBuilder(
                            "java",
                            "-jar",
                            ReadSettingFile.getSettingFromConfigFile().getExecutedFilePath());
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();
            BufferedReader bfr =
                    new BufferedReader(new InputStreamReader(process.getInputStream()));
            int exitCode = process.waitFor();
            speakerActionStatus = SpeakerActionStatus.builder().exitCode(exitCode).build();
            readScriptOutput(bfr, speakerActionStatus);
            System.out.println("Exit Code : " + exitCode);
        } catch (Exception e) {

        }
        return null;
    }

    private static SpeakerActionStatus runBashScript() {
        boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");
        String cmd = "";
        if (isWindows) {
            cmd = "D://script.bat"; // for windows
        } else {
            cmd = "/home/javasavvy/sample_script.sh";
        }
        try {
            ProcessBuilder pb = new ProcessBuilder(cmd.split(" "));
            Process process = pb.start();
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder builder = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            String result = builder.toString();
            System.out.print(result);
            System.out.println("end of script execution");
        } catch (IOException e) {
            System.out.print("error");
            e.printStackTrace();
        }

        return null;
    }

    private static SpeakerActionStatus runPowerShellScript() {
        try {
            // String command = "powershell.exe  your command";
            // Getting the version
            String command = "powershell.exe  $PSVersionTable.PSVersion";
            // Executing the command
            Process powerShellProcess = Runtime.getRuntime().exec(command);
            // Getting the results
            powerShellProcess.getOutputStream().close();
            String line;
            System.out.println("Standard Output:");
            BufferedReader stdout =
                    new BufferedReader(new InputStreamReader(powerShellProcess.getInputStream()));
            while ((line = stdout.readLine()) != null) {
                System.out.println(line);
            }
            stdout.close();
            System.out.println("Standard Error:");
            BufferedReader stderr =
                    new BufferedReader(new InputStreamReader(powerShellProcess.getErrorStream()));
            while ((line = stderr.readLine()) != null) {
                System.out.println(line);
            }
            stderr.close();
            System.out.println("Done");
        } catch (Exception e) {

        }
        return null;
    }

    private static void readScriptOutput(
            BufferedReader bfr, SpeakerActionStatus speakerActionStatus) throws IOException {
        String line = "";
        System.out.println("Running Python starts: " + line);
        while ((line = bfr.readLine()) != null) {
            String[] pythonOutput = line.split(":");
            if (pythonOutput != null && pythonOutput.length > 1) {
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
                    default:
                        break;
                }
            }
            System.out.println("Python Output: " + line);
        }
    }

    private static String getFullOutputLine(String[] pythonOutput) {
        String fullOutputLine = pythonOutput[1].trim();
        if (pythonOutput.length > 2) {
            for (int i = 2; i < pythonOutput.length; i++) {
                fullOutputLine += pythonOutput[i];
            }
        }
        return fullOutputLine;
    }
}

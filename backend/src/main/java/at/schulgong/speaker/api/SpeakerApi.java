package at.schulgong.speaker.api;

import at.schulgong.speaker.util.ReadSettingFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SpeakerApi {

  public static void runSpeakerApi(String[] args) throws Exception {
    switch (ReadSettingFile.getSettingFromConfigFile().getType().toLowerCase()) {
      case "python":
        runPythonScript(args);
        break;
      case "java":
        runJarFile();
        break;
      case "bash":
        runBashScript();
        break;
      case "powershell":
        runPowerShellScript();
    }
  }

  private static void runPythonScript(String[] args) throws Exception {
    String filePath = ReadSettingFile.getSettingFromConfigFile().getExecutedFilePath();
    ProcessBuilder processBuilder = null;
    if (args != null && args.length > 0) {
      if(args.length > 1) {
        processBuilder = new ProcessBuilder("python", filePath, args[0], args[1]);
      }else {
        processBuilder = new ProcessBuilder("python", filePath, args[0]);
      }
    }
    if(processBuilder != null) {
      processBuilder.redirectErrorStream(true);

      Process process = processBuilder.start();
      BufferedReader bfr = new BufferedReader(new InputStreamReader(process.getInputStream()));
      String line = "";
      System.out.println("Running Python starts: " + line);
      int exitCode = process.waitFor();
      System.out.println("Exit Code : " + exitCode);
      line = bfr.readLine();
      System.out.println("First Line: " + line);
      while ((line = bfr.readLine()) != null) {
        System.out.println("Python Output: " + line);
      }
    }

  }

  private static void runJarFile() throws IOException, InterruptedException {
    ProcessBuilder processBuilder = new ProcessBuilder("java", "-jar", ReadSettingFile.getSettingFromConfigFile().getExecutedFilePath());
    processBuilder.redirectErrorStream(true);
    Process process = processBuilder.start();
    BufferedReader bfr = new BufferedReader(new InputStreamReader(process.getInputStream()));
    String line = "";
    System.out.println("Running Python starts: " + line);
    int exitCode = process.waitFor();
    System.out.println("Exit Code : " + exitCode);
    line = bfr.readLine();
    System.out.println("First Line: " + line);
    while ((line = bfr.readLine()) != null) {
      System.out.println("Python Output: " + line);
    }
  }

  private static void runBashScript() {
    boolean isWindows = System.getProperty("os.name")
      .toLowerCase().startsWith("windows");
    String cmd = "";
    if (isWindows) {
      cmd = "D://script.bat"; //for windows
    } else {
      cmd = "/home/javasavvy/sample_script.sh";
    }
    try {
      ProcessBuilder pb = new ProcessBuilder(cmd.split(" "));
      Process process = pb.start();
      BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
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
  }

  private static void runPowerShellScript() throws IOException {
    //String command = "powershell.exe  your command";
    //Getting the version
    String command = "powershell.exe  $PSVersionTable.PSVersion";
    // Executing the command
    Process powerShellProcess = Runtime.getRuntime().exec(command);
    // Getting the results
    powerShellProcess.getOutputStream().close();
    String line;
    System.out.println("Standard Output:");
    BufferedReader stdout = new BufferedReader(new InputStreamReader(
      powerShellProcess.getInputStream()));
    while ((line = stdout.readLine()) != null) {
      System.out.println(line);
    }
    stdout.close();
    System.out.println("Standard Error:");
    BufferedReader stderr = new BufferedReader(new InputStreamReader(
      powerShellProcess.getErrorStream()));
    while ((line = stderr.readLine()) != null) {
      System.out.println(line);
    }
    stderr.close();
    System.out.println("Done");

  }

}



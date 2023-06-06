package at.schulgong.util;

import ws.schild.jave.Encoder;
import ws.schild.jave.EncoderException;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.encode.AudioAttributes;
import ws.schild.jave.encode.EncodingAttributes;

import java.io.File;

public class AudioConverter {

  public static void convertWebAToMP3(String inputFilePath, String outputFilePath) {
    try {
      AudioAttributes audio = new AudioAttributes();
      audio.setCodec("libmp3lame");
      audio.setBitRate(64000);
      audio.setChannels(2);
      audio.setSamplingRate(44100);
      audio.setVolume(1024);


      //Encoding attributes
      EncodingAttributes attrs = new EncodingAttributes();
      attrs.setOutputFormat("mp3");
      attrs.setAudioAttributes(audio);

      File webaFile = new File(inputFilePath);
      File mp3File = new File(outputFilePath);
      //Encode
      Encoder encoder = new Encoder();
      encoder.encode(new MultimediaObject(webaFile), mp3File, attrs);
      MultimediaObject test = new MultimediaObject(mp3File);
      System.out.println("FILE INFO");
      System.out.println(test.getInfo().getDuration());
    } catch (IllegalArgumentException | EncoderException ex) {
      ex.printStackTrace();
    } catch (Exception ex) {
      ex.printStackTrace();
    } finally {
    }
    System.out.println("Audio conversion completed successfully.");
  }
}

import soco
import json
import os

SPEAKER_CONFIG_FILE_URL = "C:/Users/kralm/OneDrive/Dokumente/Schule/IdeaProjects/FH-Burgenland/4. Semester/Praxisprojekt/schulgong_server/src/main/resources/python/config/speaker_config.json"
START_PATH_URI = "x-file-cifs://DESKTOP-Q0STA8D"

def discover():
  try:
    with open(SPEAKER_CONFIG_FILE_URL, 'r') as myfile:
        data=myfile.read()
    speaker_objects = json.loads(data)
    if speaker_objects is not None and len(speaker_objects) and speaker_objects['speakerObjects'] is not None and len(speaker_objects['speakerObjects']) > 0:
      return discover_by_name()
    else:
      return discover_all()
  except:
      print("exception: An exception occurred in the method discover")

def discover_by_name():
    print("command: discover_by_name")
    try:
        with open(SPEAKER_CONFIG_FILE_URL, 'r') as myfile:
            data=myfile.read()
        speaker_objects = json.loads(data)
        soco_speaker_list = {}
        for speaker in speaker_objects['speakerObjects']:
            sonos_speaker = soco.discovery.by_name(speaker['name'])
            if sonos_speaker is not None:
              soco_speaker_list.update({speaker['name']: sonos_speaker})
        if soco_speaker_list is not None and len(soco_speaker_list) > 0:
          coordinator_speaker_name = _get_coordinator_speaker_name(soco_speaker_list)
          if not _is_grouped(soco_speaker_list):
            soco_speaker_list[coordinator_speaker_name].partymode()
          return soco_speaker_list[coordinator_speaker_name]
        else:
          print("exception: No speaker was found")
    except:
        print("exception: An exception occurred in the method discover_by_name")
    return None

def discover_all():
    print("command: discover_all")
    try:
        soco_speaker_list = {device.player_name: device for device in soco.discover()}
        if soco_speaker_list is not None:
          coordinator_speaker_name = _get_coordinator_speaker_name(soco_speaker_list)
          if not _is_grouped(soco_speaker_list):
            soco_speaker_list[coordinator_speaker_name].partymode()
          return soco_speaker_list[coordinator_speaker_name]
        else:
          print("exception: No speaker was found")
    except:
        print("exception: An exception occurred in the method discover_all")
    return None

def get_playing_state():
    print("command: get_playing_state")
    try:
        soco_speaker = discover()
        if soco_speaker is not None:
            state = soco_speaker.get_current_transport_info()['current_transport_state']
        print("information:", state)
    except:
        print("exception: An exception occurred in the method get_playing_state")

def play():
    print("command: play")
    try:
        soco_speaker = discover()
        if soco_speaker is not None:
            soco_speaker.play()
    except:
        print("exception: An exception occurred in the method play")

def stop():
    print("command: stop")
    try:
        soco_speaker = discover()
        if soco_speaker is not None:
            soco_speaker.stop()
    except:
        print("exception: An exception occurred in the method stop")

def pause():
    print("command: pause")
    try:
        soco_speaker = discover()
        if soco_speaker is not None:
            soco_speaker.pause()
    except:
        print("exception: An exception occurred in the method pause")

def mute(mute):
    print("command: mute")
    try:
        soco_speaker = discover()
        if soco_speaker is not None:
            if str(mute).lower() == 'true':
                soco_speaker.group.mute = True
            else:
                soco_speaker.group.mute = False
    except:
        print("exception: An exception occurred in the method mute(mute)")

def get_mute_state():
    print("command: get_mute_state")
    try:
        soco_speaker = discover()
        if soco_speaker is not None:
            print("information:", soco_speaker.group.mute)
    except:
        print("exception: An exception occurred in the method get_mute_state")

def play_uri(uri):
    print("command: play_uri")
    url = START_PATH_URI + uri
    print(url)
    try:
        soco_speaker = discover()
        if soco_speaker is not None:
          soco_speaker.play_uri(url)
    except:
        print("exception: An exception occurred in the method play_uri")

def add_uri_to_queue(path_song):
    print("command: add_uri_to_queue")
    uri = START_PATH_URI + path_song
    try:
        soco_speaker = discover()
        if soco_speaker is not None:
            soco_speaker.add_uri_to_queue(uri)
    except:
        print("exception: An exception occurred in the method add_uri_to_queue(path_song)")

def play_from_queue(index):
    print("command: play_from_queue")
    try:
        soco_speaker = discover()
        if soco_speaker is not None:
            soco_speaker.play_from_queue(int(index))
    except:
        print("exception: An exception occurred in the method play_from_queue(index)")

def get_queue():
    print("command: get_queue")
    try:
        soco_speaker = discover()
        if soco_speaker is not None:
            queue = soco_speaker.get_queue()
        print("information:", queue)
    except:
        print("exception: An exception occurred in the method get_queue")


def next_song_queue():
    print("command: next_song_queue")
    try:
        soco_speaker = discover()
        if soco_speaker is not None:
            position = soco_speaker.get_current_track_info()['playlist_position']
            queue_length = soco_speaker.queue_size
            if int(position) == int(queue_length):
                soco_speaker.play_from_queue(0)
            else:
                soco_speaker.next()
    except:
        print("exception: An exception occurred in the method next_song_queue")

def previous_song_queue():
    print("command: previous_song_queue")
    try:
        soco_speaker = discover()
        if soco_speaker is not None:
            position = soco_speaker.get_current_track_info()['playlist_position']
            if int(position) == 1:
                queue_length = soco_speaker.queue_size
                soco_speaker.play_from_queue(int(queue_length-1))
            else:
                soco_speaker.previous()
    except:
        print("exception: An exception occurred in the method previous_song_queue")

def clear_queue():
    print("command: clear_queue")
    try:
        soco_speaker = discover()
        if soco_speaker is not None:
            soco_speaker.clear_queue()
    except:
        print("exception: An exception occurred in the method clear_queue")

def remove_from_queue(index):
    print("command: remove_from_queue")
    try:
        soco_speaker = discover()
        if soco_speaker is not None:
            queue = soco_speaker.get_queue()
            print("queue length")
            print(len(queue))
            index = int(index)
            if index >= 0 and index < len(queue):
                soco_speaker.remove_from_queue(index)
            else:
                print("exception: The given index is to low or high or not a number")
    except:
        print("exception: An exception occurred in the method remove_from_queue(index)")

def set_volume(volume):
    print("command: set_volume")
    if volume >= 0 and volume <= 100:
        try:
            soco_speaker = discover()
            if soco_speaker is not None:
                soco_speaker.group.volume = volume
        except:
            print("exception: An exception occurred in the method set_volume(volume)")
    else:
        print("exception: Please enter a number between 0 and 100 as argument")

def get_volume():
    print("command: get_volume")
    try:
        soco_speaker = discover()
        if soco_speaker is not None:
            volume = soco_speaker.group.volume
        print("information:", volume)
    except:
        print("exception: An exception occurred in the method get_volume")

def seek(timestamp):
    print("command: seek")
    try:
        soco_speaker = discover()
        if soco_speaker is not None:
            soco_speaker.seek(timestamp)
    except:
        print("exception: An exception occurred in the method seek(timestamp)")

def set_play_mode(playmode):
    print("command: set_play_mode")
    try:
        if str(playmode).lower() == "normal" or str(playmode).lower() == "repeat_all" or str(playmode).lower() == "shuffle" or  str(playmode).lower() == "shuffle_norepeat":
            soco_speaker = discover()
            if(soco_speaker is not None):
                soco_speaker.set_play_mode = playmode
        else:
            print("exception: Please set a correct playmode (normal, repeat_all, shuffle, shuffle_norepeat)")
    except:
        print("exception: An exception occurred in the method set_play_mode(playmode)")

def get_speaker_info():
    print("command: get_speaker_info")
    try:
        soco_speaker = discover()
        if soco_speaker is not None:
          soco_speaker_list = soco_speaker.group.members
          for speaker in soco_speaker_list:
            info = speaker.get_speaker_info()
            print("information:", info)
    except:
        print("exception: An exception occurred in the method get_speaker_info")


def _set_group(soco_speaker_list):
    print("for for-loop")
    for i in range(1, len(soco_speaker_list)):
        print("in for-loop 1")
        soco_speaker_list[0].join(soco_speaker_list[i])
        print("in for-loop 2")
    soco_speaker_list[0].all_groups
    print("after all_groups")

def _is_grouped(soco_speaker_list):
  for speaker in soco_speaker_list:
    if len(soco_speaker_list[speaker].all_groups) > 1:
      return False
    else:
      return True

def _get_coordinator_speaker_name(soco_speaker_list):
  for speaker in soco_speaker_list:
    if soco_speaker_list[speaker].is_coordinator:
      return speaker

  return list(soco_speaker_list.keys())[0]


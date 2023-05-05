import soco
import json
import os

SPEAKER_CONFIG_FILE_URL = "D://01-Studium\Software Engineering und vernetzte Systeme/4. Semester\Praxisprojekt\schulgong_server/backend\src\main/resources\python\config\speaker_config.json"
START_PATH_URI = "x-file-cifs://DESKTOP-Q0STA8D"

def discover_by_name():
    print("command: discover_by_name")
    try:
        with open(SPEAKER_CONFIG_FILE_URL, 'r') as myfile:
            data=myfile.read()
        speaker_objects = json.loads(data)
        soco_speaker_list = []
        for speaker in speaker_objects['speakerObjects']:
            soco_speaker_list.append(soco.discovery.by_name(speaker['name']))
        soco_speaker_list[0].all_groups
        _set_group(soco_speaker_list)
        print("speakerList:", soco_speaker_list)
        return soco_speaker_list
    except:
        print("exception: An exception occurred in the method discover_by_name")
    return []

def discover_all():
    print("command: discover_all")
    try:
        soco_speaker_list = {device.player_name: device for device in soco.discover()}
        _set_group(soco_speaker_list)
        print("speakerList:", soco_speaker_list)
        return soco_speaker_list
    except:
        print("exception: An exception occurred in the method discover_all")
    return {}

def get_playing_state():
    print("command: get_playing_state")
    try:
        soco_speaker_list = discover_by_name()
        print("AAAAAAAAAAAAA")
        print(soco_speaker_list[0])
        if(len(soco_speaker_list) > 0):
            state = soco_speaker_list[0].get_current_transport_info()['current_transport_state']
        print("information:", state)
    except:
        print("exception: An exception occurred in the method get_playing_state")

def play():
    print("command: play")
    try:
        soco_speaker_list = discover_by_name()
        if(len(soco_speaker_list) > 0):
            soco_speaker_list[0].play()
    except:
        print("exception: An exception occurred in the method play")

def stop():
    print("command: stop")
    try:
        soco_speaker_list = discover_by_name()
        if(len(soco_speaker_list) > 0):
            soco_speaker_list[0].stop()
    except:
        print("exception: An exception occurred in the method stop")

def pause():
    print("command: pause")
    try:
        soco_speaker_list = discover_by_name()
        if(len(soco_speaker_list) > 0):
            soco_speaker_list[0].pause()
    except:
        print("exception: An exception occurred in the method pause")

def mute(mute):
    print("command: mute")
    try:
        soco_speaker_list = discover_by_name()
        if(len(soco_speaker_list) > 0):
            if str(mute).lower() == 'true':
                soco_speaker_list[0].mute = True
            else:
                soco_speaker_list[0].mute = False
    except:
        print("exception: An exception occurred in the method mute(mute)")

def get_mute_state():
    print("command: get_mute_state")
    try:
        soco_speaker_list = discover_by_name()
        if(len(soco_speaker_list) > 0):
            print("information:", soco_speaker_list[0].mute)
    except:
        print("exception: An exception occurred in the method get_mute_state")

def play_uri(uri):
    print("command: play_uri")
    url = START_PATH_URI + uri
    print(url)
    try:
        soco_speaker_list = discover_by_name()
        if(len(soco_speaker_list) > 0):
            soco_speaker_list[0].play_uri(url)
    except:
        print("exception: An exception occurred in the method play_uri")

def add_uri_to_queue(path_song):
    print("command: add_uri_to_queue")
    uri = START_PATH_URI + path_song
    try:
        soco_speaker_list = discover_by_name()
        if(len(soco_speaker_list) > 0):
            soco_speaker_list[0].add_uri_to_queue(uri)
    except:
        print("exception: An exception occurred in the method add_uri_to_queue(path_song)")

def play_from_queue(index):
    print("command: play_from_queue")
    try:
        soco_speaker_list = discover_by_name()
        if(len(soco_speaker_list) > 0):
            soco_speaker_list[0].play_from_queue(int(index))
    except:
        print("exception: An exception occurred in the method play_from_queue(index)")

def get_queue():
    print("command: get_queue")
    try:
        soco_speaker_list = discover_by_name()
        if(len(soco_speaker_list) > 0):
            queue = soco_speaker_list[0].get_queue()
        print("information:", queue)
    except:
        print("exception: An exception occurred in the method get_queue")


def next_song_queue():
    print("command: next_song_queue")
    try:
        soco_speaker_list = discover_by_name()
        if(len(soco_speaker_list) > 0):
            speaker = soco_speaker_list[0]
            position = speaker.get_current_track_info()['playlist_position']
            queue_length = speaker.queue_size
            if int(position) == int(queue_length):
                speaker.play_from_queue(0)
            else:
                speaker.next()
    except:
        print("exception: An exception occurred in the method next_song_queue")

def previous_song_queue():
    print("command: previous_song_queue")
    try:
        soco_speaker_list = discover_by_name()
        if(len(soco_speaker_list) > 0):
            speaker = soco_speaker_list[0]
            position = speaker.get_current_track_info()['playlist_position']
            if int(position) == 1:
                queue_length = speaker.queue_size
                speaker.play_from_queue(int(queue_length-1))
            else:
                speaker.previous()
    except:
        print("exception: An exception occurred in the method previous_song_queue")

def clear_queue():
    print("command: clear_queue")
    try:
        soco_speaker_list = discover_by_name()
        if(len(soco_speaker_list) > 0):
            soco_speaker_list[0].clear_queue()
    except:
        print("exception: An exception occurred in the method clear_queue")

def remove_from_queue(index):
    print("command: remove_from_queue")
    try:
        soco_speaker_list = discover_by_name()
        if(len(soco_speaker_list) > 0):
            queue = soco_speaker_list[0].get_queue()
            print("queue length")
            print(len(queue))
            index = int(index)
            if index >= 0 and index < len(queue):
                soco_speaker_list[0].remove_from_queue(index)
            else:
                print("exception: The given index is to low or high or not a number")
    except:
        print("exception: An exception occurred in the method remove_from_queue(index)")

def set_volume(volume):
    print("command: set_volume")
    if volume >= 0 and volume <= 100:
        try:
            soco_speaker_list = discover_by_name()
            if(len(soco_speaker_list) > 0):
                soco_speaker_list[0].volume = volume
        except:
            print("exception: An exception occurred in the method set_volume(volume)")
    else:
        print("exception: Please enter a number between 0 and 100 as argument")

def get_volume():
    print("command: get_volume")
    try:
        soco_speaker_list = discover_by_name()
        if(len(soco_speaker_list) > 0):
            volume = soco_speaker_list[0].volume
        print("information:", volume)
    except:
        print("exception: An exception occurred in the method get_volume")

def seek(timestamp):
    print("command: seek")
    try:
        soco_speaker_list = discover_by_name()
        if(len(soco_speaker_list) > 0):
            soco_speaker_list[0].seek(timestamp)
    except:
        print("exception: An exception occurred in the method seek(timestamp)")

def set_play_mode(playmode):
    print("command: set_play_mode")
    try:
        if str(playmode).lower() == "normal" or str(playmode).lower() == "repeat_all" or str(playmode).lower() == "shuffle" or  str(playmode).lower() == "shuffle_norepeat":
            soco_speaker_list = discover_by_name()
            if(len(soco_speaker_list) > 0):
                soco_speaker_list[0].set_play_mode = playmode
        else:
            print("exception: Please set a correct playmode (normal, repeat_all, shuffle, shuffle_norepeat)")
    except:
        print("exception: An exception occurred in the method set_play_mode(playmode)")

def get_speaker_info():
    print("command: get_speaker_info")
    try:
        soco_speaker_list = discover_by_name()
        if(len(soco_speaker_list) > 0):
            info = soco_speaker_list[0].get_speaker_info()
            print("information:", info)
    except:
        print("exception: An exception occurred in the method get_speaker_info")


def _set_group(soco_speaker_list):
    for i in range(1, len(soco_speaker_list)):
        soco_speaker_list[0].join(soco_speaker_list[1])


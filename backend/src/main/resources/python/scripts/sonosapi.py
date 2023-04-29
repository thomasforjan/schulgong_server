import soco
import json
import os

SPEAKER_CONFIG_FILE_URL = "D://01-Studium/Software Engineering und vernetzte Systeme/4. Semester/Praxisprojekt/Python_Speaker_API/speaker_config.json"
START_PATH_URI = "x-file-cifs://DESKTOP-Q0STA8D"

def discover_by_name():
    try:
        with open(SPEAKER_CONFIG_FILE_URL, 'r') as myfile:
            data=myfile.read()
        speaker_objects = json.loads(data)
        print(speaker_objects)
        print("name: " + str(speaker_objects['speakerObjects'][0]['name']))
        print("ip_address: " + str(speaker_objects['speakerObjects'][0]['ip_address']))
        soco_speaker_list = []
        for speaker in speaker_objects['speakerObjects']:
            soco_speaker_list.append(soco.discovery.by_name(speaker['name']))
            print(soco_speaker_list)
        print('ALL GROupS')
        soco_speaker_list[0].all_groups
        _set_group(soco_speaker_list)
        print(soco_speaker_list[0])
        return soco_speaker_list
    except:
        print("An exception occurred in the method discover_by_name")
    return []

def discover_all():
    try:
        soco_speaker_list = {device.player_name: device for device in soco.discover()}
        print(soco_speaker_list)
        _set_group(soco_speaker_list)
        return soco_speaker_list
    except:
        print("An exception occurred in the method discover_all")
    return {}

def get_playing_state():
    try:
        soco_speaker_list = discover_by_name()
        if(len(soco_speaker_list) > 0):
            state = soco_speaker_list[0].get_current_transport_info()['current_transport_state']
        print(state)
        return state
    except:
        print("An exception occurred in the method get_playing_state")
    return ""

def play():
    try:
        soco_speaker_list = discover_by_name()
        if(len(soco_speaker_list) > 0):
            return soco_speaker_list[0].play()
    except:
        print("An exception occurred in the method play")
    return False

def stop():
    try:
        soco_speaker_list = discover_by_name()
        if(len(soco_speaker_list) > 0):
            return soco_speaker_list[0].stop()
    except:
        print("An exception occurred in the method stop")
    return False

def pause():
    try:
        soco_speaker_list = discover_by_name()
        if(len(soco_speaker_list) > 0):
            return soco_speaker_list[0].pause()
    except:
        print("An exception occurred in the method pause")
    return False
        
def mute(mute):
    try:
        soco_speaker_list = discover_by_name()
        if(len(soco_speaker_list) > 0):
            if str(mute).lower() == 'true':
                print("MUTE")
                print(mute)
                soco_speaker_list[0].mute = True
            else:
                print("UNMUTE")
                soco_speaker_list[0].mute = False
    except:
        print("An exception occurred in the method mute(mute)")
    return False

def get_mute_state():
    try:
        soco_speaker_list = discover_by_name()
        if(len(soco_speaker_list) > 0):
            print("MUTE")
            print(soco_speaker_list[0].mute)
            return soco_speaker_list[0].mute
    except:
        print("An exception occurred in the method get_mute_state")
    return False

def play_uri(uri):
    url = START_PATH_URI + uri
    print(url)
    try:
        soco_speaker_list = discover_by_name()
        if(len(soco_speaker_list) > 0):
            return soco_speaker_list[0].play_uri(url)
    except:
        print("An exception occurred in the method play_uri")
    return False

def add_uri_to_queue(path_song):
    uri = START_PATH_URI + path_song
    try:
        soco_speaker_list = discover_by_name()
        if(len(soco_speaker_list) > 0):
            return soco_speaker_list[0].add_uri_to_queue(uri)
    except:
        print("An exception occurred in the method add_uri_to_queue(path_song)")
    return False

def play_from_queue(index):
    print(index)
    try:
        soco_speaker_list = discover_by_name()
        if(len(soco_speaker_list) > 0):
            return soco_speaker_list[0].play_from_queue(int(index))
    except:
        print("An exception occurred in the method play_from_queue(index)")
    return False

def get_queue():
    try:
        soco_speaker_list = discover_by_name()
        if(len(soco_speaker_list) > 0):
            queue = soco_speaker_list[0].get_queue()
        print(queue)
        return queue
    except:
        print("An exception occurred in the method get_queue")
    return ""


def next_song_queue():
    try:
        soco_speaker_list = discover_by_name()
        if(len(soco_speaker_list) > 0):
            speaker = soco_speaker_list[0]
            position = speaker.get_current_track_info()['playlist_position']
            queue_length = speaker.queue_size
            if int(position) == int(queue_length):
                return speaker.play_from_queue(0)
            else:
                return speaker.next()
    except:
        print("An exception occurred in the method next_song_queue")
    return False

def previous_song_queue():
    try:
        soco_speaker_list = discover_by_name()
        if(len(soco_speaker_list) > 0):
            speaker = soco_speaker_list[0]
            position = speaker.get_current_track_info()['playlist_position']
            if int(position) == 1:
                queue_length = speaker.queue_size
                return speaker.play_from_queue(int(queue_length-1))
            else:
                return speaker.previous()
    except:
        print("An exception occurred in the method previous_song_queue")
    return False

def clear_queue():
    try:
        soco_speaker_list = discover_by_name()
        if(len(soco_speaker_list) > 0):
            return soco_speaker_list[0].clear_queue()
    except:
        print("An exception occurred in the method clear_queue")
    return False

def remove_from_queue(index):
    try:
        soco_speaker_list = discover_by_name()
        if(len(soco_speaker_list) > 0):
            queue = soco_speaker_list[0].get_queue()
            print("queue length")
            print(len(queue))
            index = int(index)
            if index >= 0 and index < len(queue):
                return soco_speaker_list[0].remove_from_queue(index)
            else:
                print("The given index is to low or high or not a number")
    except:
        print("An exception occurred in the method remove_from_queue(index)")
    return False

def set_volume(volume):
    if volume >= 0 and volume <= 100:
        try:
            soco_speaker_list = discover_by_name()
            if(len(soco_speaker_list) > 0):
                soco_speaker_list[0].volume = volume
            print(volume)
            return True
        except:
            print("An exception occurred in the method set_volume(volume)")
    else:
        print("Please enter a number between 0 and 100 as argument")
    return False

def get_volume():
    try:
        soco_speaker_list = discover_by_name()
        if(len(soco_speaker_list) > 0):
            volume = soco_speaker_list[0].volume
        #print(volume)
        return volume
    except:
        print("An exception occurred in the method get_volume")

def seek(timestamp):
    try:
        soco_speaker_list = discover_by_name()
        if(len(soco_speaker_list) > 0):
            return soco_speaker_list[0].seek(timestamp)
    except:
        print("An exception occurred in the method seek(timestamp)")
    return False

def set_play_mode(playmode):
    try:
        print(playmode)
        if str(playmode).lower() == "normal" or str(playmode).lower() == "repeat_all" or str(playmode).lower() == "shuffle" or  str(playmode).lower() == "shuffle_norepeat":
            print(playmode)
            soco_speaker_list = discover_by_name()
            if(len(soco_speaker_list) > 0):
                soco_speaker_list[0].set_play_mode = playmode
        else:
            print("Please set a correct playmode (normal, repeat_all, shuffle, shuffle_norepeat)")
    except:
        print("An exception occurred in the method set_play_mode(playmode)")
    return False

def get_speaker_info():
    try:
        soco_speaker_list = discover_by_name()
        if(len(soco_speaker_list) > 0):
            info = soco_speaker_list[0].get_speaker_info()
            print(info)
            return info
    except:
        print("An exception occurred in the method get_speaker_info")
    return ""


def _set_group(soco_speaker_list):
    for i in range(1, len(soco_speaker_list)):
        soco_speaker_list[0].join(soco_speaker_list[1])


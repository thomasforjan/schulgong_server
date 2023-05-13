import argparse
import sonosapi


def main():
    parser = argparse.ArgumentParser(
        description='Steuerung des Musik-Servers.')

    subparsers = parser.add_subparsers(dest='command')

    # discover speakers by name or all command
    subparsers.add_parser('discover')

    # discover speakers by name command
    subparsers.add_parser('discover_by_name')

    # discover speakers command
    subparsers.add_parser('discover_all')

    # get playing state of the speakers command
    subparsers.add_parser('get_playing_state')

    # Play command
    subparsers.add_parser('play')

    # Stop command
    subparsers.add_parser('stop')

    # Pause command
    subparsers.add_parser('pause')

    # Mute command
    parser_mute = subparsers.add_parser('mute')
    parser_mute.add_argument('mute', help='Mute(True) or Unmute(False) Song.')

    # Get mute state command
    subparsers.add_parser('get_mute_state')
    
    # Unpause command
    parser_play_uri = subparsers.add_parser('play_uri')
    parser_play_uri.add_argument('path_song', help='Pfad zum Song.')


    # Add uri to queue command
    parser_add_uri_to_queue = subparsers.add_parser('add_uri_to_queue')
    parser_add_uri_to_queue.add_argument('path_song', help='Pfad zum Song.')

    # Play from queue command
    parser_play_from_queue = subparsers.add_parser('play_from_queue')
    parser_play_from_queue.add_argument('index', type=int, help='Start index of the queue')

    # Get queue command
    subparsers.add_parser('get_queue')

    # Next song queue command
    subparsers.add_parser('next_song_queue')

    # Previous song queue command
    subparsers.add_parser('previous_song_queue')

    # Clear queue command
    subparsers.add_parser('clear_queue')

    # Remove from queue command
    parser_remove_from_queue = subparsers.add_parser('remove_from_queue')
    parser_remove_from_queue.add_argument('index', type=int, help='Index of the removed song in the queue')

    # Set volume command
    parser_set_volume = subparsers.add_parser('set_volume')
    parser_set_volume.add_argument(
        'volume', type=int, help='Lautstärke zwischen 0 und 100.')

    # Get volume command
    subparsers.add_parser('get_volume')

    # Set seek command
    parser_seek = subparsers.add_parser('seek')
    parser_seek.add_argument(
        'timestamp', help='Timestamp an dem das Lied gestartet/weitergespielt werden soll.')

    # Set playmode command
    parser_set_play_mode = subparsers.add_parser('set_play_mode')
    parser_set_play_mode.add_argument(
        'playmode', help='Playmode for playing songs in a queue.')

    # Get speaker info command
    subparsers.add_parser('get_speaker_info')


    args = parser.parse_args()

    if args.command == 'discover':
        sonosapi.discover()
    elif args.command == 'discover_by_name':
        sonosapi.discover_by_name()
    elif args.command == 'discover_all':
        sonosapi.discover_all()
    elif args.command == 'get_playing_state':
        sonosapi.get_playing_state()
    elif args.command == 'play':
        sonosapi.play()
    elif args.command == 'stop':
        sonosapi.stop()
    elif args.command == 'pause':
        sonosapi.pause()
    elif args.command == 'mute':
        sonosapi.mute(args.mute)
    elif args.command == 'get_mute_state':
        sonosapi.get_mute_state()
    elif args.command == 'play_uri':
        sonosapi.play_uri(args.path_song)
    elif args.command == 'add_uri_to_queue':
        sonosapi.add_uri_to_queue(args.path_song)
    elif args.command == 'play_from_queue':
        sonosapi.play_from_queue(args.index)
    elif args.command == 'get_queue':
        sonosapi.get_queue()
    elif args.command == 'next_song_queue':
        sonosapi.next_song_queue()
    elif args.command == 'next_song_queue':
        sonosapi.next_song_queue()
    elif args.command == 'previous_song_queue':
        sonosapi.previous_song_queue()
    elif args.command == 'clear_queue':
        sonosapi.clear_queue()
    elif args.command == 'remove_from_queue':
        sonosapi.remove_from_queue(args.index)
    elif args.command == 'set_volume':
        sonosapi.set_volume(args.volume)
    elif args.command == 'get_volume':
        sonosapi.get_volume()
    elif args.command == 'seek':
        sonosapi.seek(args.timestamp)
    elif args.command == 'set_play_mode':
        sonosapi.set_play_mode(args.playmode)
    elif args.command == 'get_speaker_info':
        sonosapi.get_speaker_info()
    else:
        parser.print_help()


if __name__ == '__main__':
    main()

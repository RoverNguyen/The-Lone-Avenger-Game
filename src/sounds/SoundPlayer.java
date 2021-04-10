package sounds;

import javafx.scene.media.MediaPlayer;
import settings.Settings;

public class SoundPlayer {
    public static void PlaySound(MediaPlayer mp){
        if(!Settings.IS_MUTE){
            if(mp.getStatus() == MediaPlayer.Status.PLAYING)
                mp.stop();
            mp.play();
        }
    }
}

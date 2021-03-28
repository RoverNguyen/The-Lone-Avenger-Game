package sounds;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;


public class Sound {

    public static MediaPlayer main = new MediaPlayer(SoundLoader.loadSound("res/sounds/main_theme.mp3"));
    public static MediaPlayer footstep = new MediaPlayer(SoundLoader.loadSound("res/sounds/foot_step.wav"));
    public static MediaPlayer hurt = new MediaPlayer(SoundLoader.loadSound("res/sounds/ouch.mp3"));
    public static MediaPlayer uchiha = new MediaPlayer(SoundLoader.loadSound("res/sounds/uchiha_theme.mp3"));
    public static MediaPlayer gameover = new MediaPlayer(SoundLoader.loadSound("res/sounds/gameover.mp3"));
    public static MediaPlayer victory = new MediaPlayer(SoundLoader.loadSound("res/sounds/victory.mp3"));
    public static MediaPlayer punch = new MediaPlayer(SoundLoader.loadSound("res/sounds/punch.mp3"));
    public static MediaPlayer player_fired = new MediaPlayer(SoundLoader.loadSound("res/sounds/player_fired.wav"));
}

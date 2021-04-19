package settings;

public class Settings {
    public static final int STAGE_WIDTH = 800, STAGE_HEIGHT = 600;
    public static int difficult, countWorld;
    public static int DEFAULT_HEALTH = 100 + difficult*20 + (countWorld-1)*30;
    public static double DEFAULT_SPEED = 3.0 +difficult*1.0 + (countWorld-1)*0.5;
    public static final double PLAYER_SPEED = 7.0;
    public static final int PLAYER_HEALTH = 1000;
    public static final int DEFAULT_CREATURE_WIDTH = 64, DEFAULT_CREATURE_HEIGHT = 64;
    public static final int TILE_WIDTH = 64, TILE_HEIGHT = 64;
    public static final int DISTANCE_PLAYER = 200*200;
    public static final int ATTACK_ZONE = 35*35;
    public static boolean IS_MUTE = false;
    public static int PLAYER_BULLET_DAMAGE = 60;
    public static int SCORES = 0;
    public static int PLAYER_SWORD_DAMAGE = 10;


}

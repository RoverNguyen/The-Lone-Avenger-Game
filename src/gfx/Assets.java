package gfx;

import javafx.scene.image.Image;

public class Assets {

    private static final int width = 32, height = 32;
    public static Image icon, dirt, grass, stone, skeleton, player, checkpoint, clear;
    public static Image[] tree = new Image[13];
    public static Image[][] map1 = new Image[30][30];
    public static Image background, gameover, pause, victory;
    public static Image start, exit, mute_unmute, restart, main_menu;
    public static Image[] slime_up, slime_down, slime_left, slime_right;

    public static Image player_bullet;

    public static void init(){
        icon = ImageLoader.loadImage("res/textures/icon.png");
        SpriteSheet crystal_clear = new SpriteSheet(ImageLoader.loadImage("res/textures/BladeY.png"));
        SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("res/textures/sheet.png"));
        for(int i=0; i<=12; i++){
            tree[i] = ImageLoader.loadImage("res/textures/tree/tree (" + i + ").png");
        }
        SpriteSheet teleport = new SpriteSheet(ImageLoader.loadImage("res/textures/Teleport.png"));
        SpriteSheet map_1 = new SpriteSheet(ImageLoader.loadImage("res/textures/Map_1.png"));
        for(int i = 0; i<29; i++){
            for(int j = 0; j<23; j++){
                map1[j][i] = map_1.crop(width*j, height*i, width, height);
            }
        }
        clear = crystal_clear.crop(0, 0, width, height);
        dirt = sheet.crop(width, 0, width, height);
        grass = sheet.crop(width * 2, 0, width, height);
        stone = sheet.crop(width * 3, 0, width, height);
        checkpoint = teleport.crop(40, 180, 430, 430);

        skeleton = ImageLoader.loadImage("res/textures/skeleton.png");
        player = ImageLoader.loadImage("res/textures/player.png");

        player_bullet = ImageLoader.loadImage("res/textures/player_bullet.png");


        start = ImageLoader.loadImage("res/textures/start_button.png");
        exit = ImageLoader.loadImage("res/textures/exit_button.png");
        mute_unmute = ImageLoader.loadImage("res/textures/mute_unmute.png");
        restart = ImageLoader.loadImage("res/textures/restart.png");
        main_menu = ImageLoader.loadImage("res/textures/main_menu.png");

        background = ImageLoader.loadImage("res/textures/background.jpg");
        gameover = ImageLoader.loadImage("res/textures/gameover.jpg");
        pause = ImageLoader.loadImage("res/textures/pause.jpg");
        victory = ImageLoader.loadImage("res/textures/victory.jpg");

        slime_up = new Image[4];
        for(int i = 0; i < 4; i++)
            slime_up[i] = ImageLoader.loadImage("res/textures/slime/SlimeUp_" + i + ".png");

        slime_down = new Image[4];
        for(int i = 0; i < 4; i++)
            slime_down[i] = ImageLoader.loadImage("res/textures/slime/SlimeDown_" + i + ".png");

        slime_left = new Image[4];
        for(int i = 0; i < 4; i++)
            slime_left[i] = ImageLoader.loadImage("res/textures/slime/SlimeLeft_" + i + ".png");
        slime_right = new Image[4];
        for(int i = 0; i < 4; i++)
            slime_right[i] = ImageLoader.loadImage("res/textures/slime/SlimeRight_" + i + ".png");


    }
}

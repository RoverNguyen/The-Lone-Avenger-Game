package states;

import entities.EntityManager;
import entities.creatures.Player;
import entities.creatures.Skeleton;
import entities.creatures.Slime;
import game.Handler;
import gfx.Assets;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import settings.Settings;
import sounds.Sound;
import worlds.World;


public class GameState extends State{


    public static World[] world = new World[5];
    public static EntityManager entityManager;

    public GameState(Handler handler){
        super(handler);
        world[1] = new World(handler, "res/worlds/world1.txt");
        world[2] = new World(handler, "res/worlds/world2.txt");
        world[3] = new World(handler, "res/worlds/world3.txt");
        world[4] = new World(handler, "res/worlds/world4.txt");
        world[0] = world[1];
        handler.setWorld(world[0], true);

        entityManager = world[1].getEntityManager();

        stateSound = Sound.main;
        handler.getSoundManager().addSound(stateSound);
        stateSound.setCycleCount(MediaPlayer.INDEFINITE);

        //spawn enemies in world 1:
        for(int i = 0; i < 3; ++i){
            world[1].getEntityManager().addEntity(new Skeleton(handler, Assets.skeleton, 550 + 55 * i, 1050));
            world[1].getEntityManager().addEntity(new Slime(handler, Assets.skeleton, 600 + 45 * i, 1150));
            world[1].getEntityManager().addEntity(new Skeleton(handler, Assets.skeleton, 550 + 55 * i, 1200));

            world[1].getEntityManager().addEntity(new Skeleton(handler, Assets.skeleton, 1300 + 55 * i, 1050));
            world[1].getEntityManager().addEntity(new Slime(handler, Assets.skeleton, 1350 + 45 * i, 1150));
            world[1].getEntityManager().addEntity(new Skeleton(handler, Assets.skeleton, 1300 + 55 * i, 1200));

            world[1].getEntityManager().addEntity(new Skeleton(handler, Assets.skeleton, 1100 + 55 * i, 70));
            world[1].getEntityManager().addEntity(new Slime(handler, Assets.skeleton, 1150 + 45 * i, 130));
            world[1].getEntityManager().addEntity(new Skeleton(handler, Assets.skeleton, 1100 + 55 * i, 150));
        }

        //enemies in world 2
        for(int i = 0; i < 3; ++i){
            world[2].getEntityManager().addEntity(new Skeleton(handler, Assets.skeleton, 550 + 55 * i, 1050));
            world[2].getEntityManager().addEntity(new Slime(handler, Assets.skeleton, 150 + 45 * i, 650));
            world[2].getEntityManager().addEntity(new Skeleton(handler, Assets.skeleton, 150 + 55 * i, 700));

            world[2].getEntityManager().addEntity(new Skeleton(handler, Assets.skeleton, 1200 + 55 * i, 150));
            world[2].getEntityManager().addEntity(new Slime(handler, Assets.skeleton, 1350 + 45 * i, 250));
            world[2].getEntityManager().addEntity(new Skeleton(handler, Assets.skeleton, 1100 + 55 * i, 650));

            world[2].getEntityManager().addEntity(new Skeleton(handler, Assets.skeleton, 750 + 55 * i, 300));
            world[2].getEntityManager().addEntity(new Slime(handler, Assets.skeleton, 900 + 45 * i, 400));
        }
    }

    @Override
    public void tick() {
        //stateSound.play();
        handler.getSoundManager().soundOff();
        world[0].tick();
        checkPause();
        checkWin();
    }

    public void checkPause(){
        if(handler.getKeyManager().isPause()){

            //Sounds off
            for(MediaPlayer mediaP : handler.getSoundManager().getSoundList()){
                mediaP.pause();
            }

            //Set pause state
            State.setState(new PauseState(handler));
        }
    }

    public void checkWin(){
        if(Settings.SCORES < 100){
            return;
        }

        Settings.SCORES = 0;

        //Sounds off
        handler.getSoundManager().soundOff();

        //Set victory state
        handler.getMouseManager().setUiManager(null);
        State.setState(new VictoryState(handler));
    }
    @Override
    public void render(GraphicsContext g) {

        world[0].render(g);

        //DRAW SCORES
        g.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        g.setFill(Color.LAVENDER);
        g.fillRect(Settings.STAGE_WIDTH - 200, 0, 200, 30);
        g.setFill(Color.BLACK);
        g.fillText("Điểm số: " + Settings.SCORES, Settings.STAGE_WIDTH - 190, 22);

        //DRAW HEALTH BAR
        double percent = (double) handler.getWorld().getEntityManager().getPlayer().getHealth() /
                (double) handler.getWorld().getEntityManager().getPlayer().getMaxHealth();
        g.setFill(Color.BURLYWOOD);
        g.fillRoundRect(200, 553, 400,7, 15, 15);
        g.setFill(Color.RED);
        g.fillRoundRect(200, 553, percent * 400,7, 15, 15);
        g.setFont(Font.font("Verdana", FontWeight.BOLD, 7));
        g.setFill(Color.WHITE);
        g.fillText(handler.getWorld().getEntityManager().getPlayer().getHealth() + " / "
                + handler.getWorld().getEntityManager().getPlayer().getMaxHealth(), 380, 559);

        //DRAW SPELL COOL DOWN
        g.setFill(Color.BLACK);
        g.strokeOval(700, 520,40,40);
        g.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        int coolDown;
        if(Player.spellCoolDown - Player.spellTimer < 0){
            g.setFill(Color.GREEN);
            g.fillOval(700, 520, 40, 40);
        } else {
            coolDown = (int) Math.ceil((double) (Player.spellCoolDown - Player.spellTimer)/1000);
            g.fillText(coolDown + "s", 707,546);
        }
        g.setFont(Font.font("Verdana", FontWeight.BOLD, 7));
        g.setFill(Color.web("#e2fbff"));
        g.fillRoundRect(711, 555, 20,10, 10,10);
        g.setFill(Color.BLACK);
        g.fillText("Q", 718,562);
    }
}

package entities.creatures;

import entities.Entity;
import game.Handler;

import gfx.Assets;
import gfx.SpriteAnimation;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import settings.Settings;
import sounds.Sound;
import states.GameOverState;
import states.GameState;
import states.State;


public class Player extends Creature{

    protected int count = 9;
    protected int columns = 9;
    protected int offsetX = 0;
    protected int offsetY = 640;
    protected int width = 64;
    protected int height = 64;
    protected boolean tele;
    protected SpriteAnimation animation;
    protected Image player;


    //Attack Timer
    protected long lastAttackTimer, attackCoolDown = 500, attackTimer = attackCoolDown;
    public static long lastSpellTimer, spellCoolDown = 50, spellTimer = spellCoolDown;

    protected MediaPlayer footstep;

    public Player(Handler handler, double x, double y, int damage){
        super(handler, Assets.player, x, y, Settings.DEFAULT_CREATURE_WIDTH, Settings.DEFAULT_CREATURE_HEIGHT, damage);

        setSpeed(5);

        imageView = new ImageView(image);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        imageView.setViewport(new Rectangle2D(offsetX,offsetY,width,height));
        animation = new SpriteAnimation(imageView, Duration.millis(1000),count,columns,offsetX,offsetY,width,height);

        bounds.setX(24);
        bounds.setY(38);
        bounds.setWidth(16);
        bounds.setHeight(24);

        footstep = Sound.footstep;
        handler.getSoundManager().addSound(footstep);

    }

    @Override
    public void tick() {
        //Movement
        getInput();
        move();
        checkPointMove();
        //stepSound();
        handler.getGameCamera().centerOnEntity(this);

        //Attack
        checkAttacks();
        checkSpells();

    }

    //CHECKPOINT
    //check if Player reach CheckPoint
    private boolean checkPointTile(int x, int y){
        for(int z = 0; z < 3; z++ ){
            if(!handler.getWorld().getTile(x,y, z).isCheckPoint());
                return true;
        }
        return false;
        //return handler.getWorld().getTile(x,y).isCheckPoint();
    }

    //set world
    private void setNewWorld(){
        if(handler.getWorld().getWidth()*64/2 <= x+xMove){
            GameState.world[0] = GameState.world[handler.getWorld().getCountWorld()+1];
            tele = true;
        } else {
            GameState.world[0] = GameState.world[handler.getWorld().getCountWorld()-1];
            tele = false;
        }
        handler.setWorld(GameState.world[0], tele);
    }

    //get Move
    private void checkPointMove(){
        if(xMove > 0){
            int tx = (int) (x + xMove + bounds.getX() + bounds.getWidth()) / Settings.TILE_WIDTH;

            if(checkPointTile(tx, ((int) (y + bounds.getY()) / Settings.TILE_HEIGHT)) &&
                    checkPointTile(tx, (int) (y + bounds.getY() + bounds.getHeight()) / Settings.TILE_HEIGHT)){
                setNewWorld();
            }
        } else if(xMove < 0){
            int tx = (int) (x + xMove + bounds.getX()) / Settings.TILE_WIDTH;

            if(checkPointTile(tx, ((int) (y + bounds.getY()) / Settings.TILE_HEIGHT)) &&
                    checkPointTile(tx, (int) (y + bounds.getY() + bounds.getHeight()) / Settings.TILE_HEIGHT)){
                setNewWorld();
            }
        } else if(yMove < 0){
            int ty = (int) (y + yMove + bounds.getY()) / Settings.TILE_HEIGHT;

            if(checkPointTile((int) (x + bounds.getX()) / Settings.TILE_WIDTH, ty) &&
                    checkPointTile((int) (x + bounds.getX() + bounds.getWidth()) / Settings.TILE_WIDTH, ty)){
                setNewWorld();
            }
        } else {
            int ty = (int) (y + yMove + bounds.getY() + bounds.getHeight()) / Settings.TILE_HEIGHT;

            if(checkPointTile((int) (x + bounds.getX()) / Settings.TILE_WIDTH, ty) &&
                    checkPointTile((int) (x + bounds.getX() + bounds.getWidth()) / Settings.TILE_WIDTH, ty)){
                setNewWorld();
            }
        }
    }


    private void checkSpells(){
        spellTimer += System.currentTimeMillis() - lastSpellTimer;
        lastSpellTimer = System.currentTimeMillis();
        if(spellTimer < spellCoolDown){
            return;
        }

        if(handler.getKeyManager().isDestroyThemAll()){
            for(Entity e : handler.getWorld().getEntityManager().getEntities()){
                if (e instanceof Enemy){
                    e.takeDamage(1000);
                }
            }
        }

        if(handler.getKeyManager().isSpell()){
            handler.getWorld().getEntityManager().addBullet(new Bullet(handler, Assets.player_bullet,
                    x + 22, y + 35, Settings.PLAYER_BULLET_DAMAGE, direction));
            if(!Settings.IS_MUTE){
                if(Sound.player_fired.getStatus() == MediaPlayer.Status.PLAYING)
                    Sound.player_fired.stop();
                Sound.player_fired.play();
            }
        } else {
            return;
        }

        spellTimer = 0;

    }

    private void checkAttacks(){
        attackTimer += System.currentTimeMillis() - lastAttackTimer;
        lastAttackTimer = System.currentTimeMillis();
        if(attackTimer < attackCoolDown){
            return;
        }

        Rectangle cb = getCollisionBounds(0, 0);
        Rectangle ar = new Rectangle();

        int arSize = 30;
        ar.setWidth(arSize);
        ar.setHeight(arSize);

        if(handler.getKeyManager().isSpace() && direction == 1){
            ar.setX(cb.getX() + cb.getWidth()/2 - arSize/2);
            ar.setY(cb.getY() - arSize);


        } else if(handler.getKeyManager().isSpace() && direction == 2){
            ar.setX(cb.getX() + cb.getWidth()/2 - arSize/2);
            ar.setY(cb.getY() + cb.getHeight());


        } else if(handler.getKeyManager().isSpace() && direction == 3){
            ar.setX(cb.getX() - arSize);
            ar.setY(cb.getY() + cb.getHeight()/2 - arSize/2);


        } else if(handler.getKeyManager().isSpace() && direction == 4){
            ar.setX(cb.getX() + cb.getWidth());
            ar.setY(cb.getY() + cb.getHeight()/2 - arSize/2);

        } else {

            return;
        }

        handler.getGraphicsContext().fillRect(ar.getX(), ar.getY(), arSize, arSize);

        attackTimer = 0;

        for(Entity e : handler.getWorld().getEntityManager().getEntities()){
            if(e.equals(this))
                continue;
            if(e.getCollisionBounds(0, 0).intersects(ar.getBoundsInLocal())){
                e.takeDamage(damage);
                if(!Settings.IS_MUTE){
                    if(Sound.punch.getStatus() == MediaPlayer.Status.PLAYING)
                        Sound.punch.stop();
                    //Sound.punch.play();
                }
            }
        }
    }

    public void getInput(){
        xMove = 0;
        yMove = 0;

        if(handler.getKeyManager().isMoveUp()){
            direction = 1;
            yMove = -speed;
            animation.setOffsetY(512);
//            animation.setOffsetY(768);
        }

        if(handler.getKeyManager().isMoveDown()){
            direction = 2;
            yMove = speed;
            animation.setOffsetY(640);
//            animation.setOffsetY(896);
        }

        if(handler.getKeyManager().isMoveLeft()){
            direction = 3;
            xMove = -speed;
            animation.setOffsetY(576);
//            animation.setOffsetY(832);
        }

        if(handler.getKeyManager().isMoveRight()){
            direction = 4;
            xMove = speed;
            animation.setOffsetY(704);
//            animation.setOffsetY(960);
        }
    }

    public void stepSound(){
        if(active){
            if(handler.getKeyManager().isMoveUp() || handler.getKeyManager().isMoveDown()
                    || handler.getKeyManager().isMoveLeft() || handler.getKeyManager().isMoveRight()){
                footstep.setCycleCount(MediaPlayer.INDEFINITE);
                //footstep.play();
            } else {
                //footstep.stop();
            }
        }
    }

    @Override
    public void die() {
       // System.out.println("Chết dồi :D");

        //Set active
        active = false;

        //Reset Scores
        Settings.SCORES = 0;

        //Sound off
        handler.getSoundManager().soundOff();

        //New game
        State.setState(new GameOverState(handler));
    }

    @Override
    public void render(GraphicsContext g) {
        //draw player
        if(xMove != 0 || yMove != 0)
            animation.play();
        else animation.stop();

        player = imageView.snapshot(params, null);
        g.drawImage(player, (int)(x - handler.getGameCamera().getxOffset()),
                (int) (y - handler.getGameCamera().getyOffset()));

    }

}

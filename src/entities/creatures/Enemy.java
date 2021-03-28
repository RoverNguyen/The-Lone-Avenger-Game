package entities.creatures;

import game.Handler;
import javafx.scene.image.Image;


import javafx.scene.media.MediaPlayer;
import settings.Settings;
import sounds.Sound;


public abstract class Enemy extends Creature{

    //Music

    //Attack Timer
    private long lastAttackTimer, attackCoolDown = 1000, attackTimer = attackCoolDown;

    //Zone
    double enemyX, enemyY, playerX, playerY, distance;

    public Enemy(Handler handler,  Image image, double x, double y, int damage){
        super(handler, image, x, y, Settings.DEFAULT_CREATURE_WIDTH, Settings.DEFAULT_CREATURE_HEIGHT, damage);


    }

    @Override
    public void tick() {
        //Movements
        getMove();
        move();

        //Attack
        checkAttacks();
    }

    private void checkAttacks(){
        attackTimer += System.currentTimeMillis() - lastAttackTimer;
        lastAttackTimer = System.currentTimeMillis();
        if(attackTimer < attackCoolDown){
            return;
        }

        attackTimer = 0;

        if(checkAttackZone()){
            handler.getWorld().getEntityManager().getPlayer().takeDamage(damage);
            if(!Settings.IS_MUTE){
                if(Sound.hurt.getStatus() == MediaPlayer.Status.PLAYING)
                    Sound.hurt.stop();
                Sound.hurt.play();
            }
        }
    }

    protected boolean checkPlayerZone() {
        enemyX = getCollisionBounds(0,0).getX();
        enemyY = getCollisionBounds(0,0).getY();
        playerX = handler.getWorld().getEntityManager().getPlayer().getCollisionBounds(0,0).getX();
        playerY = handler.getWorld().getEntityManager().getPlayer().getCollisionBounds(0,0).getY();
        distance = (enemyX - playerX)*(enemyX - playerX) + (enemyY - playerY)*(enemyY - playerY);

        return distance < Settings.DISTANCE_PLAYER;
    }

    protected boolean checkAttackZone() {
        enemyX = getCollisionBounds(0,0).getX();
        enemyY = getCollisionBounds(0,0).getY();
        playerX = handler.getWorld().getEntityManager().getPlayer().getCollisionBounds(0,0).getX();
        playerY = handler.getWorld().getEntityManager().getPlayer().getCollisionBounds(0,0).getY();
        distance = (enemyX - playerX)*(enemyX - playerX) + (enemyY - playerY)*(enemyY - playerY);

        return distance < Settings.ATTACK_ZONE;
    }



    public void getMove(){
        xMove = 0;
        yMove = 0;

        if(checkPlayerZone()){
            if(y > handler.getWorld().getEntityManager().getPlayer().getY() + 1){ //up
                direction = 1;
                yMove = -speed;
            }
            if(y < handler.getWorld().getEntityManager().getPlayer().getY() - 1){ //down
                direction = 2;
                yMove = speed;
            }
            if(x < handler.getWorld().getEntityManager().getPlayer().getX() - 1){ //right
                direction = 3;
                xMove = speed;
            }
            if(x > handler.getWorld().getEntityManager().getPlayer().getX() + 1){ //left
                direction = 4;
                xMove = -speed;
            }
        }
    }

    @Override
    public void die() {
        Settings.SCORES++;
        handler.getWorld().setEnemyOnBoard(handler.getWorld().getEnemyOnBoard() - 1);
        System.out.println(Settings.SCORES);
        System.out.println("xin lũiiii mà :(");
    }
}

package entities.creatures.weapons;

import entities.Entity;
import game.Handler;
import javafx.scene.image.Image;
import sounds.Sound;
import sounds.SoundPlayer;

public class Bullet extends Weapon {

    public Bullet(Handler handler, Image image, double x, double y, int damage,int direction) {
        super(handler, image, x, y, 20,21, damage, 500, 500);
        this.direction = direction;

        setSpeed(30);
        bounds.setX(4);
        bounds.setY(4);
        bounds.setHeight(20);
        bounds.setWidth(20);

    }
    @Override
    public void checkHit(){
        //Enemy hit
        for(Entity e : handler.getWorld().getEntityManager().getEntities()){
            if(e.equals(handler.getWorld().getEntityManager().getPlayer()))
                continue;
            if(e.getCollisionBounds(0, 0).intersects(getCollisionBounds(0,0).getBoundsInLocal())){
                e.takeDamage(damage);
                die();

                SoundPlayer.PlaySound(Sound.boom);
            }
        }

        //Tile hit

        //Bullet move
        if(Math.abs(xLong) > xFar || Math.abs(yLong) > yFar)
            die();
    }
}
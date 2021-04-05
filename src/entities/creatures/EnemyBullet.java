package entities.creatures;

import entities.Entity;
import game.Handler;
import javafx.scene.image.Image;



public class EnemyBullet extends Bullet{


    public EnemyBullet(Handler handler, Image image, double x, double y, int damage, int direction) {
        super(handler, image, x, y, damage, direction);
    }

    @Override
    public void checkHit(){
        for(Entity e : handler.getWorld().getEntityManager().getEntities()){
            if(e.equals(handler.getWorld().getEntityManager().getPlayer()))
                e.takeDamage(damage);
                die();
        }
    }
}

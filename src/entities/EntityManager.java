package entities;

import entities.creatures.Bullet;
import entities.creatures.Player;
import game.Handler;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

public class EntityManager{

    private Handler handler;
    private Player player;
    private ArrayList<Entity> entities;
    private ArrayList<Bullet> bullets;
    private Iterator i, j;
    private Comparator<Entity> renderSort = Comparator.comparingDouble(a -> a.getY() + a.getHeight());

    public EntityManager(Handler handler){
        this.handler = handler;
        entities = new ArrayList<>();
        bullets = new ArrayList<>();
    }

    public EntityManager(Handler handler, Player player){
        this.handler = handler;
        this.player = player;
        entities = new ArrayList<>();
        bullets = new ArrayList<>();
        addEntity(player);
    }

    public void tick(){
        i = entities.iterator();
        while(i.hasNext()){
            Entity e = (Entity) i.next();
            e.tick();

            if(!e.isActive())
                i.remove();
        }
        entities.sort(renderSort);

        j = bullets.iterator();
        while(j.hasNext()){
            Bullet bullet = (Bullet) j.next();
            bullet.tick();

            if(!bullet.isActive())
                j.remove();
        }


    }

    public void render(GraphicsContext g){
        for(Entity e: entities){
            e.render(g);
        }

        for(Bullet b : bullets){
            b.render(g);
        }

        player.postRender(g);
    }

    public void addEntity(Entity e){
        entities.add(e);
    }
    public void addBullet(Bullet b) {
        bullets.add(b);
    }
    public void removePlayer(Player player){
        entities.remove(player);
    }


    //Getters & Setters
    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player){
        this.player = player;
        addEntity(player);
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public ArrayList<Bullet> getBullets(){
        return bullets;
    }

}

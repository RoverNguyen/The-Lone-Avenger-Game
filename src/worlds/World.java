package worlds;

import entities.EntityManager;
import entities.creatures.Player;
import entities.creatures.Skeleton;
import entities.creatures.Slime;
import entities.statics.Tree;
import game.Handler;
import gfx.Assets;
import javafx.scene.canvas.GraphicsContext;
import settings.Settings;
import tiles.Tile;
import utils.Utils;


public class World {

    private int width, height;
    private int spawnXNext;
    private int spawnYNext;
    private int spawnXPre;
    private int spawnYPre;
    private int spawnX;
    private int spawnY;
//    private int layer;
    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getCountWorld() {
        return countWorld;
    }

    public void setCountWorld(int countWorld) {
        this.countWorld = countWorld;
    }

    private int countWorld;
    private int[][] tiles;


    //Entities
    private EntityManager entityManager;

    private Handler handler;
    private long lastSpawnTimer, spawnCoolDown = 500, spawnTimer = spawnCoolDown;
    private int enemyOnBoard = 0;

    public World(Handler handler, String path){
        this.handler = handler;
        //entityManager = new EntityManager(handler, new Player(handler,100, 100, 25));
//        for(int i = 0; i < 6; i++){
//            entityManager.addEntity(new Tree(handler, Assets.tree1, 175 + 100*i, 50));
//            entityManager.addEntity(new Tree(handler, Assets.tree12, 175 + 100*i, 130));
//        }
//
//        for(int i = 0; i < 4; i++) {
//            entityManager.addEntity(new Tree(handler, Assets.tree12, 290 + 100 * i, 800));
//        }
        loadWorld(path);
        if(handler.isTele()){
            spawnX = spawnXNext;
            spawnY = spawnYNext;
        }else{
            spawnX = spawnXPre;
            spawnY = spawnYPre;
        }
        entityManager = new EntityManager(handler, new Player(handler,spawnX, spawnY, 25));
        for(int i = 0; i < 6; i++){
            entityManager.addEntity(new Tree(handler, Assets.tree[1], 175 + 100*i, 50));
            entityManager.addEntity(new Tree(handler, Assets.tree[12], 175 + 100*i, 130));
        }

        for(int i = 0; i < 4; i++) {
            entityManager.addEntity(new Tree(handler, Assets.tree[12], 290 + 100 * i, 800));
        }

    }


    public void tick() {
        entityManager.tick();
        spawnEnemy();
    }

    public void spawnEnemy(){
        if(enemyOnBoard < 5){
            spawnTimer += System.currentTimeMillis() - lastSpawnTimer;
            lastSpawnTimer = System.currentTimeMillis();
            if(spawnTimer < spawnCoolDown){
                return;
            }

            enemyOnBoard++;
            int enemyType = 1 + (int) (Math.random()*(2-1+1));
            switch (enemyType){
                case 1: entityManager.addEntity(new Slime(handler, Assets.skeleton,
                        Math.random()*(700 - 100 + 1) + 100, Math.random()*(500 - 300 + 1) + 300, 15)); break;
                case 2: entityManager.addEntity(new Skeleton(handler, Assets.skeleton,
                        Math.random()*(700 - 100 + 1) + 100, Math.random()*(500 - 300 + 1) + 300, 10)); break;
                default: break;
            }

            spawnTimer = 0;
        }

    }

    public void render(GraphicsContext g){

        int xStart = (int) (Math.max(0, handler.getGameCamera().getxOffset() / Settings.TILE_WIDTH));
        int xEnd = (int) (Math.min(width, (handler.getGameCamera().getxOffset() + handler.getWidth()) / Settings.TILE_WIDTH + 1));
        int yStart = (int) (Math.max(0, handler.getGameCamera().getyOffset() / Settings.TILE_HEIGHT));
        int yEnd = (int) (Math.min(height, (handler.getGameCamera().getyOffset() + handler.getHeight()) / Settings.TILE_HEIGHT + 1));

        for (int y = yStart; y < yEnd; y++) {
            for (int x = xStart; x < xEnd; x++) {
                //render layer 1
                getTile(x, y).render(g, (int) (x * Settings.TILE_WIDTH - handler.getGameCamera().getxOffset()),
                        (int) (y * Settings.TILE_HEIGHT - handler.getGameCamera().getyOffset()));

                //render layer 2
                getTile(x, y+ height).render(g, (int) (x * Settings.TILE_WIDTH - handler.getGameCamera().getxOffset()),
                        (int) (y * Settings.TILE_HEIGHT - handler.getGameCamera().getyOffset()));
//                e nghi k can ,, không cần gì á, ơ được á, thử chạy xem ạ, sửa lại cái load world nào
                //render layer 3
                getTile(x, y + height*2).render(g, (int) (x * Settings.TILE_WIDTH - handler.getGameCamera().getxOffset()),
                        (int) (y * Settings.TILE_HEIGHT - handler.getGameCamera().getyOffset()));
            }
        }


        entityManager.render(g);
    }

    //get Tile layer 1
    public Tile getTile(int x, int y){
        if(x < 0 || y < 0 || x >= width || y >= height * 3){
            return Tile.clear;
        }
        Tile t = Tile.tiles[tiles[x][y]];
        if(t == null)
            return Tile.clear;
        return t;
    }

    //get Tile layer 2

    //get Tile layer 3

    public void loadWorld(String path){
        String file = Utils.loadFileAsString(path);
        String[] tokens = file.split("\\s+");
        width = Utils.parseInt(tokens[0]);
        height = Utils.parseInt(tokens[1]);
        spawnXNext = Utils.parseInt(tokens[2]);
        spawnYNext = Utils.parseInt(tokens[3]);
        spawnXPre = Utils.parseInt(tokens[4]);
        spawnYPre = Utils.parseInt(tokens[5]);
        countWorld = Utils.parseInt(tokens[6]);
//        layer = Utils.parseInt(tokens[7]);
        ///System.out.println(layer);

        tiles = new int[width][height*3];
        tiles = new int[25][60];

        //load layer 1
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                tiles[i][j] = Utils.parseInt(tokens[i + j * width + 7]);
            }
        }

        //load layer 2
        for (int j = height; j < height*2; j++) {
            for (int i = 0; i < width; i++) {
                tiles[i][j] = Utils.parseInt(tokens[i + j * width + 7]);
//                tiles[i][j] = 49;
            }
        }

        //load layer 3
        for (int j = height*2; j < height*3; j++) {
            for (int i = 0; i < width; i++) {
                tiles[i][j] = Utils.parseInt(tokens[i + j * width + 7]);
//                tiles[i][j] = 499;
            }
        }

    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public int getEnemyOnBoard() {
        return enemyOnBoard;
    }

    public void setEnemyOnBoard(int enemyOnBoard) {
        this.enemyOnBoard = enemyOnBoard;
    }

//    public int getLayer() {
//        return layer;
//    }
//
//    public void setLayer(int layer) {
//        this.layer = layer;
//    }
}

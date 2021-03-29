package tiles;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import settings.Settings;


public class Tile {
    public static int[] countmap1 = { 38, 39, 40, 41};

    public static Tile[] tiles = new Tile[1000];
//    public static Tile grassTile = new GrassTile(0);
//    public static Tile dirtTile = new DirtTile(1);
    public static Tile rockTile ;
    public static Tile checkPoint ;
    public static Tile clear;
    public static Tile[] Architecture = new Tile[1000];


    protected Image image;
    protected final int id;

    public Tile(Image image, int id){
        this.image = image;
        this.id = id;
        tiles[id] = this;
    }

    public static void init(){
        clear = new Clear(0);
        checkPoint = new CheckPoint(999);
        rockTile = new RockTile(998);
        for(int i=1; i<501; i++ )
            Architecture[i] = new ArchitectureMap1(i);
    }

    public void tick(){

    }

    public void render(GraphicsContext g, int x, int y){
        g.drawImage(image, x, y, Settings.TILE_WIDTH, Settings.TILE_HEIGHT);
    }

    public boolean isSolid(){
        return false;
    }

    public boolean isCheckPoint(){
        return false;
    }

    public int getId(){
        return id;
    }

}

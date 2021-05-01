package states;

import game.Handler;
import gfx.Assets;
import javafx.scene.canvas.GraphicsContext;
import settings.Settings;
import sounds.Sound;
import ui.UIImageButton;
import ui.UIManager;

public class DifficultyState extends State{
    private UIManager uiManager;
    public DifficultyState(Handler handler) {
        super(handler);
        uiManager = new UIManager(handler);
        handler.getMouseManager().setUiManager(uiManager);

        stateSound = Sound.uchiha;
        handler.getSoundManager().addSound(stateSound);
        if(!Settings.IS_MUTE)
            stateSound.play();

        uiManager.addObject(new UIImageButton(80, 400,240, 240, Assets.easy,
                () -> {
                    handler.getMouseManager().setUiManager(null);
                    handler.setTele(true);
                    handler.setDifficulty(0);
                    handler.getGame().gameState = new GameState(handler);
                    State.setState(handler.getGame().gameState);
                    stateSound.dispose();
                }));
        uiManager.addObject(new UIImageButton(480, 400,240, 240, Assets.medium,
                () -> {
                    handler.getMouseManager().setUiManager(null);
                    handler.setTele(true);
                    handler.setDifficulty(1);
                    handler.getGame().gameState = new GameState(handler);
                    State.setState(handler.getGame().gameState);
                    stateSound.dispose();
                }));
        uiManager.addObject(new UIImageButton(880, 400,240, 240, Assets.hard,
                () -> {
                    handler.getMouseManager().setUiManager(null);
                    handler.setTele(true);
                    handler.setDifficulty(2);
                    handler.getGame().gameState = new GameState(handler);
                    State.setState(handler.getGame().gameState);
                    stateSound.dispose();
                }));

    }

    @Override
    public void tick() {
        uiManager.tick();
    }

    @Override
    public void render(GraphicsContext g) {
        g.drawImage(Assets.difficultState, 0, 0, Settings.STAGE_WIDTH, Settings.STAGE_HEIGHT);
        uiManager.render(g);
    }
}

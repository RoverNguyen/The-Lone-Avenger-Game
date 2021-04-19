package states;

import game.Handler;
import gfx.Assets;
import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import settings.Settings;
import sounds.Sound;
import ui.UIImageButton;
import ui.UIManager;

public class DifficultState extends State{
    private UIManager uiManager;
    public DifficultState(Handler handler) {
        super(handler);
        uiManager = new UIManager(handler);
        handler.getMouseManager().setUiManager(uiManager);

        stateSound = Sound.uchiha;
        handler.getSoundManager().addSound(stateSound);
        if(!Settings.IS_MUTE)
            stateSound.play();

        uiManager.addObject(new UIImageButton(60, 200,160, 160, Assets.easy,
                () -> {
                    handler.getMouseManager().setUiManager(null);
                    handler.setTele(true);
                    Settings.difficult = 1;
                    handler.getGame().gameState = new GameState(handler);
                    State.setState(handler.getGame().gameState);
                    stateSound.dispose();
                }));
        uiManager.addObject(new UIImageButton(320, 200,160, 160, Assets.medium,
                () -> {
                    handler.getMouseManager().setUiManager(null);
                    handler.setTele(true);
                    Settings.difficult = 2;
                    handler.getGame().gameState = new GameState(handler);
                    State.setState(handler.getGame().gameState);
                    stateSound.dispose();
                }));
        uiManager.addObject(new UIImageButton(580, 200,160, 160, Assets.hard,
                () -> {
                    handler.getMouseManager().setUiManager(null);
                    handler.setTele(true);
                    Settings.difficult = 3;
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

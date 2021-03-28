package input;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.HashMap;

public class KeyManager {
    private HashMap<KeyCode, Boolean> keys = new HashMap<>();

    Scene scene;

    public KeyManager(Scene scene){
        this.scene = scene;
    }

    public void addListener(){
        scene.addEventFilter(KeyEvent.KEY_PRESSED, keyPressedEventHandler);
        scene.addEventFilter(KeyEvent.KEY_RELEASED, keyReleasedEventHandler);
    }

    private EventHandler<KeyEvent> keyPressedEventHandler = e -> keys.put(e.getCode(), true);

    private EventHandler<KeyEvent> keyReleasedEventHandler = e -> keys.put(e.getCode(), false);

    public boolean isMoveUp(){
        return keys.getOrDefault(KeyCode.UP, false);
    }
    public boolean isMoveDown(){
        return keys.getOrDefault(KeyCode.DOWN, false);
    }
    public boolean isMoveLeft(){
        return keys.getOrDefault(KeyCode.LEFT, false);
    }
    public boolean isMoveRight(){
        return keys.getOrDefault(KeyCode.RIGHT, false);
    }
    public boolean isSpace(){
        return keys.getOrDefault(KeyCode.SPACE, false);
    }
    public boolean isPause(){
        return keys.getOrDefault(KeyCode.P, false);
    }
    public boolean isSpell(){
        return keys.getOrDefault(KeyCode.Q, false);
    }
    public boolean isDestroyThemAll(){
        return keys.getOrDefault(KeyCode.CONTROL, false) && keys.getOrDefault(KeyCode.TAB, false);
    }

}

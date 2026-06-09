package pk.pokeclone.input;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.utils.GdxRuntimeException;

import java.util.HashMap;
import java.util.Map;

public class KeyboardController extends InputAdapter {
    private static final Map<Integer, Command> KEY_MAPPING = Map.ofEntries(
        Map.entry(Input.Keys.W, Command.UP),
        Map.entry(Input.Keys.A, Command.LEFT),
        Map.entry(Input.Keys.S, Command.DOWN),
        Map.entry(Input.Keys.D, Command.RIGHT),
        Map.entry(Input.Keys.SPACE, Command.SELECT),
        Map.entry(Input.Keys.ESCAPE, Command.MENU)
    );

    private final boolean[] commandState;
    private final Map<Class<? extends ControllerState>, ControllerState> stateCache;
    private ControllerState activeState;

    public KeyboardController(Class<? extends  ControllerState> initialState, Engine engine) {
        stateCache = new HashMap<>();
        activeState = null;

        commandState = new boolean[Command.values().length];

        stateCache.put(IdleControllerState.class, new IdleControllerState());
        stateCache.put(GameControllerState.class, new GameControllerState(engine));
        setActiveState(initialState);
    }

    public void setActiveState(Class<? extends ControllerState> stateClass) {
        ControllerState controllerState = stateCache.get(stateClass);
        if(controllerState == null) {
            throw new GdxRuntimeException("Invalid controller state");
        }

        for(Command value : Command.values()) {
            if(activeState != null && commandState[value.ordinal()]) {
                activeState.keyUp(value);
            }
            commandState[value.ordinal()] = false;
        }

        this.activeState = controllerState;
    }

    @Override
    public boolean keyDown(int keycode) {
        Command command = KEY_MAPPING.get(keycode);

        if(command == null) return false;

        commandState[command.ordinal()] = true;
        activeState.keyDown(command);

        return true;
    }

    @Override
    public boolean keyUp(int keyCode) {
        Command command = KEY_MAPPING.get(keyCode);

        if(command == null) return false;

        if(!commandState[command.ordinal()]) return false;

        commandState[command.ordinal()] = false;

        activeState.keyUp(command);

        return true;
    }
}

package pk.pokeclone.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import lombok.Getter;
import pk.pokeclone.input.Command;

import java.util.ArrayList;
import java.util.List;

public class Controller implements Component {
    public static final ComponentMapper<Controller> MAPPER = ComponentMapper.getFor(Controller.class);

    @Getter
    private final List<Command> pressedCommands;
    @Getter
    private final List<Command> releasedCommands;

    public Controller() {
        releasedCommands = new ArrayList<>();
        pressedCommands = new ArrayList<>();
    }
}

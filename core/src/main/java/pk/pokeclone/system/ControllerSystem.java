package pk.pokeclone.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import pk.pokeclone.PokeClone;
import pk.pokeclone.component.Controller;
import pk.pokeclone.component.Move;
import pk.pokeclone.input.Command;
import pk.pokeclone.screen.MenuScreen;

public class ControllerSystem extends IteratingSystem {

    private final PokeClone game;

    public ControllerSystem(PokeClone game) {
        super(Family.all(Controller.class).get());
        this.game = game;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Controller controller = Controller.MAPPER.get(entity);

        if(controller.getPressedCommands().isEmpty() && controller.getReleasedCommands().isEmpty()) {
            return;
        }

        for(Command command : controller.getPressedCommands()) {
            switch (command){
                case UP -> moveEntity(entity, 0f, 1f);
                case DOWN -> moveEntity(entity, 0f, -1f);
                case LEFT -> moveEntity(entity, -1f, 0f);
                case RIGHT -> moveEntity(entity, 1f, 0f);
                case MENU -> game.setScreen(MenuScreen.class);
            }
        }

        controller.getPressedCommands().clear();

        for(Command command : controller.getReleasedCommands()) {
            switch (command){
                case UP -> moveEntity(entity, 0f, -1f);
                case DOWN -> moveEntity(entity, 0f, 1f);
                case LEFT -> moveEntity(entity, 1f, 0f);
                case RIGHT -> moveEntity(entity, -1f, 0f);
            }
        }

        controller.getReleasedCommands().clear();
    }

    private void moveEntity(Entity entity, float directionX, float directionY) {
        Move move = Move.MAPPER.get(entity);

        if(move == null) return;

        move.getDirection().x += directionX;
        move.getDirection().y += directionY;
    }
}

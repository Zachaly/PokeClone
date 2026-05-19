package pk.pokeclone.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import pk.pokeclone.component.Facing;
import pk.pokeclone.component.Move;

public class FacingSystem extends IteratingSystem {
    public FacingSystem() {
        super(Family.all(Facing.class, Move.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Move move = Move.MAPPER.get(entity);
        Vector2 moveDirection = move.getDirection();
        if(move.getDirection().isZero()) {
            return;
        }

        Facing facing = Facing.MAPPER.get(entity);

        if(moveDirection.y > 0) {
            facing.setDirection(Facing.FacingDirection.UP);
        } else if(moveDirection.y < 0) {
            facing.setDirection(Facing.FacingDirection.DOWN);
        } else if(moveDirection.x < 0) {
            facing.setDirection(Facing.FacingDirection.LEFT);
        } else {
            facing.setDirection(Facing.FacingDirection.RIGHT);
        }
    }
}

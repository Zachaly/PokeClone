package pk.pokeclone.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import pk.pokeclone.component.Move;
import pk.pokeclone.component.Transform;

public class MoveSystem extends IteratingSystem {

    public MoveSystem() {
        super(Family.all(Move.class, Transform.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Move move = Move.MAPPER.get(entity);

        if(move.isRooted() || move.getDirection().isZero()) {
            return;
        }

        Transform transform = Transform.MAPPER.get(entity);
        Vector2 currentPos = transform.getPosition();

        transform.getPosition().set(
            currentPos.x + move.getMaxSpeed() * move.getDirection().x * deltaTime,
            currentPos.y + move.getMaxSpeed() * move.getDirection().y * deltaTime
        );
    }
}

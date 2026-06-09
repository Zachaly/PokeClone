package pk.pokeclone.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import pk.pokeclone.component.Move;
import pk.pokeclone.component.Physic;

public class MoveSystem extends IteratingSystem {
    private final Vector2 normalizedDirection = new Vector2();

    public MoveSystem() {
        super(Family.all(Move.class, Physic.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Move move = Move.MAPPER.get(entity);
        Body body = Physic.MAPPER.get(entity).getBody();
        if(move.isRooted() || move.getDirection().isZero()) {
            body.setLinearVelocity(0, 0);
            return;
        }

        normalizedDirection.set(move.getDirection()).nor();
        body.setLinearVelocity(
            move.getMaxSpeed() * normalizedDirection.x,
            move.getMaxSpeed() * normalizedDirection.y
        );
    }
}

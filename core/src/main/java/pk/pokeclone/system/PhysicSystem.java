package pk.pokeclone.system;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.World;
import pk.pokeclone.component.Transform;
import pk.pokeclone.component.Physic;

public class PhysicSystem extends IteratingSystem implements EntityListener {
    private final World world;
    private final float interval;
    private float accumulator;

    public PhysicSystem(World world, float interval) {
        super(Family.all(Physic.class, Transform.class).get());
        this.interval = interval;
        this.world = world;
        accumulator = 0;
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        engine.addEntityListener(getFamily(), this);
    }

    @Override
    public void removedFromEngine(Engine engine) {
        super.removedFromEngine(engine);
        engine.removeEntityListener(this);
    }

    @Override
    public void entityAdded(Entity entity) {

    }

    @Override
    public void entityRemoved(Entity entity) {
        Physic physic = Physic.MAPPER.get(entity);
        if(physic != null) {
            this.world.destroyBody(physic.getBody());
        }
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Physic physic = Physic.MAPPER.get(entity);

        physic.getPrevPosition().set(physic.getBody().getPosition());
    }

    @Override
    public void update(float delta) {
        accumulator += delta;

        if(accumulator >= interval) {
            accumulator = 0;
            super.update(delta);
            world.step(interval, 6, 2);
        }

        world.clearForces();

        float alpha = accumulator / interval;

        for(int i = 0; i < getEntities().size(); ++i) {
            interpolateEntity(getEntities().get(i), alpha);
        }
    }

    private void interpolateEntity(Entity entity, float alpha) {
        Transform transform = Transform.MAPPER.get(entity);
        Physic physic = Physic.MAPPER.get(entity);

        transform.getPosition().set(
            MathUtils.lerp(physic.getPrevPosition().x, physic.getBody().getPosition().x, alpha),
            MathUtils.lerp(physic.getPrevPosition().y, physic.getBody().getPosition().y, alpha)
        );
    }
}

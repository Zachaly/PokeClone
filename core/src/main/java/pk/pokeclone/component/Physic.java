package pk.pokeclone.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Physic implements Component {
    public static ComponentMapper<Physic> MAPPER = ComponentMapper.getFor(Physic.class);

    private final Body body;
    private final Vector2 prevPosition;
}

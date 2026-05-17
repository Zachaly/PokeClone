package pk.pokeclone.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.gdx.math.Vector2;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Move implements Component {
    public static final ComponentMapper<Move> MAPPER = ComponentMapper.getFor(Move.class);

    private float maxSpeed;
    private Vector2 direction;
    private boolean isRooted;

    public Move(float maxSpeed) {
        this.maxSpeed = maxSpeed;
        direction = new Vector2();
    }
}

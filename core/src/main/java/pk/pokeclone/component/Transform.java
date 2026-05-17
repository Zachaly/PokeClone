package pk.pokeclone.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.gdx.math.Vector2;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class Transform implements Component, Comparable<Transform> {
    public static final ComponentMapper<Transform> MAPPER = ComponentMapper.getFor(Transform.class);

    private final Vector2 position;
    private final int z;
    private final Vector2 size;
    private final Vector2 scaling;
    private float rotationDeg;

    @Override
    public int compareTo(Transform o) {
        if(z != o.z) {
            return Float.compare(z, o.z);
        }
        if(position.y != o.position.y) {
            return Float.compare(o.position.y, position.y);
        }
        return Float.compare(position.x, o.position.x);
    }
}

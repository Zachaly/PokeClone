package pk.pokeclone.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class Facing implements Component {
    public static final ComponentMapper<Facing> MAPPER = ComponentMapper.getFor(Facing.class);

    @Getter
    @Setter
    private FacingDirection direction;

    public enum FacingDirection {
        UP, DOWN, LEFT, RIGHT;

        @Getter
        private final String atlasKey;

        FacingDirection() {
            atlasKey = name().toLowerCase();
        }
    }
}

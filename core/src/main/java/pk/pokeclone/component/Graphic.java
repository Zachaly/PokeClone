package pk.pokeclone.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Graphic implements Component {
    public static final ComponentMapper<Graphic> MAPPER = ComponentMapper.getFor(Graphic.class);

    private TextureRegion region;
    private final Color color;
}

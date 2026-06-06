package pk.pokeclone.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import pk.pokeclone.asset.MapAsset;

@AllArgsConstructor
public class ChangeMapTrigger extends Trigger implements Component {
    public static final ComponentMapper<ChangeMapTrigger> MAPPER = ComponentMapper.getFor(ChangeMapTrigger.class);

    @Getter
    private MapAsset map;
}

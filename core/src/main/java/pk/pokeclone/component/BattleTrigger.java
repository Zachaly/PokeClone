package pk.pokeclone.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class BattleTrigger extends Trigger implements Component {
    public static final ComponentMapper<BattleTrigger> MAPPER = ComponentMapper.getFor(BattleTrigger.class);

    private final int lowerLevelLimit;
    private final int upperLevelLimit;
    private List<Integer> pokemonIds;
}

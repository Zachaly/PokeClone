package pk.pokeclone.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import pk.pokeclone.tiled.InteractionType;

@AllArgsConstructor
public class AddInteractionTrigger extends Trigger implements Component {
    public static final ComponentMapper<AddInteractionTrigger> MAPPER = ComponentMapper.getFor(AddInteractionTrigger.class);

    @Getter
    private final InteractionType type;
    @Getter
    @Setter
    private Entity leavingEntity;
}

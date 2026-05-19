package pk.pokeclone.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import lombok.Getter;
import pk.pokeclone.ai.AnimationState;

public class Fsm implements Component {
    public static final ComponentMapper<Fsm> MAPPER = ComponentMapper.getFor(Fsm.class);

    @Getter
    private final DefaultStateMachine<Entity, AnimationState> animationFsm;

    public Fsm(Entity owner) {
        this.animationFsm = new DefaultStateMachine<>(owner, AnimationState.IDLE);
    }
}

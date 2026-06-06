package pk.pokeclone.component;

import com.badlogic.ashley.core.Entity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class Trigger {
    private Entity triggeringEntity = null;
}

package pk.pokeclone.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import pk.pokeclone.asset.MapAsset;
import pk.pokeclone.component.ChangeMapTrigger;

public class ChangeMapSystem extends IteratingSystem {

    private final ChangeMap changeMap;

    public ChangeMapSystem(ChangeMap changeMap) {
        super(Family.all(ChangeMapTrigger.class).get());
        this.changeMap = changeMap;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        ChangeMapTrigger trigger = ChangeMapTrigger.MAPPER.get(entity);

        if(trigger.getTriggeringEntity() != null) {
            changeMap.changeMap(trigger.getMap());
        }
        trigger.setTriggeringEntity(null);
    }

    @FunctionalInterface
    public interface ChangeMap {
        void changeMap(MapAsset mapAsset);
    }
}

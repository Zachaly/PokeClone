package pk.pokeclone.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import pk.pokeclone.component.AddInteractionTrigger;
import pk.pokeclone.tiled.InteractionType;

public class AddInteractionTriggerSystem extends IteratingSystem {

    Interaction pokecenter;
    Interaction shop;
    Interaction clear;

    public AddInteractionTriggerSystem(Interaction pokecenter, Interaction shop, Interaction clear) {
        super(Family.all(AddInteractionTrigger.class).get());
        this.pokecenter = pokecenter;
        this.shop = shop;
        this.clear = clear;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        AddInteractionTrigger trigger = AddInteractionTrigger.MAPPER.get(entity);

        if(trigger.getTriggeringEntity() != null) {
            if(trigger.getType() == InteractionType.POKECENTER) {
                pokecenter.execute();
            } else {
                shop.execute();
            }
        }
        trigger.setTriggeringEntity(null);

        if(trigger.getLeavingEntity() != null) {
            clear.execute();
        }
        trigger.setLeavingEntity(null);
    }

    @FunctionalInterface
    public interface Interaction {
        void execute();
    }
}

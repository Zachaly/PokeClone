package pk.pokeclone.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import pk.pokeclone.PokeClone;
import pk.pokeclone.component.BattleTrigger;
import pk.pokeclone.pokemon.Pokemon;

import java.util.Random;

public class BattleTriggerSystem extends IteratingSystem {

    private final Random random = new Random();
    private final PokeClone game;

    public BattleTriggerSystem(PokeClone game) {
        super(Family.all(BattleTrigger.class).get());
        this.game = game;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        BattleTrigger trigger = BattleTrigger.MAPPER.get(entity);

        if(trigger.getTriggeringEntity() != null) {
            if(random.nextInt(100) < 30) {
                Integer index = trigger.getPokemonIds().get(random.nextInt(0, trigger.getPokemonIds().size()));

                Pokemon pokemon = Pokemon.load(index, random.nextInt(trigger.getLowerLevelLimit(), trigger.getUpperLevelLimit() + 1));

                game.startBattle(pokemon);
            }
        }

        trigger.setTriggeringEntity(null);
    }
}

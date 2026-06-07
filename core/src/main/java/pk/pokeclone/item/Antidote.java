package pk.pokeclone.item;


import pk.pokeclone.pokemon.Pokemon;
import pk.pokeclone.pokemon.attack.StatusEffect;

import java.util.Objects;

public class Antidote extends Item {

    public Antidote() {
        super("Antidote", 100);
    }

    @Override
    public boolean use(Pokemon pokemon) {
        if(pokemon.getStatusEffect() == null) {
            return false;
        }

        if(Objects.equals(pokemon.getStatusEffect().getName(), StatusEffect.POISON)) {
            pokemon.setStatusEffect(null);
        }

        return true;
    }
}

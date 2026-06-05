package pk.pokeclone.item;


import pk.pokeclone.pokemon.Pokemon;
import pk.pokeclone.pokemon.attack.StatusEffect;

import java.util.Objects;

public class Antidote extends Item {

    public Antidote() {
        super("Antidote");
    }

    @Override
    public boolean use(Pokemon pokemon) {
        if(Objects.equals(pokemon.getStatusEffect().getName(), StatusEffect.POISON)) {
            pokemon.setStatusEffect(null);
        }

        return true;
    }
}

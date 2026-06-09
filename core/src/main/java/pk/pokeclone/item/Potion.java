package pk.pokeclone.item;

import pk.pokeclone.pokemon.Pokemon;

public class Potion extends Item {
    public Potion() {
        super("Potion", 100);
    }

    @Override
    public boolean use(Pokemon pokemon) {
        if(pokemon.getHealth() == pokemon.getMaxHealth()) {
            return false;
        }

        pokemon.heal(20);

        return true;
    }
}

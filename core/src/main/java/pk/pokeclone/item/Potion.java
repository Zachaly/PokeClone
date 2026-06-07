package pk.pokeclone.item;

import lombok.AllArgsConstructor;
import pk.pokeclone.pokemon.Pokemon;


public class Potion extends Item {
    public Potion() {
        super("Potion", 100);
    }

    @Override
    public boolean use(Pokemon pokemon) {
        pokemon.heal(20);

        return true;
    }
}

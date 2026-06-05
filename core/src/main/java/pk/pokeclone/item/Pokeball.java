package pk.pokeclone.item;

import pk.pokeclone.pokemon.Pokemon;

import java.util.Random;

public class Pokeball extends Item {
    public Pokeball() {
        super("Pokeball");
    }

    @Override
    public boolean use(Pokemon pokemon) {
        Random rand = new Random();

        if(rand.nextInt(100) * (pokemon.getHealth() / pokemon.getMaxHealth()) < 10) {
            return true;
        }

        return false;
    }
}

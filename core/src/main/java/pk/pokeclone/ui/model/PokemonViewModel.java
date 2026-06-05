package pk.pokeclone.ui.model;

import lombok.Getter;
import pk.pokeclone.PokeClone;
import pk.pokeclone.pokemon.Pokemon;
import pk.pokeclone.screen.PokemonListScreen;

@Getter
public class PokemonViewModel extends ViewModel {
    private final Pokemon pokemon;
    public PokemonViewModel(PokeClone game, Pokemon pokemon) {
        super(game);
        this.pokemon = pokemon;
    }

    public void exit() {
        game.setScreen(PokemonListScreen.class);
    }
}

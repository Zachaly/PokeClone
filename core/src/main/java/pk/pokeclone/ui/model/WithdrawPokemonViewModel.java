package pk.pokeclone.ui.model;

import pk.pokeclone.PokeClone;
import pk.pokeclone.pokemon.Pokemon;
import pk.pokeclone.screen.PokecenterScreen;

import java.util.List;

public class WithdrawPokemonViewModel extends ViewModel {
    public WithdrawPokemonViewModel(PokeClone game) {
        super(game);
    }

    public List<Pokemon> getBoxedPokemons() {
        return game.getPlayerState().getBoxedPokemons();
    }

    public void withdrawPokemon(Pokemon pokemon) {
        if(game.getPlayerState().getPokemons().size() > 5) {
            return;
        }

        game.getPlayerState().getBoxedPokemons().remove(pokemon);
        game.getPlayerState().getPokemons().add(pokemon);
    }

    public void quit() {
        game.setScreen(PokecenterScreen.class);
    }
}

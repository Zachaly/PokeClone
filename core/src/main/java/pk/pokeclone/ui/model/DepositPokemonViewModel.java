package pk.pokeclone.ui.model;

import pk.pokeclone.PokeClone;
import pk.pokeclone.pokemon.Pokemon;
import pk.pokeclone.screen.PokecenterScreen;

import java.util.List;

public class DepositPokemonViewModel extends ViewModel{
    public DepositPokemonViewModel(PokeClone game) {
        super(game);
    }

    public void depositPokemon(Pokemon pokemon) {
        if(game.getPlayerState().getPokemons().size() < 2) {
            return;
        }

        game.getPlayerState().getPokemons().remove(pokemon);
        game.getPlayerState().getBoxedPokemons().add(pokemon);
    }

    public List<Pokemon> getPokemon() {
        return game.getPlayerState().getPokemons();
    }

    public void quit() {
        game.setScreen(PokecenterScreen.class);
    }
}

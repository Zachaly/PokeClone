package pk.pokeclone.ui.model;

import pk.pokeclone.PokeClone;
import pk.pokeclone.pokemon.Pokemon;
import pk.pokeclone.screen.DepositPokemonScreen;
import pk.pokeclone.screen.GameScreen;
import pk.pokeclone.screen.WithdrawPokemonScreen;

public class PokecenterViewModel extends ViewModel{
    public PokecenterViewModel(PokeClone game) {
        super(game);
    }

    public void healPokemon() {
        for(Pokemon pokemon : game.getPlayerState().getPokemons()) {
            pokemon.heal();
        }
    }

    public void depositPokemon() {
        if(game.getPlayerState().getPokemons().size()< 2) {
            return;
        }

        game.setScreen(DepositPokemonScreen.class);
    }

    public void withdrawPokemon() {
        if(game.getPlayerState().getPokemons().size() > 5) {
            return;
        }

        game.setScreen(WithdrawPokemonScreen.class);
    }

    public void quit() {
        game.setScreen(GameScreen.class);
    }
}

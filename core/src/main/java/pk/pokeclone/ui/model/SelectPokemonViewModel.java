package pk.pokeclone.ui.model;

import lombok.Getter;
import pk.pokeclone.PokeClone;
import pk.pokeclone.pokemon.Pokemon;
import pk.pokeclone.pokemon.attack.StatusEffect;
import pk.pokeclone.screen.GameScreen;

public class SelectPokemonViewModel extends ViewModel {

    public SelectPokemonViewModel(PokeClone game) {
        super(game);
    }

    public void startGame(StartPokemon pokemon) {
        game.getPlayerState().addPokemon(Pokemon.load(pokemon.getIndex(), 5));
        game.setScreen(GameScreen.class);
    }

    public enum StartPokemon {
        CHARMANDER(4),
        BULBASAUR(1),
        SQUIRTLE(7);

        @Getter
        private final int index;

        StartPokemon(int index) {
            this.index = index;
        }
    }
}

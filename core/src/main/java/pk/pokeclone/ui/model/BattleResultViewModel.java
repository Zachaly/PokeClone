package pk.pokeclone.ui.model;

import lombok.Getter;
import pk.pokeclone.PokeClone;
import pk.pokeclone.pokemon.Pokemon;
import pk.pokeclone.screen.GameScreen;

public class BattleResultViewModel extends ViewModel{

    @Getter
    private final int expGain;

    @Getter
    private final Pokemon winnerPokemon;

    public BattleResultViewModel(PokeClone game, Pokemon winnerPokemon, int expGain) {
        super(game);
        this.winnerPokemon = winnerPokemon;
        this.expGain = expGain;
    }

    public void win() {
        winnerPokemon.addExperience(expGain);
        game.getPlayerState().addMoney(200);
        game.getPlayerState().getPokemons().forEach(Pokemon::clearStages);

        game.setScreen(GameScreen.class);
    }

    public void loose() {
        game.getPlayerState().addMoney(-100);
        game.getPlayerState().getPokemons().forEach(Pokemon::heal);
        game.getPlayerState().getPokemons().forEach(Pokemon::clearStages);

        game.returnToPokecenter();
    }
}

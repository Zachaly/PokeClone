package pk.pokeclone.ui.model;

import pk.pokeclone.PokeClone;
import pk.pokeclone.item.Antidote;
import pk.pokeclone.item.Potion;
import pk.pokeclone.player.PlayerState;
import pk.pokeclone.pokemon.Pokemon;
import pk.pokeclone.screen.MenuScreen;
import pk.pokeclone.screen.PokemonScreen;

import java.util.List;

public class PokemonListViewModel extends ViewModel {

    private final PlayerState playerState;

    public PokemonListViewModel(PokeClone game, PlayerState playerState) {
        super(game);
        this.playerState = playerState;
    }

    public List<Pokemon> getPokemons() {
        return playerState.getPokemons();
    }

    public void selectPokemon(Pokemon pokemon) {
        game.addScreen(new PokemonScreen(game, pokemon));

        game.setScreen(PokemonScreen.class);
    }

    public void back() {
        game.setScreen(MenuScreen.class);
    }

    public void swapPokemon(int currentIndex, int targetIndex) {
        if(targetIndex > playerState.getPokemons().size() - 1 || targetIndex < 0) {
            return;
        }

        Pokemon current = playerState.getPokemons().get(currentIndex);
        Pokemon target = playerState.getPokemons().get(targetIndex);

        if(target == null) {
            return;
        }

        playerState.getPokemons().set(currentIndex, target);
        playerState.getPokemons().set(targetIndex, current);
    }

    public void healPokemon(Pokemon pokemon) {
        if(pokemon.getHealth() < 1) {
            return;
        }

        Potion potion = playerState.withdrawItem(Potion.class);

        if(potion == null) {
            return;
        }

        if(!potion.use(pokemon)) {
            playerState.getItems().add(potion);
        }
    }

    public void unpoisonPokemon(Pokemon pokemon) {
        if(pokemon.getHealth() < 1) {
            return;
        }

        Antidote antidote = playerState.withdrawItem(Antidote.class);

        if(antidote == null) {
            return;
        }


        if(!antidote.use(pokemon)) {
            playerState.getItems().add(antidote);
        }
    }
}

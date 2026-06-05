package pk.pokeclone.ui.model;

import pk.pokeclone.PokeClone;
import pk.pokeclone.screen.GameScreen;
import pk.pokeclone.screen.ItemListScreen;
import pk.pokeclone.screen.PokemonListScreen;

public class MenuViewModel extends ViewModel{
    public MenuViewModel(PokeClone game) {
        super(game);
    }

    public void quitMenu() {
        game.setScreen(GameScreen.class);
    }
    public void pokemonList() {
        game.setScreen(PokemonListScreen.class);
    }
    public void itemList() {
        game.setScreen(ItemListScreen.class);
    }
}

package pk.pokeclone.ui.model;

import com.badlogic.gdx.Gdx;
import pk.pokeclone.PokeClone;
import pk.pokeclone.screen.SelectPokemonScreen;

public class MainMenuViewModel extends ViewModel {

    public MainMenuViewModel(PokeClone game) {
        super(game);
    }

    public void startGame() {
        game.setScreen(SelectPokemonScreen.class);
    }

    public void quit() {
        Gdx.app.exit();
    }
}

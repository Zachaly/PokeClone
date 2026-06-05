package pk.pokeclone.ui.model;

import pk.pokeclone.PokeClone;
import pk.pokeclone.item.Item;
import pk.pokeclone.player.PlayerState;
import pk.pokeclone.screen.MenuScreen;

import java.util.Map;

public class ItemListViewModel extends ViewModel {
    private final PlayerState playerState;

    public ItemListViewModel(PokeClone game) {
        super(game);
        playerState = game.getPlayerState();
    }

    public Map<Class<? extends Item>, Integer> getItemsCount() {
        return playerState.getItemsCount();
    }

    public void quit() {
        game.setScreen(MenuScreen.class);
    }
}

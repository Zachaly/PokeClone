package pk.pokeclone.ui.model;

import pk.pokeclone.PokeClone;
import pk.pokeclone.item.Item;
import pk.pokeclone.screen.GameScreen;

import java.util.Map;

public class ShopViewModel extends ViewModel{
    public ShopViewModel(PokeClone game) {
        super(game);
    }

    public int getPlayerMoney() {
        return game.getPlayerState().getMoney();
    }

    public void buyItem(Item item) {
        if(getPlayerMoney() < item.getPrice()) {
            return;
        }

        game.getPlayerState().addMoney(-item.getPrice());

        game.getPlayerState().getItems().add(item);
    }

    public Map<Class<? extends Item>, Integer> getItemsCount() {
        return game.getPlayerState().getItemsCount();
    }

    public void quit() {
        game.setScreen(GameScreen.class);
    }
}

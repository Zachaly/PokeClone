package pk.pokeclone.ui.view;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import pk.pokeclone.item.Antidote;
import pk.pokeclone.item.Pokeball;
import pk.pokeclone.item.Potion;
import pk.pokeclone.ui.model.ShopViewModel;

public class ShopView extends View<ShopViewModel> {
    public ShopView(Stage stage, Skin skin, ShopViewModel viewModel) {
        super(stage, skin, viewModel);
    }

    @Override
    protected void setupUi() {
        setBackground(skin.getDrawable("BackgroundBag"));
        setFillParent(true);
        Label label = new Label("You have: " + viewModel.getPlayerMoney() + "\nCLICK IMAGE TO BUY", skin);

        add(label);
        row();

        Image image = new Image(skin, "pokeball");
        add(image);

        onClick(image, () -> {
            viewModel.buyItem(new Pokeball());
            clear();
            setupUi();
        });

        label = new Label("Pokeball", skin);
        add(label);

        label = new Label("YOU HAVE: " + String.valueOf(nullOrZero(viewModel.getItemsCount().get(Pokeball.class))), skin);
        add(label);

        row();

        image = new Image(skin, "potion");
        add(image);

        onClick(image, () -> {
            viewModel.buyItem(new Potion());
            clear();
            setupUi();
        });

        label = new Label("Potion", skin);
        add(label);

        label = new Label("YOU HAVE: " + String.valueOf(nullOrZero(viewModel.getItemsCount().get(Potion.class))), skin);
        add(label);

        row();

        image = new Image(skin, "antidote");
        add(image);

        onClick(image, () -> {
            viewModel.buyItem(new Antidote());
            clear();
            setupUi();
        });

        label = new Label("Antidote", skin);
        add(label);

        label = new Label("YOU HAVE: " + String.valueOf(nullOrZero(viewModel.getItemsCount().get(Antidote.class))), skin);
        add(label);

        row();

        TextButton textButton = new TextButton("QUIT", skin);
        add(textButton);
        onClick(textButton, viewModel::quit);
    }

    private int nullOrZero(Integer val) {
        return val == null ? 0 : val;
    }
}

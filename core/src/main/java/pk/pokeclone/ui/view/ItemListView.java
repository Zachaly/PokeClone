package pk.pokeclone.ui.view;


import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import pk.pokeclone.item.Antidote;
import pk.pokeclone.item.Pokeball;
import pk.pokeclone.item.Potion;
import pk.pokeclone.ui.model.ItemListViewModel;

public class ItemListView extends View<ItemListViewModel> {
    public ItemListView(Stage stage, Skin skin, ItemListViewModel viewModel) {
        super(stage, skin, viewModel);
    }

    @Override
    protected void setupUi() {
        setBackground(skin.getDrawable("BackgroundBag"));
        setFillParent(true);

        Image image = new Image(skin, "pokeball");
        add(image);

        Label label = new Label("Pokeball", skin);
        add(label);

        label = new Label(String.valueOf(nullOrZero(viewModel.getItemsCount().get(Potion.class))), skin);
        add(label);

        row();

        image = new Image(skin, "potion");
        add(image);

        label = new Label("Potion", skin);
        add(label);

        label = new Label(String.valueOf(nullOrZero(viewModel.getItemsCount().get(Potion.class))), skin);
        add(label);

        row();

        image = new Image(skin, "antidote");
        add(image);

        label = new Label("Antidote", skin);
        add(label);

        label = new Label(String.valueOf(nullOrZero(viewModel.getItemsCount().get(Antidote.class))), skin);
        add(label);

        row();

        TextButton textButton = new TextButton("QUIT MENU", skin);
        add(textButton);
        onClick(textButton, viewModel::quit);
    }

    private int nullOrZero(Integer val) {
        return val == null ? 0 : val;
    }
}

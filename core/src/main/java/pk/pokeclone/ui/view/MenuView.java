package pk.pokeclone.ui.view;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import pk.pokeclone.ui.model.MenuViewModel;

public class MenuView extends View<MenuViewModel> {
    public MenuView(Stage stage, Skin skin, MenuViewModel viewModel) {
        super(stage, skin, viewModel);
    }

    @Override
    protected void setupUi() {
        setBackground(skin.getDrawable("BackgroundScroll"));
        setFillParent(true);

        TextButton textButton = new TextButton("POKEMON", skin);
        add(textButton).fillX();
        onClick(textButton, viewModel::pokemonList);

        row();
        textButton = new TextButton("ITEMS", skin);
        add(textButton).fillX();
        onClick(textButton, viewModel::itemList);

        row();
        textButton = new TextButton("QUIT MENU", skin);
        add(textButton);

        onClick(textButton, viewModel::quitMenu);
    }
}

package pk.pokeclone.ui.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import pk.pokeclone.ui.model.SelectPokemonViewModel;

public class SelectPokemonView extends View<SelectPokemonViewModel> {
    public SelectPokemonView(Stage stage, Skin skin, SelectPokemonViewModel viewModel) {
        super(stage, skin, viewModel);
    }

    @Override
    protected void setupUi() {
        setBackground(skin.getDrawable("DialogBox"));

        setFillParent(true);

        Label label = new Label("SELECT YOUR STARTER", skin);
        label.setColor(1, 1, 1, 1);
        label.setAlignment(Align.center);
        add(label).fillX();

        row();
        HorizontalGroup horizontalGroup = new HorizontalGroup();

        Image image = new Image(skin, "charmander");
        image.setName("charmander");
        horizontalGroup.addActor(image);

        onClick(image, () -> viewModel.startGame(SelectPokemonViewModel.StartPokemon.CHARMANDER));

        image = new Image(skin, "squirtle");
        image.setName("squirtle");
        horizontalGroup.addActor(image);

        onClick(image, () -> viewModel.startGame(SelectPokemonViewModel.StartPokemon.SQUIRTLE));

        image = new Image(skin, "bulbasaur");
        image.setName("bulbasaur");
        horizontalGroup.addActor(image);
        add(horizontalGroup);

        onClick(image, () -> viewModel.startGame(SelectPokemonViewModel.StartPokemon.BULBASAUR));
    }
}

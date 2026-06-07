package pk.pokeclone.ui.view;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import pk.pokeclone.ui.model.PokecenterViewModel;
import pk.pokeclone.ui.model.ViewModel;

public class PokecenterView extends View<PokecenterViewModel> {
    public PokecenterView(Stage stage, Skin skin, PokecenterViewModel viewModel) {
        super(stage, skin, viewModel);
    }

    @Override
    protected void setupUi() {
        setBackground(skin.getDrawable("BackgroundScroll"));
        setFillParent(true);

        TextButton textButton = new TextButton("HEAL POKEMON", skin);
        add(textButton).fillX();
        onClick(textButton, viewModel::healPokemon);

        row();
        textButton = new TextButton("DEPOSIT POKEMON", skin);
        add(textButton).fillX();
        onClick(textButton, viewModel::depositPokemon);

        row();
        textButton = new TextButton("WITHDRAW POKEMON", skin);
        add(textButton).fillX();
        onClick(textButton, viewModel::withdrawPokemon);

        row();
        textButton = new TextButton("QUIT MENU", skin);
        add(textButton);

        onClick(textButton, viewModel::quit);
    }
}

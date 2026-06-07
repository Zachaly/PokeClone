package pk.pokeclone.ui.view;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import pk.pokeclone.pokemon.Pokemon;
import pk.pokeclone.ui.model.WithdrawPokemonViewModel;

public class WithdrawPokemonView extends View<WithdrawPokemonViewModel> {
    public WithdrawPokemonView(Stage stage, Skin skin, WithdrawPokemonViewModel viewModel) {
        super(stage, skin, viewModel);
    }

    @Override
    protected void setupUi() {
        setBackground(skin.getDrawable("BackgroundScroll"));
        setFillParent(true);

        for(Pokemon pokemon : viewModel.getBoxedPokemons()) {
            Image image = new Image(skin, pokemon.getPokedexIndex() + "_small");
            add(image);

            VerticalGroup verticalGroup = new VerticalGroup();

            TextButton textButton = new TextButton(pokemon.getDescription().getName(), skin);
            onClick(textButton, () -> {
                viewModel.withdrawPokemon(pokemon);
                clear();
                setupUi();
            });
            verticalGroup.addActor(textButton);
            ProgressBar healthBar = new ProgressBar(0, pokemon.getMaxHealth(), 1, false, skin);
            healthBar.setValue(pokemon.getHealth());
            verticalGroup.addActor(healthBar);

            if(pokemon.getStatusEffect() != null) {
                Label label = new Label(pokemon.getStatusEffect().getName(), skin);
                verticalGroup.addActor(label);
            }

            add(verticalGroup);

            row();
        }

        TextButton textButton = new TextButton("BACK", skin);
        add(textButton).fillX();;

        onClick(textButton, viewModel::quit);
    }
}

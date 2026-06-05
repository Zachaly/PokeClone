package pk.pokeclone.ui.view;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import pk.pokeclone.pokemon.Pokemon;
import pk.pokeclone.ui.model.PokemonListViewModel;

public class PokemonListView extends View<PokemonListViewModel> {
    public PokemonListView(Stage stage, Skin skin, PokemonListViewModel viewModel) {
        super(stage, skin, viewModel);
    }

    @Override
    protected void setupUi() {
        setBackground(skin.getDrawable("BackgroundScroll"));
        setFillParent(true);

        int index = 0;
        for(Pokemon pokemon : viewModel.getPokemons()) {
            Image image = new Image(skin, pokemon.getPokedexIndex() + "_small");
            add(image);

            VerticalGroup verticalGroup = new VerticalGroup();

            TextButton textButton = new TextButton(pokemon.getDescription().getName(), skin);
            onClick(textButton, () -> viewModel.selectPokemon(pokemon));
            verticalGroup.addActor(textButton);
            ProgressBar healthBar = new ProgressBar(0, pokemon.getMaxHealth(), 1, false, skin);
            healthBar.setValue(pokemon.getHealth());
            verticalGroup.addActor(healthBar);

            if(pokemon.getStatusEffect() != null) {
                Label label = new Label(pokemon.getStatusEffect().getName(), skin);
                verticalGroup.addActor(label);
            }

            add(verticalGroup);

            verticalGroup = new VerticalGroup();

            image = new Image(skin, "ArrowUp");

            int finalIndex = index;
            onClick(image, () -> rearrangePokemon(finalIndex, finalIndex - 1));

            verticalGroup.addActor(image);

            image = new Image(skin, "ArrowDown");
            onClick(image, () -> rearrangePokemon(finalIndex, finalIndex + 1));

            verticalGroup.addActor(image);

            add(verticalGroup);

            image = new Image(skin, "potion");
            add(image);

            onClick(image, () ->  {
                viewModel.healPokemon(pokemon);
                healthBar.setValue(pokemon.getHealth());
            });

            image = new Image(skin, "antidote");
            add(image);

            onClick(image, () -> {
                viewModel.unpoisonPokemon(pokemon);
                clear();
                setupUi();
            });

            row();
            index++;
        }

        TextButton textButton = new TextButton("BACK", skin);
        add(textButton).fillX();;

        onClick(textButton, viewModel::back);
    }

    private void rearrangePokemon(int index1, int index2) {
        viewModel.swapPokemon(index1, index2);
        clear();
        setupUi();
    }
}

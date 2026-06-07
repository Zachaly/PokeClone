package pk.pokeclone.ui.view;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import pk.pokeclone.pokemon.Pokemon;
import pk.pokeclone.pokemon.attack.AttackType;
import pk.pokeclone.pokemon.attack.PokemonAttack;
import pk.pokeclone.ui.model.PokemonViewModel;

import java.util.stream.Collectors;

public class PokemonView extends View<PokemonViewModel> {
    public PokemonView(Stage stage, Skin skin, PokemonViewModel viewModel) {
        super(stage, skin, viewModel);
    }

    @Override
    protected void setupUi() {
        setBackground(skin.getDrawable("DialogBox"));
        setFillParent(true);

        VerticalGroup verticalGroup = new VerticalGroup();

        Pokemon pokemon = viewModel.getPokemon();

        Image image = new Image(skin, String.valueOf(pokemon.getPokedexIndex()));
        verticalGroup.addActor(image);

        Label label = new Label("LEVEL " + pokemon.getLevel(), skin);
        verticalGroup.addActor(label);

        label = new Label("EXP " + pokemon.getExperience(), skin);
        verticalGroup.addActor(label);

        label = new Label("HP" + pokemon.getHealth() + "/" + pokemon.getMaxHealth(), skin);
        verticalGroup.addActor(label);

        label = new Label("ATTACK " + pokemon.getAttack(), skin);
        verticalGroup.addActor(label);

        label = new Label("DEFENSE " + pokemon.getDefense(), skin);
        verticalGroup.addActor(label);

        label = new Label("SP ATTACK " + pokemon.getSpAttack(), skin);
        verticalGroup.addActor(label);

        label = new Label("SP DEFENSE " + pokemon.getSpDefense(), skin);
        verticalGroup.addActor(label);

        label = new Label("SPEED " + pokemon.getSpeed(), skin);
        verticalGroup.addActor(label);

        String types = String.join(" ", pokemon.getDescription().getTypes().stream().map(Enum::toString).collect(Collectors.toSet()));

        label = new Label(types, skin);
        verticalGroup.addActor(label);

        TextButton textButton = new TextButton("EXIT", skin);
        onClick(textButton, viewModel::exit);
        verticalGroup.addActor(textButton);
        add(verticalGroup);

        verticalGroup = new VerticalGroup();

        for(PokemonAttack attack : pokemon.getAttacks()) {
            Table table = new Table();
            table.setBackground(skin.getDrawable("button_normal"));
            table.pad(0.0f);

            label = new Label(attack.getDefinition().getName(), skin);
            table.add(label).spaceRight(5.0f).spaceBottom(5.0f);

            label = new Label(attack.getDefinition().getAttackType() == AttackType.SPECIAL ? "SPECIAL" : "PHYSICAL", skin);
            table.add(label);

            table.row();
            label = new Label(attack.getUses() + "/" + attack.getDefinition().getMaxUses(), skin);
            table.add(label);

            label = new Label(attack.getDefinition().getType().toString(), skin);
            table.add(label);
            verticalGroup.addActor(table);
        }
        add(verticalGroup);

    }
}

package pk.pokeclone.ui.view;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import pk.pokeclone.pokemon.Pokemon;
import pk.pokeclone.pokemon.attack.PokemonAttack;
import pk.pokeclone.pokemon.attack.StatusEffect;
import pk.pokeclone.ui.model.BattleViewModel;

public class BattleView extends View<BattleViewModel>{
    public BattleView(Stage stage, Skin skin, BattleViewModel viewModel) {
        super(stage, skin, viewModel);
        viewModel.setRerender(this::battleScreenRender);
    }

    @Override
    protected void setupUi() {
        battleScreenRender();
    }

    private void battleScreenRender() {
        clear();
        setBackground(skin.getDrawable("BackgroundScroll"));
        setFillParent(true);

        if(viewModel.isForceSwap()) {
            swapPokemonScreenRender();
            return;
        }

        Table table = new Table();
        table.setBackground(skin.getDrawable("BackgroundScroll"));

        String nameLabel = String.format("%s lvl %d", viewModel.getCurrentPlayerPokemon().getDescription().getName(),
            viewModel.getCurrentPlayerPokemon().getLevel());
        Label label = new Label(nameLabel, skin);
        table.add(label);

        nameLabel = String.format("%s lvl %d", viewModel.getAttackerPokemon().getDescription().getName(),
            viewModel.getAttackerPokemon().getLevel());

        label = new Label(nameLabel, skin);
        table.add(label);
        table.row();

        ProgressBar progressBar = new ProgressBar(0.0f, viewModel.getCurrentPlayerPokemon().getMaxHealth(), 1.0f, false, skin);
        progressBar.setValue(viewModel.getCurrentPlayerPokemon().getHealth());
        table.add(progressBar);

        progressBar = new ProgressBar(0.0f, viewModel.getAttackerPokemon().getMaxHealth(), 1.0f, false, skin);
        progressBar.setValue(viewModel.getAttackerPokemon().getHealth());
        table.add(progressBar);

        table.row();
        StatusEffect playerStatusEffect = viewModel.getCurrentPlayerPokemon().getStatusEffect();
        StatusEffect attackerStatusEffect = viewModel.getAttackerPokemon().getStatusEffect();

        String playerStatusEffectName = "";
        String attackerStatusEffectName = "";

        if(playerStatusEffect != null) {
            playerStatusEffectName = playerStatusEffect.getName();
        }
        if(attackerStatusEffect != null) {
            attackerStatusEffectName = attackerStatusEffect.getName();
        }

        label = new Label(playerStatusEffectName, skin);
        table.add(label);

        label = new Label(attackerStatusEffectName, skin);
        table.add(label);

        table.row();
        Image image = new Image(skin, String.valueOf(viewModel.getCurrentPlayerPokemon().getPokedexIndex()));
        table.add(image);

        image = new Image(skin, String.valueOf(viewModel.getAttackerPokemon().getPokedexIndex()));
        table.add(image);
        table.row();

        VerticalGroup verticalGroup = new VerticalGroup();

        for(String com : viewModel.getBattleCommunicates()) {
            label = new Label(com, skin);

            verticalGroup.addActor(label);

        }
        table.add(verticalGroup).align(Align.center).fillX();

        add(table);
        row();

        table = new Table();
        table.setBackground(skin.getDrawable("DialogBox"));
        table.padTop(20.0f);
        table.padBottom(20.0f);

        Table table2 = new Table();

        int i = 1;
        for(PokemonAttack attackDefinition : viewModel.getCurrentAttacks()) {
            TextButton textButton = new TextButton(attackDefinition.getDefinition().getName() + " " + attackDefinition.getUses(), skin);
            onClick(textButton, () -> {
                if(attackDefinition.getUses() > 0) {
                    viewModel.setPlayerAttack(attackDefinition);
                    viewModel.battleRound(BattleViewModel.PlayerAction.ATTACK);
                }
            });
            table2.add(textButton);

            if(i % 2 == 0) {
                table2.row();
            }
            i++;
        }

        table.add(table2);

        HorizontalGroup horizontalGroup = new HorizontalGroup();

        verticalGroup = new VerticalGroup();

        image = new Image(skin, "potion");
        onClick(image, () -> viewModel.battleRound(BattleViewModel.PlayerAction.HEAL));
        horizontalGroup.addActor(image);

        image = new Image(skin, "pokeball");
        onClick(image, () -> viewModel.battleRound(BattleViewModel.PlayerAction.CATCH));
        horizontalGroup.addActor(image);

        image = new Image(skin, "antidote");
        onClick(image, () -> viewModel.battleRound(BattleViewModel.PlayerAction.UNPOISON));
        horizontalGroup.addActor(image);
        verticalGroup.addActor(horizontalGroup);

        TextButton textButton = new TextButton("SWAP POKEMON", skin);
        onClick(textButton, this::swapPokemonScreenRender);
        verticalGroup.addActor(textButton);

        textButton = new TextButton("RUN", skin);
        onClick(textButton, viewModel::run);
        verticalGroup.addActor(textButton);
        table.add(verticalGroup);
        add(table);
    }

    private void swapPokemonScreenRender() {
        clear();
        setBackground(skin.getDrawable("BackgroundScroll"));
        setFillParent(true);

        for(Pokemon pokemon : viewModel.getPlayerPokemons()) {
            Image image = new Image(skin, pokemon.getPokedexIndex() + "_small");
            add(image);

            VerticalGroup verticalGroup = new VerticalGroup();

            TextButton textButton = new TextButton(pokemon.getDescription().getName(), skin);
            onClick(textButton, () -> {
                if(pokemon != viewModel.getCurrentPlayerPokemon() && pokemon.getHealth() > 0) {
                    viewModel.setSwapPokemon(pokemon);
                    viewModel.battleRound(BattleViewModel.PlayerAction.SWAP);
                }
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

        onClick(textButton, this::battleScreenRender);
    }
}

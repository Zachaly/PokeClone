package pk.pokeclone.ui.view;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import pk.pokeclone.ui.model.BattleResultViewModel;

public class BattleResultView extends View<BattleResultViewModel>{
    public BattleResultView(Stage stage, Skin skin, BattleResultViewModel viewModel) {
        super(stage, skin, viewModel);
    }

    @Override
    protected void setupUi() {
        setBackground(skin.getDrawable("BackgroundScroll"));
        setFillParent(true);

        String message;

        if(viewModel.getWinnerPokemon() != null) {
            message = String.format("%s won and gained %d exp",
                viewModel.getWinnerPokemon().getDescription().getName(), viewModel.getExpGain());
        } else {
            message = "You lost $" + 100;
        }

        Label label = new Label(message, skin);

        if(viewModel.getWinnerPokemon() != null) {
            onClick(label, viewModel::win);
        } else {
            onClick(label, viewModel::loose);
        }

        add(label);
    }
}

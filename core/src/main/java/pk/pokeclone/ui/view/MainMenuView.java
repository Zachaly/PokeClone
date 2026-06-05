package pk.pokeclone.ui.view;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import pk.pokeclone.ui.model.MainMenuViewModel;

public class MainMenuView extends View<MainMenuViewModel> {
    public MainMenuView(Stage stage, Skin skin, MainMenuViewModel viewModel) {
        super(stage, skin, viewModel);
    }

    @Override
    protected void setupUi() {
        setFillParent(true);

        TextButton textButton = new TextButton("START", skin);
        textButton.setName("start_button");
        add(textButton).spaceBottom(15.0f);

        onClick(textButton, viewModel::startGame);

        row();
        textButton = new TextButton("EXIT", skin);
        textButton.setName("exit_button");
        add(textButton).fillX();

        onClick(textButton, viewModel::quit);
    }
}

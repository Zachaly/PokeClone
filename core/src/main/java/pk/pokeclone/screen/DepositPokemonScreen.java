package pk.pokeclone.screen;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import pk.pokeclone.PokeClone;
import pk.pokeclone.asset.SkinAsset;
import pk.pokeclone.ui.model.DepositPokemonViewModel;
import pk.pokeclone.ui.view.DepositPokemonView;

public class DepositPokemonScreen extends ScreenAdapter {
    private final PokeClone game;
    private final Stage stage;
    private final Skin skin;
    private final Viewport viewport;

    public DepositPokemonScreen(PokeClone game) {
        this.game = game;
        viewport = new FitViewport(800f, 450f);
        stage = new Stage(viewport, game.getBatch());
        skin = game.getAssetService().get(SkinAsset.INVENTORY);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void show() {
        game.setInputProcessor(stage);

        stage.addActor(new DepositPokemonView(stage, skin, new DepositPokemonViewModel(game)));
    }

    @Override
    public void hide() {
        stage.clear();
    }

    @Override
    public void render(float delta) {
        viewport.apply();
        stage.getBatch().setColor(Color.WHITE);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}

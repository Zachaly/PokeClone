package pk.pokeclone.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import lombok.AllArgsConstructor;
import pk.pokeclone.PokeClone;
import pk.pokeclone.asset.AssetService;
import pk.pokeclone.asset.AtlasAsset;

@AllArgsConstructor
public class LoadingScreen extends ScreenAdapter {
    private final PokeClone game;
    private final AssetService assetService;

    @Override
    public void show() {
        for(AtlasAsset atlas : AtlasAsset.values()) {
            assetService.queue(atlas);
        }
    }

    @Override
    public void render(float delta) {
        if(assetService.update()) {
            Gdx.app.debug("Loading", "Finished loading");
        }

        createScreens();
        this.game.removeScreen(this);
        this.dispose();
        this.game.setScreen(GameScreen.class);
    }

    private void createScreens() {
        this.game.addScreen(new GameScreen(game));
    }
}

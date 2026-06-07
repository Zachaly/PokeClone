package pk.pokeclone.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import lombok.AllArgsConstructor;
import lombok.With;
import pk.pokeclone.PokeClone;
import pk.pokeclone.asset.AssetService;
import pk.pokeclone.asset.AtlasAsset;
import pk.pokeclone.asset.SkinAsset;

import java.awt.*;

@AllArgsConstructor
public class LoadingScreen extends ScreenAdapter {
    private final PokeClone game;
    private final AssetService assetService;

    @Override
    public void show() {
        for(AtlasAsset atlas : AtlasAsset.values()) {
            assetService.queue(atlas);
        }

        for(SkinAsset skin : SkinAsset.values()) {
            assetService.queue(skin);
        }
    }

    @Override
    public void render(float delta) {
        if(assetService.update()) {
            Gdx.app.debug("Loading", "Finished loading");
            createScreens();
            this.game.removeScreen(this);
            this.dispose();
            this.game.setScreen(MainMenuScreen.class);
        }
    }

    private void createScreens()
    {
        this.game.addScreen(new MainMenuScreen(game));
        this.game.addScreen(new GameScreen(game));
        this.game.addScreen(new SelectPokemonScreen(game));
        this.game.addScreen(new MenuScreen(game));
        this.game.addScreen(new PokemonListScreen(game));
        this.game.addScreen(new ItemListScreen(game));
        this.game.addScreen(new PokecenterScreen(game));
        this.game.addScreen(new WithdrawPokemonScreen(game));
        this.game.addScreen(new DepositPokemonScreen(game));
        this.game.addScreen(new ShopScreen(game));
    }
}

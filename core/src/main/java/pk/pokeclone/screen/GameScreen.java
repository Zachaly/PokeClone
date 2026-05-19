package pk.pokeclone.screen;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.Disposable;
import pk.pokeclone.PokeClone;
import pk.pokeclone.asset.MapAsset;
import pk.pokeclone.input.GameControllerState;
import pk.pokeclone.input.KeyboardController;
import pk.pokeclone.system.*;
import pk.pokeclone.tiled.TiledAshleyConfigurator;
import pk.pokeclone.tiled.TiledService;

import java.util.function.Consumer;

public class GameScreen extends ScreenAdapter {
    private final Engine engine;
    private final TiledService tiledService;
    private final TiledAshleyConfigurator tiledAshleyConfigurator;
    private final KeyboardController keyboardController;
    private final PokeClone game;

    public GameScreen(PokeClone game) {
        engine = new Engine();
        tiledService = new TiledService(game.getAssetService());
        keyboardController = new KeyboardController(GameControllerState.class, engine);
        this.game = game;

        engine.addSystem(new ControllerSystem());
        engine.addSystem(new MoveSystem());
        engine.addSystem(new FsmSystem());
        engine.addSystem(new FacingSystem());
        engine.addSystem(new AnimationSystem(game.getAssetService()));
        engine.addSystem(new RenderSystem(game.getBatch(), game.getViewport(), game.getCamera()));

        tiledAshleyConfigurator = new TiledAshleyConfigurator(engine, game.getAssetService());
    }

    @Override
    public void show() {
        game.setInputProcessor(keyboardController);
        keyboardController.setActiveState(GameControllerState.class);

        Consumer<TiledMap> renderConsumer = engine.getSystem(RenderSystem.class)::setMap;

        tiledService.setMapChangeConsumer(renderConsumer);
        tiledService.setLoadObjectConsumer(tiledAshleyConfigurator::onLoadObject);

        TiledMap map = tiledService.loadMap(MapAsset.START);
        tiledService.setMap(map);
    }

    @Override
    public void render(float delta) {
        delta = Math.min(delta, 1 / 30f);
        this.engine.update(delta);


    }

    @Override
    public void hide() {
        this.engine.removeAllEntities();
    }

    @Override
    public void dispose() {
        for(EntitySystem system : engine.getSystems()) {
            if(system instanceof Disposable disposableSystem) {
                disposableSystem.dispose();
            }
        }
    }
}

package pk.pokeclone.screen;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
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
    private final World physicWorld;

    public GameScreen(PokeClone game) {
        engine = new Engine();
        physicWorld = new World(Vector2.Zero, true);
        physicWorld.setAutoClearForces(false);
        tiledService = new TiledService(game.getAssetService(), physicWorld);
        keyboardController = new KeyboardController(GameControllerState.class, engine);
        this.game = game;

        engine.addSystem(new ControllerSystem());
        engine.addSystem(new MoveSystem());
        engine.addSystem(new FsmSystem());
        engine.addSystem(new FacingSystem());
        engine.addSystem(new PhysicSystem(physicWorld, 1/60f));
        engine.addSystem(new AnimationSystem(game.getAssetService()));
        engine.addSystem(new CameraSystem(game.getCamera()));
        engine.addSystem(new RenderSystem(game.getBatch(), game.getViewport(), game.getCamera()));
        engine.addSystem(new PhysicDebugRenderSystem(physicWorld, game.getCamera()));

        tiledAshleyConfigurator = new TiledAshleyConfigurator(engine, game.getAssetService(), physicWorld);
    }

    @Override
    public void show() {
        game.setInputProcessor(keyboardController);
        keyboardController.setActiveState(GameControllerState.class);

        Consumer<TiledMap> renderConsumer = engine.getSystem(RenderSystem.class)::setMap;
        Consumer<TiledMap> cameraConsumer = engine.getSystem(CameraSystem.class)::setMap;

        tiledService.setMapChangeConsumer(renderConsumer.andThen(cameraConsumer));
        tiledService.setLoadObjectConsumer(tiledAshleyConfigurator::onLoadObject);
        tiledService.setLoadTileConsumer(tiledAshleyConfigurator::onLoadTile);

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

        physicWorld.dispose();
    }
}

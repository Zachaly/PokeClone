package pk.pokeclone;

import com.badlogic.gdx.*;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.profiling.GLProfiler;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import lombok.Getter;
import pk.pokeclone.asset.AssetService;
import pk.pokeclone.player.PlayerState;
import pk.pokeclone.screen.LoadingScreen;

import java.util.HashMap;
import java.util.Map;

public class PokeClone extends Game {
    public static final float WORLD_WIDTH = 16f;
    public static final float WORLD_HEIGHT = 9f;
    public static final float SCALE = 1f / 16;

    private final Map<Class<? extends Screen>, Screen> screenCache = new HashMap<>();
    @Getter
    private final PlayerState playerState = new PlayerState();

    @Getter
    private Batch batch;
    @Getter
    private BitmapFont font;
    @Getter
    private OrthographicCamera camera;
    @Getter
    private Viewport viewport;
    @Getter
    private AssetService assetService;
    @Getter
    private GLProfiler glProfiler;
    private InputMultiplexer inputMultiplexer;

    @Override
    public void create() {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        inputMultiplexer = new InputMultiplexer();
        Gdx.input.setInputProcessor(inputMultiplexer);

        batch = new SpriteBatch();
        font = new BitmapFont();
        camera = new OrthographicCamera();
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        assetService = new AssetService(new InternalFileHandleResolver());
        glProfiler = new GLProfiler(Gdx.graphics);
        glProfiler.enable();

        addScreen(new LoadingScreen(this, assetService));
        setScreen(LoadingScreen.class);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        super.resize(width, height);
    }

    public void addScreen(Screen screen) {
        screenCache.put(screen.getClass(), screen);
    }

    public void setScreen(Class<? extends Screen> screenClass) {
        Screen screen = screenCache.get(screenClass);

        if(screen == null) {
            throw new GdxRuntimeException("Screen not found");
        }
        setScreen(screen);
    }

    public void removeScreen(Screen screen) {
        screenCache.remove(screen.getClass());
    }
    public void setInputProcessor(InputProcessor... processors) {
        inputMultiplexer.clear();
        if(processors == null) return;

        for(InputProcessor processor : processors) {
            inputMultiplexer.addProcessor(processor);
        }
    }

    @Override
    public void render() {
        glProfiler.reset();

        Gdx.gl.glClearColor(0, 0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        super.render();

        Gdx.graphics.setTitle("Pokeclone");
    }

    @Override
    public void dispose() {
        screenCache.values().forEach(Screen::dispose);
        screenCache.clear();
        batch.dispose();
        font.dispose();
        this.assetService.dispose();
        super.dispose();
    }

}

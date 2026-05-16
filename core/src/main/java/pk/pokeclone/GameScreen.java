package pk.pokeclone;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import pk.pokeclone.enums.Direction;
import pk.pokeclone.objects.Player;

public class GameScreen extends ScreenAdapter {
    private final Main game;
    private final Texture mapTexture = new Texture(Gdx.files.internal("map.png"));
    private final Texture playerTexture = new Texture(Gdx.files.internal("player/player_front.png"));
    private final Viewport viewport = new FitViewport(30, 30);
    private final Player player = new Player(10, 10, playerTexture);

    public GameScreen(Main game) {
        this.game = game;
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);

        if(Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            player.move(Direction.UP);
        }
        else if(Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            player.move(Direction.DOWN);
        }
        else if(Gdx.input.isKeyJustPressed(Input.Keys.A)) {
            player.move(Direction.LEFT);
        }
        else if(Gdx.input.isKeyJustPressed(Input.Keys.D)) {
            player.move(Direction.RIGHT);
        }

        viewport.apply();

        game.getBatch().setProjectionMatrix(viewport.getCamera().combined);
        game.getBatch().begin();

        game.getBatch().draw(mapTexture,0,0, viewport.getWorldWidth(), viewport.getWorldHeight());

        player.draw(game.getBatch());

        game.getBatch().end();
    }
}

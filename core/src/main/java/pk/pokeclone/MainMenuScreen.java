package pk.pokeclone;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MainMenuScreen extends ScreenAdapter {
    private final Main game;
    private final Viewport viewport = new ScreenViewport();
    private final GlyphLayout layout = new GlyphLayout();

    public MainMenuScreen(Main game) {
        this.game = game;
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void render(float delta) {
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            game.setScreen(new GameScreen(game));
            dispose();
            return;
        }

        ScreenUtils.clear(Color.BLACK);

        viewport.apply();
        game.getBatch().setProjectionMatrix(viewport.getCamera().combined);
        game.getBatch().begin();

        float x = (viewport.getWorldWidth() - layout.width) / 2;
        float y = viewport.getWorldHeight() / 2 + 100;

        layout.setText(game.getFont(), "WASD - movement");
        game.getFont().draw(game.getBatch(), layout, x, y);
        y -= 40;

        layout.setText(game.getFont(), "ESC - menu");
        game.getFont().draw(game.getBatch(), layout, x, y);
        y -= 40;

        layout.setText(game.getFont(), "SPACE - interact");
        game.getFont().draw(game.getBatch(), layout, x, y);
        y -= 40;

        layout.setText(game.getFont(), "SHIFT - cancel");
        game.getFont().draw(game.getBatch(), layout, x, y);
        y -= 60;

        layout.setText(game.getFont(), "PRESS SPACE TO START");
        game.getFont().draw(game.getBatch(), layout, x, y);


        game.getBatch().end();
    }
}

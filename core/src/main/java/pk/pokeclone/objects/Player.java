package pk.pokeclone.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.Viewport;
import pk.pokeclone.enums.Direction;

public class Player extends MapObject {
    private static final float SCALE = 1 / 30f;

    private final Texture downTexture = new Texture(Gdx.files.internal("player/player_front.png"));
    private final Texture upTexture = new Texture(Gdx.files.internal("player/player_back.png"));
    private final Texture rightTexture = new Texture(Gdx.files.internal("player/player_right.png"));
    private final Texture leftTexture = new Texture(Gdx.files.internal("player/player_left.png"));


    public Player(float x, float y, Texture texture) {
        super(x, y, texture.getWidth() * SCALE, texture.getWidth() * SCALE);
        texture = downTexture;
    }

    public void move(Direction direction) {
        float deltaX = 0;
        float deltaY = 0;

        switch (direction) {
            case UP -> {
                deltaY = 1;
                texture = upTexture;
            }
            case DOWN -> {
                deltaY = -1;
                texture = downTexture;
            }
            case RIGHT -> {
                deltaX = 1;
                texture = rightTexture;
            }
            case LEFT -> {
                deltaX = -1;
                texture = leftTexture;
            }
        }

        rect.setPosition(rect.getX() + deltaX, rect.getY() + deltaY);
    }
}

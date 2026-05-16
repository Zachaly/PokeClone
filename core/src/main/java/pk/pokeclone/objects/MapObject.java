package pk.pokeclone.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;

public abstract class MapObject {
    protected final Rectangle rect;
    protected Texture texture;

    protected MapObject(float x, float y, float w, float h, Texture texture) {
        this.rect = new Rectangle(x, y, w, h);
        this.texture = texture;
    }

    protected MapObject(float x, float y, float w, float h) {
        this(x, y, w, h, null);
    }

    public void draw(Batch batch) {
        if(texture == null)
            return;

        batch.draw(texture, rect.x, rect.y, rect.width, rect.height);
    }
}

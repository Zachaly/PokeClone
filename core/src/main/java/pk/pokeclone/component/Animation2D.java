package pk.pokeclone.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import lombok.Getter;
import lombok.Setter;
import pk.pokeclone.asset.AtlasAsset;

public class Animation2D implements Component {
    public static ComponentMapper<Animation2D> MAPPER = ComponentMapper.getFor(Animation2D.class);

    @Getter
    private final AtlasAsset atlasAsset;
    @Getter
    private final String atlasKey;
    @Getter
    private AnimationType type;
    @Getter
    private Facing.FacingDirection direction;
    @Getter
    private Animation.PlayMode playMode;
    @Setter
    private float speed;
    private float stateTime;
    @Getter
    private Animation<TextureRegion> animation;
    @Getter
    private boolean dirty;

    public Animation2D(AtlasAsset atlasAsset, String atlasKey, AnimationType type,
                       Animation.PlayMode playMode, float speed) {
        this.atlasAsset = atlasAsset;
        this.atlasKey = atlasKey;
        this.type = type;
        this.playMode = playMode;
        this.speed = speed;
        direction = null;
        stateTime = 0;
        animation = null;
    }

    public void setAnimation(Animation<TextureRegion> animation, Facing.FacingDirection direction) {
        this.animation = animation;
        this.direction = direction;
        stateTime = 0;
        dirty = false;
    }

    public void setType(AnimationType type) {
        this.type = type;
        dirty = true;
    }

    public boolean isAnimationFinished() {
        return animation.isAnimationFinished(stateTime);
    }

    public float increaseAndGetStateTime(float delta) {
        stateTime += delta * speed;
        return stateTime;
    }

    public enum AnimationType {
        WALK, IDLE;

        @Getter
        private final String atlasKey;

        AnimationType() {
            atlasKey = name().toLowerCase();
        }
    }
}

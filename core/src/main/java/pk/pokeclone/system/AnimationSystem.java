package pk.pokeclone.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import pk.pokeclone.asset.AssetService;
import pk.pokeclone.asset.AtlasAsset;
import pk.pokeclone.component.Animation2D;
import pk.pokeclone.component.Facing;
import pk.pokeclone.component.Graphic;

import java.util.HashMap;
import java.util.Map;

public class AnimationSystem extends IteratingSystem {
    private static final float FRAME_DURATION = 1 / 4f;
    private final AssetService assetService;
    private final Map<CacheKey, Animation<TextureRegion>> animationCache;

    public AnimationSystem(AssetService assetService) {
        super(Family.all(Animation2D.class, Graphic.class, Facing.class).get());
        this.assetService = assetService;
        animationCache = new HashMap<>();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Animation2D animation2D = Animation2D.MAPPER.get(entity);
        Facing.FacingDirection direction = Facing.MAPPER.get(entity).getDirection();
        final float stateTime;

        if(animation2D.isDirty() || direction != animation2D.getDirection()) {
            updateAnimation(animation2D, direction);
            stateTime = 0;
        } else {
            stateTime = animation2D.increaseAndGetStateTime(deltaTime);
        }

        Animation<TextureRegion> animation = animation2D.getAnimation();
        animation.setPlayMode(animation2D.getPlayMode());

        TextureRegion currentFrame = animation.getKeyFrame(stateTime);

        Graphic.MAPPER.get(entity).setRegion(currentFrame);
    }

    private void updateAnimation(Animation2D animation2D, Facing.FacingDirection direction) {
        AtlasAsset asset = animation2D.getAtlasAsset();
        String atlasKey = animation2D.getAtlasKey();
        Animation2D.AnimationType type = animation2D.getType();

        CacheKey cacheKey = new CacheKey(asset, atlasKey, type, direction);
        Animation<TextureRegion> animation = animationCache.computeIfAbsent(cacheKey, key -> {
            TextureAtlas atlas = this.assetService.get(asset);
            String combinedKey = atlasKey + "/" + type.getAtlasKey().toLowerCase() + "_" + direction.getAtlasKey().toLowerCase();
            Array<TextureAtlas.AtlasRegion> regions = atlas.findRegions(combinedKey);

            if(regions.isEmpty()) {
                throw new GdxRuntimeException("Regions not found");
            }

            return new Animation<>(FRAME_DURATION, regions);
        });

        animation2D.setAnimation(animation, direction);
    }

    public record CacheKey(AtlasAsset atlasAsset,
                           String atlasKey,
                           Animation2D.AnimationType type,
                           Facing.FacingDirection direction) {}
}

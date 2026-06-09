package pk.pokeclone.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.AnimatedTiledMapTile;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.Viewport;
import pk.pokeclone.PokeClone;
import pk.pokeclone.component.Graphic;
import pk.pokeclone.component.Transform;
import pk.pokeclone.tiled.TiledPropertyNames;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class RenderSystem extends SortedIteratingSystem implements Disposable {
    private final OrthogonalTiledMapRenderer mapRenderer;
    private final Batch batch;
    private final Viewport viewport;
    private final OrthographicCamera camera;
    private final List<MapLayer> fgLayers;
    private final List<MapLayer> bgLayers;

    public RenderSystem(Batch batch, Viewport viewport, OrthographicCamera camera) {
        super(
            Family.all(Transform.class, Graphic.class).get(),
            Comparator.comparing(Transform.MAPPER::get)
        );
        mapRenderer = new OrthogonalTiledMapRenderer(null, PokeClone.SCALE, batch);
        this.batch = batch;
        this.viewport = viewport;
        this.camera = camera;
        fgLayers = new ArrayList<>();
        bgLayers = new ArrayList<>();
    }

    @Override
    protected void processEntity(Entity entity, float v) {
        Transform transform = Transform.MAPPER.get(entity);
        Graphic graphic = Graphic.MAPPER.get(entity);

        if(graphic.getRegion() == null) {
            return;
        }

        Vector2 position = transform.getPosition();
        Vector2 scaling = transform.getScaling();
        Vector2 size = transform.getSize();

        batch.setColor(graphic.getColor());
        batch.draw(graphic.getRegion(),
            position.x - (1f - scaling.x) * size.x * 0.5f,
            position.y - (1f - scaling.y) * size.y * 0.5f,
            size.x * 0.5f, size.y * 0.5f,
            size.x, size.y,
            scaling.x, scaling.y,
            transform.getRotationDeg()
            );
        batch.setColor(Color.WHITE);
    }

    @Override
    public void update(float delta) {
        AnimatedTiledMapTile.updateAnimationBaseTime();

        viewport.apply();

        batch.begin();
        batch.setColor(Color.WHITE);
        mapRenderer.setView(camera);
        bgLayers.forEach(mapRenderer::renderMapLayer);

        forceSort();
        super.update(delta);
        batch.setColor(Color.WHITE);

        fgLayers.forEach(mapRenderer::renderMapLayer);
        batch.end();
    }

    public void setMap(TiledMap map) {
        mapRenderer.setMap(map);

        fgLayers.clear();
        bgLayers.clear();
        List<MapLayer> currentLayers = bgLayers;
        for(MapLayer layer : map.getLayers()) {
            if(TiledPropertyNames.OBJECTS.equals(layer.getName())) {
                currentLayers = fgLayers;
                continue;
            }

            if(layer.getClass().equals(MapLayer.class)) {
                continue;
            }

            currentLayers.add(layer);
        }
    }

    @Override
    public void dispose() {
        mapRenderer.dispose();
    }
}

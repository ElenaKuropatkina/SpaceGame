package ru.elenakuropatkina.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.elenakuropatkina.base.BaseScreen;
import ru.elenakuropatkina.math.Rect;
import ru.elenakuropatkina.pool.AsteroidPool;
import ru.elenakuropatkina.pool.BulletPool;
import ru.elenakuropatkina.sprite.Background;
import ru.elenakuropatkina.sprite.Ship;
import ru.elenakuropatkina.sprite.Star;


public class GameScreen extends BaseScreen {

    private static final int STAR_COUNT = 128;

    private TextureAtlas atlas, atlasMenu;

    private Texture bg;
    private Background background;
    private Star[] stars;

    private Ship ship;
    private BulletPool bulletPool;
    private AsteroidPool asteroidPool;
    private Sound soundBullet;

    @Override
    public void show() {
        super.show();
        bg = new Texture("bg.jpg");
        background = new Background(bg);
        atlas = new TextureAtlas(Gdx.files.internal("atlasGame.pack"));
        atlasMenu = new TextureAtlas(Gdx.files.internal("atlasMenu.pack"));
        stars = new Star[STAR_COUNT];
        for (int i = 0; i < STAR_COUNT; i++) {
            stars[i] = new Star(atlasMenu);
        }
        bulletPool = new BulletPool();
        asteroidPool = new AsteroidPool();
        soundBullet = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));
        ship = new Ship(atlas, bulletPool, soundBullet);
        music.setLooping(true);
        music.setVolume(0.5f);
        music.play();
    }

    @Override
    public void render(float delta) {
        update(delta);
        freeAllDestroyed();
        draw();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        for (Star star : stars) {
            star.resize(worldBounds);
        }
        ship.resize(worldBounds);
    }

    @Override
    public void dispose() {
        bg.dispose();
        atlas.dispose();
        bulletPool.dispose();
        soundBullet.dispose();
        asteroidPool.dispose();
        super.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        ship.keyDown(keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        ship.keyUp(keycode);
        return false;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        ship.touchDown(touch, pointer, button);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        ship.touchUp(touch, pointer, button);
        return false;
    }

    private void update(float delta) {
        background.update(delta);
        for (Star star : stars) {
            star.update(delta);
        }
        ship.update(delta);
        bulletPool.updateActiveSprites(delta);
        asteroidPool.updateActiveSprites(delta);

    }

    private void freeAllDestroyed() {
        bulletPool.freeAllDestroyedActiveObjects();
        asteroidPool.freeAllDestroyedActiveObjects();

    }

    private void draw() {
        Gdx.gl.glClearColor(0.5f, 0.9f, 0.4f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for (Star star : stars) {
            star.draw(batch);
        }
        ship.draw(batch);
        bulletPool.drawActiveSprites(batch);
        asteroidPool.drawActiveSprites(batch);
        batch.end();
    }
}
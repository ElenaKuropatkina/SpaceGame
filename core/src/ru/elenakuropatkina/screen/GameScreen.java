package ru.elenakuropatkina.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.elenakuropatkina.base.BaseScreen;
import ru.elenakuropatkina.math.Rect;
import ru.elenakuropatkina.sprite.Background;
import ru.elenakuropatkina.sprite.Ship;
import ru.elenakuropatkina.sprite.Star;


public class GameScreen extends BaseScreen {

    private TextureAtlas atlasMenu, atlasShip;

    private static final int STAR_COUNT = 128;

    private Texture bg;
    private Background background;
    private Ship ship;
    private Star[] stars;

    @Override
    public void show() {
        super.show();
        bg = new Texture("bg.jpg");
        background = new Background(bg);
        atlasShip = new TextureAtlas(Gdx.files.internal("atlasShip.pack"));
        atlasMenu = new TextureAtlas(Gdx.files.internal("atlasMenu.pack"));
        ship = new Ship(atlasShip);
        stars = new Star[STAR_COUNT];
        for (int i = 0; i < STAR_COUNT; i++) {
            stars[i] = new Star(atlasMenu);
        }
    }

    @Override
    public void render(float delta) {
        update(delta);
        draw();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        ship.resize(worldBounds);
        for (Star star : stars) {
            star.resize(worldBounds);
        }
    }

    @Override
    public void dispose() {
        bg.dispose();
        atlasMenu.dispose();
        atlasShip.dispose();
        super.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        return super.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        return super.keyUp(keycode);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        ship.touchDown(touch, pointer, button);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        return super.touchUp(touch, pointer, button);
    }

    private void update(float delta) {
        ship.update(delta);
        for (Star star : stars) {
            star.update(delta);
        }

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
        batch.end();
    }
}

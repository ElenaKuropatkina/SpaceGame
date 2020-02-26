package ru.elenakuropatkina.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import ru.elenakuropatkina.base.BaseScreen;

import ru.elenakuropatkina.math.Rect;
import ru.elenakuropatkina.sprite.Background;
import ru.elenakuropatkina.sprite.Logo;

public class MenuScreen extends BaseScreen {

    private Texture l;
    private Texture bg;
    private Vector2 pos;

    private Background background;
    private Logo logo;

    @Override
    public void show() {
        super.show();
        l = new Texture("badlogic.jpg");
        bg = new Texture("bg.jpg");
        pos = new Vector2();
        background = new Background(bg);
        logo = new Logo(l);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(0.5f, 0.9f, 0.4f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        logo.update(delta);
        batch.begin();
        background.draw(batch);
        logo.draw(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        l.dispose();
        bg.dispose();
        super.dispose();

    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        logo.resize(worldBounds);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        logo.touchDown(touch, pointer, button);
        return false;
    }
}

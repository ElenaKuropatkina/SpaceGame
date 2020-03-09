package ru.elenakuropatkina.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.elenakuropatkina.base.BaseScreen;
import ru.elenakuropatkina.math.Rect;
import ru.elenakuropatkina.pool.BulletPool;
import ru.elenakuropatkina.pool.EnemyPool;
import ru.elenakuropatkina.pool.ExplosionPool;
import ru.elenakuropatkina.sprite.Background;
import ru.elenakuropatkina.sprite.Enemy;
import ru.elenakuropatkina.sprite.MyShip;
import ru.elenakuropatkina.sprite.Star;
import ru.elenakuropatkina.utils.EnemiesEmitter;



public class GameScreen extends BaseScreen {

    private static final int STAR_COUNT = 128;

    private TextureAtlas atlas, atlasMenu, atlasTest;

    private Texture bg;
    private Background background;
    private Star[] stars;

    private MyShip myShip;

    private BulletPool bulletPool;
    private EnemyPool enemyPool;
    private ExplosionPool explosionPool;

    private Sound soundBullet;
    private Sound soundLaser;
    private Sound soundExplosion;

    private EnemiesEmitter enemiesEmitter;

    @Override
    public void show() {
        super.show();
        bg = new Texture("bg.jpg");
        background = new Background(bg);
        atlas = new TextureAtlas(Gdx.files.internal("atlasGame.pack"));
        atlasMenu = new TextureAtlas(Gdx.files.internal("atlasMenu.pack"));
        atlasTest = new TextureAtlas(Gdx.files.internal("mainAtlas.tpack"));
        soundBullet = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));
        soundLaser = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav"));
        soundExplosion = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));
        stars = new Star[STAR_COUNT];
        for (int i = 0; i < STAR_COUNT; i++) {
            stars[i] = new Star(atlasMenu);
        }
        bulletPool = new BulletPool();
        explosionPool = new ExplosionPool(atlasTest, soundExplosion);
        enemyPool = new EnemyPool(bulletPool, explosionPool, soundBullet, worldBounds);
        enemiesEmitter = new EnemiesEmitter(atlas, enemyPool, worldBounds);
        myShip = new MyShip(atlas, bulletPool, soundLaser, explosionPool);
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
        music.setLooping(true);
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
        myShip.resize(worldBounds);
    }

    @Override
    public void pause() {
        music.pause();
    }

    @Override
    public void resume() {
        music.play();
    }

    @Override
    public void dispose() {
        bg.dispose();
        atlas.dispose();
        atlasMenu.dispose();
        atlasTest.dispose();
        bulletPool.dispose();
        enemyPool.dispose();
        explosionPool.dispose();
        soundBullet.dispose();
        soundLaser.dispose();
        soundExplosion.dispose();
        super.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        myShip.keyDown(keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        myShip.keyUp(keycode);
        return false;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        myShip.touchDown(touch, pointer, button);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        myShip.touchUp(touch, pointer, button);
        return false;
    }

    private void update(float delta) {
        background.update(delta);
        for (Star star : stars) {
            star.update(delta);
        }
        myShip.update(delta);
        bulletPool.updateActiveSprites(delta);
        explosionPool.updateActiveSprites(delta);
        enemyPool.updateActiveSprites(delta);
        enemiesEmitter.generate(delta);
        checkCollisions();
    }

    private void checkCollisions() {
        for(Enemy enemy : enemyPool.getActiveObjects()){
            if(!enemy.isOutside(myShip)){
                enemy.boom();
                enemy.destroy();
            }
        }
    }

    private void freeAllDestroyed() {
        bulletPool.freeAllDestroyedActiveObjects();
        enemyPool.freeAllDestroyedActiveObjects();
        explosionPool.freeAllDestroyedActiveObjects();
    }

    private void draw() {
        Gdx.gl.glClearColor(0.5f, 0.9f, 0.4f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for (Star star : stars) {
            star.draw(batch);
        }
        myShip.draw(batch);
        bulletPool.drawActiveSprites(batch);
        enemyPool.drawActiveSprites(batch);
        explosionPool.drawActiveSprites(batch);
        batch.end();
    }
}
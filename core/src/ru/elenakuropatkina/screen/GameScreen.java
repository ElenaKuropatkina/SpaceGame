package ru.elenakuropatkina.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import java.util.List;

import ru.elenakuropatkina.base.BaseScreen;
import ru.elenakuropatkina.math.Rect;
import ru.elenakuropatkina.pool.BulletPool;
import ru.elenakuropatkina.pool.EnemyPool;
import ru.elenakuropatkina.pool.ExplosionPool;
import ru.elenakuropatkina.sprite.Background;
import ru.elenakuropatkina.sprite.Bullet;
import ru.elenakuropatkina.sprite.ButtonNewGame;
import ru.elenakuropatkina.sprite.Enemy;
import ru.elenakuropatkina.sprite.MessageGameOver;
import ru.elenakuropatkina.sprite.MyShip;
import ru.elenakuropatkina.sprite.Star;
import ru.elenakuropatkina.utils.EnemiesEmitter;



public class GameScreen extends BaseScreen {

    private Game game;

    private static final int STAR_COUNT = 128;

    private enum State {PLAYING, PAUSE, GAME_OVER}

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

    private State state;
    private State pervState;

    private MessageGameOver messageGameOver;
    private ButtonNewGame buttonNewGame;

    public GameScreen(Game game) {this.game = game;}

    @Override
    public void show() {
        super.show();
        bg = new Texture("texture/bg.jpg");
        background = new Background(bg);
        atlas = new TextureAtlas(Gdx.files.internal("texture/atlasGame.pack"));
        atlasMenu = new TextureAtlas(Gdx.files.internal("texture/atlasMenu.pack"));
        atlasTest = new TextureAtlas(Gdx.files.internal("texture/mainAtlas.tpack"));
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
        messageGameOver = new MessageGameOver(atlas);
        buttonNewGame = new ButtonNewGame(atlas, game);
        state = State.PLAYING;
    }

    @Override
    public void render(float delta) {
        update(delta);
        checkCollisions();
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
        messageGameOver.resize(worldBounds);
        buttonNewGame.resize(worldBounds);
    }

    @Override
    public void pause() {

        music.pause();
        pervState = state;
        state = State.PAUSE;
    }

    @Override
    public void resume() {

        music.play();
        state = pervState;
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
        if (state == State.PLAYING) {
            myShip.keyDown(keycode);
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (state == State.PLAYING) {
            myShip.keyUp(keycode);
        }
        return false;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        if (state == State.PLAYING) {
            myShip.touchDown(touch, pointer, button);
        }
        if (state == State.GAME_OVER) {
            buttonNewGame.touchDown(touch, pointer, button);
        }

        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if (state == State.PLAYING) {
            myShip.touchUp(touch, pointer, button);
        }
        if (state == State.GAME_OVER) {
            buttonNewGame.touchUp(touch, pointer, button);
        }
        return false;
    }

    private void update(float delta) {
        background.update(delta);
        for (Star star : stars) {
            star.update(delta);
        }
        explosionPool.updateActiveSprites(delta);
        if (state == State.PLAYING) {
            myShip.update(delta);
            bulletPool.updateActiveSprites(delta);
            enemyPool.updateActiveSprites(delta);
            enemiesEmitter.generate(delta);
        }

    }

    private void checkCollisions() {
        if (state != State.PLAYING) {
            return;
        }
        List<Enemy> enemyList = enemyPool.getActiveObjects();
        for (Enemy enemy : enemyList) {
            float minDist = myShip.getHalfWidth() + enemy.getHalfWidth();
            if (myShip.pos.dst(enemy.pos) <= minDist) {
                enemy.destroy();
                myShip.damage(enemy.getDamage());
            }
        }
        List<Bullet> bulletList = bulletPool.getActiveObjects();
        for (Bullet bullet : bulletList) {
            if (bullet.getOwner() != myShip) {
                if (myShip.isBulletCollision(bullet)) {
                    myShip.damage(bullet.getDamage());
                    bullet.destroy();
                }
                continue;
            }
            for (Enemy enemy : enemyList) {
                if (enemy.isBulletCollision(bullet)) {
                    enemy.damage(bullet.getDamage());
                    bullet.destroy();
                }
            }
        }
        if (myShip.isDestroyed()) {
            state = State.GAME_OVER;
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
        explosionPool.drawActiveSprites(batch);
        if (state == State.PLAYING) {
            myShip.draw(batch);
            bulletPool.drawActiveSprites(batch);
            enemyPool.drawActiveSprites(batch);
        } else if (state == State.GAME_OVER) {
            messageGameOver.draw(batch);
            buttonNewGame.draw(batch);
        }
        batch.end();
    }
}
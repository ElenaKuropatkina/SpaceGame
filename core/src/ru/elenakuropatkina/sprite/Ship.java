package ru.elenakuropatkina.sprite;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import ru.elenakuropatkina.base.Sprite;
import ru.elenakuropatkina.math.Rect;
import ru.elenakuropatkina.math.Rnd;
import ru.elenakuropatkina.pool.BulletPool;


public class Ship extends Sprite {

    private static final int INVALID_POINTER = -1;

    private final Vector2 v = new Vector2();
    private final Vector2 v0 = new Vector2(0.5f, 0);

    private Rect worldBounds;

    private BulletPool bulletPool;
    private Sound sound;
    private TextureRegion bulletRegion;
    private final Vector2 bulletV;
    private final Vector2 bulletPos;

    private boolean pressedLeft;
    private boolean pressedRight;
    private int leftPointer = INVALID_POINTER;
    private int rightPointer = INVALID_POINTER;

    private float animateTimer;
    private float animateInterval = 0.3f;

    public Ship(TextureAtlas atlas, BulletPool bulletPool, Sound sound) {
        super(atlas.findRegion("shipMain"), 1, 2, 2);
        this.bulletPool = bulletPool;
        this.sound = sound;
        this.bulletRegion = atlas.findRegion("bullet");
        this.bulletV = new Vector2(0, 0.5f);
        this.bulletPos = new Vector2();
        animateTimer = Rnd.nextFloat(0, 1f);
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        setHeightProportion(0.15f);
        setBottom(worldBounds.getBottom() + 0.05f);
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(v, delta);
        if (getRight() > worldBounds.getRight()) {
            setRight(worldBounds.getRight());
            stop();
        }
        if (getLeft() < worldBounds.getLeft()) {
            setLeft(worldBounds.getLeft());
            stop();
        }
        animateTimer += delta;
        if (animateTimer >= animateInterval) {
            animateTimer = 0.1f;
            shoot();
            sound.play(0.03f);
        }
    }

    @Override
    public void touchDown(Vector2 touch, int pointer, int button) {
        if (touch.x < worldBounds.pos.x) {
            if (leftPointer != INVALID_POINTER) {
                return;
            }
            leftPointer = pointer;
            moveLeft();
        } else {
            if (rightPointer != INVALID_POINTER) {
                return;
            }
            rightPointer = pointer;
            moveRight();
        }
    }

    @Override
    public void touchUp(Vector2 touch, int pointer, int button) {
        if (pointer == leftPointer) {
            leftPointer = INVALID_POINTER;
            if (rightPointer != INVALID_POINTER) {
                moveRight();
            } else {
                stop();
            }
        } else if (pointer == rightPointer) {
            rightPointer = INVALID_POINTER;
            if (leftPointer != INVALID_POINTER) {
                moveLeft();
            } else {
                stop();
            }
        }
    }

    public void keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.LEFT:
                pressedLeft = true;
                moveLeft();
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                pressedRight = true;
                moveRight();
                break;
        }
    }

    public void keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.LEFT:
                pressedLeft = false;
                if (pressedRight) {
                    moveRight();
                } else {
                    stop();
                }
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                pressedRight = false;
                if (pressedLeft) {
                    moveLeft();
                } else {
                    stop();
                }
                break;
        }
    }

    private void moveRight() {
        v.set(v0);
    }

    private void moveLeft() {
        v.set(v0).rotate(180);
    }

    private void stop() {
        v.setZero();
    }

    private void shoot() {
        Bullet bullet = bulletPool.obtain();
        bulletPos.set(pos.x -0.004f, getTop());
        bullet.set(this, bulletRegion, bulletPos, bulletV, 0.015f, worldBounds, 1);
    }

}
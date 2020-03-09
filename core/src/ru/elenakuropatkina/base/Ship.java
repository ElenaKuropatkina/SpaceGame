package ru.elenakuropatkina.base;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.elenakuropatkina.math.Rect;
import ru.elenakuropatkina.pool.BulletPool;
import ru.elenakuropatkina.pool.ExplosionPool;
import ru.elenakuropatkina.sprite.Bullet;
import ru.elenakuropatkina.sprite.Explosion;

public class Ship extends Sprite {

    protected Vector2 v;
    protected Vector2 v0;
    protected Rect worldBounds;

    protected Sound sound;

    protected BulletPool bulletPool;
    protected ExplosionPool explosionPool;
    protected TextureRegion bulletRegion;
    protected Vector2 bulletV;
    protected Vector2 bulletPos;
    protected float bulletHeight;
    protected int damage;
    protected int hp;

    protected float reloadInterval;
    protected float reloadTimer;

    public Ship() {
    }

    public Ship(TextureRegion region, int rows, int cols, int frames) {
        super(region, rows, cols, frames);
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(v, delta);
        reloadTimer += delta;
        if (reloadTimer >= reloadInterval) {
            reloadTimer = 0f;
            shoot();
        }
    }

    protected void shoot() {
        sound.play();
        Bullet bullet = bulletPool.obtain();
        bullet.set(this, bulletRegion, bulletPos, bulletV, bulletHeight, worldBounds, damage);
    }

    public void boom() {
        Explosion explosion = explosionPool.obtain();
        explosion.set(getHeight(), pos);
    }
}




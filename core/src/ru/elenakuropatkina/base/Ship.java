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

    protected final float DAMAGE_ANIMATE_INTERVAL = 0.1f;

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
    protected float damageAnimateTimer = DAMAGE_ANIMATE_INTERVAL;

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
        damageAnimateTimer += delta;
        if (damageAnimateTimer >= DAMAGE_ANIMATE_INTERVAL) {
            frame = 0;
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        this.hp = 0;
        boom();
    }

    public void destroyOutOfWorld() {
        super.destroy();
    }

    public void damage(int damage) {
        this.hp -= damage;
        if (hp <= 0) {
            destroy();
        }
        damageAnimateTimer = 0.05f;
        frame = 1;
    }

    public int getDamage() {
        return damage;
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

    public int getHp() {
        return hp;
    }

    public Vector2 getV() {
        return v;
    }
}




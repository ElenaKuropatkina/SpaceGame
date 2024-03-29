package ru.elenakuropatkina.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.elenakuropatkina.base.Ship;
import ru.elenakuropatkina.math.Rect;
import ru.elenakuropatkina.pool.BulletPool;
import ru.elenakuropatkina.pool.ExplosionPool;

public class Enemy extends Ship {

    private final Vector2 descentV;

    public Enemy(BulletPool bulletPool, ExplosionPool explosionPool, Sound sound, Rect worldBounds) {
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.sound = sound;
        this.worldBounds = worldBounds;
        this.v = new Vector2();
        this.v0 = new Vector2();
        this.bulletV = new Vector2();
        this.bulletPos = new Vector2();
        this.descentV = new Vector2(0, -0.3f);
    }

    @Override
    public void update(float delta) {
        bulletPos.set(pos.x, getBottom());
        super.update(delta);
        if (getTop() < worldBounds.getTop()) {
            v.set(v0);
        } else {
            this.reloadTimer = reloadInterval*0.9f;
        }
        if (getTop() < worldBounds.getBottom()) {
            destroyOutOfWorld();
        }
    }

    public void set(
            TextureRegion[] regions,
            Vector2 v0,
            TextureRegion bulletRegion,
            float bulletHeight,
            float bulletVY,
            int damage,
            float reloadInterval,
            float height,
            int hp
    ) {
        this.regions = regions;
        this.v0.set(v0);
        this.bulletRegion = bulletRegion;
        this.bulletHeight = bulletHeight;
        this.bulletV.set(0, bulletVY);
        this.damage = damage;
        this.reloadInterval = reloadInterval;
        this.reloadTimer = 0f;
        setHeightProportion(height);
        this.hp = hp;
        v.set(descentV);
    }

    public boolean isBulletCollision(Rect bullet) {
        return !(bullet.getRight() < getLeft()
                || bullet.getLeft() > getRight()
                || bullet.getBottom() > getTop()
                || bullet.getTop() < pos.y);
    }
}

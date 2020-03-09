package ru.elenakuropatkina.pool;

import com.badlogic.gdx.audio.Sound;

import ru.elenakuropatkina.base.SpritesPool;
import ru.elenakuropatkina.math.Rect;
import ru.elenakuropatkina.sprite.Enemy;

public class EnemyPool extends SpritesPool<Enemy> {

    private BulletPool bulletPool;
    private ExplosionPool explosionPool;
    private Sound sound;
    private Rect worldBounds;

    public EnemyPool(BulletPool bulletPool, ExplosionPool explosionPool, Sound sound, Rect worldBounds) {
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.sound = sound;
        this.worldBounds = worldBounds;
    }

    @Override
    protected Enemy newObject() {
        return new Enemy(bulletPool, explosionPool, sound, worldBounds);
    }
}

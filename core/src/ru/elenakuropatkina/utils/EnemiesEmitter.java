package ru.elenakuropatkina.utils;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.elenakuropatkina.math.Rect;
import ru.elenakuropatkina.math.Rnd;
import ru.elenakuropatkina.pool.EnemyPool;
import ru.elenakuropatkina.sprite.Enemy;

public class EnemiesEmitter {

    private static final float SMALL_ENEMY_HEIGHT = 0.14f;
    private static final float SMALL_ENEMY_BULLET_HEIGHT = 0.01f;
    private static final float SMALL_ENEMY_BULLET_VY = -0.3f;
    private static final float SMALL_ENEMY_RELOAD_INTERVAL = 3f;
    private static final int SMALL_ENEMY_DAMAGE = 1;
    private static final int SMALL_ENEMY_HP = 2;

    private static final float MEDIUM_ENEMY_HEIGHT = 0.18f;
    private static final float MEDIUM_ENEMY_BULLET_HEIGHT = 0.015f;
    private static final float MEDIUM_ENEMY_BULLET_VY = -0.22f;
    private static final int MEDIUM_ENEMY_DAMAGE = 2;
    private static final float MEDIUM_ENEMY_RELOAD_INTERVAL = 4f;
    private static final int MEDIUM_ENEMY_HP = 3;

    private static final float BIG_ENEMY_HEIGHT = 0.28f;
    private static final float BIG_ENEMY_BULLET_HEIGHT = 0.02f;
    private static final float BIG_ENEMY_BULLET_VY = -0.17f;
    private static final int BIG_ENEMY_DAMAGE = 4;
    private static final float BIG_ENEMY_RELOAD_INTERVAL = 1f;
    private static final int BIG_ENEMY_HP = 5;

    private float generateInterval = 2f;
    private float generateTimer;

    private int level = 1;

    private final TextureRegion[] smallEnemyRegion;
    private final Vector2 smallEnemyV = new Vector2(0, -0.2f);
    private final TextureRegion[] mediumEnemyRegion;
    private final Vector2 mediumEnemyV = new Vector2(0, -0.1f);
    private final TextureRegion[] bigEnemyRegion;
    private final Vector2 bigEnemyV = new Vector2(0, -0.05f);

    private TextureRegion bulletRegion;
    private final EnemyPool enemyPool;

    private Rect worldBounds;

    public EnemiesEmitter(TextureAtlas atlas, EnemyPool enemyPool, Rect worldBounds) {
        this.smallEnemyRegion = Regions.split(atlas.findRegion("smallEnemyShip"), 1, 2 ,2);
        this.mediumEnemyRegion = Regions.split(atlas.findRegion("mediumEnemyShip"), 1, 2, 2);
        this.bigEnemyRegion = Regions.split(atlas.findRegion("bigEnemyShip"), 1, 2, 2);
        this.enemyPool = enemyPool;
        this.worldBounds = worldBounds;
        this.bulletRegion = atlas.findRegion("bulletEnemy");
    }

    public void generate(float delta, int frags){
        level = frags / 5 + 1;
        generateTimer += delta;
        if(generateTimer >= generateInterval) {
            generateTimer = 0;
            Enemy enemy = enemyPool.obtain();
            float type = (float) Math.random();
            if (type < 0.5f) {
                    enemy.set(
                            smallEnemyRegion,
                            smallEnemyV,
                            bulletRegion,
                            SMALL_ENEMY_BULLET_HEIGHT,
                            SMALL_ENEMY_BULLET_VY,
                            SMALL_ENEMY_DAMAGE + level,
                            SMALL_ENEMY_RELOAD_INTERVAL,
                            SMALL_ENEMY_HEIGHT,
                            SMALL_ENEMY_HP + level
                    );

            }else if (type < 0.7f) {
                enemy.set(
                            mediumEnemyRegion,
                            mediumEnemyV,
                            bulletRegion,
                            MEDIUM_ENEMY_BULLET_HEIGHT,
                            MEDIUM_ENEMY_BULLET_VY,
                            MEDIUM_ENEMY_DAMAGE + level,
                            MEDIUM_ENEMY_RELOAD_INTERVAL,
                            MEDIUM_ENEMY_HEIGHT,
                            MEDIUM_ENEMY_HP + level

                    );
            } else {
                    enemy.set(
                            bigEnemyRegion,
                            bigEnemyV,
                            bulletRegion,
                            BIG_ENEMY_BULLET_HEIGHT,
                            BIG_ENEMY_BULLET_VY,
                            BIG_ENEMY_DAMAGE + level,
                            BIG_ENEMY_RELOAD_INTERVAL,
                            BIG_ENEMY_HEIGHT,
                            BIG_ENEMY_HP + level
                    );
            }
            enemy.setBottom(worldBounds.getTop());
            enemy.pos.x = Rnd.nextFloat(worldBounds.getLeft() + enemy.getHalfWidth(), worldBounds.getRight() - enemy.getHalfWidth());
       }
    }

    public int getLevel() {
        return level;
    }
}

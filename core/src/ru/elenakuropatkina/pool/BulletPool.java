package ru.elenakuropatkina.pool;

import ru.elenakuropatkina.base.SpritesPool;
import ru.elenakuropatkina.sprite.Bullet;

public class BulletPool extends SpritesPool <Bullet>{

    @Override
    protected Bullet newObject() {
        return new Bullet();
    }
}


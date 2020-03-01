package ru.elenakuropatkina.pool;

import ru.elenakuropatkina.base.SpritesPool;
import ru.elenakuropatkina.sprite.Asteroid;


public class AsteroidPool extends SpritesPool<Asteroid> {
    @Override
    protected Asteroid newObject() {
        return new Asteroid();
    }
}



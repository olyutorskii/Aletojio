/*
 * License : The MIT License
 * Copyright(c) 2022 olyutorskii
 */

package io.github.olyutorskii.aletojio.rng;

/**
 * 32bit Random number generator.
 *
 * <p>INFO : {@code java.util.random.RandomGenerator#nextInt()} added since JDK17 or later.
 *
 * @see java.util.Random#nextInt()
 */
public interface RndInt32 {

    /**
     * Return next random number as 32bit int with random sign-bit.
     *
     * @return random number
     */
    public abstract int nextInt32();

}

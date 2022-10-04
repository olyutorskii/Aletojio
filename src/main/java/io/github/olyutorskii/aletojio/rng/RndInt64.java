/*
 * License : The MIT License
 * Copyright(c) 2022 olyutorskii
 */

package io.github.olyutorskii.aletojio.rng;

/**
 * 64bit Random number generator.
 *
 * <p>INFO : {@code java.util.random.RandomGenerator#nextLong()} added since JDK17 or later.
 *
 * @see java.util.Random#nextLong()
 */
public interface RndInt64 {

    /**
     * Return next random number as 64bit long with random sign-bit.
     *
     * @return random number
     */
    public abstract long nextInt64();

}

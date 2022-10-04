/*
 * License : The MIT License
 * Copyright(c) 2022 olyutorskii
 */

package io.github.olyutorskii.aletojio.rng;

/**
 * 31bit Random number generator.
 *
 * <p>INFO : {@code java.util.random.RandomGenerator#nextInt(int bound)} added since JDK17 or later.
 *
 * @see java.util.Random#nextInt(int bound)
 */
public interface RndInt31 {

    /**
     * Return next random number as 31bit int from LSB without sign-bit.
     *
     * <p>Only positive or zero value returned.
     *
     * <p>* LCG implementations on CPU without 64-bit remainders
     * often output only 31-bit random numbers.
     *
     * @return random number
     */
    public abstract int nextInt31();

}

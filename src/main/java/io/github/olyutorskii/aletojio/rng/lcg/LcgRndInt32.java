/*
 * License : The MIT License
 * Copyright(c) 2022 olyutorskii
 */

package io.github.olyutorskii.aletojio.rng.lcg;

import io.github.olyutorskii.aletojio.rng.RndInt32;

/**
 * Implementation of 32bit output Linear congruential generator(LCG).
 *
 * <p>LCG is a commonly used random number generator in the past.
 *
 * <p>Recurrence relation sequences : {@code X(n+1) = (X(n) * Mul + Inc) mod Mod}
 *
 * <ul>
 * <li>Mul : Multiplier
 * <li>Inc : Increment
 * <li>Mod : Modulus
 * </ul>
 *
 * @see <a href="https://en.wikipedia.org/wiki/Linear_congruential_generator">
 * Linear congruential generator (Wikipedia)
 * </a>
 */
public class LcgRndInt32 extends AbstractLcg implements RndInt32 {

    /**
     * Constructor.
     *
     * <ul>
     * <li>Multiplier must be 1 or greatrer.
     * <li>Increment must be 0 or greatrer.
     * <li>Modulus must be 2 or greater.
     * </ul>
     *
     * @param mulArg multiplier
     * @param incArg increment
     * @param modArg modulus
     * @throws IllegalArgumentException illegal argument
     */
    public LcgRndInt32(long mulArg, long incArg, long modArg)
            throws IllegalArgumentException {
        super(mulArg, incArg, modArg);
        return;
    }

    /**
     * {@inheritDoc}
     *
     * <p>result(32bit) reflects seed[16:47]
     *
     * @return {@inheritDoc}
     */
    @Override
    protected int seedToResult() {
        long lVal = getSeed() >>> 16;
        lVal &= MASK_B32;
        int result = (int) lVal;
        return result;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public int nextInt32() {
        int result = nextIntImpl();
        return result;
    }

}

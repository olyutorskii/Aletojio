/*
 * License : The MIT License
 * Copyright(c) 2022 olyutorskii
 */

package io.github.olyutorskii.aletojio.rng.lcg;

import io.github.olyutorskii.aletojio.rng.RndInt31;

/**
 * Implementation of 31bit output Linear congruential generator(LCG).
 *
 * <p>LCG is a commonly used random number generator in the past.
 *
 * <p>* LCG implementations on CPU without 64-bit remainders
 * often output only 31-bit random numbers.
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
public class LcgRndInt31 extends AbstractLcg implements RndInt31 {

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
    public LcgRndInt31(long mulArg, long incArg, long modArg)
            throws IllegalArgumentException {
        super(mulArg, incArg, modArg);
        return;
    }

    /**
     * {@inheritDoc}
     *
     * <p>result(31bit) reflects seed[0:30]
     *
     * @return {@inheritDoc}
     */
    @Override
    protected int seedToResult() {
        long lVal = getSeed() & MASK_B31;
        int result = (int) lVal;
        return result;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public int nextInt31() {
        int result = nextIntImpl();
        return result;
    }

}

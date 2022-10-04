/*
 * License : The MIT License
 * Copyright(c) 2021 Olyutorskii
 */

package io.github.olyutorskii.aletojio.rng.xorsft;

import io.github.olyutorskii.aletojio.rng.RndInt32;

/**
 * xorshift (32bit) Pseudo Random Generator.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Xorshift">Xorshift (Wikipedia)</a>
 * @see <a href="https://www.jstatsoft.org/article/view/v008i14">Xorshift RNGs</a>
 */
public class XorShift implements RndInt32 {

    private static final int LSF_0 = 13;
    private static final int RSF_0 = 17;
    private static final int LSF_1 =  5;

    private static final String MSG_ILLSEED = "Seed must be non-zero";


    private int seed = 1;


    /**
     * Constructor.
     */
    public XorShift() {
        super();
        return;
    }


    /**
     * {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public int nextInt32() {
        int result = this.seed;

        result = result ^ (result <<  LSF_0);
        result = result ^ (result >>> RSF_0);
        result = result ^ (result <<  LSF_1);

        this.seed = result;

        return result;
    }

    /**
     * Set seed.
     *
     * <p>Seed must be non-zero.
     *
     * @param seedArg seed
     * @throws IllegalArgumentException Seed must be non-zero
     */
    public void setSeed(int seedArg) throws IllegalArgumentException {
        if (seedArg == 0) {
            throw new IllegalArgumentException(MSG_ILLSEED);
        }

        this.seed = seedArg;

        return;
    }

}

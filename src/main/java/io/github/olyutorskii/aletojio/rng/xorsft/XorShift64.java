/*
 * License : The MIT License
 * Copyright(c) 2021 Olyutorskii
 */

package io.github.olyutorskii.aletojio.rng.xorsft;

import io.github.olyutorskii.aletojio.rng.RndInt64;

/**
 * xorshift (64bit) Pseudo Random Generator.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Xorshift">Xorshift (Wikipedia)</a>
 * @see <a href="https://www.jstatsoft.org/article/view/v008i14">Xorshift RNGs</a>
 */
public class XorShift64 implements RndInt64 {

    private static final int LSF_0 = 13;
    private static final int RSF_0 =  7;
    private static final int LSF_1 = 17;

    private static final String MSG_ILLSEED = "Seed must be non-zero";


    private long seed = 1L;


    /**
     * Constructor.
     */
    public XorShift64() {
        super();
        return;
    }


    /**
     * {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public long nextInt64() {
        long result = this.seed;

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
    public void setSeed(long seedArg) {
        if (seedArg == 0L) {
            throw new IllegalArgumentException(MSG_ILLSEED);
        }

        this.seed = seedArg;

        return;
    }

}

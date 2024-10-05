/*
 * License : The MIT License
 * Copyright(c) 2021 Olyutorskii
 */

package io.github.olyutorskii.aletojio.rng.xorsft;

import io.github.olyutorskii.aletojio.rng.RndInt32;

/**
 * xorshift (128bit) Pseudo Random Generator.
 *
 * <p>Warning: The default seed value will give a very biased result.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Xorshift">Xorshift (Wikipedia)</a>
 * @see <a href="https://www.jstatsoft.org/article/view/v008i14">Xorshift RNGs</a>
 */
public class XorShift128 implements RndInt32 {

    // [5, 14, 1], [15, 4, 21], [23, 24, 3], [5, 12, 29]
    private static final int LSF_0 =  5;
    private static final int RSF_0 = 12;
    private static final int RSF_1 = 29;

    private static final String MSG_ILLSEED = "At least one seed must be non-zero";


    private int s0 = 1;
    private int s1 = 0;
    private int s2 = 0;
    private int s3 = 0;


    /**
     * Constructor.
     *
     * <p>Warning: The default seed value will give a very biased result.
     */
    public XorShift128() {
        super();
        return;
    }


    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public int nextInt32() {
        int tmp0 = this.s0;
        int tmp3 = this.s3;

        tmp0 = tmp0 ^ (tmp0 <<  LSF_0);
        tmp0 = tmp0 ^ (tmp0 >>> RSF_0);
        tmp3 = tmp3 ^ (tmp3 >>> RSF_1);

        final int result = tmp0 ^ tmp3;

        this.s0 = this.s1;
        this.s1 = this.s2;
        this.s2 = this.s3;
        this.s3 = result;

        return result;
    }

    /**
     * Set seed.
     *
     * <p>At least one seed must be non-zero.
     *
     * @param s0Arg seed0
     * @param s1Arg seed1
     * @param s2Arg seed2
     * @param s3Arg seed3
     * @throws IllegalArgumentException At least one seed must be non-zero
     */
    public void setSeed(int s0Arg, int s1Arg, int s2Arg, int s3Arg)
            throws IllegalArgumentException {
        if ((s0Arg | s1Arg | s2Arg | s3Arg) == 0L) {
            throw new IllegalArgumentException(MSG_ILLSEED);
        }

        this.s0 = s0Arg;
        this.s1 = s1Arg;
        this.s2 = s2Arg;
        this.s3 = s3Arg;

        return;
    }

}

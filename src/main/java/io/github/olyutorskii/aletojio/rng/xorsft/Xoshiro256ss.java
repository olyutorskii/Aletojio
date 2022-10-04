/*
 * License : The MIT License
 * Copyright(c) 2021 Olyutorskii
 */

package io.github.olyutorskii.aletojio.rng.xorsft;

import io.github.olyutorskii.aletojio.rng.RndInt64;

/**
 * xoshiro256** Pseudo Random Generator.
 *
 * <p>Warning: The default seed value will give a very biased result.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Xorshift">Xorshift (Wikipedia)</a>
 * @see <a href="https://prng.di.unimi.it/">xoshiro / xoroshiro generators and the PRNG shootout</a>
 */
public class Xoshiro256ss implements RndInt64 {

    private static final int LSFT   = 17;
    private static final int ROTL_0 =  7;
    private static final int ROTL_1 = 45;

    private static final long MUL_0 = 5L;
    private static final long MUL_1 = 9L;

    private static final String MSG_ILLSEED = "At least one seed must be non-zero";


    private long s0 = 1L;
    private long s1 = 0L;
    private long s2 = 0L;
    private long s3 = 0L;


    /**
     * Constructor.
     *
     * <p>Warning: The default seed value will give a very biased result.
     */
    public Xoshiro256ss() {
        super();
        return;
    }


    /**
     * {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public long nextInt64() {
        long tmp0 = this.s0;
        long tmp1 = this.s1;
        long tmp2 = this.s2;
        long tmp3 = this.s3;

        final long lsft1 = tmp1 << LSFT;

        final long result = Long.rotateLeft(tmp1 * MUL_0, ROTL_0) * MUL_1;

        tmp2 ^= tmp0;
        tmp3 ^= tmp1;
        tmp1 ^= tmp2;
        tmp0 ^= tmp3;

        tmp2 ^= lsft1;
        tmp3 = Long.rotateLeft(tmp3, ROTL_1);

        this.s0 = tmp0;
        this.s1 = tmp1;
        this.s2 = tmp2;
        this.s3 = tmp3;

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
    public void setSeed(long s0Arg, long s1Arg, long s2Arg, long s3Arg)
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

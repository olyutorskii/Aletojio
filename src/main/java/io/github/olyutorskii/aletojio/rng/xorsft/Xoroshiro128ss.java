/*
 * License : The MIT License
 * Copyright(c) 2021 Olyutorskii
 */

package io.github.olyutorskii.aletojio.rng.xorsft;

import io.github.olyutorskii.aletojio.rng.RndInt64;

/**
 * xoroshiro128** Pseudo Random Generator.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Xorshift">Xorshift (Wikipedia)</a>
 * @see <a href="https://prng.di.unimi.it/">xoshiro / xoroshiro generators and the PRNG shootout</a>
 */
public class Xoroshiro128ss implements RndInt64 {

    private static final int LSFT   = 16;
    private static final int ROTL_0 =  7;
    private static final int ROTL_1 = 24;
    private static final int ROTL_2 = 37;

    private static final long MUL_0 = 5L;
    private static final long MUL_1 = 9L;

    private static final String MSG_ILLSEED = "At least one seed must be non-zero";


    private long s0 = 1L;
    private long s1 = 0L;


    /**
     * Constructor.
     */
    public Xoroshiro128ss() {
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

        final long result = Long.rotateLeft(tmp0 * MUL_0, ROTL_0) * MUL_1;

        tmp1 ^= tmp0;
        final long orshift1 = tmp1 ^ (tmp1 << LSFT);

        tmp0 = Long.rotateLeft(tmp0, ROTL_1) ^ orshift1;
        tmp1 = Long.rotateLeft(tmp1, ROTL_2);

        this.s0 = tmp0;
        this.s1 = tmp1;

        return result;
    }

    /**
     * Set seed.
     *
     * <p>At least one seed must be non-zero.
     *
     * @param s0Arg seed0
     * @param s1Arg seed1
     * @throws IllegalArgumentException At least one seed must be non-zero
     */
    public void setSeed(long s0Arg, long s1Arg)
            throws IllegalArgumentException {
        if ((s0Arg | s1Arg) == 0L) {
            throw new IllegalArgumentException(MSG_ILLSEED);
        }

        this.s0 = s0Arg;
        this.s1 = s1Arg;

        return;
    }

}

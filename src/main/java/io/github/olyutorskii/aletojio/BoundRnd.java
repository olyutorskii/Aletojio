/*
 * License : The MIT License
 * Copyright(c) 2022 Olyutorskii
 */

package io.github.olyutorskii.aletojio;

import io.github.olyutorskii.aletojio.rng.RndInt32;
import java.util.Objects;

/**
 * Bounded random integer value {@code [0,bound)} generator.
 *
 * <p>Random source is {@link RndInt32}.
 *
 * <p>This implementation uses a fast algorithm(Nearly Divisionless)
 * that does not use a division(/) or remainder(%) but bitmask and right-shift.
 *
 * <p>Modulo bias removal may consume multiple 32-bit random numbers per one output.
 *
 * @see <a href="https://arxiv.org/abs/1805.10941">Fast Random Integer Generation in an Interval (arXiv)</a>
 * @see <a href="https://lemire.me/blog/2019/06/06/nearly-divisionless-random-integer-generation-on-various-systems/">
 * Nearly Divisionless Random Integer Generation On Various Systems
 * </a>
 */
public class BoundRnd {

    private static final String ERRMSG_BOUND = "too small bound";

    private static final long RNDCAR   =  1L << Integer.SIZE;
    private static final long MASK_I32 = (1L << Integer.SIZE) - 1;


    private final RndInt32 rnd;
    private final long bound;
    private final long judge2nd;


    /**
     * Constructor for [0,N) bounded random number.
     *
     * <p>N is upper bound(exclusive).
     *
     * @param rnd 32bit random number generator
     * @param bound upper bound(exclusive) must be 2 or more
     * @throws NullPointerException argument is null
     * @throws IllegalArgumentException toosmall bound
     */
    public BoundRnd(RndInt32 rnd, int bound) throws IllegalArgumentException {
        super();

        Objects.requireNonNull(rnd);
        if (bound < 2) throw new IllegalArgumentException(ERRMSG_BOUND);

        this.rnd = rnd;
        this.bound = bound;
        this.judge2nd = RNDCAR % this.bound;   // = (RNDCAR - bound) % bound

        assert this.bound > this.judge2nd;

        return;
    }


    /**
     * Return sparce random number.
     *
     * <p>unsigned 32bit random number * bound.
     *
     * @return sparce random number
     */
    private long sparceRnd() {
        int r32 = this.rnd.nextInt32();
        long r64 = Integer.toUnsignedLong(r32);
        long result = r64 * this.bound;
        return result;
    }

    /**
     * Return next bounded random number.
     *
     * @return [0, bound) random number
     */
    public int next() {
        long mul64;
        long low32;

        mul64 = sparceRnd();
        low32 = mul64 & MASK_I32;

        if (low32 < this.bound) {
            final long limit2nd = this.judge2nd;
            while (low32 < limit2nd) {
                mul64 = sparceRnd();
                low32 = mul64 & MASK_I32;
            }
        }

        int high32 = (int) (mul64 >>> Integer.SIZE);
        return high32;
    }

}

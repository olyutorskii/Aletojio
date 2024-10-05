/*
 * License : The MIT License
 * Copyright(c) 2022 olyutorskii
 */

package io.github.olyutorskii.aletojio.rng.dull;

import io.github.olyutorskii.aletojio.rng.RndInt32;

/**
 * Step progression sequence generator.
 *
 * <p>{@code X(n) = start + step * n}
 *
 * <p>If a suitable step is selected,
 * it can be compromised as a quasi-random number generator.
 *
 * <p>Using large prime numbers other than 2 for step would give a long period.
 *
 * <p>{@link Integer#MAX_VALUE} is followed by {@link Integer#MIN_VALUE}.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Low-discrepancy_sequence">
 * Low-discrepancy sequence (Wikipedia)
 * </a>
 */
public class StepSequence implements RndInt32 {

    /**
     * Prime number near equal golden ratio of Integer.MAX.
     *
     * <p>Using this number for step yields sparse results
     * that are hard to approximate with rational numbers.
     *
     * <p>Ref : Integer.MAX * (3-SQRT(5)) = 1640531525
     *
     * @see <a href="https://en.wikipedia.org/wiki/Golden_ratio">Golden ratio (Wikipedia)</a>
     * @see <a href="https://en.wikipedia.org/wiki/Low-discrepancy_sequence">
     * Low-discrepancy sequence (Wikipedia)
     * </a>
     */
    public static final int PRIME_STEP = 1642716433;


    private final int step;
    private int next;


    /**
     * Constructor.
     *
     * <p>output sequence will be {start, start+step, start+step*2, start+step*3, ...}
     *
     * <p>Overflow is ignored.
     *
     * @param start start value
     * @param step step value
     */
    public StepSequence(int start, int step) {
        super();
        this.next = start;
        this.step = step;
        return;
    }


    /**
     * Create simple counter.
     *
     * <p>{0, 1, 2, ...}
     *
     * @return counter
     */
    public static StepSequence createCounter() {
        StepSequence result = new StepSequence(0, 1);
        return result;
    }

    /**
     * Create Quasi-random generator.
     *
     * <p>Quasi-random generator outputs extremely uniform random numbers.
     *
     * @return random generator
     * @see <a href="https://en.wikipedia.org/wiki/Low-discrepancy_sequence">
     * Low-discrepancy sequence (Wikipedia)
     * </a>
     */
    public static StepSequence createQuasiRandom() {
        StepSequence result = new StepSequence(PRIME_STEP, PRIME_STEP);
        return result;
    }


    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public int nextInt32() {
        int result = this.next;
        this.next += this.step;
        return result;
    }

}

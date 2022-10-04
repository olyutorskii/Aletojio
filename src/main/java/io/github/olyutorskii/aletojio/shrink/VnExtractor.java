/*
 * License : The MIT License
 * Copyright(c) 2022 olyutorskii
 */

package io.github.olyutorskii.aletojio.shrink;

import io.github.olyutorskii.aletojio.BitPool;
import io.github.olyutorskii.aletojio.rng.RndInt32;
import java.util.Objects;

/**
 * Von Neumann randomness extractor (whitening).
 *
 * <p>Crunching input data by an average of 50%
 * and improve output data randomness bias.
 *
 * <ul>
 * <li>...00... → ×
 * <li>...01... → 0
 * <li>...10... → 1
 * <li>...11... → ×
 * </ul>
 *
 * <p>Random number output throughput decreases half.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Bernoulli_process#Basic_von_Neumann_extractor">
 * Basic von Neumann extractor (Wikipedia)</a>
 * @see <a href="https://en.wikipedia.org/wiki/Self-shrinking_generator">
 * Self-shrinking generator (Wikipedia)</a>
 */
@SuppressWarnings("serial")
public class VnExtractor implements RndInt32 {

    private static final int STEP = 2;
    private static final int MASK_LSB2 = 0b11;


    private final RndInt32 rnd;

    private final BitPool bitPool = new BitPool();


    /**
     * Constructor.
     *
     * @param rnd random generator
     * @throws NullPointerException null argument
     */
    public VnExtractor(RndInt32 rnd) throws NullPointerException {
        super();
        Objects.requireNonNull(rnd);

        this.rnd = rnd;

        return;
    }


    /**
     * fill random entropy to pool.
     */
    private void fillPool() {
        while (this.bitPool.remaining() >= Integer.SIZE) {
            int iRndVal = this.rnd.nextInt32();

            // TODO: High speed by 8-bit look-up table
            for (int rsft = Integer.SIZE - STEP; rsft >= 0; rsft -= STEP) {
                int lsb2 = (iRndVal >>> rsft) & MASK_LSB2;

                boolean bVal;
                switch (lsb2) {
                case 0b01:
                    bVal = false;
                    break;
                case 0b10:
                    bVal = true;
                    break;
                default:
                    continue;
                }

                this.bitPool.pushBoolean(bVal);
            }
        }

        return;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public int nextInt32() {
        int result;

        if (this.bitPool.size() < Integer.SIZE) {
            fillPool();
        }
        result = this.bitPool.chopInt();

        return result;
    }

}

/*
 * License : The MIT License
 * Copyright(c) 2022 olyutorskii
 */

package io.github.olyutorskii.aletojio.shrink;

import io.github.olyutorskii.aletojio.rng.RndInt32;
import java.util.Objects;

/**
 * Switch output bits from 2 input streams.
 *
 * <p>Another stream is required to provide the switching.
 * If switching-bit is 0, output 1st input stream bit.
 * If switching-bit is 1, output 2nd input stream bit.
 *
 *
 * <p>Random number output throughput decreases {@code 1/3}.
 *
 * <p>Example
 * <ul>
 * <li> input stream 1st 01010101
 * <li> input stream 2nd 11001100
 * <li> switch stream 00001111
 * </ul>
 * will be output 01011100
 */
public class BitSwitcher implements RndInt32 {

    private final RndInt32 sw;
    private final RndInt32 rnd0;
    private final RndInt32 rnd1;


    /**
     * Constructor.
     *
     * @param sw switch stream
     * @param rnd0 input stream 1st
     * @param rnd1 input stream 2nd
     * @throws NullPointerException stream is null
     */
    public BitSwitcher(RndInt32 sw, RndInt32 rnd0, RndInt32 rnd1) throws NullPointerException {
        super();
        Objects.requireNonNull(sw);
        Objects.requireNonNull(rnd0);
        Objects.requireNonNull(rnd1);

        this.sw = sw;
        this.rnd0 = rnd0;
        this.rnd1 = rnd1;

        return;
    }


    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public int nextInt32() {
        int mask  = this.sw.nextInt32();
        int iVal0 = this.rnd0.nextInt32();
        int iVal1 = this.rnd1.nextInt32();

        iVal0 &= ~mask;
        iVal1 &=  mask;
        int result = iVal0 | iVal1;

        return result;
    }

}

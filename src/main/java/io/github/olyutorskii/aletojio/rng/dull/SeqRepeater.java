/*
 * License : The MIT License
 * Copyright(c) 2022 olyutorskii
 */

package io.github.olyutorskii.aletojio.rng.dull;

import io.github.olyutorskii.aletojio.rng.RndInt32;
import io.github.olyutorskii.aletojio.rng.RndInt64;
import java.util.List;
import java.util.Objects;

/**
 * Just repeating integer sequence generator.
 *
 * <p>If X0,X1,X2 is specified, outputs {X0, X1, X2, X0, X1, X2, X0...} forever.
 *
 * <p>Yes, all pseudo-random generators on finite state automaton are repeated.
 */
public class SeqRepeater implements RndInt32, RndInt64 {

    private final int[] iVec;
    private final int size;

    private int pos;


    /**
     * Constructor.
     *
     * @param iVecArg int values sequence
     * @throws NullPointerException null argument
     * @throws IllegalArgumentException no values
     */
    public SeqRepeater(int... iVecArg)
            throws NullPointerException, IllegalArgumentException {
        super();
        Objects.requireNonNull(iVecArg);

        this.size = iVecArg.length;
        if (this.size < 1) throw new IllegalArgumentException();

        this.iVec = new int[this.size];
        System.arraycopy(iVecArg, 0, this.iVec, 0, this.size);

        this.pos = 0;

        return;
    }

    /**
     * Constructor.
     *
     * @param iVecList int values sequence
     * @throws NullPointerException argument is null
     * @throws IllegalArgumentException no values
     */
    public SeqRepeater(List<Integer> iVecList)
            throws NullPointerException, IllegalArgumentException {
        super();
        Objects.requireNonNull(iVecList);

        this.size = iVecList.size();
        if (this.size < 1) throw new IllegalArgumentException();

        this.iVec = new int[this.size];

        int idx = 0;
        for (int iVal : iVecList) {
            this.iVec[idx++] = iVal;
        }
        assert idx == this.size;

        this.pos = 0;

        return;
    }


    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public int nextInt32() {
        int result = this.iVec[this.pos++];

        if (this.pos >= this.size) {
            this.pos = 0;
        }

        return result;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public long nextInt64() {
        long rndHigh = Integer.toUnsignedLong(nextInt32());
        long rndLow  = Integer.toUnsignedLong(nextInt32());
        long result = (rndHigh << Integer.SIZE) | rndLow;
        return result;
    }

}

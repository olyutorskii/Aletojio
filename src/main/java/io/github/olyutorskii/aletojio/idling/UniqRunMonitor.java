/*
 * License : The MIT License
 * Copyright(c) 2022 Olyutorskii
 */

package io.github.olyutorskii.aletojio.idling;

import java.util.BitSet;

/**
 * Monitor the occurrence of consecutive unique byte values.
 */
public class UniqRunMonitor extends AbstractRndMonitor {

    private static final int CARDINALITY_BYTE = 1 << Byte.SIZE;
    private static final int MASK_BYTE = (1 << Byte.SIZE) - 1;

    private static final int DEF_GOALLEN = 32;


    private final int goalLen;
    private final BitSet bitMap = new BitSet(CARDINALITY_BYTE);
    private int uniqLen;


    /**
     * Constructor.
     *
     * @param goalArg goal length of unique byte value sequence
     * @throws IllegalArgumentException max must be between 1 and 256
     */
    public UniqRunMonitor(int goalArg) {
        super();

        if (goalArg < 1) {
            throw new IllegalArgumentException();
        }
        if (CARDINALITY_BYTE < goalArg) {
            throw new IllegalArgumentException();
        }

        this.goalLen = goalArg;
        this.bitMap.clear();
        this.uniqLen = 0;

        return;
    }

    /**
     * Constructor.
     *
     * <p>unique bytes goal length is 32.
     */
    public UniqRunMonitor() {
        this(DEF_GOALLEN);
        return;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    protected void resetImpl() {
        this.bitMap.clear();
        this.uniqLen = 0;
        return;
    }

    /**
     * {@inheritDoc}
     *
     * @param iVal {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    protected boolean probeImpl(int iVal) {
        int bVal3 = (iVal >>> 24) & MASK_BYTE;
        int bVal2 = (iVal >>> 16) & MASK_BYTE;
        int bVal1 = (iVal >>>  8) & MASK_BYTE;
        int bVal0 =  iVal         & MASK_BYTE;

        boolean result =
                   probeByte(bVal3)
                || probeByte(bVal2)
                || probeByte(bVal1)
                || probeByte(bVal0);

        return result;
    }

    /**
     * probe byte value.
     *
     * @param bVal byte value
     * @return true if uniq length reaches goal.
     */
    private boolean probeByte(int bVal) {
        boolean collision = this.bitMap.get(bVal);

        boolean result = false;

        if (collision) {
            this.bitMap.clear();
            this.bitMap.set(bVal);
            this.uniqLen = 1;
        } else {
            this.bitMap.set(bVal);
            this.uniqLen++;
            if (this.uniqLen >= this.goalLen) {
                result = true;
            }
        }

        return result;
    }

}

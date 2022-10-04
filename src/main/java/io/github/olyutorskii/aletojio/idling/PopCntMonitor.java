/*
 * License : The MIT License
 * Copyright(c) 2022 Olyutorskii
 */

package io.github.olyutorskii.aletojio.idling;

/**
 * Monitor pop-count of random number sequence.
 *
 * <ul>
 * <li>pop-count of 0x00000000 is 0
 * <li>pop-count of 0xa5a5a5a5 is 16
 * <li>pop-count of 0xffffffff is 32
 * </ul>
 *
 * <p>Pop-count is another name for Hamming-weight.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Hamming_weight">Hamming weight(Wikipedia)</a>
 */
public class PopCntMonitor extends AbstractRndMonitor {

    private final int popCt;


    /**
     * Constructor.
     *
     * @param pop pop counts (must be 0&lt;= and &lt;= 32)
     * @throws IllegalArgumentException pop counts too small or large
     */
    public PopCntMonitor(int pop) throws IllegalArgumentException {
        super();

        if (pop < 0 || Integer.SIZE < pop) {
            throw new IllegalArgumentException();
        }

        this.popCt = pop;

        return;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    protected void resetImpl() {
        assert true;
    }

    /**
     * {@inheritDoc}
     *
     * @param iVal {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    protected boolean probeImpl(int iVal) {
        boolean result =
                Integer.bitCount(iVal) == this.popCt;
        return result;
    }

}

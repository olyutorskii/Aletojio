/*
 * License : The MIT License
 * Copyright(c) 2022 Olyutorskii
 */

package io.github.olyutorskii.aletojio.idling;

/**
 * Abstract {@link RndMonitor} implements.
 */
public abstract class AbstractRndMonitor implements RndMonitor {

    private boolean meet = false;


    /**
     * Constructor.
     */
    protected AbstractRndMonitor() {
        super();
        return;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void reset() {
        this.meet = false;
        resetImpl();
        return;
    }

    /**
     * Implement of {@link #reset()}.
     */
    protected abstract void resetImpl();

    /**
     * {@inheritDoc}
     *
     * @param iVal {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public boolean probe(int iVal) {
        if (this.meet) {
            return true;
        }

        if (probeImpl(iVal)) {
            this.meet = true;
        }

        return this.meet;
    }

    /**
     * Implement of {@link #probe(int)}.
     *
     * @param iVal int value
     * @return true if
     */
    protected abstract boolean probeImpl(int iVal);

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public boolean hasMet() {
        return this.meet;
    }

}

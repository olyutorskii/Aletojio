/*
 * License : The MIT License
 * Copyright(c) 2022 Olyutorskii
 */

package io.github.olyutorskii.aletojio.idling;

/**
 * Monitor whether or not a random number sequence satisfies statistical criteria.
 */
public interface RndMonitor {

    /**
     * Reset statistical status.
     */
    public abstract void reset();

    /**
     * Probe new random value.
     *
     * <p>The statistical state of the past random sequence is recalculated.
     *
     * <p>Once the statistical condition is met,
     * it must continue to return true until {@link #reset()} is called.
     *
     * @param iVal new random value
     * @return true if met statistical condition
     */
    public abstract boolean probe(int iVal);

    /**
     * Inspect statistical condition.
     *
     * <p>Must return false immediately after constructor
     * and {@link #reset()} calls.
     *
     * <p>Once {@link #probe(int)} returns true, must continue to return true
     * until {@link #reset()} called.
     *
     * @return true if met statistical condition.
     */
    public abstract boolean hasMet();

}

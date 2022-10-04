/*
 * License : The MIT License
 * Copyright(c) 2021 Olyutorskii
 */

package io.github.olyutorskii.aletojio.idling;

import io.github.olyutorskii.aletojio.rng.RndInt32;
import java.util.Objects;

/**
 * Random numbers are consumed until statistical characteristics are met.
 *
 * <p>It is convenient to wait until the seed values are confused.
 */
public class RndSkipper {

    private final RndMonitor monitor;


    /**
     * Constructor.
     *
     * @param monitorArg monitor
     * @throws NullPointerException argument is null
     */
    public RndSkipper(RndMonitor monitorArg) {
        super();
        Objects.requireNonNull(monitorArg);
        this.monitor = monitorArg;
        return;
    }


    /**
     * Consume numbers.
     *
     * @param rnd random generator
     * @return consumed numbers
     * @throws NullPointerException argument is null
     */
    public long skip(RndInt32 rnd) {
        Objects.requireNonNull(rnd);

        long counter = 0L;

        this.monitor.reset();

        boolean passed;
        do {
            int iVal = rnd.nextInt32();
            counter++;
            passed = this.monitor.probe(iVal);
        } while (!passed);

        return counter;
    }

}

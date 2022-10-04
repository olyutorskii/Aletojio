/*
 * License : The MIT License
 * Copyright(c) 2022 Olyutorskii
 */

package io.github.olyutorskii.aletojio.idling;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * Bunch of monitor.
 *
 * <p>Consolidate multiple monitors into one monitor.
 *
 * <p>Monitor all passed monitors for fulfillment of the condition.
 *
 * <p>If even one of the monitors does not meet the conditions, it is reflected as is.
 */
public class BunchMonitor extends AbstractRndMonitor {

    private final List<RndMonitor> monitors;


    /**
     * Constructor.
     *
     * @param monsArg monitors
     * @throws NullPointerException argument is null
     */
    public BunchMonitor(RndMonitor... monsArg) {
        this(Arrays.asList(monsArg));
        return;
    }

    /**
     * Constructor.
     *
     * @param monsArg monitors
     * @throws NullPointerException argument or any monitor are null
     */
    public BunchMonitor(Collection<RndMonitor> monsArg) {
        super();

        Objects.requireNonNull(monsArg);
        for (RndMonitor monitor : monsArg) {
            Objects.requireNonNull(monitor);
        }

        this.monitors = new ArrayList<>(monsArg);

        return;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    protected void resetImpl() {
        for (RndMonitor monitor : this.monitors) {
            monitor.reset();
        }
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
        boolean result = true;

        for (RndMonitor monitor : this.monitors) {
            if (monitor.hasMet()) continue;

            if (!monitor.probe(iVal)) {
                result = false;
            }
        }

        return result;
    }

}

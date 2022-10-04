/*
 * License : The MIT License
 * Copyright(c) 2022 olyutorskii
 */

package io.github.olyutorskii.aletojio.shrink;

import io.github.olyutorskii.aletojio.rng.RndInt32;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

/**
 * Mixing entropy of N random number streams by XOR.
 *
 * <p>Random number output throughput decreases {@code 1/N}.
 */
@SuppressWarnings("serial")
public class XorMixer implements RndInt32 {

    private Collection<RndInt32> rnds;


    /**
     * Constructor.
     *
     * @param rndColl random streams
     * @throws NullPointerException argument or stream is null
     * @throws IllegalArgumentException no stream
     */
    public XorMixer(Collection<RndInt32> rndColl)
            throws NullPointerException, IllegalArgumentException {
        super();
        Objects.requireNonNull(rndColl);

        if (rndColl.isEmpty()) {
            throw new IllegalArgumentException();
        }

        for (RndInt32 rnd : rndColl) {
            Objects.requireNonNull(rnd);
        }

        this.rnds = new ArrayList<>(rndColl);

        return;
    }

    /**
     * Constructor.
     *
     * @param rndVec random streams
     * @throws NullPointerException argument is null
     * @throws IllegalArgumentException no stream
     */
    public XorMixer(RndInt32... rndVec)
            throws NullPointerException, IllegalArgumentException {
        this(Arrays.asList(rndVec));
        return;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public int nextInt32() {
        int result = 0x00;

        for (RndInt32 rnd : this.rnds) {
            result ^= rnd.nextInt32();
        }

        return result;
    }

}

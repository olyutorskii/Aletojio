/*
 * License : The MIT License
 * Copyright(c) 2022 Olyutorskii
 */

package io.github.olyutorskii.aletojio.rng;

import io.github.olyutorskii.aletojio.BitPool;
import java.util.Random;

/**
 * Random generator exchange adapter.
 *
 * <p>Designed to conserve as much random source entropy as possible.
 *
 * <p>Entropy is consumed first from MSB.
 *
 * <p>512 bits buffer is provided.
 *
 * <p>TODO: supporting {@literal java.util.random.RandomGenerator} (JDK17 or later)
 * and {@literal org.apache.commons.rng.UniformRandomProvider}
 */
@SuppressWarnings("serial")
public class RndAdapter
        extends Random
        implements RndInt32, RndInt64, RndInt31 {

    private final transient RndSource source;

    private final transient RndInt31 delRndInt31;
    private final transient RndInt32 delRndInt32;
    private final transient RndInt64 delRndInt64;
    private final transient Random   delRandom;

    private final transient BitPool bitPool = new BitPool();

    private final transient int inUnit;

    private final transient boolean initDone;


    /**
     * Constructor.
     *
     * @param rndSrc random generator
     * @throws NullPointerException null argument
     */
    public RndAdapter(RndInt32 rndSrc) throws NullPointerException {
        this(rndSrc, null, null, null);
        return;
    }

    /**
     * Constructor.
     *
     * @param rndSrc random generator
     * @throws NullPointerException null argument
     */
    public RndAdapter(RndInt64 rndSrc) throws NullPointerException {
        this(null, rndSrc, null, null);
        return;
    }

    /**
     * Constructor.
     *
     * @param rndSrc random generator
     * @throws NullPointerException null argument
     */
    public RndAdapter(RndInt31 rndSrc) throws NullPointerException {
        this(null, null, rndSrc, null);
        return;
    }

    /**
     * Constructor.
     *
     * @param rndSrc random generator
     * @throws NullPointerException null argument
     */
    public RndAdapter(Random rndSrc) throws NullPointerException {
        this(null, null, null, rndSrc);
        return;
    }

    /**
     * Constructor.
     *
     * <p>Any generator argument must be non-null.
     *
     * @param rndInt32 random generator
     * @param rndInt64 random generator
     * @param rndInt31 random generator
     * @param rnd random generator
     * @throws NullPointerException all argument is null.
     */
    private RndAdapter(
            RndInt32 rndInt32,
            RndInt64 rndInt64,
            RndInt31 rndInt31,
            Random rnd
    ) throws NullPointerException {
        super();

        RndSource src;
        int unit;
        if (rndInt32 != null) {
            src = RndSource.R32;
            unit = 32;
        } else if (rndInt64 != null) {
            src = RndSource.R64;
            unit = 64;
        } else if (rndInt31 != null) {
            src = RndSource.R31;
            unit = 31;
        } else if (rnd      != null) {
            src = RndSource.JRND;
            unit = 32;
        } else {
            throw new NullPointerException();
        }
        this.source = src;
        this.inUnit = unit;

        this.delRndInt32 = rndInt32;
        this.delRndInt64 = rndInt64;
        this.delRndInt31 = rndInt31;
        this.delRandom   = rnd;

        this.initDone = true;

        assert this.bitPool.capacity() >= Long.SIZE * 2;

        return;
    }


    /**
     * Seed change is not supported.
     *
     * <p>* However, it is allowed only during the initial constructor.
     *
     * @param seed not supported
     * @throws UnsupportedOperationException always
     */
    @Override
    public void setSeed(long seed) throws UnsupportedOperationException {
        if (this.initDone) {
            throw new UnsupportedOperationException();
        }
        return;
    }

    /**
     * If not enough, fill bit-pool.
     *
     * @param minSz minimum pool size
     */
    private void fillPool(int minSz) {
        if (this.bitPool.size() >= minSz) return;

        while (this.bitPool.remaining() >= this.inUnit) {
            int iVal;
            long lVal;

            switch (this.source) {
            case R32:
                iVal = this.delRndInt32.nextInt32();
                this.bitPool.pushInt(iVal);
                break;
            case R64:
                lVal = this.delRndInt64.nextInt64();
                this.bitPool.pushLong(lVal);
                break;
            case R31:
                iVal = this.delRndInt31.nextInt31();
                this.bitPool.pushInt(iVal, 31);
                break;
            case JRND:
                iVal = this.delRandom.nextInt();
                this.bitPool.pushInt(iVal);
                break;
            default:
                break;
            }
        }

        return;
    }

    /**
     * Return next random number as 1bit boolean.
     *
     * <p>Unlike {@link java.util.Random#nextBoolean()} implement,
     * any entropy from random source is not discarded.
     *
     * @return random number
     */
    public boolean nextBit() {
        fillPool(1);
        boolean result = this.bitPool.chopBoolean();
        return result;
    }

    /**
     * Return next random number as 8bit byte.
     *
     * <p>Unlike {@link java.util.Random#nextBytes(byte[])} implement,
     * any entropy from random source is not discarded.
     *
     * @return random number
     */
    public byte nextByte() {
        fillPool(Byte.SIZE);
        byte result = this.bitPool.chopByte();
        return result;
    }

    /**
     * {@inheritDoc}
     *
     * <p>Entropy obtained from random source is returned
     * without entropy missing.
     *
     * @return {@inheritDoc}
     */
    @Override
    public int nextInt31() {
        fillPool(31);
        int result = this.bitPool.chopInt(31);
        return result;
    }

    /**
     * {@inheritDoc}
     *
     * <p>Entropy obtained from random source is returned
     * without entropy missing.
     *
     * @return {@inheritDoc}
     */
    @Override
    public int nextInt32() {
        fillPool(Integer.SIZE);
        int result = this.bitPool.chopInt();
        return result;
    }

    /**
     * {@inheritDoc}
     *
     * <p>Entropy obtained from random source is returned
     * without entropy missing.
     *
     * @return {@inheritDoc}
     */
    @Override
    public long nextInt64() {
        fillPool(Long.SIZE);
        long result = this.bitPool.chopLong();
        return result;
    }

    /**
     * Generates the next pseudorandom number.
     * Subclasses should override this,
     * as this is used by {@link java.util.Random} original implementations.
     *
     * <p>* The original {@link java.util.Random} implementation
     * often discards higher or lower bits of this return value.
     *
     * <p>Entropy obtained from random source is returned
     * without entropy missing.
     *
     * @param bits {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    protected int next(int bits) {
        fillPool(bits);
        int result = this.bitPool.chopInt(bits);
        return result;
    }

    /**
     * Random sources.
     */
    private static enum RndSource {
        /** {@link RndInt32}. */
        R32,
        /** {@link RndInt64}. */
        R64,
        /** {@link RndInt31}. */
        R31,
        /** {@link java.util.Random}. */
        JRND,
    }

}

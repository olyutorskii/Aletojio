/*
 * License : The MIT License
 * Copyright(c) 2021 Olyutorskii
 */

package io.github.olyutorskii.aletojio.rng.fibo;

import io.github.olyutorskii.aletojio.rng.RndInt32;
import java.util.Objects;

/**
 * Lagged Fibonacci Pseudo Random Generator(LFG).
 *
 * <p>Recurrence relation sequences : {@code X(n) = X(n-j) + X(n-k)}
 *
 * <p>If generation(1,2) given, it generates the so-called Fibonacchi numbers sequence.
 *
 * <p>Typical generation pairs.
 * <ul>
 * <li>(1,2)
 * <li>(7,10)
 * <li>(24,55)
 * <li>(353,521)
 * <li>(861,1279)
 * <li>(9739,23209)
 * </ul>
 *
 * <p>Seed values are mixed well if the number of generations(k,j) are prime to each other.
 *
 * <p>If polynomial: {@code y = x**k + x**j + 1} is primitive over GF(2),
 * generator has more long period.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Lagged_Fibonacci_generator">
 * Lagged_Fibonacci_generator (Wikipedia)
 * </a>
 */
public class LagFib implements RndInt32 {

    private static final int INITIAL_ODD = 0x01;

    private static final String ERRMSG_POSARG  = "Generation must be positive";
    private static final String ERRMSG_ORDER   = "Older generation must be greater than younger";
    private static final String ERRMSG_ODDSEED = "At least one of the seeds must be odd number";
    private static final String ERRMSG_SEEDLEN = "Unmatch seeds length";


    private final int[] recRing;
    private final int recSize;

    private int recentIdx;

    private final int genYng;
    private final int genOld;


    /**
     * Constructor.
     *
     * <p>Number of generation must be greater than 0.
     *
     * <p>Older generation number must be greater than younger.
     *
     * @param genYounger younger past generation X(n-j)
     * @param genOlder older past generation X(n-k)
     * @throws IndexOutOfBoundsException Given generation is out of range
     * @throws IllegalArgumentException older generation is not greater than youger generation
     */
    public LagFib(int genYounger, int genOlder)
            throws IndexOutOfBoundsException, IllegalArgumentException {
        super();

        if (genYounger < 1) {
            throw new IndexOutOfBoundsException(ERRMSG_POSARG);
        }
        if (genYounger >= genOlder) {
            throw new IllegalArgumentException(ERRMSG_ORDER);
        }

        this.genYng = genYounger;
        this.genOld = genOlder;

        this.recRing = new int[this.genOld];
        this.recSize = this.recRing.length;

        this.recentIdx = 0;

        int lastIdx = this.recSize - 1;
        this.recRing[lastIdx] = INITIAL_ODD;

        assert hasOdd(this.recRing);

        return;
    }


    /**
     * Check whether int array contains odd number or not.
     *
     * @param iVec int array
     * @return true if odd number exists.
     * @throws NullPointerException argument is null
     */
    public static boolean hasOdd(int... iVec) throws NullPointerException {
        Objects.requireNonNull(iVec);

        for (int iSeed : iVec) {
            boolean isOdd = (iSeed & 0b1) != 0;
            if (isOdd) {
                return true;
            }
        }

        return false;
    }


    /**
     * Check seeds.
     *
     * @param initVec seeds
     * @throws IllegalArgumentException illegal seeds
     */
    protected void checkSeeds(int... initVec) throws IllegalArgumentException {
        if (!hasOdd(initVec)) {
            throw new IllegalArgumentException(ERRMSG_ODDSEED);
        }
        return;
    }

    /**
     * Set seed.
     *
     * <p>At least one of the seeds must be odd number.
     *
     * <p>Seeds must be old-generation length array.
     *
     * @param initVec seed array
     * @throws NullPointerException argument is null
     * @throws IndexOutOfBoundsException unmatch seeds length
     * @throws IllegalArgumentException odd number is not be found
     */
    public void setSeed(int... initVec)
            throws NullPointerException, IndexOutOfBoundsException, IllegalArgumentException {
        Objects.requireNonNull(initVec);

        if (initVec.length != this.recSize) {
            throw new IndexOutOfBoundsException(ERRMSG_SEEDLEN);
        }

        checkSeeds(initVec);

        for (int ct = 0; ct < initVec.length; ct++) {
            int idx = this.recentIdx + ct;
            if (idx >= this.recSize) {
                idx -= this.recSize;
            }
            this.recRing[idx] = initVec[ct];
        }

        return;
    }

    /**
     * Return generational past value.
     *
     * @param genNo generation number
     * @return past value
     */
    private int genValue(int genNo) {
        assert 1 <= genNo;
        assert genNo <= this.recSize;

        int rawIdx = this.recentIdx + genNo - 1;
        if (rawIdx >= this.recSize) {
            rawIdx -= this.recSize;
        }

        int result = this.recRing[rawIdx];
        return result;
    }

    /**
     * Binary operation between 2 taps.
     *
     * <p>Just adding.
     *
     * @param tap1 tap 1st
     * @param tap2 tap 2nd
     * @return result
     */
    protected int binOp(int tap1, int tap2) {
        int result = tap1 + tap2;
        return result;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public int nextInt32() {
        int valYng = genValue(this.genYng);
        int valOld = genValue(this.genOld);

        int result = binOp(valYng, valOld);

        this.recentIdx--;
        if (this.recentIdx < 0) {
            int lastIdx = this.recSize - 1;
            this.recentIdx = lastIdx;
        }
        this.recRing[this.recentIdx] = result;

        return result;
    }

}

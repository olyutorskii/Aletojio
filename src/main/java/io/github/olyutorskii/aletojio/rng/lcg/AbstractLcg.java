/*
 * License : The MIT License
 * Copyright(c) 2022 olyutorskii
 */

package io.github.olyutorskii.aletojio.rng.lcg;

/**
 * Common implementation of Linear congruential generator(LCG).
 *
 * <p>LCG is a commonly used random number generator in the past.
 *
 * <p>Recurrence relation sequences : {@code X(n+1) = (X(n) * Mul + Inc) mod Mod}
 *
 * <ul>
 * <li>Mul : Multiplier
 * <li>Inc : Increment
 * <li>Mod : Modulus
 * </ul>
 */
public abstract class AbstractLcg {

    /** 31bit bitmask. */
    protected static final long MASK_B31 = (1L << 31) - 1L;
    /** 32bit bitmask. */
    protected static final long MASK_B32 = (1L << 32) - 1L;

    private static final long DEF_SEED = 0x01L;

    private static final String ERRMSG_MUL = "multiplier must be 1 or greater";
    private static final String ERRMSG_INC = "increment must be 0 or greater";
    private static final String ERRMSG_MOD = "modulus must be 2 or greater";
    private static final String ERRMSG_SEED = "seed must not be negative";


    private long seed;

    private final long multiplier;
    private final long increment;
    private final long modulus;

    // if not 0L, use bitmask operator(&) instead of modulo oeprator(%)
    private final long modMask;


    /**
     * Constructor.
     *
     * <ul>
     * <li>Multiplier must be 1 or greatrer.
     * <li>Increment must be 0 or greatrer.
     * <li>Modulus must be 2 or greater.
     * </ul>
     *
     * @param mulArg multiplier
     * @param incArg increment
     * @param modArg modulus
     * @throws IllegalArgumentException illegal argument
     */
    protected AbstractLcg(long mulArg, long incArg, long modArg)
            throws IllegalArgumentException {
        super();

        if (mulArg < 1) {
            throw new IllegalArgumentException(ERRMSG_MUL);
        }

        if (incArg < 0) {
            throw new IllegalArgumentException(ERRMSG_INC);
        }

        // modulus must be greater than either multiplier or increment
        if (modArg < 2) {
            throw new IllegalArgumentException(ERRMSG_MOD);
        }

        this.multiplier = mulArg;
        this.increment = incArg;
        this.modulus = modArg;

        this.modMask = calcModMask(this.modulus);

        this.seed = DEF_SEED;

        return;
    }


    /**
     * Calculate modulus mask if modulus parameter is 2**N.
     *
     * <ul>
     * <li>If 0b100, return 0b11.
     * <li>If 0b101, return 0.
     * <li>If 0b10000, return 0b1111.
     * <li>If 0b10101, return 0.
     * <li>If 1(=2**0), return 0.
     * <li>If 0, return 0.
     * </ul>
     *
     * @param mod modulus parameter
     * @return modulus bitmask
     */
    static long calcModMask(long mod) {
        if (mod == 0L) return 0L;

        long dec = mod - 1L;
        boolean pow2Mod = (mod & dec) == 0L;

        long newMask;
        if (pow2Mod) {
            newMask = dec;
        } else {
            newMask = 0L;
        }

        return newMask;
    }


    /**
     * Return next random number as int.
     *
     * <p>Negative value returned if (and only) result bits are 32.
     *
     * @return random number
     */
    protected int nextIntImpl() {
        this.seed = nextSeed();
        int result = seedToResult();
        return result;
    }

    /**
     * Calculate next seed value from current seed value.
     *
     * @return next seed value
     * @throws IllegalStateException seed overflow
     */
    protected long nextSeed() throws IllegalStateException {
        long dividend = this.seed * this.multiplier + this.increment;

        long nextSeed;
        if (this.modMask == 0) {
            if (dividend < 0) {
                throw new IllegalStateException();
            }
            nextSeed = dividend % this.modulus;
        } else {
            nextSeed = dividend & this.modMask;
        }

        return nextSeed;
    }

    /**
     * Calculate result number from seed.
     *
     * @return result number
     */
    protected abstract int seedToResult();

    /**
     * Set new seed value.
     *
     * @param seedArg new seed value
     * @throws IllegalArgumentException seed too small
     */
    public void setSeed(long seedArg) throws IllegalArgumentException {
        if (seedArg < 0) {
            throw new IllegalArgumentException(ERRMSG_SEED);
        }

        this.seed = seedArg;

        return;
    }

    /**
     * Get seed value.
     *
     * @return seed value
     */
    public long getSeed() {
        return this.seed;
    }

}

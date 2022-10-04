/*
 * License : The MIT License
 * Copyright(c) 2022 olyutorskii
 */

package io.github.olyutorskii.aletojio.rng.lcg;

/**
 * Factory of Linear congruential generator (LCG) implementations.
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
 *
 * <p>Provides a random number generator
 * compatible with the following LCGs.
 *
 * <ul>
 * <li>RANDU :   IBM Scientific Subroutine Library for IBM System/360
 * <li>MINSTD0 : C++11's minstd_rand0
 * <li>MINSTD :  C++11's minstd_rand
 * <li>GLIBC :   glibc rand()
 * <li>LRAND48 : glibc lrand48()
 * <li>MRAND48 : glibc mrand48()
 * </ul>
 *
 * <p>mrand48() is compatible with {@link java.util.Random}.
 * But seed of java.util.Random is scrambled. Try {@link #seedScrambler4M48(long)}
 *
 * @see java.util.Random
 * @see <a href="https://en.wikipedia.org/wiki/Linear_congruential_generator">
 * Linear congruential generator (Wikipedia)
 * </a>
 */
public final class LcgFactory {

    private static final long RANDU_MUL   =           65539L;      // 6544th prime number
    private static final long RANDU_INC   =               0L;
    private static final long RANDU_MOD   =      2147483648L;

    private static final long MINSTD0_MUL =           16807L;

    private static final long MINSTD_MUL  =           48271L;      // 4968th prime number
    private static final long MINSTD_INC  =               0L;
    private static final long MINSTD_MOD  =      2147483647L;      // 8th Mersenne prime number

    private static final long GLIBC_MUL   =      1103515245L;      // prime number
    private static final long GLIBC_INC   =           12345L;
    private static final long GLIBC_MOD   =      2147483648L;

    private static final long MRAND48_MUL =     25214903917L;      // prime number
    private static final long MRAND48_INC =              11L;
    private static final long MRAND48_MOD = 281474976710656L;

    private static final long RANDU_INITSEED   =              1L;
    private static final long MINSTD_INITSEED  =              1L;
    private static final long GLIBC_INITSEED   =              1L;
    private static final long MRAND48_INITSEED = 20017429951246L;

    private static final long MRAND48_MASK = MRAND48_MOD - 1L;

    static {
        assert RANDU_MUL   == 0x00010003L;
        assert MINSTD0_MUL == 7L * 7L * 7L * 7L * 7L;
        assert MINSTD_MUL  == 0x0000bc8fL;
        assert GLIBC_MUL   == 0x41c64e6dL;
        assert MRAND48_MUL == 0x05deece66dL;

        assert RANDU_MOD   ==  1L << 31;
        assert MINSTD_MOD  == (1L << 31) - 1L;
        assert GLIBC_MOD   ==  1L << 31;
        assert MRAND48_MOD ==  1L << 48;

        assert MRAND48_INITSEED == 0x1234abcd330eL;

        assert MRAND48_MASK == 0xffff_ffff_ffffL;

        new LcgFactory().hashCode();
    }


    /**
     * Hidden constructor.
     */
    private LcgFactory() {
    }


    /**
     * Create RANDU compatible random number generator.
     *
     * <p>RANDU was included in IBM Scientific Subroutine Library for IBM System/360
     *
     * <p>RANDU generates 31bit output without sign.
     *
     * @return random number generator
     * @see <a href="https://en.wikipedia.org/wiki/RANDU">RANDU (Wikipedia)</a>
     */
    public static LcgRndInt31 createRandu() {
        LcgRndInt31 result;
        result = new LcgRndInt31(RANDU_MUL, RANDU_INC, RANDU_MOD);
        result.setSeed(RANDU_INITSEED);
        return result;
    }

    /**
     * Create minstd_rand0() compatible random number generator.
     *
     * <p>minstd_rand0() is used in C++11.
     *
     * <p>minstd_rand() is better than minstd_rand0(),
     *
     * <p>minstd_rand0() generates 31bit output without sign.
     *
     * @return random number generator
     * @see <a href="https://en.wikipedia.org/wiki/Lehmer_random_number_generator">
     * Lehmer random number generator (Wikipedia)
     * </a>
     */
    public static LcgRndInt31 createMinStd0() {
        LcgRndInt31 result;
        result = new LcgRndInt31(MINSTD0_MUL, MINSTD_INC, MINSTD_MOD);
        result.setSeed(MINSTD_INITSEED);
        return result;
    }

    /**
     * Create minstd_rand() compatible random number generator.
     *
     * <p>minstd_rand() is used in C++11.
     *
     * <p>minstd_rand() is better than minstd_rand0(),
     *
     * <p>minstd_rand() generates 31bit output without sign.
     *
     * @return random number generator
     * @see <a href="https://en.wikipedia.org/wiki/Lehmer_random_number_generator">
     * Lehmer random number generator (Wikipedia)
     * </a>
     */
    public static LcgRndInt31 createMinStd() {
        LcgRndInt31 result;
        result = new LcgRndInt31(MINSTD_MUL, MINSTD_INC, MINSTD_MOD);
        result.setSeed(MINSTD_INITSEED);
        return result;
    }

    /**
     * Create glibc rand() compatible random number generator.
     *
     * <p>rand() is used in glibc library.
     *
     * <p>rand() generates 31bit output without sign.
     *
     * @return random number generator
     */
    public static LcgRndInt31 createGlibc() {
        LcgRndInt31 result;
        result = new LcgRndInt31(GLIBC_MUL, GLIBC_INC, GLIBC_MOD);
        result.setSeed(GLIBC_INITSEED);
        return result;
    }

    /**
     * Create lrand48() compatible random number generator.
     *
     * <p>lrand48() is used in Un*x common C-library.
     *
     * <p>lrand48() generates 31bit output without sign.
     * result(31bit) reflects seed[17:47]
     *
     * @return random number generator
     */
    public static LcgRndInt31 createLrand48() {
        LcgRndInt31 result;
        result = new LcgRndInt31(MRAND48_MUL, MRAND48_INC, MRAND48_MOD) {
            @Override
            protected int seedToResult() {
                long lVal = getSeed() >>> 17;
                lVal &= MASK_B31;
                int result = (int) lVal;
                return result;
            }
        };

        result.setSeed(MRAND48_INITSEED);
        return result;
    }

    /**
     * Create mrand48() compatible random generator.
     *
     * <p>mrand48() is used in Un*x common C-library.
     *
     * <p>mrand48() generates 32bit output.
     *
     * @return random number generator
     */
    public static LcgRndInt32 createMrand48() {
        LcgRndInt32 result;
        result = new LcgRndInt32(MRAND48_MUL, MRAND48_INC, MRAND48_MOD);
        result.setSeed(MRAND48_INITSEED);
        return result;
    }

    /**
     * Seed scrambler for mrand48().
     *
     * <p>Seed of {@link java.util.Random#setSeed(long)} will be converted to seed of mrand48().
     *
     * @param seedArg seed of {@link java.util.Random}
     * @return seed of mrand48()
     *
     * @see java.util.Random#setSeed(long)
     */
    public static long seedScrambler4M48(long seedArg) {
        long result = (seedArg ^ MRAND48_MUL) & MRAND48_MASK;
        return result;
    }

}

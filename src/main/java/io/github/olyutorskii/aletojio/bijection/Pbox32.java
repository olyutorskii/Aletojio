/*
 * License : The MIT License
 * Copyright(c) 2022 Olyutorskii
 */

package io.github.olyutorskii.aletojio.bijection;

import io.github.olyutorskii.aletojio.rng.RndInt32;
import java.util.Arrays;
import java.util.Objects;

/**
 * P-box diffusion by bit permutation.
 *
 * <p>Takes a 32-bit integer as input and outputs a diffused 32-bit integer.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Permutation_box">
 * Permutation box (Wikipedia)
 * </a>
 * @see <a href="https://en.wikipedia.org/wiki/Confusion_and_diffusion">
 * Confusion_and_diffusion (Wikipedia)
 * </a>
 * @see <a href="https://en.wikipedia.org/wiki/Substitution%E2%80%93permutation_network">
 * Substitution-permutation network (SPN) (Wikipedia)
 * </a>
 */
public class Pbox32 implements RndInt32 {

    private static final String ERRMSG_LENGTH = "P-box table must be 32 entry";
    private static final String ERRMSG_BIJECTION = "P-box table must be bijection";

    private static final int START = 17;
    private static final int STEP = 19;

    private static final int[] DEF_PERM;

    static {
        DEF_PERM = new int[Integer.SIZE];
        for (int ct = 0; ct < Integer.SIZE; ct++) {
            DEF_PERM[ct] = (START + STEP * ct) % Integer.SIZE;
        }
    }


    private final RndInt32 rnd;
    private final int[] permute = new int[Integer.SIZE];


    /**
     * Constructor.
     *
     * <p>Default P-box tabel is used. {@code p[N] = (17 + 19 * N) mod 32}
     *
     * @param rndArg random source
     */
    public Pbox32(RndInt32 rndArg)
            throws NullPointerException, IllegalArgumentException {
        this(rndArg, DEF_PERM);
        return;
    }

    /**
     * Constructor.
     *
     * <p>P-box table length must be 32.
     *
     * <p>P-box table must be bijection. (minimal perfect hash function)
     *
     * <p>{@code (permuteArg[31] = 7)} means copying bit from bit-31 to bit-7.
     *
     * @param rndArg random source
     * @param permuteArg P-box bit-permutation table
     * @throws NullPointerException null argument
     * @throws IllegalArgumentException invalid P-box
     */
    public Pbox32(RndInt32 rndArg, int[] permuteArg)
            throws NullPointerException, IllegalArgumentException {
        super();
        Objects.requireNonNull(rndArg);
        Objects.requireNonNull(permuteArg);

        this.rnd = rndArg;

        checkPbox(permuteArg);
        System.arraycopy(permuteArg, 0, this.permute, 0, Integer.SIZE);

        return;
    }


    /**
     * Map int value with default P-box table.
     *
     * <p>Default P-box tabel is used. {@code p[N] = (17 + 19 * N) mod 32}
     *
     * @param iVal int value
     * @return mapped value
     */
    public static int mapPermutation(int iVal) {
        int result = mapTable(iVal, DEF_PERM);
        return result;
    }

    /**
     * Map int value with specified P-box table.
     *
     * <p>There is no bijection check.
     *
     * @param iVal int value
     * @param table P-box table
     * @return mapped value
     * @throws NullPointerException table is null
     */
    public static int mapTable(int iVal, int[] table) {
        Objects.requireNonNull(table);

        int result = 0;

        for (int idx = 0; idx < Integer.SIZE; idx++) {
            int bitVal = (iVal >>> idx) & 0b1;
            int toPos = table[idx];
            result |= bitVal << toPos;
        }

        return result;
    }

    /**
     * Check P-box table.
     *
     * <p>P-box table length must be 32.
     *
     * <p>P-box table must be bijection. (minimal perfect hash function)
     *
     * <p>{@code (permuteArg[31] = 7)} means copying bit from bit-31 to bit-7.
     *
     * @param permuteArg P-box table
     * @throws NullPointerException null argument
     * @throws IllegalArgumentException size unmatch or not bijection detected
     */
    public static void checkPbox(int[] permuteArg)
            throws NullPointerException, IllegalArgumentException {
        Objects.requireNonNull(permuteArg);
        if (permuteArg.length != Integer.SIZE) {
            throw new IllegalArgumentException(ERRMSG_LENGTH);
        }

        // checking minimal perfect hash function or not
        int[] sorted = Arrays.copyOf(permuteArg, Integer.SIZE);
        Arrays.sort(sorted);
        for (int src = 0; src < Integer.SIZE; src++) {
            int dst = sorted[src];
            if (dst != src) {
                throw new IllegalArgumentException(ERRMSG_BIJECTION);
            }
        }

        return;
    }


    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public int nextInt32() {
        int iVal = this.rnd.nextInt32();
        int result = mapTable(iVal, this.permute);
        return result;
    }

}

/*
 * License : The MIT License
 * Copyright(c) 2021 Olyutorskii
 */

package io.github.olyutorskii.aletojio.rng.fibo;

import io.github.olyutorskii.aletojio.rng.RndInt32;
import java.util.Objects;

/**
 * Fibonacci Linear-feedback shift register (LFSR) Pseudo Random Generator.
 *
 * <p>A 32-bit length shift register is provided.
 * Any number of taps can be specified.
 *
 * <p>The shift operation is performed 32 times to obtain a 32-bit random integer from MSB to LSB.
 *
 * <p>For LFSR to have a period of maximum length,
 * at least the conditions
 * "number of taps is even" and "Set of taps is setwise co-prime"
 * must be satisfied.
 * (but not sufficient)
 *
 * <p>If polynomial: {@code [x**(tapN) + x**(tapN-1) + ... + x**(tap1) + 1]} is primitive over GF(2),
 * LFSR has maximum long period.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Linear-feedback_shift_register">
 * Linear-feedback shift register (Wikipedia)
 * </a>
 */
public class LfShiftReg implements RndInt32 {

    /** Default seed value. */
    public static final int DEF_SEED = 0b1;

    private static final String MSG_ILLSEED   = "Seed must be non-zero";
    private static final String MSG_ILLTAPPOS = "Illegal tap position";


    private final int tapMask;

    private int seed;


    /**
     * Constructor.
     *
     * <p>Tap position numbers starts from 1 to 32.
     * Value (1) points LSB of LFSR register.
     * Value (32) points MSB of LFSR register.
     *
     * <p>For LFSR to have a period of maximum length,
     * at least the conditions
     * "number of taps is even" and "Set of taps is setwise co-prime"
     * must be satisfied.
     * (but not sufficient)
     *
     * <p>If polynomial: {@code [x**(tapN) + x**(tapN-1) + ... + x**(tap1) + 1]}
     * is primitive over GF(2),
     * LFSR has maximum long period.
     *
     * <P>Taps example : (32, 31, 30, 10), (32, 31, 29, 1), (32, 25, 17, 7), etc.
     *
     * @param taps taps in register
     * @throws NullPointerException taps is null.
     * @throws IllegalArgumentException illegal tap position
     * @see <a href="https://en.wikipedia.org/wiki/Linear-feedback_shift_register">
     * Linear-feedback shift register (Wikipedia)
     * </a>
     */
    public LfShiftReg(int... taps)
            throws NullPointerException, IllegalArgumentException {
        super();
        this.tapMask = taps2Mask(taps);
        this.seed = DEF_SEED;
        return;
    }

    /**
     * Constructor.
     *
     * <p>Default taps are <code> { 32, 31, 30, 10 } </code>
     */
    public LfShiftReg() {
        this(32, 31, 30, 10);
        return;
    }


    /**
     * Convert from LFSR-taps notation to bitMask.
     *
     * <p>Tap position numbers starts from 1 to 32.
     * Value (1) points LSB of LFSR register.
     * Value (32) points MSB of LFSR register.
     *
     * @param taps Tap numbers array. Order is ignored. Duplications are ignored.
     * @return Bit mask in which tapped bits are set.
     * @throws IllegalArgumentException illegal tap position
     * @throws NullPointerException taps is null.
     */
    protected static int taps2Mask(int... taps)
            throws IllegalArgumentException, NullPointerException {
        Objects.requireNonNull(taps);

        int result = 0b0;

        for (int tap : taps) {
            if (tap < 1 || Integer.SIZE < tap) {
                throw new IllegalArgumentException(MSG_ILLTAPPOS);
            }

            int bitPos = tap - 1;
            int tapBit = 0b1 << bitPos;

            result |= tapBit;
        }

        return result;
    }

    /**
     * XOR whole bit sequence.
     *
     * <p>{@code result = b0 xor b1 xor ... xor b31}
     *
     * <p>Equivalent to <code>[ Integer.bitCount(i) &amp; 1 ]</code>
     * or ODD Parity.
     *
     * <p>Yes, this is part of linear map function in boolean algebra.
     *
     * @param iVal bit pattern
     * @return XOR result (1 or 0)
     */
    public static int xorBitSeq(int iVal) {
        int b32 = iVal;

        b32 ^= b32 >>> 16;    // 1 << 4
        b32 ^= b32 >>>  8;    // 1 << 3
        b32 ^= b32 >>>  4;    // 1 << 2
        b32 ^= b32 >>>  2;    // 1 << 1
        b32 ^= b32 >>>  1;    // 1 << 0

        int result = b32 & 0b1;
        return result;
    }


    /**
     * {@inheritDoc}
     *
     * <p>The 32bits obtained by repeating the register-shift 32times are returned,
     * packed sequentially starting from MSB.
     *
     * @return {@inheritDoc}
     */
    @Override
    public int nextInt32() {
        int newSeed = this.seed;

        for (int ct = 0; ct < Integer.SIZE; ct++) {
            int tappedSeed = newSeed & this.tapMask;
            int xoredBit = xorBitSeq(tappedSeed);    // 0 or 1
            newSeed =  (newSeed << 1) | xoredBit;
        }

        this.seed = newSeed;
        int result = newSeed;

        return result;
    }


    /**
     * Set seed.
     *
     * <p>Seed value must not be 0.
     *
     * <p>It will take some time before the extreme bias in seed value pop-counts is corrected.
     *
     * @param newSeed seed value
     * @throws IllegalArgumentException Seed must be non-zero
     */
    public void setSeed(int newSeed) throws IllegalArgumentException {
        if (newSeed == 0b0) {
            throw new IllegalArgumentException(MSG_ILLSEED);
        }
        this.seed = newSeed;
        return;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(32 + 8 - 1);

        for (int ct = 0; ct < Integer.SIZE; ct++) {
            if (ct % 4 == 0 && sb.length() != 0) {
                sb.append('-');
            }

            int bSft = Integer.SIZE - ct - 1;
            int bit = (this.seed    >>> bSft) & 0b1;
            int tap = (this.tapMask >>> bSft) & 0b1;

            char ch;
            if (bit == 0) {
                if (tap == 0) {
                    ch = 'o';
                } else {
                    ch = 'O';
                }
            } else {
                if (tap == 0) {
                    ch = 'i';
                } else {
                    ch = 'I';
                }
            }

            sb.append(ch);
        }

        String result = sb.toString();
        return result;
    }

}

/*
 * License : The MIT License
 * Copyright(c) 2022 Olyutorskii
 */

package io.github.olyutorskii.aletojio.rng.mwc;

import io.github.olyutorskii.aletojio.rng.RndInt32;
import java.util.Objects;

/**
 * lag-r Multiply-with-carry random number generator.
 *
 * <p>Random sequence X(n) is derived as follows with parameter a(multiplier), b(divisor).
 * <ul>
 * <li>{@code T(n) = a * X(n-r) + C(n-1)}
 * <li>{@code C(n) = T(n) / b}
 * <li>{@code X(n) = T(n) mod b}
 * </ul>
 *
 * <p>r is the length of the record that stores past results.
 * "carry" comes from C(n-1).
 *
 * @see <a href="https://en.wikipedia.org/wiki/Multiply-with-carry_pseudorandom_number_generator">
 * Multiply-with-carry pseudorandom number generator (Wikipedia)
 * </a>
 */
public class Mwc implements RndInt32 {

    private static final int  DEF_RECORDS    = 1038;
    private static final long DEF_MULTIPLIER = 611373678L;
    private static final long DEF_DIVISOR    = 1L << 32;

    private static final int  INIT_OLDEST = 0x0000_0001;
    private static final int  INIT_CARRY  = 1;

    private static final String ERRMSG_SMALLLEN = "too short records length";
    private static final String ERRMSG_SMALLMUL = "too small multiplier";
    private static final String ERRMSG_SMALLDIV = "too small divisor";
    private static final String ERRMSG_SEEDLEN = "unmatch seeds length";


    private final long mul;
    private final long div;

    private final int[] records;
    private final int length;

    private int index;

    private long carry;


    /**
     * Constructor.
     *
     * <ul>
     * <li>record length must be greater than 0.
     * <li>multiplier must be greater than 1.
     * <li>divisor must be greater than 1.
     * </ul>
     *
     * <p>If you want 32-bit uniformly distributed random numbers,
     * the divisor must be 2**32, but you can compromise with 2**32 -1.
     *
     * @param recLen records length
     * @param mulArg multiplier
     * @param divArg divisor
     * @throws IllegalArgumentException illegal argument
     */
    public Mwc(int recLen, long mulArg, long divArg) throws IllegalArgumentException {
        super();

        if (recLen < 1) {
            throw new IllegalArgumentException(ERRMSG_SMALLLEN);
        }
        if (mulArg < 2L) {
            throw new IllegalArgumentException(ERRMSG_SMALLMUL);
        }
        if (divArg < 2L) {
            throw new IllegalArgumentException(ERRMSG_SMALLDIV);
        }

        this.records = new int[recLen];
        this.length = this.records.length;

        this.index = 0;
        this.records[this.index] = INIT_OLDEST;

        this.mul = mulArg;
        this.div = divArg;
        this.carry = INIT_CARRY;

        return;
    }

    /**
     * Constructor.
     *
     * <ul>
     * <li>record length must be greater than 0.
     * <li>multiplier must be greater than 1.
     * </ul>
     *
     * <p>Default divisor value = 2**32.
     *
     * @param recLen records length
     * @param mulArg multiplier
     * @throws IllegalArgumentException illegal argument
     */
    public Mwc(int recLen, long mulArg) throws IllegalArgumentException {
        this(recLen, mulArg, DEF_DIVISOR);
        return;
    }

    /**
     * Constructor.
     *
     * <ul>
     * <li>Default record length = 1038
     * <li>Default multiplier value = 611373678
     * <li>Default divisor value = 2**32
     * </ul>
     *
     * <p>* Caution : If you don't provide your entropy to seeds,
     * it takes over million trials until the seed is scrambled.
     *
     * @throws IllegalArgumentException illegal argument
     * @see <a href="http://digitalcommons.wayne.edu/cgi/viewcontent.cgi?article=1725&context=jmasm">
     * Random Number Generators
     * </a>
     */
    public Mwc() throws IllegalArgumentException {
        this(DEF_RECORDS, DEF_MULTIPLIER);
        return;
    }


    /**
     * Return length of past records.
     *
     * @return length of past records
     */
    public int getRecords() {
        return this.length;
    }

    /**
     * Return carry value.
     *
     * @return carry value
     */
    public long getCarry() {
        return this.carry;
    }

    /**
     * Set carry value.
     *
     * @param lVal new carry value
     */
    public void setCarry(long lVal) {
        this.carry = lVal;
        return;
    }

    /**
     * Set seeds.
     *
     * <p>Seeds length must be same as the result records.
     *
     * <p>Younger index represent older results.
     *
     * @param seeds seeds
     * @throws NullPointerException argument is null
     * @throws IllegalArgumentException size unmatched
     */
    public void setSeeds(int... seeds)
            throws NullPointerException, IllegalArgumentException {
        Objects.requireNonNull(seeds);

        if (seeds.length != this.length) {
            throw new IllegalArgumentException(ERRMSG_SEEDLEN);
        }

        System.arraycopy(seeds, 0, this.records, 0, this.length);

        // [0] is oldest
        this.index = 0;

        return;

    }

    /**
     * Get oldest result.
     *
     * <p>It's mean X(n-r).
     *
     * @return oldest result
     */
    protected int getOldest() {
        // If record-length meets certain conditions,
        // it can provide fast implementations such as bit-mask.
        int result = this.records[this.index];
        return result;
    }

    /**
     * Push latest result.
     *
     * <p>The oldest result at this time is lost.
     *
     * @param iVal latest result
     */
    protected void pushLatest(int iVal) {
        // If record-length meets certain conditions,
        // it can provide fast implementations such as bit-mask.
        this.records[this.index++] = iVal;
        if (this.index >= this.length) {
            this.index = 0;
        }
        return;
    }

    /**
     * Function that takes oldest result and last carry as arguments
     * and returns T-value.
     *
     * @param oldest oldest result
     * @param lastCarry last carry
     * @return T-value
     */
    protected long funcTval(long oldest, long lastCarry) {
        long result = this.mul * oldest + lastCarry;
        return result;
    }

    /**
     * Function that takes T-value as an argument and returns random result.
     *
     * @param tVal T-value
     * @return random result
     */
    protected int funcRndFromTval(long tVal) {
        // If divisor meets certain conditions,
        // it can provide fast implementations such as bit-mask.
        long lVal = tVal % this.div;
        int result = (int) lVal;
        return result;
    }

    /**
     * Function that takes T-value as an argument and returns carry.
     *
     * @param tVal T-value
     * @return carry
     */
    protected long funcCarryFromTval(long tVal) {
        // If divisor meets certain conditions,
        // it can provide fast implementations such as right shift.
        long result = tVal / this.div;
        return result;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public int nextInt32() {
        long oldest = getOldest();
        long lastCarry = this.carry;

        long tVal = funcTval(oldest, lastCarry);

        int latest = funcRndFromTval(tVal);
        long newCarry = funcCarryFromTval(tVal);

        pushLatest(latest);
        this.carry = newCarry;

        return latest;
    }

}

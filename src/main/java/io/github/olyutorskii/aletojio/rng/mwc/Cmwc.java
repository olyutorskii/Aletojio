/*
 * License : The MIT License
 * Copyright(c) 2022 Olyutorskii
 */

package io.github.olyutorskii.aletojio.rng.mwc;

/**
 * Complementary-multiply-with-carry random number generator.
 *
 * <p>Random sequence X(n) is derived as follows with parameter a(multiplier), b(divisor).
 * <ul>
 * <li>{@code T(n) = a * X(n-r) + C(n-1)}
 * <li>{@code C(n) = T(n) / b}
 * <li>{@code Y(n) = T(n) mod b}
 * <li>{@code X(n) = b - 1 - Y(n)}
 * </ul>
 *
 * <p>r is the length of the record that stores past results.
 * "carry" comes from C(n-1).
 *
 * @see <a href="https://en.wikipedia.org/wiki/Multiply-with-carry_pseudorandom_number_generator">
 * Multiply-with-carry pseudorandom number generator (Wikipedia)
 * </a>
 */
public class Cmwc extends Mwc {

    private static final int  DEF_RECORDS    = 1024;
    private static final long DEF_MULTIPLIER = 987769338L;
    private static final long DEF_DIVISOR    = (1L << 32) - 1L;


    private final long minuend;


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
    public Cmwc(int recLen, long mulArg, long divArg) throws IllegalArgumentException {
        super(recLen, mulArg, divArg);
        this.minuend = divArg - 1;
        return;
    }

    /**
     * Constructor.
     *
     * <ul>
     * <li>Default record length = 1024
     * <li>Default multiplier value = 987769338
     * <li>Default divisor value = 2**32 - 1
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
    public Cmwc() throws IllegalArgumentException {
        this(DEF_RECORDS, DEF_MULTIPLIER, DEF_DIVISOR);
        // this(4, 987654978L, (1L << 32) - 1L);
        // this(8, 987651670L, (1L << 32) - 1L);
        // this(2048, 1047570L, (1L << 32) - 1L);
        // this(4096, 18782L, (1L << 32) - 1L);
        // this(42658, 15455296L, 1L << 32);
        return;
    }


    /**
     * {@inheritDoc}
     *
     * @param tVal {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    protected int funcRndFromTval(long tVal) {
        long lVal = this.minuend - super.funcRndFromTval(tVal);
        int result = (int) lVal;
        return result;
    }

}

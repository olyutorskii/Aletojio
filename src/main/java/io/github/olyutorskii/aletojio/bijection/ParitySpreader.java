/*
 * License : The MIT License
 * Copyright(c) 2022 Olyutorskii
 */

package io.github.olyutorskii.aletojio.bijection;

/**
 * Diffusion of parity information.
 *
 * <p>Take the XOR of all even-numbered input bytes to obtain the odd-parity byte.
 * The output is the XOR of the parity byte and the original each byte value.
 *
 * <p>This conversion is bijection.
 * Perform this operation an even number of times to return to the original value.
 *
 * <p>Used as a pre-processor for 8bit S-box conversion to improve confusion.
 *
 * <p>byte-length must be even.
 *
 * <p>If 1 bit of N bytes input array changes,
 * N-1 bits of output array changes.
 * Hamming distance is N-1 bits.
 *
 * <p>ex) Input I has 4 bytes (I0-I3)
 * Output O has 4 bytes (O0-O3)
 *
 * <p>{@code X = I0 ^ I1 ^ I2 ^ I3}
 *
 * <p>{@code O0 = I0 ^ X, O1 = I1 ^ X, O2 = I3 ^ X, O3 = I3 ^ X}
 */
public final class ParitySpreader {

    private static final String ERRMSG_ODDLEN   = "length must be even";
    private static final String ERRMSG_TOOSMALL = "too small length";
    private static final String ERRMSG_TOOLARGE = "too large length";

    private static final int MASK_BYTE = (1 << Byte.SIZE) - 1;


    /**
     * Hidden constructor.
     */
    private ParitySpreader() {
        assert false;
    }


    /**
     * Spread parity information.
     *
     * @param iVal int value
     * @return result
     */
    public static int spread(int iVal) {
        int i0 =  iVal                      & MASK_BYTE;
        int i1 = (iVal >>> (Byte.SIZE * 1)) & MASK_BYTE;
        int i2 = (iVal >>> (Byte.SIZE * 2)) & MASK_BYTE;
        int i3 = (iVal >>> (Byte.SIZE * 3)) & MASK_BYTE;

        int xorVal = (i0 ^ i1) ^ (i2 ^ i3);

        int o0 =  i0 ^ xorVal;
        int o1 = (i1 ^ xorVal) << (Byte.SIZE * 1);
        int o2 = (i2 ^ xorVal) << (Byte.SIZE * 2);
        int o3 = (i3 ^ xorVal) << (Byte.SIZE * 3);

        int result = (o0 ^ o1) ^ (o2 ^ o3);

        return result;
    }

    /**
     * Spread parity information.
     *
     * @param bytes input and output bytes array
     * @param length length of input must be even and smaller than input array.
     * @throws IndexOutOfBoundsException length is negative or too large
     * @throws IllegalArgumentException length is odd number
     */
    public static void spread(byte[] bytes, int length)
            throws IndexOutOfBoundsException, IllegalArgumentException {
        if (length < 0) throw new IndexOutOfBoundsException(ERRMSG_TOOSMALL);
        if ((length & 1) == 1) throw new IllegalArgumentException(ERRMSG_ODDLEN);
        if (bytes.length < length) throw new IndexOutOfBoundsException(ERRMSG_TOOLARGE);

        byte xorVal = 0;
        for (int idx = 0; idx < length; idx++) {
            xorVal ^= bytes[idx];
        }

        for (int idx = 0; idx < length; idx++) {
            byte bVal = bytes[idx];
            bVal ^= xorVal;
            bytes[idx] = bVal;
        }

        return;
    }

}

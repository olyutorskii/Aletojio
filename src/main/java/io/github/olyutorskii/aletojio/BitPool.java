/*
 * License : The MIT License
 * Copyright(c) 2020 Olyutorskii
 */

package io.github.olyutorskii.aletojio;

/**
 * Bit pool.
 *
 * <p>Pushing any size of bits to bit-queue tail.
 *
 * <p>Chopping any size of bits from bit-queue head.
 *
 * <p>From MSB to LSB.
 */
public class BitPool {

    /**
     * Default bit size.
     */
    public static final int DEFAULT_BITS = 512;

    private static final long MASK_LONGINT = (1L << Integer.SIZE) - 1L;

    private static final int BOOLEAN_SIZE = 1;
    private static final int ELEM_SIZE = Integer.SIZE;

    private static final int RSFT_DIV_E;
    private static final int MASK_MOD_E;

    static {
        assert Integer.bitCount(ELEM_SIZE) == 1;

        RSFT_DIV_E = Integer.numberOfTrailingZeros(ELEM_SIZE);
        MASK_MOD_E = (1 << RSFT_DIV_E) - 1;

        int testVal = ELEM_SIZE * 3 + (0x5555_5555 & MASK_MOD_E);
        assert  testVal >> RSFT_DIV_E  == testVal / ELEM_SIZE;
        assert (testVal &  MASK_MOD_E) == testVal % ELEM_SIZE;
    }

    // pushing and chopping bits from MSB to LSB.
    // pushing and chopping bits from lower to higher array index if not overlapping.
    private final int[] buf;

    private final int bufCapa;

    private int bufSize;
    private int headRawBitPos;
    private int tailRawBitPos;


    /**
     * Constructor.
     *
     * <p>bit size is 512.
     */
    public BitPool() {
        this(DEFAULT_BITS);
        return;
    }

    /**
     * Constructor.
     *
     * @param bitLength bit length
     * @throws IllegalArgumentException length is not positive value
     */
    public BitPool(int bitLength) throws IllegalArgumentException {
        super();

        if (bitLength <= 0) throw new IllegalArgumentException();

        this.bufCapa = bitLength;

        int bufLen = (this.bufCapa - 1) / ELEM_SIZE + 1;
        this.buf = new int[bufLen];

        this.bufSize = 0;
        this.headRawBitPos = 0;
        this.tailRawBitPos = 0;

        return;
    }


    /**
     * Return contiguous bitmask pattern from LSB.
     *
     * <p>Result is 0b111 if 3.
     *
     * <p>Result is -1 if 32 or larger.
     *
     * @param lowerBits mask length
     * @return bitmask
     */
    static int getMaskInt(int lowerBits) {
        if (lowerBits >= Integer.SIZE) return -1;
        int result = (1 << lowerBits) - 1;
        return result;
    }

    /**
     * Return contiguous bitmask pattern from LSB.
     *
     * <p>Result is 0b111L if 3.
     *
     * <p>Result is -1L if 64 or larger.
     *
     * @param lowerBits mask length
     * @return bitmask
     */
    static long getMaskLong(int lowerBits) {
        if (lowerBits >= Long.SIZE) return -1L;
        long result = (1L << lowerBits) - 1L;
        return result;
    }


    /**
     * Resturn bit size.
     *
     * @return bit size
     */
    public int size() {
        return this.bufSize;
    }

    /**
     * Return max available size.
     *
     * @return capacity size
     */
    public int capacity() {
        return this.bufCapa;
    }

    /**
     * Return true if empty.
     *
     * @return true if empty
     */
    public boolean isEmpty() {
        boolean result = this.bufSize <= 0;
        return result;
    }

    /**
     * Return remaining spaces.
     *
     * @return remaining spaces.
     */
    public int remaining() {
        int result = this.bufCapa - this.bufSize;
        return result;
    }

    /**
     * Return true if has remaining spaces.
     *
     * @return true if has remaining spaces
     */
    public boolean hasRemaining() {
        boolean result = this.bufCapa > this.bufSize;
        return result;
    }

    /**
     * clear bit pool.
     */
    public void clear() {
        this.headRawBitPos = 0;
        this.tailRawBitPos = 0;
        this.bufSize = 0;
        return;
    }

    /**
     * check push bits argument.
     *
     * @param bits push bits
     * @param max bits max
     * @throws IndexOutOfBoundsException no more space or bits is not positive or over max size
     */
    private void checkPushSize(int bits, int max) throws IndexOutOfBoundsException {
        if (bits <= 0 || remaining() < bits || max < bits) {
            throw new IndexOutOfBoundsException();
        }
        return;
    }

    /**
     * check chop bits argument.
     *
     * @param bits chop bits
     * @param max bits max
     * @throws IndexOutOfBoundsException not enough bits or bits is not positive or over max size
     */
    private void checkChopSize(int bits, int max) throws IndexOutOfBoundsException {
        if (bits <= 0 || this.bufSize < bits || max < bits) {
            throw new IndexOutOfBoundsException();
        }
        return;
    }

    /**
     * Push boolean value to tail of queue.
     *
     * @param bool boolean value
     * @throws IndexOutOfBoundsException no more space
     */
    public void pushBoolean(boolean bool) throws IndexOutOfBoundsException {
        checkPushSize(BOOLEAN_SIZE, BOOLEAN_SIZE);

        int bufIndex = this.tailRawBitPos >> RSFT_DIV_E; // / ELEM_SIZE;
        int bposInInt = this.tailRawBitPos & MASK_MOD_E; // % ELEM_SIZE;
        int lsft = ELEM_SIZE - 1 - bposInInt;

        int mask = 1 << lsft;

        int elem = this.buf[bufIndex];
        if (bool) {
            elem |=   mask;
        } else {
            elem &=  ~mask;
        }
        this.buf[bufIndex] = elem;

        this.bufSize++;
        this.tailRawBitPos++;
        if (this.tailRawBitPos >= this.bufCapa) {
            this.tailRawBitPos -= this.bufCapa;
        }

        return;
    }

    /**
     * Chop boolean value from head of queue.
     *
     * @return boolean value
     * @throws IndexOutOfBoundsException not enough bits
     */
    public boolean chopBoolean() throws IndexOutOfBoundsException {
        checkChopSize(BOOLEAN_SIZE, BOOLEAN_SIZE);

        int bufIndex = this.headRawBitPos >> RSFT_DIV_E; // / ELEM_SIZE;
        int bposInInt = this.headRawBitPos & MASK_MOD_E; // % ELEM_SIZE;
        int lsft = ELEM_SIZE - 1 - bposInInt;

        int mask = 1 << lsft;
        int elem = this.buf[bufIndex];
        boolean result = (elem & mask) != 0;

        this.bufSize--;
        this.headRawBitPos++;
        if (this.headRawBitPos >= this.bufCapa) {
            this.headRawBitPos -= this.bufCapa;
        }

        return result;
    }

    /**
     * Push int value to tail of queue.
     *
     * @param iArg int value
     * @throws IndexOutOfBoundsException no more space
     */
    public void pushInt(int iArg) throws IndexOutOfBoundsException {
        pushInt(iArg, Integer.SIZE);
        return;
    }

    /**
     * Push lower bits of int value to tail of queue.
     *
     * @param iArg int value
     * @param bitCt bits from LSB
     * @throws IndexOutOfBoundsException bits is not positive or no more space or over 32
     */
    public void pushInt(int iArg, int bitCt) throws IndexOutOfBoundsException {
        checkPushSize(bitCt, Integer.SIZE);

        int bits1st = this.bufCapa - this.tailRawBitPos;

        if (bitCt <= bits1st) {
            pushIntImpl(iArg, bitCt);
        } else {
            // Overlapping end of array
            int bits2nd = bitCt - bits1st;
            int highArg = iArg >> bits2nd;
            pushIntImpl(highArg, bits1st);
            pushIntImpl(iArg,    bits2nd);
        }

        return;
    }

    /**
     * Push lower bits of int value to tail of queue.
     *
     * <p>No bits bound argument checking.
     *
     * <p>Pushing over last of array elements is not supported.
     *
     * @param iArg int value
     * @param bitCt bits from LSB
     */
    private void pushIntImpl(int iArg, int bitCt) {
        int bposInLastElem = this.tailRawBitPos & MASK_MOD_E; // % ELEM_SIZE;
        int restInLastElem  = ELEM_SIZE - bposInLastElem;

        if (restInLastElem < bitCt) {
            // Overlapping array elements
            int bits2nd = bitCt - restInLastElem;
            int highArg = iArg >> bits2nd;
            pushIntImpl(highArg, restInLastElem);
            pushIntImpl(iArg,    bits2nd);
            return;
        }

        int bufIndex = this.tailRawBitPos >> RSFT_DIV_E; // / ELEM_SIZE;

        int gap = restInLastElem - bitCt;
        int mask = getMaskInt(bitCt) << gap;
        int bits = (iArg << gap) & mask;

        int elem = this.buf[bufIndex];
        elem &= ~mask;
        elem |=  bits;
        this.buf[bufIndex] = elem;

        this.bufSize += bitCt;
        this.tailRawBitPos += bitCt;
        if (this.tailRawBitPos >= this.bufCapa) {
            this.tailRawBitPos -= this.bufCapa;
        }

        return;
    }

    /**
     * Chop int value from head of queue.
     *
     * @return int value
     * @throws IndexOutOfBoundsException not enough bits
     */
    public int chopInt() throws IndexOutOfBoundsException {
        return chopInt(Integer.SIZE);
    }

    /**
     * Chop bits and store to lower bits of int value from head of queue.
     *
     * @param bitCt bits
     * @return lower bits filled int value
     * @throws IndexOutOfBoundsException not enough bits or bits is not positive or over 32
     */
    public int chopInt(int bitCt) throws IndexOutOfBoundsException {
        checkChopSize(bitCt, Integer.SIZE);

        int result;

        if (this.headRawBitPos + bitCt <= this.bufCapa) {
            result = chopIntImpl(bitCt);
        } else {
            // Overlapping end of array
            int bits1st = this.bufCapa - this.headRawBitPos;
            int bits2nd = bitCt - bits1st;
            int highVal = chopIntImpl(bits1st);
            int lowVal  = chopIntImpl(bits2nd);
            result  = (highVal << bits2nd) | lowVal;
        }

        return result;
    }

    /**
     * Chop bits and store to lower bits of int value from head of queue.
     *
     * <p>No bits bound argument checking.
     *
     * <p>Chopping over last of array elements is not supported.
     *
     * @param bitCt bits
     * @return lower bits filled int value
     */
    private int chopIntImpl(int bitCt) {
        int bposInLastElem = this.headRawBitPos & MASK_MOD_E; // % ELEM_SIZE;
        int restInLastElem = ELEM_SIZE - bposInLastElem;

        if (restInLastElem < bitCt) {
            // Overlapping array elements
            int bits2nd = bitCt - restInLastElem;
            int highVal = chopIntImpl(restInLastElem);
            int lowVal  = chopIntImpl(bits2nd);
            int result = (highVal << bits2nd) | lowVal;
            return result;
        }

        int bufIndex = this.headRawBitPos >> RSFT_DIV_E; // / ELEM_SIZE;

        int elem = this.buf[bufIndex];
        int gap = restInLastElem - bitCt;
        int result = (elem >> gap) & getMaskInt(bitCt);

        this.bufSize -= bitCt;
        this.headRawBitPos += bitCt;
        if (this.headRawBitPos >= this.bufCapa) {
            this.headRawBitPos -= this.bufCapa;
        }

        return result;
    }

    /**
     * Push long value to tail of queue.
     *
     * @param lArg long value
     * @throws IndexOutOfBoundsException no more space
     */
    public void pushLong(long lArg) throws IndexOutOfBoundsException {
        pushLong(lArg, Long.SIZE);
        return;
    }

    /**
     * Push lower bits of long value to tail of queue.
     *
     * @param lArg long value
     * @param bitCt bits from LSB
     * @throws IndexOutOfBoundsException bits is not positive or no more space or over 64
     */
    public void pushLong(long lArg, int bitCt) throws IndexOutOfBoundsException {
        checkPushSize(bitCt, Long.SIZE);

        if (bitCt <= Integer.SIZE) {
            pushInt((int) lArg, bitCt);
            return;
        }

        int bits1st = bitCt - Integer.SIZE;
        int bits2nd = Integer.SIZE;

        int highArg = (int) (lArg >> Integer.SIZE);
        int lowArg  = (int) lArg;

        pushInt(highArg, bits1st);
        pushInt(lowArg,  bits2nd);

        return;
    }

    /**
     * Chop long value from head of queue.
     *
     * @return long value
     * @throws IndexOutOfBoundsException not enough bits
     */
    public long chopLong() throws IndexOutOfBoundsException {
        return chopLong(Long.SIZE);
    }

    /**
     * Chop bits and store to lower bits of long value from head of queue.
     *
     * @param bitCt bits
     * @return lower bits filled long value
     * @throws IndexOutOfBoundsException not enough bits or bits is not positive or over 64
     */
    public long chopLong(int bitCt) throws IndexOutOfBoundsException {
        checkChopSize(bitCt, Long.SIZE);

        if (bitCt <= Integer.SIZE) {
            long result = (long) chopInt(bitCt);
            result &= getMaskLong(bitCt);
            return result;
        }

        int bits1st = bitCt - Integer.SIZE;

        long highVal = (long) chopInt(bits1st);
        long lowVal  = (long) chopInt(Integer.SIZE);

        highVal &= getMaskLong(bits1st);
        lowVal  &= MASK_LONGINT;

        long result = (highVal << Integer.SIZE) | lowVal;

        return result;
    }

    /**
     * Push byte value to tail of queue.
     *
     * @param bArg byte value
     * @throws IndexOutOfBoundsException no more space
     */
    public void pushByte(byte bArg) throws IndexOutOfBoundsException {
        pushInt(bArg, Byte.SIZE);
        return;
    }

    /**
     * Chop byte value from head of queue.
     *
     * @return byte value
     * @throws IndexOutOfBoundsException not enough bits
     */
    public byte chopByte() throws IndexOutOfBoundsException {
        byte result = (byte) chopInt(Byte.SIZE);
        return result;
    }

}

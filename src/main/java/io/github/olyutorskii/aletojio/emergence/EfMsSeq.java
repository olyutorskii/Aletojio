/*
 * License : The MIT License
 * Copyright(c) 2022 Olyutorskii
 */

package io.github.olyutorskii.aletojio.emergence;

import java.util.Arrays;

/**
 * Ehrenfeucht-Mycielski random bits sequence.
 *
 * <p>Continue to generate random bits sequences acyclic and emergently
 * until the memory capacity and indexing mechanism reach their limits.
 *
 * <p>This is Automatic sequence. This means infinite sequence from finite automaton.
 *
 * <p>It is only possible to add bit to the end of sequence.
 *
 * <p>TODO: use suffix tree.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Ehrenfeucht%E2%80%93Mycielski_sequence">
 * Ehrenfeucht Mycielski sequence.
 * </a>
 * @see <a href="https://oeis.org/A038219">A038219 sample list</a>
 */
public class EfMsSeq {

    private static final int PRIMLEN = Long.SIZE;
    private static final int INIT_VECLEN = 2;
    private static final int GROWTH_RATE = 2;


    private long[] vec;
    private int capacity;

    private int bitLength;


    /**
     * Constructor.
     *
     * <p>Bit sequence is empty.
     */
    public EfMsSeq() {
        super();

        this.vec = new long[INIT_VECLEN];
        this.capacity = PRIMLEN * this.vec.length;
        this.bitLength = 0;

        return;
    }

    /**
     * Return length of bit sequence.
     *
     * @return length of bit sequence
     */
    public int length() {
        return this.bitLength;
    }

    /**
     * Ensure min capacity of bit length.
     *
     * @param minCapacity min capacity of bit length
     */
    private void ensureCapacity(int minCapacity) {
        if (minCapacity <= this.capacity) {
            return;
        }

        int newArraySize = this.vec.length * GROWTH_RATE;
        while (PRIMLEN * newArraySize < minCapacity) {
            newArraySize *= GROWTH_RATE;
        }

        this.vec = Arrays.copyOf(this.vec, newArraySize);

        this.capacity = PRIMLEN * this.vec.length;

        return;
    }

    /**
     * Append new bit to tail of bit sequence.
     *
     * @param iVal bit. 0 means 0b0, not 0 means 0b1.
     */
    public void append(int iVal) {
        int newLength = this.bitLength + 1;
        ensureCapacity(newLength);

        if (iVal != 0) {
            int vecIdx = this.bitLength / PRIMLEN;
            int bitPos = this.bitLength % PRIMLEN;
            this.vec[vecIdx] |= 1L << bitPos;
        }

        this.bitLength = newLength;

        return;
    }

    /**
     * Append new bits to tail of bit sequence.
     *
     * @param iVals bits. 0 means 0b0, not 0 means 0b1.
     */
    public void append(int... iVals) {
        ensureCapacity(this.bitLength + iVals.length);

        for (int iVal : iVals) {
            append(iVal);
        }

        return;
    }

    /**
     * Get bit from bit sequence with index.
     *
     * <p>Index starts with 0 (oldest bit).
     * Index ends with bit sequence length - 1 (recently added bit).
     *
     * @param idx bit position. 0 means oldest bit.
     * @return bit. 0 means 0b0. 1 means 0b1.
     * @throws IndexOutOfBoundsException illegal index
     */
    public int get(int idx) throws IndexOutOfBoundsException {
        if (idx >= this.bitLength || idx < 0) {
            throw new IndexOutOfBoundsException();
        }

        int vecIdx = idx / PRIMLEN;
        int bitPos = idx % PRIMLEN;

        long lVal = this.vec[vecIdx];
        lVal = (lVal >> bitPos) & 1L;

        return (int) lVal;
    }

    /**
     * Return last index of suffix pattern appeared in bit sequence.
     *
     * <p>Suffix never unmatch it self.
     *
     * <p>Suffix of "01010010101" with index 8 is "101".
     *
     * <p>"101" pattern appear 3-times in "01010010101".
     * 3rd one is ignored because last one is suffix it self.
     *
     * <p>Therefore, last index of "01010010101" with suffix index 8 is position 6
     *
     * @param suffixIdx index of suffix starting
     * @return last index of suffix pattern appeared in bit sequence. Negative if unmatched.
     * @throws IndexOutOfBoundsException invalid index
     */
    public int lastIdxSuffix(int suffixIdx) throws IndexOutOfBoundsException {
        if (suffixIdx <= 0) throw new IndexOutOfBoundsException();
        if (suffixIdx >= this.bitLength) throw new IndexOutOfBoundsException();

        int suffixLen = this.bitLength - suffixIdx;
        int revStart = this.bitLength - suffixLen - 1;

        assert suffixLen > 0;
        assert revStart >= 0;
        assert revStart < this.bitLength - 1;

        int lastIdx = -1;

        for (int seqRevIdx = revStart; seqRevIdx >= 0; seqRevIdx--) {
            boolean suffixMatched = true;

            for (int offset = 0; offset < suffixLen; offset++) {
                int bitSeq = get(seqRevIdx + offset);
                int bitSfx = get(suffixIdx + offset);
                if (bitSeq != bitSfx) {
                    suffixMatched = false;
                    break;
                }
            }

            if (suffixMatched) {
                lastIdx = seqRevIdx;
                break;
            }
        }

        return lastIdx;
    }

    /**
     * Determin next random bit from past bits.
     *
     * @return next random bit. (0 or 1)
     * @throws NullPointerException argument is null
     */
    public int nextBool()
            throws NullPointerException {
        int targetLen = length();
        if (targetLen <= 0) return 0;

        int suffixNext = -1;

        for (int suffixIdx = 1; suffixIdx < targetLen; suffixIdx++) {
            int matchedPos = lastIdxSuffix(suffixIdx);
            if (matchedPos >= 0) {
                int suffixLen = targetLen - suffixIdx;
                suffixNext = matchedPos + suffixLen;
                break;
            }
        }

        int resultPos;
        if (suffixNext >= 0) resultPos = suffixNext;
        else                 resultPos = length() - 1;

        int resultNeg;
        if (get(resultPos) == 0) resultNeg = 1;
        else                     resultNeg = 0;

        return resultNeg;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public int hashCode() {
        long lVal1st  = 0xdeadbeefaa554321L;
        long lValLast = 0x1234aa55cafebabeL;
        if (this.bitLength >= 1) {
            int vecs = (this.bitLength - 1) / PRIMLEN + 1;
            lVal1st  ^= this.vec[0]        * 23;
            lValLast ^= this.vec[vecs - 1] * 29;
        }
        long lVal = lVal1st ^ lValLast;

        int result = 0;
        result ^= lVal;
        result ^= lVal >>> 32;

        return result;
    }

    /**
     * {@inheritDoc}
     *
     * @param obj {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof EfMsSeq)) {
            return false;
        }
        EfMsSeq other = (EfMsSeq) obj;

        if (this.bitLength != other.bitLength) {
            return false;
        }

        boolean result = Arrays.equals(this.vec, other.vec);
        return result;
    }

    /**
     * {@inheritDoc}
     *
     * <p>Output 0/1 sequence in historical order.
     *
     * @return bit sequence text. "nil" if empty.
     */
    @Override
    public String toString() {
        if (this.bitLength <= 0) return "nil";

        StringBuilder result = new StringBuilder();
        result.ensureCapacity(this.bitLength);

        for (int bitIdx = 0; bitIdx < this.bitLength; bitIdx++) {
            char ch;
            if (get(bitIdx) == 0) ch = '0';
            else                  ch = '1';
            result.append(ch);
        }

        return result.toString();
    }

}

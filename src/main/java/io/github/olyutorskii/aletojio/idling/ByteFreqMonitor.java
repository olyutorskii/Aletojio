/*
 * License : The MIT License
 * Copyright(c) 2022 Olyutorskii
 */

package io.github.olyutorskii.aletojio.idling;

import java.util.BitSet;

/**
 * Byte frequency monitor.
 *
 * <p>Monitor all bytes for all patterns (0x00 to 0xff) appear.
 */
public class ByteFreqMonitor extends AbstractRndMonitor {

    private static final int BITS_PRIM = Integer.SIZE;
    private static final int BITS_CHUNK = Byte.SIZE;
    private static final int MASK_CHUNK = (1 << BITS_CHUNK) - 1;
    private static final int CHUNKS = BITS_PRIM / BITS_CHUNK;

    private final BitSet[] chunkArr;


    /**
     * Constructor.
     */
    public ByteFreqMonitor() {
        super();

        this.chunkArr = new BitSet[CHUNKS];

        for (int idx = 0; idx < CHUNKS; idx++) {
            this.chunkArr[idx] = buildChunkMap();
        }

        return;
    }


    /**
     * build map of each chunk.
     *
     * @return map of each chunk
     */
    private static BitSet buildChunkMap() {
        BitSet result = new BitSet();
        initChunkMap(result);
        return result;
    }

    /**
     * init map of each chunk.
     *
     * <p>All bits become true.
     *
     * @param chunkMap map of each chunk
     */
    private static void initChunkMap(BitSet chunkMap) {
        chunkMap.clear();

        final int fromIdx = 0;
        final int toIdx = 1 << BITS_CHUNK;
        chunkMap.set(fromIdx, toIdx, true);

        return;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    protected void resetImpl() {
        for (BitSet chunkMap : this.chunkArr) {
            initChunkMap(chunkMap);
        }
        return;
    }

    /**
     * {@inheritDoc}
     *
     * @param iVal {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    protected boolean probeImpl(int iVal) {
        boolean result = true;

        for (int ct = 0; ct < CHUNKS; ct++) {
            BitSet chunkMap = this.chunkArr[ct];
            if (chunkMap.isEmpty()) continue;

            int bVal = (iVal >>> (BITS_CHUNK * ct)) & MASK_CHUNK;
            chunkMap.clear(bVal);

            if (!chunkMap.isEmpty()) result = false;
        }

        return result;
    }

}

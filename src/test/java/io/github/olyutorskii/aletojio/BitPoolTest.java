/*
 */

package io.github.olyutorskii.aletojio;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 */
public class BitPoolTest {

    public BitPoolTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getMaskInt method, of class BitPool.
     */
    @Test
    public void testGetMaskInt() {
        System.out.println("getMaskInt");

        assertEquals(0, BitPool.getMaskInt(0));
        assertEquals(1, BitPool.getMaskInt(1));
        assertEquals(31, BitPool.getMaskInt(5));
        assertEquals(Integer.MAX_VALUE, BitPool.getMaskInt(31));
        assertEquals(-1, BitPool.getMaskInt(32));
        assertEquals(-1, BitPool.getMaskInt(33));

        return;
    }

    /**
     * Test of getMaskLong method, of class BitPool.
     */
    @Test
    public void testGetMaskLong() {
        System.out.println("getMaskLong");

        assertEquals(0L, BitPool.getMaskLong(0));
        assertEquals(1L, BitPool.getMaskLong(1));
        assertEquals(31L, BitPool.getMaskLong(5));
        assertEquals(Long.MAX_VALUE, BitPool.getMaskLong(63));
        assertEquals(-1L, BitPool.getMaskLong(64));
        assertEquals(-1L, BitPool.getMaskLong(65));

        return;
    }

    /**
     * Test of size method, of class BitPool.
     */
    @Test
    public void testSize() {
        System.out.println("size");

        BitPool bp;

        bp = new BitPool();
        assertEquals(BitPool.DEFAULT_BITS, bp.capacity());
        assertEquals(0, bp.size());
        assertTrue(bp.isEmpty());
        assertEquals(BitPool.DEFAULT_BITS, bp.remaining());
        assertTrue(bp.hasRemaining());

        bp = new BitPool(6);
        assertEquals(6, bp.capacity());
        assertEquals(0, bp.size());
        assertEquals(6, bp.remaining());
        assertTrue(bp.hasRemaining());

        bp.pushBoolean(true);
        bp.pushBoolean(false);
        assertEquals(6, bp.capacity());
        assertEquals(2, bp.size());
        assertFalse(bp.isEmpty());
        assertEquals(4, bp.remaining());
        assertTrue(bp.hasRemaining());

        bp.pushBoolean(true);
        bp.pushBoolean(true);
        bp.pushBoolean(false);
        assertEquals(6, bp.capacity());
        assertEquals(5, bp.size());
        assertFalse(bp.isEmpty());
        assertEquals(1, bp.remaining());
        assertTrue(bp.hasRemaining());

        bp.pushBoolean(false);
        assertEquals(6, bp.capacity());
        assertEquals(6, bp.size());
        assertFalse(bp.isEmpty());
        assertEquals(0, bp.remaining());
        assertFalse(bp.hasRemaining());

        try{
            bp.pushBoolean(true);
            fail();
        } catch(IndexOutOfBoundsException e) {
            assert true;
        }

        assertTrue(bp.chopBoolean());
        assertFalse(bp.chopBoolean());
        assertTrue(bp.chopBoolean());
        assertTrue(bp.chopBoolean());
        assertEquals(6, bp.capacity());
        assertEquals(2, bp.size());
        assertFalse(bp.isEmpty());
        assertEquals(4, bp.remaining());
        assertTrue(bp.hasRemaining());

        bp.pushBoolean(false);
        bp.pushBoolean(false);
        assertEquals(6, bp.capacity());
        assertEquals(4, bp.size());
        assertFalse(bp.isEmpty());
        assertEquals(2, bp.remaining());
        assertTrue(bp.hasRemaining());

        bp.clear();
        assertEquals(6, bp.capacity());
        assertEquals(0, bp.size());
        assertTrue(bp.isEmpty());
        assertEquals(6, bp.remaining());
        assertTrue(bp.hasRemaining());

        try {
            bp = new BitPool(0);
            fail();
            bp.hashCode();
        } catch(IllegalArgumentException e) {
            assert true;
        }

        try {
            bp = new BitPool(-1);
            fail();
            bp.hashCode();
        } catch(IllegalArgumentException e) {
            assert true;
        }

        return;
    }

    /**
     * Test of pushInt method, of class BitPool.
     */
    @Test
    public void testPushInt_int() {
        System.out.println("pushInt");

        BitPool bp;

        bp = new BitPool(232);

        bp.pushBoolean(true);
        bp.pushBoolean(false);
        bp.pushBoolean(true);
        bp.pushInt(0);
        bp.pushInt(1);
        bp.pushInt(-1);
        bp.pushInt(-2);
        bp.pushInt(Integer.MAX_VALUE);
        bp.pushInt(Integer.MIN_VALUE);
        bp.pushInt(0x12345678);

        assertEquals(5, bp.remaining());

        assertTrue(bp.chopBoolean());
        assertFalse(bp.chopBoolean());
        assertTrue(bp.chopBoolean());
        assertEquals(0, bp.chopInt());
        assertEquals(1, bp.chopInt());
        assertEquals(-1, bp.chopInt());
        assertEquals(-2, bp.chopInt());
        assertEquals(Integer.MAX_VALUE, bp.chopInt());
        assertEquals(Integer.MIN_VALUE, bp.chopInt());
        assertEquals(0x12345678, bp.chopInt());

        bp = new BitPool(70);
        bp.pushLong(1L, 60);
        bp.pushBoolean(true);
        bp.pushBoolean(true);
        assertEquals(1L, bp.chopLong(60));
        bp.pushInt(0x12345678);
        assertTrue(bp.chopBoolean());
        assertTrue(bp.chopBoolean());
        assertEquals(0x12345678, bp.chopInt());

        return;
    }

    /**
     * Test of pushLong method, of class BitPool.
     */
    @Test
    public void testPushLong_long() {
        System.out.println("pushLong");

        BitPool bp;

        bp = new BitPool(456);

        bp.pushBoolean(true);
        bp.pushBoolean(false);
        bp.pushBoolean(true);
        bp.pushLong(0L);
        bp.pushLong(1L);
        bp.pushLong(-1L);
        bp.pushLong(-2L);
        bp.pushLong(Long.MAX_VALUE);
        bp.pushLong(Long.MIN_VALUE);
        bp.pushLong(0x1234567878563412L);

        assertEquals(5, bp.remaining());

        assertTrue(bp.chopBoolean());
        assertFalse(bp.chopBoolean());
        assertTrue(bp.chopBoolean());
        assertEquals(0L, bp.chopLong());
        assertEquals(1L, bp.chopLong());
        assertEquals(-1L, bp.chopLong());
        assertEquals(-2L, bp.chopLong());
        assertEquals(Long.MAX_VALUE, bp.chopLong());
        assertEquals(Long.MIN_VALUE, bp.chopLong());
        assertEquals(0x1234567878563412L, bp.chopLong());

        bp = new BitPool(70);
        bp.pushLong(1L, 60);
        bp.pushBoolean(true);
        bp.pushBoolean(true);
        assertEquals(1L, bp.chopLong(60));
        bp.pushLong(0x1234567878563412L);
        assertTrue(bp.chopBoolean());
        assertTrue(bp.chopBoolean());
        assertEquals(0x1234567878563412L, bp.chopLong());

        return;
    }

    /**
     * Test of pushBoolean method, of class BitPool.
     */
    @Test
    public void testPushBoolean_boolean() {
        System.out.println("pushBoolean");

        BitPool bp;

        bp = new BitPool(5);
        bp.pushBoolean(true);
        bp.pushBoolean(false);
        bp.pushBoolean(true);
        assertEquals(true, bp.chopBoolean());
        bp.pushBoolean(false);
        assertEquals(false, bp.chopBoolean());
        bp.pushBoolean(true);
        bp.pushBoolean(false);
        assertEquals(true, bp.chopBoolean());
        bp.pushBoolean(true);
        assertEquals(4, bp.size());
        assertEquals(false, bp.chopBoolean());
        assertEquals(true, bp.chopBoolean());
        assertEquals(false, bp.chopBoolean());

        return;
    }

    /**
     * Test of pushBoolean method, of class BitPool.
     */
    @Test
    public void testPushBoolean() {
        System.out.println("pushBoolean");

        BitPool bp;

        bp = new BitPool();
        bp.pushBoolean(true);
        bp.pushBoolean(false);
        bp.pushBoolean(true);
        bp.pushBoolean(false);
        assertEquals(0xa, bp.chopInt(4));

        bp = new BitPool();
        bp.pushBoolean(true);
        bp.pushBoolean(false);
        bp.pushBoolean(false);
        bp.pushBoolean(true);
        assertEquals(0x9, bp.chopInt(4));

        bp = new BitPool(2);
        bp.pushBoolean(true);
        bp.pushBoolean(false);
        try {
            bp.pushBoolean(false);
            fail();
        } catch(IndexOutOfBoundsException e) {
            assert true;
        }

        return;
    }

    /**
     * Test of chopBoolean method, of class BitPool.
     */
    @Test
    public void testChopBoolean() {
        System.out.println("chopBoolean");

        BitPool bp;

        bp = new BitPool();
        bp.pushInt(0xa, 4);
        assertEquals(true, bp.chopBoolean());
        assertEquals(false, bp.chopBoolean());
        assertEquals(true, bp.chopBoolean());
        assertEquals(false, bp.chopBoolean());
        try {
            bp.chopBoolean();
            fail();
        } catch(IndexOutOfBoundsException e) {
            assert true;
        }

        return;
    }

    /**
     * Test of pushLong method, of class BitPool.
     */
    @Test
    public void testPushLong_long_int() {
        System.out.println("pushLong");

        BitPool bp;

        bp = new BitPool();
        bp.pushByte((byte)0xaa);
        bp.pushByte((byte)0xbb);
        bp.pushByte((byte)0xcc);
        bp.pushLong(0x1234567887654321L, 8);
        assertEquals(0xaabbcc21, bp.chopInt());

        bp = new BitPool();
        bp.pushByte((byte)0xaa);
        bp.pushByte((byte)0xbb);
        bp.pushByte((byte)0xcc);
        bp.pushLong(0x1234567887654321L, 40);
        assertEquals(0xaabbcc7887654321L, bp.chopLong());

        bp = new BitPool();
        bp.pushByte((byte)0xaa);
        bp.pushByte((byte)0xbb);
        bp.pushByte((byte)0xcc);
        bp.pushLong(0x1234567887654321L, 64);
        assertEquals(0xaabbcc1234567887L, bp.chopLong());

        bp = new BitPool();
        try {
            bp.pushLong(0x1234567887654321L, 0);
            fail();
        } catch(IndexOutOfBoundsException e) {
            assert true;
        }

        bp = new BitPool();
        try {
            bp.pushLong(0x1234567887654321L, -1);
            fail();
        } catch(IndexOutOfBoundsException e) {
            assert true;
        }

        bp = new BitPool();
        try {
            bp.pushLong(0x1234567887654321L, 65);
            fail();
        } catch(IndexOutOfBoundsException e) {
            assert true;
        }

        bp = new BitPool(63);
        try {
            bp.pushLong(0x1234567887654321L, 64);
            fail();
        } catch(IndexOutOfBoundsException e) {
            assert true;
        }

        return;
    }

    /**
     * Test of chopLong method, of class BitPool.
     */
    @Test
    public void testChopLong_0args() {
        System.out.println("chopLong");

        BitPool bp;

        bp = new BitPool();
        bp.pushByte((byte)0xaa);
        bp.pushByte((byte)0xbb);
        bp.pushByte((byte)0xcc);
        bp.pushLong(0x1234567887654321L);
        assertEquals(0xaabbcc1234567887L, bp.chopLong());
        assertEquals(0x654321, bp.chopInt(24));

        return;
    }

    /**
     * Test of chopLong method, of class BitPool.
     */
    @Test
    public void testChopLong_int() {
        System.out.println("chopLong");

        BitPool bp;

        bp = new BitPool();
        bp.pushLong(0x1234567887654321L);
        assertEquals(0x12L, bp.chopLong(8));

        bp = new BitPool();
        bp.pushLong(0x1234567887654321L);
        assertEquals(0x12345678L, bp.chopLong(32));

        bp = new BitPool();
        bp.pushLong(0xaaaaaaaaaaaaaaaaL);
        assertEquals(0x155555555L, bp.chopLong(33));

        bp = new BitPool();
        bp.pushLong(0x1234567887654321L);
        assertEquals(0x1234567887654321L, bp.chopLong(64));

        bp = new BitPool();
        bp.pushLong(0x1234567887654321L);
        try {
            bp.chopLong(0);
            fail();
        } catch(IndexOutOfBoundsException e) {
            assert true;
        }

        bp = new BitPool();
        bp.pushLong(0x1234567887654321L);
        try {
            bp.chopLong(-1);
            fail();
        } catch(IndexOutOfBoundsException e) {
            assert true;
        }

        bp = new BitPool();
        bp.pushLong(0x1234567887654321L);
        bp.pushInt(-1);
        try {
            bp.chopLong(65);
            fail();
        } catch(IndexOutOfBoundsException e) {
            assert true;
        }

        return;
    }

    /**
     * Test of pushByte method, of class BitPool.
     */
    @Test
    public void testPushByte() {
        System.out.println("pushByte");

        BitPool bp;

        bp = new BitPool();
        bp.pushByte((byte)0x12);
        bp.pushByte((byte)0x34);
        bp.pushByte((byte)0x56);
        bp.pushByte((byte)0x78);
        assertEquals(0x12345678, bp.chopInt());

        bp = new BitPool();
        bp.pushByte((byte)0xff);
        bp.pushByte((byte)0x00);
        bp.pushByte(Byte.MAX_VALUE);
        bp.pushByte(Byte.MIN_VALUE);
        assertEquals(0xff007f80, bp.chopInt());

        bp = new BitPool(24);
        bp.pushByte((byte)0x12);
        bp.pushByte((byte)0x34);
        bp.pushByte((byte)0x56);
        try {
            bp.pushByte((byte)0x78);
            fail();
        } catch(IndexOutOfBoundsException e) {
            assert true;
        }

        return;
    }

    /**
     * Test of chopByte method, of class BitPool.
     */
    @Test
    public void testChopByte() {
        System.out.println("chopByte");

        BitPool bp;

        bp = new BitPool();
        bp.pushInt(0x12345678);
        assertEquals((byte)0x12, bp.chopByte());
        assertEquals((byte)0x34, bp.chopByte());
        assertEquals((byte)0x56, bp.chopByte());
        assertEquals((byte)0x78, bp.chopByte());
        try {
            bp.chopByte();
            fail();
        } catch(IndexOutOfBoundsException e) {
            assert true;
        }

        bp = new BitPool();
        bp.pushInt(0xff007f80);
        assertEquals((byte)0xff, bp.chopByte());
        assertEquals((byte)0x00, bp.chopByte());
        assertEquals(Byte.MAX_VALUE, bp.chopByte());
        assertEquals(Byte.MIN_VALUE, bp.chopByte());

        return;
    }

}

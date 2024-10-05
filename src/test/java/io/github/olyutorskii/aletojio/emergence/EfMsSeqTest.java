/*
 */

package io.github.olyutorskii.aletojio.emergence;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 */
public class EfMsSeqTest {

    public EfMsSeqTest() {
    }

    @BeforeAll
    public static void setUpClass() throws Exception {
    }

    @AfterAll
    public static void tearDownClass() throws Exception {
    }

    @BeforeEach
    public void setUp() throws Exception {
    }

    @AfterEach
    public void tearDown() throws Exception {
    }

    /**
     * Test of length method, of class BitVec.
     */
    @Test
    public void testLength() {
        System.out.println("length");
        EfMsSeq instance;

        instance = new EfMsSeq();
        assertEquals(0, instance.length());

        instance.append(1);
        assertEquals(1, instance.length());

        instance.append(0);
        assertEquals(2, instance.length());

        int[] arr = new int[98];
        instance.append(arr);
        assertEquals(100, instance.length());

        return;
    }

    /**
     * Test of append method, of class BitVec.
     */
    @Test
    public void testAppend_int() {
        System.out.println("append");

        EfMsSeq instance;

        instance = new EfMsSeq();

        instance.append(0);
        assertEquals(0, instance.get(0));
        assertEquals(1, instance.length());

        instance.append(1);
        assertEquals(0, instance.get(0));
        assertEquals(1, instance.get(1));
        assertEquals(2, instance.length());

        instance.append(2);
        assertEquals(1, instance.get(2));
        assertEquals(3, instance.length());

        instance.append(-1);
        assertEquals(1, instance.get(3));
        assertEquals(4, instance.length());

        instance.append(0);
        assertEquals(0, instance.get(4));
        assertEquals(5, instance.length());

        return;
    }

    /**
     * Test of append method, of class BitVec.
     */
    @Test
    public void testAppend_intArr() {
        System.out.println("append");

        EfMsSeq instance;

        instance = new EfMsSeq();

        instance.append(0, 1);
        assertEquals(0, instance.get(0));
        assertEquals(1, instance.get(1));
        assertEquals(2, instance.length());

        instance.append();
        assertEquals(2, instance.length());

        try {
            instance.append(null);
            fail();
        } catch(NullPointerException e) {
            assert true;
        }

        int[] arr;

        arr = new int[61];
        instance.append(arr);
        assertEquals(63, instance.length());

        arr = new int[2];
        instance.append(arr);
        assertEquals(65, instance.length());

        return;
    }

    /**
     * Test of get method, of class BitVec.
     */
    @Test
    public void testGet() {
        System.out.println("get");

        EfMsSeq instance;

        instance = new EfMsSeq();

        try {
            instance.get(0);
            fail();
        } catch(IndexOutOfBoundsException e) {
            assert true;
        }

        instance.append(0, 1, 0);
        assertEquals(0, instance.get(0));
        assertEquals(1, instance.get(1));
        assertEquals(0, instance.get(2));

        try {
            instance.get(-1);
            fail();
        } catch(IndexOutOfBoundsException e) {
            assert true;
        }

        try {
            instance.get(3);
            fail();
        } catch(IndexOutOfBoundsException e) {
            assert true;
        }

        int[] arr;

        arr = new int[1021];
        instance.append(arr);
        instance.append(1, 1);
        assertEquals(0, instance.get(1023));
        assertEquals(1, instance.get(1024));
        assertEquals(1, instance.get(1025));

        return;
    }

    /**
     * Test of lastIdxSuffix method, of class BitVec.
     */
    @Test
    public void testLastIdxSuffix() {
        System.out.println("lastIdxSuffix");

        EfMsSeq instance;

        instance = new EfMsSeq();
        try {
            instance.lastIdxSuffix(0);
            fail();
        } catch(IndexOutOfBoundsException e) {
            assert true;
        }

        instance.append(0);
        try {
            instance.lastIdxSuffix(1);
            fail();
        } catch(IndexOutOfBoundsException e) {
            assert true;
        }

        instance = new EfMsSeq();
        instance.append(0, 1);
        assertTrue(instance.lastIdxSuffix(1) < 0);

        instance = new EfMsSeq();
        instance.append(1, 1);
        assertEquals(0, instance.lastIdxSuffix(1));

        instance = new EfMsSeq();
        instance.append(1, 0, 1, 0, 0, 1, 0, 1, 0, 1);
        assertEquals(5, instance.lastIdxSuffix(7));
        assertEquals(6, instance.lastIdxSuffix(8));
        assertEquals(7, instance.lastIdxSuffix(9));
        assertTrue(instance.lastIdxSuffix(1) < 0);
        assertTrue(instance.lastIdxSuffix(5) < 0);

        return;
    }

    /**
     * Test of nextBool method, of class EfMsSeq.
     *
     * @see <a href="https://oeis.org/A038219">A038219 sample list</a>
     */
    @Test
    public void testNextBool() {
        System.out.println("nextBool");

        EfMsSeq a038219 = new EfMsSeq();
        a038219.append(
            0, 1, 0, 0, 1, 1, 0, 1, 0, 1, 1, 1, 0, 0, 0, 1,
            0, 0, 0, 0, 1, 1, 1, 1, 0, 1, 1, 0, 0, 1, 0, 1,
            0, 0, 1, 0, 0, 1, 1, 1, 0, 1, 0, 0, 0, 1, 1, 0,
            0, 0, 0, 0, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 0,
            1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1,
            1, 0, 0, 0, 1, 1, 1, 0, 0, 1, 0, 0, 0, 1, 0, 1,
            0, 1, 0, 0, 0, 0, 0, 0, 1
        );

        EfMsSeq bs = new EfMsSeq();
        for (int i = 0; i < 105; i++) {
            int next = bs.nextBool();
            bs.append(next);
        }

        assertEquals(a038219.length(), bs.length());
        assertTrue(a038219.equals(bs));

        return;
    }

    /**
     * Test of hashCode method, of class BitVec.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        EfMsSeq instance1;
        EfMsSeq instance2;

        instance1 = new EfMsSeq();
        instance2 = new EfMsSeq();
        assertEquals(instance1.hashCode(), instance2.hashCode());
        assertEquals(0xac32ed25, instance1.hashCode());

        instance1.append(0);
        instance2.append(0);
        assertEquals(instance1.hashCode(), instance2.hashCode());
        assertEquals(0xac32ed25, instance1.hashCode());

        instance1.append(1, 0, 1);
        instance2.append(1, 0, 1);
        assertEquals(instance1.hashCode(), instance2.hashCode());
        assertEquals(0xac32ece1, instance1.hashCode());

        int[] arr;

        arr = new int[62];
        instance1.append(arr);
        instance2.append(arr);
        assertEquals(instance1.hashCode(), instance2.hashCode());

        return;
    }

    /**
     * Test of equals method, of class BitVec.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");

        EfMsSeq instance1;
        EfMsSeq instance2;

        instance1 = new EfMsSeq();
        assertTrue(instance1.equals(instance1));
        assertFalse(instance1.equals(null));
        assertFalse(instance1.equals("string"));

        instance2 = new EfMsSeq();
        assertTrue(instance1.equals(instance2));

        instance1.append(0);
        instance2.append(0, 1);
        assertFalse(instance1.equals(instance2));

        instance1.append(1);
        assertTrue(instance1.equals(instance2));

        instance1.append(0);
        instance2.append(1);
        assertFalse(instance1.equals(instance2));

        return;
    }

    /**
     * Test of toString method, of class BitVec.
     */
    @Test
    public void testToString() {
        System.out.println("toString");

        EfMsSeq instance;

        instance = new EfMsSeq();
        assertEquals("nil", instance.toString());

        instance.append(0);
        assertEquals("0", instance.toString());

        instance.append(1, 0, 1);
        assertEquals("0101", instance.toString());

        return;
    }

}

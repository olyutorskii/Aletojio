/*
 */

package io.github.olyutorskii.aletojio;

import io.github.olyutorskii.aletojio.rng.RndInt32;
import io.github.olyutorskii.aletojio.rng.dull.SeqRepeater;
import io.github.olyutorskii.aletojio.rng.dull.StepSequence;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 */
public class BoundRndTest {

    public BoundRndTest() {
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
     * Test of constructor, of class BoundRnd.
     */
    @Test
    public void testConstructor() {
        System.out.println("constructor");

        BoundRnd instance;
        RndInt32 src;

        src = StepSequence.createCounter();

        instance = new BoundRnd(src, 2);
        assertNotNull(instance);

        try {
            instance = new BoundRnd(null, 3);
            fail();
        } catch(NullPointerException e) {
            assert true;
        }

        try {
            instance = new BoundRnd(src, 1);
            fail();
        } catch(IllegalArgumentException e) {
            assert true;
        }

        assert instance == instance;

        return;
    }

    /**
     * Test of next method, of class BoundRnd.
     */
    @Test
    public void testNext() {
        System.out.println("next");

        BoundRnd instance;
        RndInt32 src;
        int[] seq;

        long b30 = 1L << 30;
        seq = new int[]{
                0,
                (int)(b30 - 1),     //1_073_741_823L,
                (int)(b30),         //1_073_741_824L,
                (int)(b30 * 2 - 1), //2_147_483_647L,
                (int)(b30 * 2),     //2_147_483_648L,
                (int)(b30 * 3 - 1), //3_221_225_471L,
                (int)(b30 * 3),     //3_221_225_472L,
                (int)(b30 * 4 - 1), //4_294_967_295L,
        };
        src = new SeqRepeater(seq);
        instance = new BoundRnd(src, 4);

        assertEquals(0, instance.next());
        assertEquals(0, instance.next());
        assertEquals(1, instance.next());
        assertEquals(1, instance.next());
        assertEquals(2, instance.next());
        assertEquals(2, instance.next());
        assertEquals(3, instance.next());
        assertEquals(3, instance.next());
        assertEquals(0, instance.next());

        seq = new int[]{
                (int)1_431_655_765L,
                (int)1_431_655_766L,
                (int)2_863_311_530L,
                (int)2_863_311_531L,
                (int)4_294_967_295L,
        };
        src = new SeqRepeater(seq);
        instance = new BoundRnd(src, 3);

        assertEquals(0, instance.next());
        assertEquals(1, instance.next());
        assertEquals(1, instance.next());
        assertEquals(2, instance.next());
        assertEquals(2, instance.next());
        assertEquals(0, instance.next());

        seq = new int[]{
            0,  // reject
            1,
            (int)2_863_311_531L,
        };
        src = new SeqRepeater(seq);
        instance = new BoundRnd(src, 3);

        assertEquals(0, instance.next());
        assertEquals(2, instance.next());

        return;
    }

}

/*
 */

package io.github.olyutorskii.aletojio.rng.xorsft;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 */
public class XorShift128Test {

    public XorShift128Test() {
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
     * Test of nextInt32 method, of class XorShift128.
     */
    @Test
    public void testNextInt32() {
        System.out.println("nextInt32");


        XorShift128 instance;
        instance = new XorShift128();

        assertEquals(33, instance.nextInt32());
        assertEquals(33, instance.nextInt32());
        assertEquals(33, instance.nextInt32());
        assertEquals(33, instance.nextInt32());
        assertEquals(1056, instance.nextInt32());

        instance.setSeed(0x12345678, 0xffeeddcc, 0xa55a5aa5, 0x456789ab);

        assertEquals( 299653944, instance.nextInt32());
        assertEquals( 334044194, instance.nextInt32());
        assertEquals( 502854455, instance.nextInt32());
        assertEquals(-194980201, instance.nextInt32());
        assertEquals(-566931493, instance.nextInt32());

        return;
    }

    /**
     * Test of setSeed method, of class XorShift128.
     */
    @Test
    public void testSetSeed() {
        System.out.println("setSeed");

        XorShift128 instance;
        instance = new XorShift128();

        try {
            instance.setSeed(0, 0, 0, 0);
            fail();
        } catch(IllegalArgumentException e) {
            assert true;
        }

        return;
    }

}

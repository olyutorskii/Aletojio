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
public class XorShift64Test {

    public XorShift64Test() {
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
     * Test of nextInt64 method, of class XorShift64.
     */
    @Test
    public void testNextInt64() {
        System.out.println("nextInt64");

        XorShift64 instance;
        instance = new XorShift64();

        assertEquals(          1082269761L, instance.nextInt64());
        assertEquals( 1152992998833853505L, instance.nextInt64());
        assertEquals(-7269227409276787159L, instance.nextInt64());
        assertEquals( -768720241707614171L, instance.nextInt64());
        assertEquals(-8787613929710185883L, instance.nextInt64());

        instance.setSeed(1L);
        assertEquals(          1082269761L, instance.nextInt64());

        return;
    }

    /**
     * Test of setSeed method, of class XorShift64.
     */
    @Test
    public void testSetSeed() {
        System.out.println("setSeed");

        XorShift64 instance;
        instance = new XorShift64();

        try {
            instance.setSeed(0);
            fail();
        } catch(IllegalArgumentException e) {
            assert true;
        }

        return;
    }

}

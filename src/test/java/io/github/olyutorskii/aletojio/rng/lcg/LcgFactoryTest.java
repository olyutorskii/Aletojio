/*
 */

package io.github.olyutorskii.aletojio.rng.lcg;

import io.github.olyutorskii.aletojio.rng.RndInt31;
import io.github.olyutorskii.aletojio.rng.RndInt32;
import java.util.Random;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 */
public class LcgFactoryTest {

    public LcgFactoryTest() {
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
     * Test of createRandu method, of class LcgFactory.
     *
     * https://oeis.org/A096555
     */
    @Test
    public void testCreateRandu() {
        System.out.println("createRandu");

        RndInt31 rnd;

        rnd = LcgFactory.createRandu();

        int[] testSeq = {
                 65539,
                393225,
               1769499,
               7077969,
              26542323,
              95552217,
             334432395,
            1146624417,
            1722371299,
              14608041,
        };

        for(int ti : testSeq) {
            assertEquals(ti, rnd.nextInt31());
        }

        return;
    }

    /**
     * Test of createMinstd0 method, of class LcgFactory.
     *
     * <p>https://oeis.org/A096550
     */
    @Test
    public void testCreateMinStd0() {
        System.out.println("createMinstd0");

        RndInt31 rnd;

        rnd = LcgFactory.createMinStd0();

        int[] testSeq = {
                 16807,
             282475249,
            1622650073,
             984943658,
            1144108930,
             470211272,
             101027544,
            1457850878,
            1458777923,
            2007237709,
        };

        for(int ti : testSeq) {
            assertEquals(ti, rnd.nextInt31());
        }

        return;
    }

    /**
     * Test of createMinstd method, of class LcgFactory.
     *
     * https://oeis.org/A221556
     */
    @Test
    public void testCreateMinStd() {
        System.out.println("createMinstd");

        RndInt31 rnd;

        rnd = LcgFactory.createMinStd();

        int[] testSeq = {
                 48271,
             182605794,
            1291394886,
            1914720637,
            2078669041,
             407355683,
            1105902161,
             854716505,
             564586691,
            1596680831,
        };

        for(int ti : testSeq) {
            assertEquals(ti, rnd.nextInt31());
        }

        return;
    }

    /**
     * Test of createGlibc method, of class LcgFactory.
     *
     * https://oeis.org/A096553
     */
    @Test
    public void testCreateGlibc() {
        System.out.println("createGlibc");

        RndInt31 rnd;

        rnd = LcgFactory.createGlibc();

        int[] testSeq = {
            1103527590,
             377401575,
             662824084,
            1147902781,
            2035015474,
             368800899,
            1508029952,
             486256185,
            1062517886,
             267834847,
        };

        for(int ti : testSeq) {
            assertEquals(ti, rnd.nextInt31());
        }

        return;
    }

    /**
     * Test of createLrand48 method, of class LcgFactory.
     *
     * <p>Apple Clang version 11.0.0 x86_64-apple-darwin19.6.0
     */
    @Test
    public void testCreateLrand48() {
        System.out.println("createLrand48");

        RndInt31 rnd;

        rnd = LcgFactory.createLrand48();

//        assertFalse(rnd.isMcg());
//        assertEquals(31, rnd.getResultBits());

        int[] testSeq = {
             851401618,
            1804928587,
             758783491,
             959030623,
             684387517,
            1903590565,
              33463914,
            1254324197,
             342241519,
             824023566,
        };

        for(int ti : testSeq) {
            assertEquals(ti, rnd.nextInt31());
        }

        return;
    }

    /**
     * Test of createMrand48 method, of class LcgFactory.
     *
     * <p>Apple Clang version 11.0.0 x86_64-apple-darwin19.6.0
     */
    @Test
    public void testCreateMrand48() {
        System.out.println("createMrand48");

        RndInt32 rnd;

        rnd = LcgFactory.createMrand48();

//        assertFalse(rnd.isMcg());
//        assertEquals(32, rnd.getResultBits());

        int[] testSeq = {
             1702803237,
             -685110122,
             1517566982,
             1918061247,
             1368775034,
             -487786166,
               66927828,
            -1786318902,
              684483038,
             1648047133,
        };

        for(int ti : testSeq) {
            assertEquals(ti, rnd.nextInt32());
        }

        return;
    }

    /**
     * Test of seedScrambler4M48 method, of class LcgFactory.
     *
     * @see java.util.Random#setSeed(long)
     */
    @Test
    public void testSeedScrambler4M48() {
        System.out.println("seedScrambler4M48");

        LcgRndInt32 rnd;

        rnd = LcgFactory.createMrand48();
        rnd.setSeed(LcgFactory.seedScrambler4M48(1L));

        int[] testSeq = {
            -1155869325,
              431529176,
             1761283695,
             1749940626,
              892128508,
              155629808,
             1429008869,
            -1465154083,
             -138487339,
            -1242363800,
        };

        for(int ti : testSeq) {
            assertEquals(ti, rnd.nextInt32());
        }

        rnd = LcgFactory.createMrand48();
        rnd.setSeed(LcgFactory.seedScrambler4M48(456L));

        Random rndJ;
        rndJ = new Random(456L);

        assertEquals(rndJ.nextInt(), rnd.nextInt32());
        assertEquals(rndJ.nextInt(), rnd.nextInt32());

        return;
    }

    /**
     * Test of setSeed method, of class AbstractLcg.
     */
    @Test
    public void testSetSeed() {
        System.out.println("setSeed");

        LcgRndInt31 rnd;

        rnd = LcgFactory.createMinStd();

        rnd.setSeed(1L);
        rnd.setSeed(0L);

        try {
            rnd.setSeed(-1L);
            fail();
        } catch (IllegalArgumentException e) {
            assert true;
        }

        try {
            rnd.setSeed(Long.MIN_VALUE);
            fail();
        } catch (IllegalArgumentException e) {
            assert true;
        }

        return;
    }

}

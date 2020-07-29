import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

class ListaSimplesDesordenadaTest {


    ListaSimplesDesordenada<Integer> test = new ListaSimplesDesordenada<>();


    @Test
    void insiraNoFim()
    {
        try {
            test.insiraNoFim(10);
            assertEquals((Integer)10, test.getDoInicio());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void removaDoInicio()
    {
        try {
            test.insiraNoFim(10);
            test.insiraNoFim(11);
            test.removaDoInicio();
            assertEquals((Integer)11, test.getDoInicio());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void getDoInicio() {
        try {
            test.insiraNoFim(10);
            assertEquals((Integer)10, test.getDoInicio());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void getQtd() {
        try {
            test.insiraNoFim(10);
            assertEquals(1, test.getQtd());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testEquals()
    {
        assertEquals(true, test.equals(test.clone()));
    }

    @Test
    void testClone()
    {
        assertEquals(test, test.clone());
    }
}
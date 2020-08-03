package test;

import org.junit.jupiter.api.Test;

import src.Resultado;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

class ResultadoTest {

    Resultado test;

    {
        try {
            test = new Resultado(19193, 2, 10, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getRa()
    {
        assertEquals(19193, test.getRa());
    }

    @Test
    void getCodDisciplina()
    {
        assertEquals(2, test.getCod());
    }

    @Test
    void getNota()
    {
        assertEquals(10.0, test.getNota(), 0);
    }

    @Test
    void getFrequencia()
    {
        assertEquals(1.0, test.getFreq(), 0);
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
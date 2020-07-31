package test;

import org.junit.jupiter.api.Test;

import src.Fila;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

class FilaTest {

    Fila<Integer> test = new Fila<>();

    @Test
    void addItem()
    {
        try{
            test.addItem(12);
            assertEquals((Integer)12, test.recuperarItem());
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Test
    void recuperarItem()
    {
        try
        {
            test.addItem(12);
            assertEquals((Integer) 12, test.recuperarItem());
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Test
    void removerItem()
    {
        try{
            test.addItem(12);
            test.addItem(13);
            test.removerItem();
            assertEquals((Integer)13, test.recuperarItem());
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Test
    void getQtd()
    {
        assertEquals(0, test.getQtd());
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
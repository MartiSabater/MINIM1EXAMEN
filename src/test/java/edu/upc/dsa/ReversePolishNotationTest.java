package edu.upc.dsa;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ReversePolishNotationTest {

    private ReversePolishNotation rpn;

    @Before
    public void setUp() {
        this.rpn = new ReversePolishNotationImpl();
    }

    @Test
    public void testOperacioExempleEnunciat() {
        double resultat = rpn.FerOperacio("5 1 2 + 4 * + 3 -");
        assertEquals(14.0, resultat, 0.001);
    }

    @Test
    public void testSumaSimple() {
        double resultat = rpn.FerOperacio("2 3 +");
        assertEquals(5.0, resultat, 0.001);
    }

    @Test
    public void testRestaSimple() {
        double resultat = rpn.FerOperacio("10 4 -");
        assertEquals(6.0, resultat, 0.001);
    }

    @Test
    public void testMultiplicacioSimple() {
        double resultat = rpn.FerOperacio("2 3 *");
        assertEquals(6.0, resultat, 0.001);
    }

    @Test
    public void testDivisioSimple() {
        double resultat = rpn.FerOperacio("8 2 /");
        assertEquals(4.0, resultat, 0.001);
    }

    @Test
    public void testOperacioMesComplexa() {
        double resultat = rpn.FerOperacio("2 3 + 4 * 5 -");
        assertEquals(15.0, resultat, 0.001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExpressioBuida() {
        rpn.FerOperacio("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExpressioNull() {
        rpn.FerOperacio(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOperandsInsuficients() {
        rpn.FerOperacio("5 +");
    }

    @Test(expected = ArithmeticException.class)
    public void testDivisioPerZero() {
        rpn.FerOperacio("8 0 /");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTokenInvalid() {
        rpn.FerOperacio("5 2 hola +");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testParentesisNoPermesos() {
        rpn.FerOperacio("( 2 3 + )");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExpressioMalFormada() {
        rpn.FerOperacio("5 2 3 +");
    }
}
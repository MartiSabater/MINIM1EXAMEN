package edu.upc.dsa;

import edu.upc.dsa.models.Alumne;
import edu.upc.dsa.models.Institud;
import edu.upc.dsa.models.OperacioMatematica;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class MathManagerTest {

    private MathManager mathManager;

    private Institud eetac;
    private Institud santMiquel;

    private Alumne marti;
    private Alumne queralt;
    private Alumne yago;
    private Alumne carla;
    private Alumne hector;
    private Alumne marta;

    @Before
    public void setUp() {
        this.mathManager = MathManagerImpl.getInstance();


        this.mathManager.clear();

        // Crear instituts
        this.eetac = mathManager.AddInstitud("EETAC");
        this.santMiquel = mathManager.AddInstitud("Sant Miquel dels Sants de Vic");

        // Crear alumnes
        this.marti = mathManager.AddAlumne("Marti", eetac.getId());
        this.queralt = mathManager.AddAlumne("Queralt", santMiquel.getId());
        this.yago = mathManager.AddAlumne("Yago", santMiquel.getId());
        this.carla = mathManager.AddAlumne("Carla", eetac.getId());
        this.hector = mathManager.AddAlumne("Hector", eetac.getId());
        this.marta = mathManager.AddAlumne("Marta", eetac.getId());
    }

    @After
    public void tearDown() {
        if (this.mathManager != null) {
            this.mathManager.clear();
        }
    }

    @Test
    public void testAddAlumne() {
        Alumne alumne = mathManager.AddAlumne("Joan", eetac.getId());

        assertNotNull(alumne);
        assertEquals("Joan", alumne.getNom());
        assertEquals(eetac.getId(), alumne.getInstitutId());
    }

    @Test
    public void testGetAlumne() {
        Alumne resultat = mathManager.GetAlumne(marti.getId());

        assertNotNull(resultat);
        assertEquals(marti.getId(), resultat.getId());
        assertEquals("Marti", resultat.getNom());
    }

    @Test
    public void testGetAlumneNoExisteix() {
        Alumne resultat = mathManager.GetAlumne("ALUMNE_NO_EXISTEIX");
        assertNull(resultat);
    }

    @Test
    public void testAddInstitud() {
        Institud institud = mathManager.AddInstitud("Institut Prova");

        assertNotNull(institud);
        assertEquals("Institut Prova", institud.getNom());
    }

    @Test
    public void testGetInstitud() {
        Institud resultat = mathManager.GetInstitud(eetac.getId());

        assertNotNull(resultat);
        assertEquals(eetac.getId(), resultat.getId());
        assertEquals("EETAC", resultat.getNom());
    }

    @Test
    public void testGetInstitudNoExisteix() {
        Institud resultat = mathManager.GetInstitud("INSTITUT_NO_EXISTEIX");
        assertNull(resultat);
    }

    @Test
    public void testRequerirOperacioMatematica() {
        OperacioMatematica op = mathManager.requerirOperacioMatematica("OP1", "2 3 +", marti.getId(), eetac.getId(), false);

        assertNotNull(op);
        assertEquals("OP1", op.getId());
        assertEquals("2 3 +", op.getExpresioRPN());
        assertEquals(marti.getId(), op.getAlumneId());
        assertEquals(eetac.getId(), op.getInstitutId());
        assertFalse(op.isProcessada());
    }

    @Test
    public void testProcesarOperacioMatematica() {
        mathManager.requerirOperacioMatematica("OP1", "2 3 +", marti.getId(), eetac.getId(), false);

        OperacioMatematica op = mathManager.procesarOperacioMatematica();

        assertNotNull(op);
        assertEquals("OP1", op.getId());
        assertTrue(op.isProcessada());
    }

    @Test
    public void testProcesarOperacioMatematicaSensePendents() {
        OperacioMatematica op = mathManager.procesarOperacioMatematica();
        assertNull(op);
    }

    @Test
    public void testProcesarOperacionsEnOrdreFIFO() {
        mathManager.requerirOperacioMatematica("OP1", "2 3 +", marti.getId(), eetac.getId(), false);
        mathManager.requerirOperacioMatematica("OP2", "4 5 +", carla.getId(), eetac.getId(), false);
        mathManager.requerirOperacioMatematica("OP3", "7 2 -", queralt.getId(), santMiquel.getId(), false);

        OperacioMatematica op1 = mathManager.procesarOperacioMatematica();
        OperacioMatematica op2 = mathManager.procesarOperacioMatematica();
        OperacioMatematica op3 = mathManager.procesarOperacioMatematica();

        assertEquals("OP1", op1.getId());
        assertEquals("OP2", op2.getId());
        assertEquals("OP3", op3.getId());
    }

    @Test
    public void testLlistarOperacionsPerAlumne() {
        mathManager.requerirOperacioMatematica("OP1", "2 3 +", marti.getId(), eetac.getId(), false);
        mathManager.requerirOperacioMatematica("OP2", "4 5 +", marti.getId(), eetac.getId(), false);
        mathManager.requerirOperacioMatematica("OP3", "8 2 /", marti.getId(), eetac.getId(), false);

        List<OperacioMatematica> llista = mathManager.llistarOperacionsPerAlumne(marti.getId());

        assertNotNull(llista);
        assertEquals(3, llista.size());
    }

    @Test
    public void testLlistarOperacionsPerAlumneSenseOperacions() {
        List<OperacioMatematica> llista = mathManager.llistarOperacionsPerAlumne(hector.getId());

        assertNotNull(llista);
        assertEquals(0, llista.size());
    }

    @Test
    public void testLlistarOperacionsPerInstitud() {
        mathManager.requerirOperacioMatematica("OP1", "2 3 +", marti.getId(), eetac.getId(), false);
        mathManager.requerirOperacioMatematica("OP2", "4 5 +", carla.getId(), eetac.getId(), false);
        mathManager.requerirOperacioMatematica("OP3", "7 2 -", marti.getId(), eetac.getId(), false);

        List<OperacioMatematica> llista = mathManager.llistarOperacionsPerInstitud(eetac.getId());

        assertNotNull(llista);
        assertEquals(3, llista.size());
    }

    @Test
    public void testLlistarOperacionsPerInstitudSenseOperacions() {
        Institud nouInstitut = mathManager.AddInstitud("Institut Nou");

        List<OperacioMatematica> llista = mathManager.llistarOperacionsPerInstitud(nouInstitut.getId());

        assertNotNull(llista);
        assertEquals(0, llista.size());
    }

    @Test
    public void testLlistarInstitudsOrdenats() {
        mathManager.requerirOperacioMatematica("OP1", "2 3 +", marti.getId(), eetac.getId(), false);
        mathManager.requerirOperacioMatematica("OP2", "4 5 +", carla.getId(), eetac.getId(), false);
        mathManager.requerirOperacioMatematica("OP3", "8 2 /", hector.getId(), eetac.getId(), false);

        mathManager.requerirOperacioMatematica("OP4", "7 2 -", queralt.getId(), santMiquel.getId(), false);
        mathManager.requerirOperacioMatematica("OP5", "9 3 /", yago.getId(), santMiquel.getId(), false);

        mathManager.procesarOperacioMatematica();
        mathManager.procesarOperacioMatematica();
        mathManager.procesarOperacioMatematica();
        mathManager.procesarOperacioMatematica();
        mathManager.procesarOperacioMatematica();

        List<Map.Entry<String, Integer>> ranking = mathManager.llistarInstituds();

        assertNotNull(ranking);
        assertEquals(2, ranking.size());

        assertEquals(eetac.getId(), ranking.get(0).getKey());
        assertEquals(Integer.valueOf(3), ranking.get(0).getValue());

        assertEquals(santMiquel.getId(), ranking.get(1).getKey());
        assertEquals(Integer.valueOf(2), ranking.get(1).getValue());
    }

    @Test
    public void testSingleton() {
        MathManager altreManager = MathManagerImpl.getInstance();
        assertSame(mathManager, altreManager);
    }
}
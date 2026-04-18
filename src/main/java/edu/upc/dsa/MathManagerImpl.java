package edu.upc.dsa;

import edu.upc.dsa.models.Alumne;
import edu.upc.dsa.models.Institud;
import edu.upc.dsa.models.OperacioMatematica;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.HashMap;
import java.util.Map;

import io.swagger.models.auth.In;
import org.apache.log4j.Logger;

public class MathManagerImpl implements MathManager {

    private static MathManager instance;
    protected List<Alumne> alumnes;
    protected List<Institud> instituds;
    protected Queue<OperacioMatematica> operacions;
    protected List<OperacioMatematica> historialOperacions;
    protected HashMap<String, Integer> operacionsPerInstitud;
    protected double resultat;

    final static Logger logger = Logger.getLogger(MathManagerImpl.class);

    private MathManagerImpl() {
        logger.info("Inici constructor MathManagerImpl()");

        this.alumnes = new LinkedList<>();
        this.instituds = new LinkedList<>();
        this.operacions = new LinkedList<>();
        this.historialOperacions = new LinkedList<>();
        this.operacionsPerInstitud = new HashMap<>();

        logger.info("Fi constructor MathManagerImpl(). alumnes=" + alumnes.size()
                + ", instituds=" + instituds.size()
                + ", operacions=" + operacions.size());
    }

    public static MathManager getInstance() {
        logger.info("Inici getInstance()");
        if (instance == null) {
            instance = new MathManagerImpl();
            logger.info("S'ha creat una nova instancia de MathManagerImpl");
        }
        logger.info("Fi getInstance(). instance=" + instance);
        return instance;
    }

    public Alumne AddAlumne(String nom, String institutId) {
        logger.info("Inici AddAlumne(nom=" + nom + ", institutId=" + institutId + ")");

        try {
            Alumne alumne = new Alumne(nom, institutId);
            this.alumnes.add(alumne);

            logger.info("Fi AddAlumne(). alumne creat amb id=" + alumne.getId());
            return alumne;
        } catch (Exception e) {
            logger.error("Error a AddAlumne(nom=" + nom + ", institutId=" + institutId + ")", e);
            throw e;
        }
    }

    public Alumne GetAlumne(String id) {
        logger.info("Inici GetAlumne(id=" + id + ")");

        try {
            for (Alumne a : this.alumnes) {
                if (a.getId().equals(id)) {
                    logger.info("Fi GetAlumne(). Alumne trobat amb id=" + id);
                    return a;
                }
            }

            logger.error("GetAlumne(): no s'ha trobat cap alumne amb id=" + id);
            logger.info("Fi GetAlumne(). retorn=null");
            return null;
        } catch (Exception e) {
            logger.error("Error a GetAlumne(id=" + id + ")", e);
            throw e;
        }
    }

    public Institud AddInstitud(String nom) {
        logger.info("Inici AddInstitud(nom=" + nom + ")");

        try {
            Institud institud = new Institud(nom);
            this.instituds.add(institud);
            this.operacionsPerInstitud.put(institud.getId(), 0);

            logger.info("Fi AddInstitud(). institud creat amb id=" + institud.getId());
            return institud;
        } catch (Exception e) {
            logger.error("Error a AddInstitud(nom=" + nom + ")", e);
            throw e;
        }
    }

    public Institud GetInstitud(String id) {
        logger.info("Inici GetInstitud(id=" + id + ")");

        try {
            for (Institud i : this.instituds) {
                if (i.getId().equals(id)) {
                    logger.info("Fi GetInstitud(). Institud trobat amb id=" + id);
                    return i;
                }
            }

            logger.error("GetInstitud(): no s'ha trobat cap institud amb id=" + id);
            logger.info("Fi GetInstitud(). retorn=null");
            return null;
        } catch (Exception e) {
            logger.error("Error a GetInstitud(id=" + id + ")", e);
            throw e;
        }
    }

    // Requerir Operacio Matematica
    public OperacioMatematica requerirOperacioMatematica(String id, String expresioRPN, String alumneId, String institudId, boolean processada) {
        logger.info("Inici requerirOperacioMatematica(id=" + id + ", expresioRPN=" + expresioRPN + ", alumneId=" + alumneId + ", institudId=" + institudId + ", processada=" + processada + ")");

        try {
            processada = false;
            resultat = 0;
            OperacioMatematica operacio = new OperacioMatematica(id, expresioRPN, resultat, alumneId, institudId, processada);
            this.operacions.add(operacio);
            this.historialOperacions.add(operacio);

            logger.info("Fi requerirOperacioMatematica(). operacio afegida amb id=" + operacio.getId());
            return operacio;
        } catch (Exception e) {
            logger.error("Error a requerirOperacioMatematica(id=" + id + ")", e);
            throw e;
        }
    }

    // Procesar Operacio matematica
    public OperacioMatematica procesarOperacioMatematica() {
        logger.info("Inici procesarOperacioMatematica()");

        try {
            if (this.operacions.isEmpty()) {
                logger.error("No hi ha operacions pendents per processar");
                logger.info("Fi procesarOperacioMatematica(). retorn=null");
                return null;
            }

            OperacioMatematica operacio = this.operacions.poll();
            ReversePolishNotation rpn = new ReversePolishNotationImpl();
            resultat = rpn.FerOperacio(operacio.getExpresioRPN());

            String institudId = operacio.getInstitutId();

            if (!operacionsPerInstitud.containsKey(institudId)) {
                logger.error("L'institud amb id=" + institudId + " no existeix al mapa operacionsPerInstitud");
                return null;
            }

            operacionsPerInstitud.put(institudId, operacionsPerInstitud.get(institudId) + 1);

            operacio.setResultat(resultat);
            operacio.setProcessada(true);

            logger.info("Fi procesarOperacioMatematica(). operacio id=" + operacio.getId() + ", resultat=" + resultat + ", processada=" + operacio.isProcessada());

            return operacio;
        } catch (Exception e) {
            logger.error("Error a procesarOperacioMatematica()", e);
            throw e;
        }
    }

    // Listado de Operaciones Matematica Por instituto
    public List<OperacioMatematica> llistarOperacionsPerInstitud(String institutId) {
        logger.info("Inici llistarOperacionsPerInstitud(institutId=" + institutId + ")");

        try {
            List<OperacioMatematica> operacionsPerInstitut = new LinkedList<>();
            for (OperacioMatematica o : this.historialOperacions) {
                if (o.getInstitutId().equals(institutId)) {
                    operacionsPerInstitut.add(o);
                }
            }

            logger.info("Fi llistarOperacionsPerInstitud(). nombre operacions=" + operacionsPerInstitut.size());
            return operacionsPerInstitut;
        } catch (Exception e) {
            logger.error("Error a llistarOperacionsPerInstitud(institutId=" + institutId + ")", e);
            throw e;
        }
    }

    // Listado de Operaciones Matematica Por alumne
    public List<OperacioMatematica> llistarOperacionsPerAlumne(String alumneId) {
        logger.info("Inici llistarOperacionsPerAlumne(alumneId=" + alumneId + ")");

        try {
            List<OperacioMatematica> operacionsPerAlumne = new LinkedList<>();
            for (OperacioMatematica o : this.historialOperacions) {
                if (o.getAlumneId().equals(alumneId)) {
                    operacionsPerAlumne.add(o);
                }
            }

            logger.info("Fi llistarOperacionsPerAlumne(). nombre operacions=" + operacionsPerAlumne.size());
            return operacionsPerAlumne;
        } catch (Exception e) {
            logger.error("Error a llistarOperacionsPerAlumne(alumneId=" + alumneId + ")", e);
            throw e;
        }
    }

    // Llistat d'instituds ordenats de forma descendent pel nombre d'operacions procesades
    public List<Map.Entry<String, Integer>> llistarInstituds() {
        logger.info("Inici llistarInstituds()");

        try {
            List<Map.Entry<String, Integer>> list = new LinkedList<>(operacionsPerInstitud.entrySet());
            list.sort((a, b) -> b.getValue().compareTo(a.getValue()));

            logger.info("Fi llistarInstituds(). mida=" + list.size());

            logger.info("Id del Institut, Nombre d operacions => " + list);
            return list;
        } catch (Exception e) {
            logger.error("Error a llistarInstituds()", e);
            throw e;
        }
    }

    public void clear() {
        this.alumnes.clear();
        this.instituds.clear();
        this.operacions.clear();
        this.operacionsPerInstitud.clear();
    }
}

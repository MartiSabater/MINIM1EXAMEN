package edu.upc.dsa;

import edu.upc.dsa.models.Alumne;
import edu.upc.dsa.models.Institud;
import edu.upc.dsa.models.OperacioMatematica;

import java.util.List;
import java.util.Map;

//Metodes Per gestionar el Singelton
public interface MathManager {

    //Gestio d'alumnes
    public Alumne AddAlumne(String nom, String institutId);
    public Alumne GetAlumne(String id);

    //Gestio d'instituds
    public Institud AddInstitud(String nom);
    public Institud GetInstitud(String id);

    //Requistits del anunciat
    //Resrir Operacio matecmatica
    public OperacioMatematica requerirOperacioMatematica(String id, String expresioRPN, String alumneId, String institudId, boolean processada);
    //Procesar Operacio matecmatica
    public OperacioMatematica procesarOperacioMatematica();
    //Llista d'opreracions per institut
    public List<OperacioMatematica> llistarOperacionsPerInstitud(String institutId);
    //Lista d'opreracions per alumne
    public List<OperacioMatematica> llistarOperacionsPerAlumne(String alumneId);
    //Llista d'instituds ordenats de forma decendentment pel nombre d'operacions procesades
    public List<Map.Entry<String, Integer>> llistarInstituds();
    
    //Metide per fer tests
    public void clear();

}

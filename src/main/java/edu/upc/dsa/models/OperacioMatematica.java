package edu.upc.dsa.models;



public class OperacioMatematica {

    String id;
    String expresioRPN;
    double resultat;
    String alumneId;
    String institutId;
    boolean processada;
    
    static int lastId;


    public OperacioMatematica(String id, String expresioRPN, double resultat, String alumneId, String institutId, boolean processada) {

        this.setProcessada(processada);
        this.setId(id);
        this.setExpresioRPN(expresioRPN);
        this.setResultat(resultat);
        this.setAlumneId(alumneId);
        this.setInstitutId(institutId);
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id=id;
    }


    public String getExpresioRPN() {
        return expresioRPN;
    }

    public void setExpresioRPN(String expresioRPN) {
        this.expresioRPN = expresioRPN;
    }

    public double getResultat() {
        return resultat;
    }

    public void setResultat(double resultat) {
        this.resultat = resultat;
    }

    public String getAlumneId() {
        return alumneId;
    }

    public void setAlumneId(String alumneId) {
        this.alumneId = alumneId;
    }

    public String getInstitutId() {
        return institutId;
    }

    public void setInstitutId(String institutId) {
        this.institutId = institutId;
    }

    public void setProcessada(boolean processada) {
        this.processada = processada;
    }

    public boolean isProcessada() {
        return processada;
    }

    @Override
    public String toString() {
        return "Track [id="+id+", expresioRPN=" + expresioRPN + ", resultat=" + resultat + ", alumneId=" + alumneId + ", institutId=" + institutId + ", processada=" + processada + "]";
    }

}

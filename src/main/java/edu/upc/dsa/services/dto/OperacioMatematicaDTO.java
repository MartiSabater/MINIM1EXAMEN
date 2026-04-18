package edu.upc.dsa.services.dto;

import edu.upc.dsa.models.OperacioMatematica;

public class OperacioMatematicaDTO {

    private String id;
    private String expresioRPN;
    private double resultat;
    private String alumneId;
    private String institutId;
    private boolean processada;

    public OperacioMatematicaDTO() {
    }

    public OperacioMatematicaDTO(OperacioMatematica operacio) {
        this.id = operacio.getId();
        this.expresioRPN = operacio.getExpresioRPN();
        this.resultat = operacio.getResultat();
        this.alumneId = operacio.getAlumneId();
        this.institutId = operacio.getInstitutId();
        this.processada = operacio.isProcessada();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public boolean isProcessada() {
        return processada;
    }

    public void setProcessada(boolean processada) {
        this.processada = processada;
    }
}
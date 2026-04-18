package edu.upc.dsa.services.dto;

public class InstitudRankingDTO {

    private String institutId;
    private int operacionsProcessades;

    public InstitudRankingDTO() {
    }

    public InstitudRankingDTO(String institutId, int operacionsProcessades) {
        this.institutId = institutId;
        this.operacionsProcessades = operacionsProcessades;
    }

    public String getInstitutId() {
        return institutId;
    }

    public void setInstitutId(String institutId) {
        this.institutId = institutId;
    }

    public int getOperacionsProcessades() {
        return operacionsProcessades;
    }

    public void setOperacionsProcessades(int operacionsProcessades) {
        this.operacionsProcessades = operacionsProcessades;
    }
}
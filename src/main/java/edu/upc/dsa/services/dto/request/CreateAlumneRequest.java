package edu.upc.dsa.services.dto.request;

public class CreateAlumneRequest {

    private String nom;
    private String institutId;

    public CreateAlumneRequest() {
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getInstitutId() {
        return institutId;
    }

    public void setInstitutId(String institutId) {
        this.institutId = institutId;
    }
}
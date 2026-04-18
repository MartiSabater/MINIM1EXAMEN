package edu.upc.dsa.services.dto;

import edu.upc.dsa.models.Alumne;

public class AlumneDTO {

    private String id;
    private String nom;
    private String institutId;

    public AlumneDTO() {
    }

    public AlumneDTO(Alumne alumne) {
        this.id = alumne.getId();
        this.nom = alumne.getNom();
        this.institutId = alumne.getInstitutId();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
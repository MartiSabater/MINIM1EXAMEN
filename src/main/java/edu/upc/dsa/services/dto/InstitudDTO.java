package edu.upc.dsa.services.dto;

import edu.upc.dsa.models.Institud;

public class InstitudDTO {

    private String id;
    private String nom;

    public InstitudDTO() {
    }

    public InstitudDTO(Institud institud) {
        this.id = institud.getId();
        this.nom = institud.getNom();
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
}
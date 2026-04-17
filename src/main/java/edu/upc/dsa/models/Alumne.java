package edu.upc.dsa.models;

import edu.upc.dsa.util.RandomUtils;

public class Alumne {

    String id;
    String nom;
    String institutId;

    public Alumne() {
        this.setId(RandomUtils.getId());
    }
    public Alumne(String nom, String institutId) {
        this(null, nom, institutId);
    }

    public Alumne(String id, String nom, String institutId) {
        this();
        if (id != null) this.setId(id);
        this.setNom(nom);
        this.setInstitutId(institutId);
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id=id;
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

    @Override
    public String toString() {
        return "Alumne [id=" + id + ", nom=" + nom + ", institutId=" + institutId +"]";
    }

}
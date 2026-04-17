package edu.upc.dsa.models;

import edu.upc.dsa.util.RandomUtils;

public class Institud {

    String id;
    String nom;
    
    static int lastId;

    public Institud() {
        this.setId(RandomUtils.getId());
    }
    public Institud(String nom) {
        this(null, nom);
    }

    public Institud(String id, String nom) {
        this();
        if (id != null) this.setId(id);
        this.setNom(nom);
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

    @Override
    public String toString() {
        return "Track [id="+id+", nom=" + nom +"]";
    }

}
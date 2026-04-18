package edu.upc.dsa.services.dto.request;

public class CreateOperacioRequest {

    private String id;
    private String expresioRPN;
    private String alumneId;
    private String institutId;

    public CreateOperacioRequest() {
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
}
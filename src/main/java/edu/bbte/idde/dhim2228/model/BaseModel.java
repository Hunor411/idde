package edu.bbte.idde.dhim2228.model;

public abstract class BaseModel {
    protected Long id;

    public BaseModel(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

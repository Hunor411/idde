package edu.bbte.idde.dhim2228.model;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Token extends BaseEntity {
    private String value;
    private Type type;
    private Entities entity;
}

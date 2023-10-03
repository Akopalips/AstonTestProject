package ru.aston.testproj.model;

import lombok.Data;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * @author tuspring
 */
@Entity
@Table
@Data
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name;

    private String PIN;

    private Long founds;//todo по идее это два спареных целочисленных
}

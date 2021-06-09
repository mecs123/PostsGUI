package com.codigo.backendcursojava.Entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity(name = "exposure")
public class ExposureEntity {
    @Column(name = "ID", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String type;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "exposure")
    private List<PostEntity> post = new ArrayList<>();





}

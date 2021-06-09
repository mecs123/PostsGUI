package com.codigo.backendcursojava.Entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity(name = "posts")
///Para la fecha automatica
@EntityListeners(AuditingEntityListener.class)
@Table(indexes = @Index(columnList = "postId",name = "index_postId",unique = true))
public class PostEntity implements Serializable {
    private static final long serialVersionUID=1L;
    @Column(name = "ID", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String postId;
    private String content;
    private String title;
    private Date expirationAt;
    @CreatedDate
    private Date creationAt;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity users;

    @ManyToOne
    @JoinColumn(name = "exposure_id")
    private ExposureEntity exposure;



}

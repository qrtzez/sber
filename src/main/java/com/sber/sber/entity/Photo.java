package com.sber.sber.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "photo")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String originalFileName;

    private Long size;

    private String contentType;

    private LocalDate uploadDate;

    @Lob
    private byte[] bytes;

    @ManyToOne(fetch = FetchType.LAZY)
    private Human human;

    @PrePersist
    private void init() {
        uploadDate = LocalDate.now();
    }
}

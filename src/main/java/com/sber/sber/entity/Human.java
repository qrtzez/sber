package com.sber.sber.entity;

import com.sber.sber.entity.security.Role;
import com.sun.istack.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;


@Entity
@Table(name = "human")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Human implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Length(min = 3, max = 12)
    @NotNull
    private String name;

    private Integer growth;

    private BigDecimal money;

    private String phone;

    private String password;

    @Email
    private String email;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "human_id", referencedColumnName = "id")
    private List<Ticket> tickets;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "human_id", referencedColumnName = "id")
    private List<Notification> notifications;

    @OneToMany(cascade = CascadeType.ALL, fetch =  FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "human_id", referencedColumnName = "id")
    private List <Photo> photos;

    @ManyToMany
    @JoinTable(name = "human_role",
       joinColumns = @JoinColumn(name = "human_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List <Role> roles;
}

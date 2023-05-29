package com.poly.TKShop.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="roles")
@Builder
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    @ManyToMany(mappedBy = "roles", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private Set<User> users;
    public Role(String name ){
        this.name = name;
    }
}

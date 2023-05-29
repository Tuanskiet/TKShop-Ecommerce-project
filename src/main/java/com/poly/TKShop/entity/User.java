package com.poly.TKShop.entity;


import com.poly.TKShop.utils.AuthenticationProvider;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int user_id;

    private String username;
    private String password;

    @Column(columnDefinition = "VARCHAR(255)")
    private String email;
    private boolean enabled ;

    @Enumerated(EnumType.STRING)
    @Column(name="auth_provider")
    private AuthenticationProvider authenticationProvider;

    @ManyToMany( fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(
            name="users_roles",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="role_id")
    )
    private Set<Role> roles;

}

package com.poly.TKShop.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="confirmation_token")
public class ConfirmationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String token;
    private LocalDateTime createAt;
    private LocalDateTime expiredAt;
    private LocalDateTime confirmAt;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public ConfirmationToken(String token, LocalDateTime createAt, LocalDateTime expiredAt, User user){
        this.token = token;
        this.createAt = createAt;
        this.expiredAt = expiredAt;
        this.user = user;
    }

}

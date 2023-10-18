package com.mati.gamechat.entity;

import com.mati.gamechat.entity.lol.LolStats;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tb_user")
@NoArgsConstructor @AllArgsConstructor @Getter @Setter
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Please fill this field")
    @Size(min = 4, max = 25, message = "Username must be between 4 and 25 characters")
//    @Pattern(regexp = "")
    private String username;

    @NotBlank(message = "Please fill this field")
    @Email
    private String email;

    @Size(min = 8, max = 64, message = "Password must be between 8 and 64 characters")
    @NotBlank(message = "Please fill this field")
    private String password;

    @Transient
    @NotBlank(message = "Please fill this field")
    private String passwordConfirmation;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "friendships",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id")
    )
    private List<User> friends;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "friendships",
            joinColumns = @JoinColumn(name = "friend_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> userFriends;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private LolStats lolStats;

    @Column(nullable = false)
    private Date created_at;

    private Date updated_at;

    @PrePersist
    private void onCreate(){
        this.created_at = new Date();
        this.updated_at = new Date();
    }

    @PreUpdate
    private void onUpdate(){
        this.updated_at = new Date();
    }

}

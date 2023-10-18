package com.mati.gamechat.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "groups")
@NoArgsConstructor @AllArgsConstructor @Getter @Setter
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @Size(max = 300, message = "The description must have less than 300 characters.")
    private String description;

    @NotNull
    private Date date_to_play;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner;

    @ManyToMany
    @JoinColumn(name = "user_id")
    List<User> participants;

    @OneToMany(cascade = CascadeType.ALL)
    List<Comment> comments;
}

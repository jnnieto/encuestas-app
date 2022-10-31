package com.jnnieto.encuestas.springboot.app.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "polls")
@Data
@Table(indexes = @Index(columnList = "poll_id", name = "index_poll_id", unique = true))
public class PollEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column( nullable = false, name = "poll_id")
    private String pollId;

    @Column(nullable = false, length = 255)
    private String content;

    @Column
    private boolean opened;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "poll")
    private List<QuestionEntity> questions = new ArrayList<>();

}

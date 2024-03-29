package com.jnnieto.encuestas.springboot.app.entities;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@EntityListeners(AuditingEntityListener.class)
@Entity(name = "poll_replies")
@Data
public class PollReplyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name")
    private String userName;

    @CreatedDate
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "poll_id")
    private PollEntity poll;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "pollReply")
    private List<PollReplyDetailEntity> pollReplies;

}

package com.jnnieto.encuestas.springboot.app.entities;

import lombok.Data;

import javax.persistence.*;

@Entity(name = "poll_reply_details")
@Data
public class PollReplyDetailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long questionId;

    @Column
    private Long answerId;

    @ManyToOne
    @JoinColumn(name = "poll_reply_id")
    private PollReplyEntity pollReply;

}

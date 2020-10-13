package kr.or.hanium.lego.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
@Data
public class Class {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String department;

    private String days;

    private LocalTime start_time;

    private LocalTime end_time;

    private Long verifier_id;

    @ManyToOne
    @JoinColumn(name = "verifier_id", updatable = false, insertable = false)
    private Verifier verifier;
}

package org.example.softwareengineeringlab6.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false)
    private String userName;

    @Column(name = "commentary", nullable = false)
    private String commentary;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(nullable = false)
    private boolean handled = false;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "course_id")
    private Courses course;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "request_operators",
            joinColumns = @JoinColumn(name = "request_id"),
            inverseJoinColumns = @JoinColumn(name = "operator_id")
    )
    private List<Operators> operators;
}
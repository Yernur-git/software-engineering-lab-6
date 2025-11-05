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
    private String userName;
    private String commentary;
    private String phone;
    private boolean handled;

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

    public ApplicationRequest(String userName, Courses course, String commentary, String phone) {
        this.userName = userName;
        this.course = course;
        this.commentary = commentary;
        this.phone = phone;
        this.handled = false;

    }
}
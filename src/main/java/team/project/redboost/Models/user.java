package team.project.redboost.Models;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class user {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role; // ENTREPRENEUR ou COACH

    @OneToMany(mappedBy = "host")
    private List<Meeting> hostedMeetings;

    @ManyToMany(mappedBy = "participants")
    private List<Meeting> meetings;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Meeting> getHostedMeetings() {
        return hostedMeetings;
    }

    public void setHostedMeetings(List<Meeting> hostedMeetings) {
        this.hostedMeetings = hostedMeetings;
    }

    public List<Meeting> getMeetings() {
        return meetings;
    }

    public void setMeetings(List<Meeting> meetings) {
        this.meetings = meetings;
    }

    // Getters & Setters
}

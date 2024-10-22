package com.rizzatto.ToDo.entity;

import com.rizzatto.ToDo.enums.Priority;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "task_id")
    private Long id;

    @Column(name = "task_name", columnDefinition = "TEXT")
    private String name;
    
    @Column(name = "task_description", columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "task_done")
    private Boolean done;

    @Enumerated(EnumType.STRING)
    @Column(name = "task_priority")
    private Priority priority;
    
    @Column(name = "task_points")
    private Integer points;

    @ManyToOne
    @JoinColumn(name = "usr_id")
    private User user;

    public Task(){
    }

    public Task(Long id, String name, String description, Priority priority, User user, Boolean done) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.done = done;
        this.priority = priority;

        this.points = Points.calcPoints(priority);
        this.user = user;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints() {
        this.points = Points.calcPoints(this.priority);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Task other = (Task) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }


    

    
    

    

}


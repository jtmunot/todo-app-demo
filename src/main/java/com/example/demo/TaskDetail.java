package com.example.demo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@JsonIgnoreProperties(value={"id"}, allowGetters=true)
@Table(name="subtasks")
public class TaskDetail {
    @NotBlank
    @Size(min=3, max=100)
    private String subTask;

    @Id
    @GeneratedValue(generator = "subtask_generator")
    @SequenceGenerator(
            name = "subtask_generator",
            sequenceName = "subtask_sequence",
            initialValue = 1
    )
    private int id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "task_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Task task;


    public TaskDetail(){
        super();
    }

    @JsonCreator
    public TaskDetail (@JsonProperty("subTask") String str, @JsonProperty("id") int number){
        this.subTask = str;
        this.id = number;
    }
    
    public String getSubTask(){
        return this.subTask;
    }
    public int getId(){
        return this.id;
    }
    public void setSubTask(String newTask){
        this.subTask = newTask;
    }
    public void setTask(Task tsk){
        this.task = tsk;
    }
}
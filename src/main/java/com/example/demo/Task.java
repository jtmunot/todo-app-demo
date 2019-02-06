package com.example.demo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Entity
@Table(name="tasks")
@JsonIgnoreProperties(
        value = {"id"},
        allowGetters = true
)
public class Task{
    @NotBlank
    @Size(min=3, max=100)
    private String task;

    @Id
    @GeneratedValue(generator = "task_generator")
    @SequenceGenerator(
            name = "task_generator",
            sequenceName = "task_sequence",
            initialValue = 1
    )
    private int id;

    public Task(){
        super();
    }

    @JsonCreator
    public Task (@JsonProperty("task") String str, @JsonProperty("id") int number){
        this.task = str;
        this.id = number;
    }
  
    public String getTask(){
        return this.task;
    }
    public int getId(){
        return this.id;
    }
    public void setTask(String newTask){
        this.task = newTask;
    }
}
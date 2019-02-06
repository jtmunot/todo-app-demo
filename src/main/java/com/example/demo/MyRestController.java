package com.example.demo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import javax.validation.Valid;
import java.util.List;

@RestController
public class MyRestController{

    @Autowired
    private TaskRepository tskRepository;
    @Autowired
    private SubTaskRepository subTskRepository;

    @PostMapping("/addTask")
    public Task addTask(@Valid @RequestBody Task task) {
        System.out.println("Inside addTask() of MyRestController, got task from request: " + task.getId() + ". " + task.getTask());
        Task resultTsk = tskRepository.save(task);
        System.out.println("Saved task: " + resultTsk.getId() + ". " + resultTsk.getTask());
        return resultTsk;
    }

    @PostMapping("/addSubTask/{tskId}")
    public TaskDetail addSubTask(@PathVariable Integer tskId, @Valid @RequestBody TaskDetail tskDetail){
        System.out.println("Inside addSubTask(), got subTask from request: " + tskDetail.getId() + ". " + tskDetail.getSubTask());
        return tskRepository.findById(tskId)
                .map(task -> {
                    tskDetail.setTask(task);
                    return subTskRepository.save(tskDetail);
                }).orElseThrow(() -> new RuntimeException("Task not found with id " + tskId));
    }


    @GetMapping("/getTasks")
    public List <Task> getTaskList(){
        System.out.println("getTaskList() is invoked...");
        List <Task> gotTsks = tskRepository.findAll();
        System.out.println("Found tasks in the DB: " + gotTsks.toString());
        return gotTsks;
    }

    @PutMapping ("/editTask/{id}") 
    public Task editTask(@Valid @RequestBody Task requestTask, @PathVariable Integer id){
        System.out.println("editTask() is invoked... id = " + id);
        return tskRepository.findById(id).
            map(task -> {
                task.setTask(requestTask.getTask());
                return tskRepository.save(task);
            }).orElseThrow(() -> new RuntimeException("Task not found with id = " + id));
    }

    @DeleteMapping("/deleteTask/{id}")
    public ResponseEntity <?> deleteTask(@PathVariable Integer id){
        System.out.println("deleteTask() is invoked... id = " + id);
        if (!tskRepository.existsById(id)){
            throw new RuntimeException("Task is not found in the database, id= " + id);
        }
        return tskRepository.findById(id).
                map(task -> {
                    tskRepository.delete(task);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new RuntimeException("Task delete failed, id = " + id));
    }

    @GetMapping("/getTaskSteps/{id}")
    public List <TaskDetail> getTaskSteps(@PathVariable Integer id){
        System.out.println("getTaskSteps() is invoked...");
        return subTskRepository.findByTaskId(id);
    }
    
}
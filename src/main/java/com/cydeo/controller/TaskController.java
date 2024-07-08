package com.cydeo.controller;

import com.cydeo.dto.ResponseWrapper;
import com.cydeo.dto.TaskDTO;
import com.cydeo.enums.Status;
import com.cydeo.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/task")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }


    @GetMapping
    public ResponseEntity<ResponseWrapper> getTasks(){
        List<TaskDTO>taskDTOList = taskService.listAllTasks();
        return ResponseEntity.ok(new ResponseWrapper("Tasks are successfully retrieved",taskDTOList, HttpStatus.OK));
    };

    @GetMapping("/{taskId}")
    public ResponseEntity<ResponseWrapper> getTaskById(@PathVariable("taskId")Long taskId){
        TaskDTO task = taskService.findById(taskId);
        return ResponseEntity.ok(new ResponseWrapper("Task is successfully retrieved", task, HttpStatus.OK));
    };

    @PostMapping
    public ResponseEntity<ResponseWrapper> createTasks(@RequestBody TaskDTO task){
        taskService.save(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper("Task is successfully created", HttpStatus.CREATED));

    };

    @DeleteMapping("/{taskId}")
    public ResponseEntity<ResponseWrapper> deleteTasks(@PathVariable("taskId")Long taskId){
        taskService.delete(taskId);
        return ResponseEntity.ok(new ResponseWrapper("Task is successfully deleted",HttpStatus.OK));
    };

    @PutMapping
    public ResponseEntity<ResponseWrapper> updateTasks(@RequestBody TaskDTO task){
        taskService.update(task);
        return ResponseEntity.ok(new ResponseWrapper("Task is successfully updated",HttpStatus.OK));
    };


     @GetMapping("/employee/pending-tasks")
    public ResponseEntity<ResponseWrapper> employeePendingTasks(){
         List<TaskDTO> taskDTOList = taskService
                 .listAllTasksByStatusIsNot(Status.COMPLETE);
         return ResponseEntity.ok(new ResponseWrapper("Tasks are successfully retrieved",taskDTOList,HttpStatus.OK));
     };

     @PutMapping("/employee/update/")
    public ResponseEntity<ResponseWrapper> employeeUpdateTasks(@RequestBody TaskDTO task){
         taskService.update(task);
         return  ResponseEntity.ok(new ResponseWrapper("Task is successfully updated",HttpStatus.OK));
     };

     @GetMapping("/employee/archive/")
    public ResponseEntity<ResponseWrapper> employeeArchiveTasks(){
         List<TaskDTO> taskDTOList = taskService.listAllTasksByStatus(Status.COMPLETE);
         return ResponseEntity.ok(new ResponseWrapper("Tasks are successfully retrieved",HttpStatus.OK));
     };
}

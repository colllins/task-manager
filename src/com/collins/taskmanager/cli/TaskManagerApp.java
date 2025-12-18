package com.collins.taskmanager.cli;

import com.collins.taskmanager.io.FileTaskStorage;
import com.collins.taskmanager.model.Task;
import com.collins.taskmanager.model.TaskPriority;
import com.collins.taskmanager.model.TaskStatus;
import com.collins.taskmanager.repository.InMemoryTaskRepository;
import com.collins.taskmanager.repository.TaskRepository;
import com.collins.taskmanager.service.TaskService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

/**
 * Console-based entry point for the Task Manager application.
 * <p>
 * Responsibilities:
 * - Wire together repository, service, and file storage.
 * - Load existing tasks from disk at startup.
 * - Present an interactive menu for creating, listing, updating,
 *   filtering, deleting, and saving tasks.
 */
public class TaskManagerApp {
    public static void main(String[] args) {

        // Set up core components:
        // - In-memory repository for storing tasks during this run
        // - Service layer for business logic
        // - File-based storage for persistence across runs
        TaskRepository repo = new InMemoryTaskRepository();
        TaskService service = new TaskService(repo);
        FileTaskStorage taskStorage = new FileTaskStorage();

        // Load previously saved tasks from disk (if any),
        // and push them into the repository so they are available in memory.
        try{
            List<Task> tasks = taskStorage.loadAll();
            for (Task task:tasks) {
                repo.save(task);
            }

        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        Scanner scanner = new Scanner(System.in);
        int num=0;

        // Track current number of tasks in memory for some menu checks.
        int size = service.getAllTasks().size();

        // Main interactive loop:
        // Continues until the user chooses option 7 (Exit).
        do{
            System.out.print("Enter 1 to Create New Task");
            System.out.println();
            System.out.print("Enter 2 to List All Tasks(Sorted by Due Date)");
            System.out.println();
            System.out.print("Enter 3 to Update Task Status");
            System.out.println();
            System.out.print("Enter 4 to Filter Task");
            System.out.println();
            System.out.print("Enter 5 to Delete Task");
            System.out.println();
            System.out.print("Enter 6 to Save Tasks to File");
            System.out.println();
            System.out.print("Enter 7 to Exit: ");
            try{
                // Read the menu choice from the user.
                num = Integer.parseInt(scanner.nextLine());

                // 1 – Create a new task by collecting all required fields.
                 if(num==1){
                    System.out.println();
                    System.out.print("Enter Task Title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter Task Description: ");
                    String desc = scanner.nextLine();
                    System.out.print("Enter Task Status (TODO, IN_PROGRESS, DONE): ");
                    TaskStatus status = TaskStatus.valueOf(scanner.nextLine().toUpperCase());
                    System.out.print("Enter Task Priority(LOW, MEDIUM, HIGH): ");
                    TaskPriority priority = TaskPriority.valueOf(scanner.nextLine().toUpperCase());
                    System.out.print("Enter Due Date (yyyy-MM-dd): ");
                    LocalDate dueDate = LocalDate.parse(scanner.nextLine());
                    Task task = new Task(title,desc,status,priority,dueDate);
                    task = service.createTask(task);

                     // Update cached size after creating a new task.
                    size= service.getAllTasks().size();
                    System.out.println("Task created with Id: "+task.getId());
                    System.out.println();

                     // 2 – List all tasks, sorted by due date.
                }else if(num==2){
                    System.out.println();
                    if (size==0){
                        System.out.println("Task List is Empty");
                    }else {
                        service.getAllTasks().forEach(System.out::println);
                    }
                    System.out.println();

                     // 3 – Update the status of an existing task by id.
                }else if(num==3){
                    System.out.println();
                    System.out.print("Enter id of Task status to be updated: ");
                    int id = Integer.parseInt(scanner.nextLine());
                    System.out.print("Enter Updated Status: ");
                    TaskStatus status = TaskStatus.valueOf(scanner.nextLine().toUpperCase());
                    Task task = service.updateStatus(id,status);
                    System.out.println("Status updated for task with id "+task.getId());
                    System.out.println();

                     // 4 – Filter tasks by status, priority, or due date.
                }else if(num==4){
                    System.out.println();
                    if(size>0){
                        System.out.println();
                        System.out.print("Enter a to Filter by Status");
                        System.out.println();
                        System.out.print("Enter b to Filter by Priority");
                        System.out.println();
                        System.out.print("Enter c to Filter by Due Before");
                        System.out.println();
                        System.out.print("Enter d to Filter by Due After: ");
                        String c = scanner.nextLine().toLowerCase();
                        System.out.println();

                        // 4a – Filter by status.
                        if(c.equals("a")){
                            System.out.println();
                            System.out.print("Enter Status to Filter By: ");
                            TaskStatus status = TaskStatus.valueOf(scanner.nextLine().toUpperCase());
                            if(service.filterByStatus(status).size()>0) {
                                service.filterByStatus(status).forEach(System.out::println);
                            }else{
                                System.out.println("No Tasks matched that filter");
                            }
                            System.out.println();

                            // 4b – Filter by priority.
                        }else if(c.equals("b")){
                            System.out.println();
                            System.out.print("Enter Priority to Filter By: ");
                            TaskPriority priority = TaskPriority.valueOf(scanner.nextLine().toUpperCase());
                            if(service.filterByPriority(priority).size()>0) {
                                service.filterByPriority(priority).forEach(System.out::println);
                            }else{
                                System.out.println("No Tasks matched that filter");
                            }

                            // 4c – Filter by tasks due before a given date.
                        }else if(c.equals("c")){
                            System.out.println();
                            System.out.print("Enter Due Date Before to Filter By: ");
                            LocalDate date = LocalDate.parse(scanner.nextLine());
                            if(service.filterByDueDateBefore(date).size()>0) {
                                service.filterByDueDateBefore(date).forEach(System.out::println);
                            }else{
                                System.out.println("No Tasks matched that filter");
                            }
                        }

                        // 4d – Filter by tasks due after a given date.
                        else if(c.equals("d")){
                            System.out.println();
                            System.out.print("Enter Due Date After to Filter By: ");
                            LocalDate date = LocalDate.parse(scanner.nextLine());
                            if(service.filterByDueDateAfter(date).size()>0) {
                                service.filterByDueDateAfter(date).forEach(System.out::println);
                            }else{
                                System.out.println("No Tasks matched that filter");
                            }

                            // Invalid sub-option for filter menu.
                        }else{
                            System.out.println("Invalid Input. try Again");
                            System.out.println();
                        }
                    }else{
                        System.out.println("Task list is empty");
                        System.out.println();
                    }

                     // 5 – Delete a task by id.
                }else if(num==5){
                    System.out.println();
                    try{
                        System.out.print("Enter id for task to be Deleted: ");
                        int id = Integer.parseInt(scanner.nextLine());
                        service.deleteTask(id);

                        // Update cached size after deletion.
                        size = service.getAllTasks().size();
                        System.out.println("Task Deleted!");
                    }catch (RuntimeException e){
                        System.out.println(e.getMessage());
                    }
                    System.out.println();

                     // 6 – Save all current tasks to the backing file.
                }else if(num==6){
                    try{
                        System.out.println();
                        taskStorage.saveAll(service.getAllTasks());
                        System.out.println("Tasks saved successfully!");
                        System.out.println();
                    }catch (IOException e) {
                        System.out.println(e.getMessage());
                    }

                     // 7 – Exit the program after saving once more.
                }else if(num==7){
                    try{
                        System.out.println();
                        taskStorage.saveAll(service.getAllTasks());
                        System.out.println("GoodBye!!!");
                        System.out.println();
                    }catch (IOException e) {
                        System.out.println(e.getMessage());
                    }

                     // Any other number outside 1–7.
                }else{
                     System.out.println();
                     System.out.println("Number entered is out of range! Try Again!");
                     System.out.println();
                 }
            }catch (NumberFormatException e){
                // Handles non-numeric menu input (for example, typing letters instead of a number).
                System.out.println("Not a passable integer");
            }
        }while(num!=7);         // Loop terminates when user chooses Exit.

        // Clean up scanner resource before exiting the application
        scanner.close();
    }
}

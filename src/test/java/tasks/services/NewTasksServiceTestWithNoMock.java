package tasks.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import tasks.model.Task;
import tasks.repo.ArrayTaskList;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class NewTasksServiceTestWithNoMock {
    private NewTasksService newTasksService;
    private Date date_for_test;
    private Date date_for_test_plus_2;
    private Date date_for_test_plus_1;
    private Task task;

    public Date convertToDateViaInstant(LocalDate dateToConvert) { return java.util.Date.from(dateToConvert.atStartOfDay() .atZone(ZoneId.systemDefault()) .toInstant()); }

    @BeforeEach
    void setUp() {
        date_for_test = convertToDateViaInstant(LocalDate.now());
        date_for_test_plus_1 = convertToDateViaInstant(LocalDate.now().plusDays(1));
        date_for_test_plus_2 = convertToDateViaInstant(LocalDate.now().plusDays(2));

        task = new Task("groceries",date_for_test, date_for_test_plus_1,true,1);
        Task task_invalid =  new Task("groceries",date_for_test_plus_2);

        ArrayTaskList arrayTaskList = new ArrayTaskList();
        arrayTaskList.add(task);
        arrayTaskList.add(task_invalid);
        newTasksService = new NewTasksService(arrayTaskList);
    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void filterTasks()
    {
        Iterable<Task> filterTasks = newTasksService.filterTasks(date_for_test, date_for_test_plus_2);
        Integer count = 0;
        for(Task t : filterTasks) {
            count++;
            assertEquals(task, t);
        }
        assertEquals(1,count);

    }

    @Test
    void addTask()
    {
        newTasksService.addTask(task);
        Iterable<Task> filterTasks = newTasksService.filterTasks(date_for_test, date_for_test_plus_2);
        Integer count = 0;
        for(Task t : filterTasks) {
            count++;
            assertEquals(task, t);
        }
        assertEquals(2,count);
    }
}

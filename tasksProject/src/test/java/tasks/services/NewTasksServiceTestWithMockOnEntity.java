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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class NewTasksServiceTestWithMockOnEntity {

    private NewTasksService newTasksService;
    private Date date_for_test;
    private Date date_for_test_plus_2;
    private Task task;

    public Date convertToDateViaInstant(LocalDate dateToConvert) { return java.util.Date.from(dateToConvert.atStartOfDay() .atZone(ZoneId.systemDefault()) .toInstant()); }

    @BeforeEach
    void setUp() {
        task = mock(Task.class);
        Task task_invalid = mock(Task.class);

        date_for_test = convertToDateViaInstant(LocalDate.now());
        Date date_for_test_plus_1 = convertToDateViaInstant(LocalDate.now().plusDays(1));
        date_for_test_plus_2 = convertToDateViaInstant(LocalDate.now().plusDays(2));
        Mockito.when(task.nextTimeAfter(date_for_test)).thenReturn(date_for_test_plus_1);
        Mockito.when(task_invalid.nextTimeAfter(date_for_test)).thenReturn(null);

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


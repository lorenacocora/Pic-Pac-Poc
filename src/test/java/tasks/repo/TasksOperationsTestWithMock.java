package tasks.repo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import tasks.model.Task;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Set;
import java.util.SortedMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class TasksOperationsTestWithMock {

    private Date date_for_test;
    private Date date_for_test_plus_1;
    private Date date_for_test_plus_2;
    private TasksOperations tasksOperations;

    public Date convertToDateViaInstant(LocalDate dateToConvert) { return java.util.Date.from(dateToConvert.atStartOfDay() .atZone(ZoneId.systemDefault()) .toInstant()); }


    @BeforeEach
    void setUp() {
        Task task = mock(Task.class);
        Task task_invalid = mock(Task.class);

        date_for_test = convertToDateViaInstant(LocalDate.now());
        date_for_test_plus_1 = convertToDateViaInstant(LocalDate.now().plusDays(1));
        date_for_test_plus_2 = convertToDateViaInstant(LocalDate.now().plusDays(2));
        Mockito.when(task.nextTimeAfter(date_for_test)).thenReturn(date_for_test_plus_1);
        Mockito.when(task_invalid.nextTimeAfter(date_for_test)).thenReturn(null);

        ObservableList<Task> taskObservableList = FXCollections.observableArrayList();
        taskObservableList.add(task);
        taskObservableList.add(task_invalid);
        tasksOperations=new TasksOperations(taskObservableList);
    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void incoming() {

        Iterable<Task> incomingTasks = tasksOperations.incoming(date_for_test, date_for_test_plus_2);
        Integer count = 0;
        for(Task t : incomingTasks) count++;
        assertEquals(1,count);
    }

    @Test
    void calendar() {

        SortedMap<Date, Set<Task>> tasks = tasksOperations.calendar(date_for_test, date_for_test_plus_2);
        assertEquals(tasks.keySet().size(),1);
        assertEquals(tasks.get(date_for_test_plus_1).size(),1);
    }
}
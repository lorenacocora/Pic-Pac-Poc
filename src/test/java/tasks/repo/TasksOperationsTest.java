package tasks.repo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.model.Task;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TasksOperationsTest
{


    TasksOperations tasksOperations;
    private String incorrectOrderOfDatesMessage = "Start date cannot be after the end date!";
    public Date convertToDateViaInstant(LocalDate dateToConvert) { return java.util.Date.from(dateToConvert.atStartOfDay() .atZone(ZoneId.systemDefault()) .toInstant()); }

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void test_F02_TC01()
    {
        List<Task> taskList = new ArrayList<Task>();
        taskList.add(new Task("Cumparaturi",convertToDateViaInstant(LocalDate.now().plusDays(1)),convertToDateViaInstant(LocalDate.now()),true, 0));
        ObservableList<Task> taskObservableList = FXCollections.observableArrayList();
        tasksOperations = new TasksOperations(taskObservableList);
        assertThrows(IllegalArgumentException.class,()-> tasksOperations.incoming(convertToDateViaInstant(LocalDate.now().plusDays(1)),convertToDateViaInstant(LocalDate.now())),incorrectOrderOfDatesMessage);

    }

    @Test
    void test_F02_TC02()
    {
        List<Task> taskList = new ArrayList<Task>();
        taskList.add(new Task("Cumparaturi",convertToDateViaInstant(LocalDate.now()),convertToDateViaInstant(LocalDate.now()), true, 0));
        ObservableList<Task> taskObservableList = FXCollections.observableArrayList();
        tasksOperations = new TasksOperations(taskObservableList);
        try{
            int count = 0;
            Iterable<Task> taskIterable =tasksOperations.incoming(convertToDateViaInstant(LocalDate.now()),convertToDateViaInstant(LocalDate.now().plusDays(1)));
            for ( ; taskIterable.iterator().hasNext() ; ++count ) taskIterable.iterator().next();
            assertEquals(0,count);
        }
        catch (Exception e)
        {
            fail("Filter failed!" + e.getMessage());
        }
    }

    @Test
    void test_F02_TC03()
    {
        List<Task> taskList = new ArrayList<Task>();
        Task addedTask = new Task("Cumparaturi",convertToDateViaInstant(LocalDate.now().plusDays(1)));
        addedTask.setActive(true);
        addedTask.setInterval(0);
        taskList.add(addedTask);
        ObservableList<Task> taskObservableList = FXCollections.observableArrayList(taskList);
        tasksOperations = new TasksOperations(taskObservableList);
        try{
            int count = 0;
            Iterable<Task> taskIterable = tasksOperations.incoming(convertToDateViaInstant(LocalDate.now()),convertToDateViaInstant(LocalDate.now().plusDays(1)));
            Iterator<Task> iterator = taskIterable.iterator();
            while (iterator.hasNext())
            {
                count++;
                Task task = iterator.next();
                assertEquals(addedTask.getTitle(),task.getTitle());
                assertEquals(addedTask.getStartTime(),task.getStartTime());
                assertTrue(task.isActive());
            }
            assertEquals(1,count);
        }
        catch (Exception e)
        {
            fail("Filter failed!" + e.getMessage());
        }
    }

    @Test
    void test_F02_TC04()
    {
        List<Task> taskList = new ArrayList<Task>();
        Task addedTask = new Task("Cumparaturi",convertToDateViaInstant(LocalDate.now().plusDays(1)));
        addedTask.setActive(true);
        addedTask.setInterval(0);
        taskList.add(addedTask);
        ObservableList<Task> taskObservableList = FXCollections.observableArrayList(taskList);
        tasksOperations = new TasksOperations(taskObservableList);
        try{
            int count = 0;
            Iterable<Task> taskIterable = tasksOperations.incoming(convertToDateViaInstant(LocalDate.now()),convertToDateViaInstant(LocalDate.now().plusDays(2)));
            Iterator<Task> iterator = taskIterable.iterator();
            while (iterator.hasNext())
            {
                count++;
                Task task = iterator.next();
                assertEquals(addedTask.getTitle(),task.getTitle());
                assertEquals(addedTask.getStartTime(),task.getStartTime());
                assertTrue(task.isActive());
            }
            assertEquals(1,count);
        }
        catch (Exception e)
        {
            fail("Filter failed!" + e.getMessage());
        }
    }




}

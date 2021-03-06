package tasks.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    private Task task;

    @BeforeEach
    void setUp() {
        try {
            task=new Task("new task",Task.getDateFormat().parse("2021-02-12 10:10"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testTaskCreation() throws ParseException
    {
       assert task.getTitle() == "new task";
        System.out.println(task.getFormattedDateStart());
        System.out.println(task.getDateFormat().format(Task.getDateFormat().parse("2021-02-12 10:10")));
       assert task.getFormattedDateStart().equals(task.getDateFormat().format(Task.getDateFormat().parse("2021-02-12 10:10")));

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getDateFormat() {
    }

    @Test
    void getTitle() {
        assertEquals("new task", task.getTitle());
    }

    @Test
    void setTitle() {
        task.setTitle("new title new task");
        assertEquals("new title new task", task.getTitle());
    }

    @Test
    void isActive() {
        assertEquals(false,task.isActive());
    }

    @Test
    void setActive() {
        task.setActive(true);
        assertEquals(true,task.isActive());
    }
}

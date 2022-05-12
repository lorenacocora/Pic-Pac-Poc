package tasks.services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tasks.model.Task;
import tasks.repo.ArrayTaskList;
import tasks.repo.TasksOperations;

import java.util.Date;

public class NewTasksService
{
    private ArrayTaskList tasks;
    private TasksOperations tasksOps;

    public NewTasksService(ArrayTaskList tasks){
        this.tasks = tasks;
        this.tasksOps= new TasksOperations(getObservableList());
    }

    public ObservableList<Task> getObservableList(){
        return FXCollections.observableArrayList(tasks.getAll());
    }

    public String getIntervalInHours(Task task){
        int seconds = task.getRepeatInterval();
        int minutes = seconds / DateService.SECONDS_IN_MINUTE;
        int hours = minutes / DateService.MINUTES_IN_HOUR;
        minutes = minutes % DateService.MINUTES_IN_HOUR;
        return formTimeUnit(hours) + ":" + formTimeUnit(minutes);//hh:MM
    }
    public String formTimeUnit(int timeUnit){
        StringBuilder sb = new StringBuilder();
        if (timeUnit < 10) sb.append("0");
        if (timeUnit == 0) sb.append("0");
        else {
            sb.append(timeUnit);
        }
        return sb.toString();
    }

    public int parseFromStringToSeconds(String stringTime){//hh:MM
        String[] units = stringTime.split(":");
        if(units.length!=2){
            throw new RuntimeException("Invalid format! Expected format: hh:MM");
        }
        try{
            int hours = Integer.parseInt(units[0]);
            int minutes = Integer.parseInt(units[1]);
            int result = (hours * DateService.MINUTES_IN_HOUR + minutes) * DateService.SECONDS_IN_MINUTE;
            return result;
        }catch (Exception e){
            throw new RuntimeException("Invalid number conversion!");
        }

    }

    public Iterable<Task> filterTasks(Date start, Date end){
        return tasksOps.incoming(start,end);
    }

    public void addTask(Task t)
    {
        tasksOps.addTask(t);
    }

}

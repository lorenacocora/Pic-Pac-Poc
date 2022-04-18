package tasks.repo;

import javafx.collections.ObservableList;
import tasks.model.Task;

import java.util.*;

public class TasksOperations {

    private ArrayList<Task> tasks;

    public TasksOperations(ObservableList<Task> tasksList){
        tasks=new ArrayList<>();
        tasks.addAll(tasksList);
    }

//    public Iterable<Task> incoming(Date start, Date end)
//    {
//        System.out.println(start);
//        System.out.println(end);
//        ArrayList<Task> incomingTasks = new ArrayList<>();
//        for (Task t : tasks) {
//            Date nextTime = t.nextTimeAfter(start);
//            if (nextTime != null && (nextTime.before(end) || nextTime.equals(end))) {
//                incomingTasks.add(t);
//                System.out.println(t.getTitle());
//            }
//        }
//        return incomingTasks;
//    }


    //returns a list of tasks between the given dates
    public Iterable<Task> incoming(Date start, Date end)
    {

        ArrayList<Task> incomingTasks = new ArrayList<>();
        if(start.after(end))
            throw new IllegalArgumentException("Start date cannot be after the end date!");
        for (Task t : tasks)
        {
            Date nextTime = t.nextTimeAfter(start);
            System.out.println(nextTime);
            if (nextTime != null)
            {
                if (nextTime.before(end))
                {
                    incomingTasks.add(t);
                }
                if (nextTime.equals(end))
                {
                    incomingTasks.add(t);
                }
            }
        }
        return incomingTasks;
    }


    public SortedMap<Date, Set<Task>> calendar( Date start, Date end)
    {
        Iterable<Task> incomingTasks = incoming(start, end);
        TreeMap<Date, Set<Task>> calendar = new TreeMap<>();

        for (Task t : incomingTasks){
            Date nextTimeAfter = t.nextTimeAfter(start);
            while (nextTimeAfter!= null && (nextTimeAfter.before(end) || nextTimeAfter.equals(end))){
                if (calendar.containsKey(nextTimeAfter)){
                    calendar.get(nextTimeAfter).add(t);
                }
                else {
                    HashSet<Task> oneDateTasks = new HashSet<>();
                    oneDateTasks.add(t);
                    calendar.put(nextTimeAfter,oneDateTasks);
                }
                nextTimeAfter = t.nextTimeAfter(nextTimeAfter);
            }
        }
        return calendar;
    }
}


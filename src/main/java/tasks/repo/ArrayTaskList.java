package tasks.repo;
import org.apache.log4j.Logger;
import tasks.model.Task;

import java.util.*;


public class ArrayTaskList extends TaskList {

    private Task[] tasks;
    private int numberOfTasks;
    private int currentCapacity;
    private static final Logger log = Logger.getLogger(ArrayTaskList.class.getName());
    private class ArrayTaskListIterator implements Iterator<Task>
    {
        private int cursor=0;
        private int lastCalled = -1;
        @Override
        public boolean hasNext() {
            return cursor < numberOfTasks;
        }

        @Override
        public Task next() {
            if (!hasNext()){
                log.error("next iterator element doesn't exist");
                throw new NoSuchElementException("No next element");
            }
            lastCalled = cursor;
            Task t = getTask(cursor);
            cursor++;
            return t;
        }

        @Override
        public void remove() {
            if (lastCalled == -1){
                throw new IllegalStateException();
            }
            ArrayTaskList.this.remove(getTask(lastCalled));
            cursor = lastCalled;
            lastCalled = -1;
        }
    }
    public ArrayTaskList(){
        currentCapacity = 10;
        this.tasks = new Task[currentCapacity];
    }

    @Override
    public Iterator<Task> iterator() {
        return new ArrayTaskListIterator();
    }

    @Override
    public void add(Task task)
    {
        final int doi=2;
        if (task==null) {
            throw new IllegalArgumentException("Task shouldn't be null");
        }
        if (numberOfTasks == currentCapacity-1){
            currentCapacity = currentCapacity * doi;
            Task[] withAddedTask = new Task[currentCapacity];
            System.arraycopy(tasks,0,withAddedTask,0,tasks.length);
            this.tasks = withAddedTask;
        }
        this.tasks[numberOfTasks] = task;
        this.numberOfTasks++;
    }
    @Override
    public boolean remove(Task task){
        int indexOfTaskToDelete = -1;
        for(int i = 0; i < tasks.length; i++){
            if (task.equals(tasks[i])){
                indexOfTaskToDelete = i;
                break;
            }
        }
        if (indexOfTaskToDelete >= 0){
            this.numberOfTasks--;
            System.arraycopy(tasks, indexOfTaskToDelete+1,tasks,indexOfTaskToDelete,
                    numberOfTasks-indexOfTaskToDelete+1);
            return true;
        }
        return false;
    }
    @Override
    public int size(){
        return this.numberOfTasks;
    }
    @Override
    public Task getTask(int index){
        if (index < 0 || index > size()-1) {
            log.error("not existing index");
            throw new IndexOutOfBoundsException("Index not found");
        }


        return tasks[index];
    }

    @Override
    public List<Task> getAll() {
        ArrayList<Task> tks=new ArrayList<>();
        for (int i=0; i<this.numberOfTasks;i++)
        {
            tks.add(this.tasks[i]);
        }
        return tks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ArrayTaskList that = (ArrayTaskList) o;

        if (numberOfTasks != that.numberOfTasks) return false;
        int i = 0;
        for (Task a : this){
            if (!a.equals(((ArrayTaskList) o).getTask(i))){
                return false;
            }
            i++;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(tasks);
        result = 31 * result + numberOfTasks;
        result = 31 * result + currentCapacity;
        return result;
    }

    @Override
    public String toString() {
        return "ArrayTaskList{" +
                "tasks=" + Arrays.toString(tasks) +
                ", numberOfTasks=" + numberOfTasks +
                ", currentCapacity=" + currentCapacity +
                '}';
    }
    @Override
    protected ArrayTaskList clone() throws CloneNotSupportedException
    {
        ArrayTaskList tasksClone = new ArrayTaskList();
        for (int i = 0; i < this.tasks.length; i++){
            tasksClone.add(this.getTask(i));
        }
        return tasksClone;
    }

}

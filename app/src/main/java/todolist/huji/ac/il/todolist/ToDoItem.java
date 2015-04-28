package todolist.huji.ac.il.todolist;

import java.util.Date;


public class ToDoItem {

    private String title;
    private Date dueDate;

    public ToDoItem(String title, Date dueDate){
        this.title = title;
        this.dueDate = dueDate;
    }

    public String getTitle(){
        return this.title;
    }

    public Date getDueDate(){
        return this.dueDate;
    }

}

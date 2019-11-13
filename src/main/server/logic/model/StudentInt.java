package main.server.logic.model;

import java.util.List;

public interface StudentInt {
	
	int StudentNumber();
	String Name();
	boolean isFullTime();
	boolean isCreated();
	
	List<Course> CompletedCourse();
	List<Course> CurrentCourse();
	
	boolean SelectCourse(Course course);
	boolean RegisterCourse(Course course);
	boolean DropCourse(Course course,boolean force);
	boolean DeRegisterCourse(Course course);
	
	int AverageMark();

}

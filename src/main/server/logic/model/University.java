package main.server.logic.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import main.utilities.Config;
import main.utilities.Trace;

import org.apache.log4j.Logger;

public class University implements UniversityInt {
	
	private Logger logger = Trace.getInstance().getLogger("opreation_file");

	static int universityCourses;
	static int maxCourseForFTStudent = 4;
	static int maxCourseForPTStudent = 2;
	static int passRate = 70;
	
	List<Course> courses = new ArrayList<Course>();
	List<Student> students = new ArrayList<Student>();
	
	static int currentstudent;
	
	static Timer timer_registrationstarts;
	static Timer timer_registrationends;
	static Timer timer_termends;
	
	private static class UniversityHolder {
		private static final University INSTANCE = new University();
	}
	
	public University() {
		super();
		TimeSimulate();
		InitializeCourses();
		InitializeStudents();
		universityCourses = courses.size();
		logger.info(String.format("University Operation: Initialize university; courses: %s", courses));
		logger.info(String.format("University Operation: Initialize university; students: %s", students));
	}

	public static final University getInstance() {
		return UniversityHolder.INSTANCE;
	}
	
	public void Reset() {
		Config.REGISTRATION_STARTS = false;
		Config.REGISTRATION_ENDS = false;
		Config.TERM_ENDS = false;
		timer_registrationstarts.cancel();
		timer_registrationends.cancel();
		timer_termends.cancel();
		TimeSimulate();
		InitializeCourses();
		InitializeStudents();
	}
	
	private void TimeSimulate() {
		timer_registrationstarts = new Timer();
		timer_registrationends = new Timer();
		timer_termends = new Timer();
		
		timer_registrationstarts.schedule(new TimerTask() {
			
			@Override
			public void run() {
				Config.REGISTRATION_STARTS = true;
				System.out.println("registration starts");
			}
		}, Config.SIMULATED_DAY * 2 * 100);
		
		timer_registrationends.schedule(new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Config.REGISTRATION_ENDS = true;
				System.out.println("registration ends");
			}
		}, Config.SIMULATED_DAY * (20 + 14) * 100);
		
		timer_termends.schedule(new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Config.TERM_ENDS = true;
				for (int i=0; i<University.getInstance().getCourses().size(); i++) {
					University.getInstance().MarkStudents(University.getInstance().getCourses().get(i));
				}
				System.out.println("term ends");
			}
		}, Config.SIMULATED_DAY * (20 + 14 + 84) * 100);
	}
	
	private void InitializeCourses() {
		courses.clear();
	}

	private void InitializeStudents() {
		students.clear();
	}

	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	public int getCurrentstudent() {
		return currentstudent;
	}

	public void setCurrentstudent(int currentstudent) {
		University.currentstudent = currentstudent;
	}

	@Override
	public List<Course> Courses() {
		// TODO Auto-generated method stub
		return getCourses();
	}

	@Override
	public List<Student> Students() {
		// TODO Auto-generated method stub
		return getStudents();
	}
	
	@Override
	public Student GetStudent(int number) {
		// TODO Auto-generated method stub
		Student student = null;
		for (int i=0; i<students.size(); i++) {
			if (students.get(i).StudentNumber() == number) {
				student = students.get(i);
				logger.info(String.format("University Operation: Get student; Student info: %d; State: Success", number));
			}
		}
		return student;
	}

	@Override
	public Course GetCourse(int mycode) {
		// TODO Auto-generated method stub
		Course course = null;
		for (int i=0; i<courses.size(); i++) {
			if (courses.get(i).Code() == mycode) {
				course = courses.get(i);
				logger.info(String.format("University Operation: Get course; Course info: %d; State: Success", mycode));
			}
		}
		return course;
	}
	
	@Override
	public boolean CheckCourse(int mycode) {
		for (Course c : courses){
			if (c.Code() == mycode && c.Students().size() < c.getCapsize()){
				logger.info(String.format("University Operation: Check course; Course info: %d; State: Success", mycode));
				return true;
			}
		}
		logger.info(String.format("University Operation: Check course; Course info: %d; State: Fail", mycode));
		return false;
	}
	
	@Override
	public boolean CheckStudent(int number) {
		// TODO Auto-generated method stub
		int flag = 0;
		boolean result = true;
		for (int i=0; i<students.size(); i++) {
			if (students.get(i).StudentNumber() == number) {
				flag = flag + 1;
			}
		}
		if (flag!=0) {
			result = true;
			logger.info(String.format("University Operation: Check student; Student info: %d; State: Success", number));
		} else {
			result = false;
			logger.info(String.format("University Operation: Check student; Student info: %d; State: Fail", number));
		}
		return result;
	}

	@Override
	public boolean LookupStudent(int number, String name) {
		// TODO Auto-generated method stub
		boolean result = true;
		int flag = 0;
		for (int i=0; i<students.size(); i++) {
			int studentnumber = students.get(i).StudentNumber();
			String studentname = students.get(i).Name();
			if (studentnumber==number && studentname.equalsIgnoreCase(studentname)) {
				flag = flag + 1;
			}
		}
		if (flag!=0) {
			result = true;
			logger.info(String.format("University Operation: Lookup student; Student info: [%d,%s]; State: Success", number, name));
		} else {
			result = false;
			logger.info(String.format("University Operation: Lookup student; Student info: [%d,%s]; State: Fail", number, name));
		}
		return result;
	}

	@Override
	public boolean CreateCourse(String title, int mycode, int cap, 
			boolean enforcePrereqs, int numberofmidterms, 
			int numberofassignments, boolean hasafinal, 
			boolean isprojectcourse) {
		// TODO Auto-generated method stub
		boolean result = true;
		/*
		int flag = 0;
		for (int i=0; i<courses.size(); i++) {
			String coursetitle = courses.get(i).Title();
			int coursemycode = courses.get(i).Code();
			if (coursetitle.equalsIgnoreCase(title)||coursemycode == mycode) {
				flag = flag + 1;
			}
		}
		*/
		if (!CheckCourse(mycode)) {
			if (!isprojectcourse) {
				Course c = new Course(title, mycode, cap, enforcePrereqs, numberofmidterms, numberofassignments, hasafinal);
				result = courses.add(c);
				logger.info(String.format("University Operation: Create new course; Course info: [%s,%d]; State: Success", title, mycode));
			} else {
				ProjectCourse c = new ProjectCourse(title, mycode, cap,enforcePrereqs, numberofmidterms, numberofassignments,hasafinal);
				result = courses.add(c);
				logger.info(String.format("University Operation: Create new project course; Course info: [%s,%d]; State: Success", title, mycode));
			}
		} else {
			result = false;
			logger.info(String.format("University Operation: Create new course; Course info: [%s,%d]; State: Fail; Reason: The course already existed.", title, mycode));
		}
		return result;
	}

	@Override
	public boolean CreateStudent(int number, String name,
			boolean isfulltime) {
		// TODO Auto-generated method stub
		boolean result = true;
		/*
		int flag = 0;
		for (int i=0; i<students.size(); i++) {
			int studentnumber = students.get(i).StudentNumber();
			if (studentnumber==number) {
				flag = flag + 1;
			}
		}
		*/
		if (!CheckStudent(number)) {
			Student s = new Student(number, name, isfulltime);
			result = students.add(s);
			logger.info(String.format("University Operation: Create new student; Student info: [%d,%s]; State: Success", number, name));
		} else {
			result = false;
			logger.info(String.format("University Operation: Create new student; Student info: [%d,%s]; State: Fail; Reason: The student already existed.", number, name));
		}
		return result;
	}

	@Override
	public boolean RegisterStudentForCourse(Student student, Course course) {
		// TODO Auto-generated method stub
		boolean result = true;
		/*
		int flag1 = 0;
		int flag2 = 0;
		for (int i=0; i<courses.size(); i++) {
			if (courses.get(i).Code()==course.Code()) {
				flag1 = flag1 + 1;
			}
		}
		for (int i=0; i<students.size(); i++) {
			if (students.get(i).StudentNumber()==student.StudentNumber()) {
				flag2 = flag2 + 1;
			}
		}
		*/
		if (CheckCourse(course.Code()) && CheckStudent(student.StudentNumber()) && student.IsSelected(course)) {
			result = student.RegisterCourse(course);
			if (result) {
				result = course.AddStudent(student);
				logger.info(String.format("University Operation: student %d register course %d; State: Success", student.StudentNumber(), course.Code()));
			} else {
				logger.info(String.format("University Operation: student %d register course %d; State: Fail; Reason: The course registered has reached the maximum number.", student.StudentNumber(), course.Code()));
			}
		} else {
			result = false;
			logger.info(String.format("University Operation: student %d register course %d; State: Fail; Reason: The student or course doesn't exist or the student hasn't selected the course.", student.StudentNumber(), course.Code()));
		}
		return result;
	}
	
	@Override
	public boolean DeRegisterStudentFromCourse(Student student, Course course) {
		// TODO Auto-generated method stub
		boolean result = true;
		if (CheckCourse(course.Code()) && CheckStudent(student.StudentNumber()) && student.IsRegistered(course)) {
			result = student.DeRegisterCourse(course);
			if (result) {
				result = course.RemoveStudent(student);
				logger.info(String.format("University Operation: student %d deregister course %d; State: Success", student.StudentNumber(), course.Code()));
			} else {
				logger.info(String.format("University Operation: student %d deregister course %d; State: Fail", student.StudentNumber(), course.Code()));
			}
		} else {
			result = false;
			logger.info(String.format("University Operation: student %d deregister course %d; State: Fail; Reason: The student or course doesn't exist or the student hasn't registered the course.", student.StudentNumber(), course.Code()));
		}
		return result;
	}

	@Override
	public boolean CancelCourse(Course course) {
		// TODO Auto-generated method stub
		boolean result = true;
		/*
		int flag = 0;
		for (int i=0; i<courses.size(); i++) {
			if (courses.get(i).Code()==course.Code()) {
				flag = flag + 1;
			}
		}
		*/
		if (CheckCourse(course.Code())) {
			//List<Student> s = new ArrayList<Student>();
			for (int i=0; i<students.size(); i++){
				course.RemoveStudent(students.get(i));
				students.get(i).DeRegisterCourse(course);
			}
			result = true;
			logger.info(String.format("University Operation: cancel course %d; State: Success", course.Code()));
		} else {
			result = false;
			logger.info(String.format("University Operation: cancel course %d; State: Fail; Reason: The course doesn't exist.", course.Code()));
		}
		return result;
	}

	@Override
	public boolean DestroyCourse(Course course) {
		// TODO Auto-generated method stub
		boolean result = true;
		if (CheckCourse(course.Code())) {
			//List<Student> s = new ArrayList<Student>();
			for (int i=0; i<students.size(); i++){
				course.RemoveStudent(students.get(i));
				students.get(i).DropCourse(course,true);
				students.get(i).DeRegisterCourse(course);
			}
			result = courses.remove(course);
			logger.info(String.format("University Operation: delete course %d; State: Success", course.Code()));
		} else {
			result = false;
			logger.info(String.format("University Operation: delete course %d; State: Fail; Reason: The course doesn't exist.", course.Code()));
		}
		return result;
	}

	@Override
	public boolean DestroyStudent(Student student) {
		// TODO Auto-generated method stub
		boolean result = true;
		if (CheckStudent(student.StudentNumber())) {
			//List<Course> c = new ArrayList<Course>();
			for (int i=0; i<courses.size(); i++){
				student.DeRegisterCourse(courses.get(i));
				courses.get(i).RemoveStudent(student);
			}
			result = students.remove(student);
			logger.info(String.format("University Operation: delete student %d; State: Success", student.StudentNumber()));
		} else {
			result = false;
			logger.info(String.format("University Operation: delete student %d; State: Fail; Reason: The student doesn't exist.", student.StudentNumber()));
		}
		return result;
	}
	
	private boolean candofinal = false;
	private boolean cantakecourse = true;
	
	@Override
	public boolean MarkStudents(Course course) {
		// TODO Auto-generated method stub
		boolean result = true;
		if (CheckCourse(course.Code())) {
			if (!course.getEnrollStudent().isEmpty()) {
				for (Student key: course.getEnrollStudent().keySet()) {
					//Random random = new Random();
					//course.getEnrollStudent().put(key, random.nextInt(25) + 75);
					CanTakeCourse();
					CannotDoFinal();
					DoAssignments(key, course);
					DoMidterms(key, course);
					if (course instanceof ProjectCourse) {
						DoProject(key, (ProjectCourse) course);
					}
					DoFinal(key, course);
				}
				result = true;
				logger.info(String.format("University Operation: mark students for course %d; State: Success", course.Code()));
			} else {
				result = false;
				logger.info(String.format("University Operation: mark students for course %d; State: Fail Reason: No students enrolled in this course.", course.Code()));
			}
		} else {
			result = false;
			logger.info(String.format("University Operation: mark students for course %d; State: Fail; Reason: The course doesn't exist.", course.Code()));
		}
		return result;
	}
	
	public void CannotDoFinal() {
		candofinal = false;
	}
	
	public void CanDoFinal() {
		candofinal = true;
	}
	
	public void CannotTakeCourse() {
		cantakecourse = false;
	}
	
	public void CanTakeCourse() {
		cantakecourse = true;
	}
	
	public int DoAssignments(Student student, Course course) {
		double mark;
		double weight;
		if (cantakecourse) {
			Random random = new Random();
			weight = 0;
			for (int i = 0; i < course.getWeightOfAssignments().size(); i++) {
				weight = weight + course.getWeightOfAssignments().get(i);
			}
			if (weight > 0) {
				weight = weight / 100;
				mark = course.getEnrollStudent().get(student) + weight
						* (random.nextInt(25) + 75);
				course.getEnrollStudent().put(student, (int) mark);
				CanDoFinal();
				CanTakeCourse();
				return (int) mark;
			} else {
				CannotDoFinal();
				CannotTakeCourse();
				return 0;
			}
		} else {
			return 0;
		}
	}

	public int DoMidterms(Student student, Course course) {
		double mark;
		double weight;
		if (cantakecourse) {
			Random random = new Random();
			weight = 0;
			for (int i = 0; i < course.getWeightOfMidterms().size(); i++) {
				weight = weight + course.getWeightOfMidterms().get(i);
			}
			if (weight > 0) {
				weight = weight / 100;
				mark = course.getEnrollStudent().get(student) + weight
						* (random.nextInt(25) + 75);
				course.getEnrollStudent().put(student, (int) mark);
				CanDoFinal();
				CanTakeCourse();
				return (int) mark;
			} else {
				CannotDoFinal();
				CannotTakeCourse();
				return 0;
			}
		} else {
			return 0;
		}
	}

	public int DoProject(Student student, ProjectCourse course) {
		double mark;
		double weight;
		if (cantakecourse) {
			Random random = new Random();
			weight = course.getWeightOfProject();
			if (weight > 0) {
				weight = weight / 100;
				mark = course.getEnrollStudent().get(student) + weight
						* (random.nextInt(25) + 75);
				course.getEnrollStudent().put(student, (int) mark);
				CanDoFinal();
				CanTakeCourse();
				return (int) mark;
			} else {
				CannotDoFinal();
				CannotTakeCourse();
				return 0;
			}
		} else {
			return 0;
		}
	}

	public int DoFinal(Student student, Course course) {
		double mark;
		double weight;
		if (candofinal) {
			Random random = new Random();
			weight = course.getWeightOfFinal();
			if (weight > 0) {
				weight = weight / 100;
				mark = course.getEnrollStudent().get(student) + weight
						* (random.nextInt(25) + 75);
				course.getEnrollStudent().put(student, (int) mark);
				CannotDoFinal();
				CannotTakeCourse();
				return (int) mark;
			} else {
				CannotDoFinal();
				CannotTakeCourse();
				return 0;
			}
		} else {
			CannotDoFinal();
			CannotTakeCourse();
			return 0;
		}
	}

	@Override
	public List<Student> DeansList() {
		// TODO Auto-generated method stub
		List<Student> deansList = new ArrayList<Student>();
		for (int i=0; i<students.size(); i++) {
			if (students.get(i).AverageMark()>=85) {
				deansList.add(students.get(i));
			}
		}
		return deansList;
	}

}

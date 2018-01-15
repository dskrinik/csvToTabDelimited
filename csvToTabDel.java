import java.io.*;
import java.util.*;

public class csvToTabDel{

	private Set <Student> studentListHolder;
	private Set <School> schoolListHolder;
	private School schoolHolder;

	public void parse(String file){

		School temp; 
		Student studentHolder;
		schoolListHolder = new HashSet<>();
		studentListHolder = new HashSet<>();
	
		String[] result = file.trim().split("\\s*,\\s*");
			for(int i = 0; i < result.length-2; i+=3){				
				schoolHolder = new School(result[i+2]);
				schoolHolder.addStudent(new Student(result[i], result[i+1]));
					Iterator<School> it = schoolListHolder.iterator();
					while(it.hasNext()){
						temp = it.next();
						if(temp.equals(schoolHolder)){
							temp.addStudent(new Student(result[i], result[i+1]));
						}
					}
				schoolListHolder.add(schoolHolder);
			}
		writeToFiles(schoolListHolder);
	}

	void writeToFiles(Set<School> list){
		
		String destination;
		String content;
		String students;

		for(School s: list){
			destination = s.getName();
			try(BufferedWriter wr = new BufferedWriter(new FileWriter(destination))){
				studentListHolder = s.getStudents();	
				content = studentListToTabDel(studentListHolder);				
				wr.write(content);
			} catch (IOException e){e.printStackTrace();}
		}
	}

	public String studentListToTabDel(Set<Student> studentGroup){
		String separator = System.getProperty("line.separator");
		StringBuilder sb = new StringBuilder();
		for(Student s : studentGroup){
			sb.append(s.getID()).append("\t").append(s.getName()).append(separator);
		}
		return sb.toString();
	}

	public String fromFileToString(String fileName){
		String tester;
		String result="";
		try{
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			while((tester = br.readLine())!= null){
				result +=(tester+", ");
			}
		} catch (IOException e) { e.printStackTrace();}
		return result;
	}
	
	public static void main(String[] args){

		String file;
		csvToTabDel csv = new csvToTabDel();
		file = csv.fromFileToString("sourceFile.txt");
		csv.parse(file);
	}
}

class School{

	private String name;
	private Set<Student> students = new HashSet();

	School(String name){
		this.name = name;
	}
	Set<Student> getStudents(){
		return students;
	}
	String getName(){
		return name;
	}
	void addStudent(Student s){
		students.add(s);
	}
	public String toString(){
		return name;
	}
	
	public boolean equals(Object obj){
		if (obj == this) return true;
		if (!(obj instanceof School)) return false;
		School other = (School) obj;
		return other.name.equals(getName());
	}
	public int hashCode(){
		return Objects.hash(getName());
	}
}

class Student{
	private String studentName;
	private String id;
	School schoolName;
	
	Student(String id, String name){
		this.id = id;
		this.studentName = name;
	}
	String getID(){
		return id;
	}
	String getName(){
		return studentName;
	}
	public boolean equals(Object obj){
		if (obj == this) return true;
		if (!(obj instanceof Student)) return false;
		Student other = (Student) obj;
		return other.studentName.equals(getName())&& other.id.equals(getID());
	}
	public int hashCode() {
		return Objects.hash(getName(), getID());
	}
}


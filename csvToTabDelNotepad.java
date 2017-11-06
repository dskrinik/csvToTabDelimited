import java.io.*;
import java.util.*;

    class Tester{
	ArrayList<School> schoolListHolder;
	ArrayList<School> schoolListHolderDuplicate;
	Set<Student> studentListHolder;
	School schoolHolder;

	public void csvFileToTabDelFiles(String input){
		studentListHolder = new HashSet<>();
		schoolListHolderDuplicate = new ArrayList<>();
		schoolListHolder = new ArrayList<>();

		String[] st = input.split("\\s*,\\s*");
		for (int i = 0; i < st.length-2; i+=3){
			schoolHolder = new School(st[i+2]);
			schoolHolder.addStudent(new Student(st[i], st[i+1]));
			if(schoolListHolder.contains(schoolHolder))
				schoolListHolderDuplicate.add(schoolHolder);
			else
				schoolListHolder.add(schoolHolder);
		}
		for (School duplicateSchool : schoolListHolderDuplicate) {
			for (Student duplicateStudent : duplicateSchool.getStudents()) {
				schoolHolder = schoolListHolder.get(schoolListHolder.indexOf(duplicateSchool));
				studentListHolder = schoolHolder.getStudents();
				studentListHolder.add(duplicateStudent);
				schoolHolder.setStudents(studentListHolder);
				schoolListHolder.set(schoolListHolder.indexOf(duplicateSchool), schoolHolder);
			}
			writeSchoolsAndStudentsToFiles(schoolListHolder);
		}
	}
	public String convertFileToString(String path) {
		String test="";
		String result="";
		try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
			while ((test = reader.readLine()) != null) {
				result +=(test + ", ");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	public void writeSchoolsAndStudentsToFiles(ArrayList<School> schoolList) {
		String toWrite = "";
		for (School s : schoolList){
			String destination = s.getName();
			try(BufferedWriter writer = new BufferedWriter(
					new FileWriter(destination))) {
				studentListHolder = s.getStudents();
				toWrite = studentGroupToSpaceDelString(studentListHolder);
				writer.write(toWrite);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public String studentGroupToSpaceDelString(Set<Student> studentGroup){
		String separator = System.getProperty("line.separator");
		StringBuilder sb = new StringBuilder();
		for(Student student : studentGroup){
			sb.append(student.getId()).append("\t")
			.append(student.getName()).append(separator);
		}
		return sb.toString();
	}
	public static void main(String[] args){
		Tester t = new Tester();
		String file = t.convertFileToString("sourceFile.txt");
		t.csvFileToTabDelFiles(file);
	}
}
    class Student{
	private String id;
	private String name;

	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name = name;
	}
	public String getId(){
		return id;
	}
	public Student(String id, String name){
		this.id = id;
		this.name = name;
	}
	public int hashCode() {
		return Objects.hash(getName(), getId());
	}
	public boolean equals(Object obj){
		if (obj == this) return true;
		if (!(obj instanceof Student)) return false;
		Student other = (Student) obj;
		return other.name.equals(getName())&& other.id.equals(getId());
	}
}
    class School {
	private String schoolName;
	private Set<Student> studentGroup = new HashSet<>();

	public String getName(){
		return schoolName;
	}
	public void setName(String name){
		this.schoolName = name;
	}
	public Set<Student> getStudents(){
		return studentGroup;
	}
	public void setStudents(Set<Student> studentListHolder){
		studentGroup = studentListHolder;
	}
	public School(String schoolName){
		this.schoolName = schoolName;
	}
	public void addStudent(Student s){
		studentGroup.add(s);
	}
	public int hashCode(){
		return Objects.hash(getName());
	}
	public boolean equals(Object obj){
		if (obj == this) return true;
		if (!(obj instanceof School)) return false;
		School other = (School) obj;
		return other.schoolName.equals(getName());
	}
}

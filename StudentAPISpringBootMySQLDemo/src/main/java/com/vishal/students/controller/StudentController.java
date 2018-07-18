package com.vishal.students.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vishal.students.exception.ResourceNotFoundException;
import com.vishal.students.model.Student;
import com.vishal.students.repository.StudentRepository;

@RestController
@RequestMapping("/api")
public class StudentController {

	@Autowired
	StudentRepository repository;

	// get all students
	@GetMapping("/students")
	public List<Student> getAllStudents() {
		return repository.findAll();
	}

	// add new student
	@PostMapping("/student")
	public Student createStudent(@Valid @RequestBody Student student) {
		return repository.save(student);
	}

	// get single student by id
	@GetMapping("student/{id}")
	public Student getStudentById(@PathVariable(value = "id") int rollNo) {
		return repository.findById(rollNo).orElseThrow(() -> new ResourceNotFoundException("Student", "id", rollNo));
	}

	// get students by first name
	@GetMapping("students/name/{name}")
	public List<Student> getStudentByFirstName(@PathVariable(value = "name") String name) {
		return repository.findAllByFirstName(name);
	}

	// get math's toper
	@GetMapping("student/maths")
	public Student getMathsTopper() {
		Student student = (Student) repository.getMaxMathsScorer().get(0);
		return student;
	}

	// get Science toper
	@GetMapping("student/science")
	public Student getScienceTopper() {
		Student student = (Student) repository.getMaxScienceScorer().get(0);
		return student;
	}

	// get English toper
	@GetMapping("student/english")
	public Student getEnglishTopper() {
		Student student = (Student) repository.getMaxEnglishScorer().get(0);
		return student;
	}

	// update a student
	@PutMapping("student/{id}")
	public Student updateSyudent(@PathVariable(value = "id") int rollNo, @Valid @RequestBody Student studentDetails) {

		Student student = repository.findById(rollNo)
				.orElseThrow(() -> new ResourceNotFoundException("Student", "id", rollNo));

		student.setFirstName(studentDetails.getFirstName());
		student.setLastName(studentDetails.getLastName());
		student.setMathsMarks(studentDetails.getMathsMarks());
		student.setScienceMarks(studentDetails.getScienceMarks());
		student.setEnglishMarks(studentDetails.getEnglishMarks());

		Student updatedStudent = repository.save(student);
		return updatedStudent;
	}

	// delete a student
	@DeleteMapping("/student/{id}")
	public ResponseEntity<?> deleteStudent(@PathVariable(value = "id") int rollNo) {
		Student student = repository.findById(rollNo)
				.orElseThrow(() -> new ResourceNotFoundException("Student", "id", rollNo));

		repository.delete(student);

		return ResponseEntity.ok().build();
	}

	// sorted list by first name ascending
	@GetMapping("/students/sorted/firstname/asc")
	public List<Student> sortedListFirstName() {
		return repository.getStudentsSortedFirstName();
	}

	// sorted list by last name ascending
	@GetMapping("/students/sorted/lastname/asc")
	public List<Student> sortedListLastName() {
		return repository.getStudentsSortedLastName();
	}

	// sorted list by first name descending
	@GetMapping("/students/sorted/firstname/desc")
	public List<Student> sortedListFirstNameDesc() {
		return repository.getStudentsSortedFirstNameDesc();
	}

	// sorted list by last name descending
	@GetMapping("/students/sorted/lastname/desc")
	public List<Student> sortedListLastNameDesc() {
		return repository.getStudentsSortedLastNameDesc();
	}
}

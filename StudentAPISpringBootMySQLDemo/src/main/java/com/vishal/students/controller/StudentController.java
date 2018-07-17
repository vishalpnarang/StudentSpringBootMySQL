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
	public List<Student> getAllStudents(){
		return repository.findAll();
	}
	
	// add new student
	@PostMapping("/student")
	public Student createStudent(@Valid @RequestBody Student student){
		return repository.save(student);
	}
	
	// get single student
	@GetMapping("student/{id}")
	public Student getStudentById(@PathVariable(value = "id")int rollNo){
		return repository.findById(rollNo)
				.orElseThrow(() -> new ResourceNotFoundException("Student", "id", rollNo));
	}
	
	// update a student
	@PutMapping("student/{id}")
	public Student updateSyudent(@PathVariable(value = "id")int rollNo, @Valid @RequestBody Student studentDetails){
		
		Student student = repository.findById(rollNo)
				.orElseThrow(() -> new ResourceNotFoundException("Student","id",rollNo));
		
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
	public ResponseEntity<?> deleteStudent(@PathVariable(value = "id")int rollNo){
		Student student = repository.findById(rollNo)
				.orElseThrow(() -> new ResourceNotFoundException("Student", "id", rollNo));
		
		repository.delete(student);
		
		return ResponseEntity.ok().build();
	}
}

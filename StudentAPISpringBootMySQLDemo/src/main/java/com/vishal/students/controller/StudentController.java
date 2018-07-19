package com.vishal.students.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api")
@Api(value = "Student Controllers", description = "Student CRUD operations along with other API's.")
public class StudentController {

	@Autowired
	StudentRepository repository;

	// get all students
	@GetMapping("/students")
	@ApiOperation(value = "Get All Students", notes = "Will return StudentVO")
	public List<Student> getAllStudents() {
		return repository.findAll();
	}

	// add new student
	@PostMapping("/student")
	@ApiOperation(value = "To Add new Student", notes = "Can accept First Name, Last Name, and marks for Maths, Science and English")
	public Student createStudent(@Valid @RequestBody Student student) {
		return repository.save(student);
	}

	// get single student by id
	@GetMapping("student/{id}")
	@ApiOperation(value = "Get Student Details", notes = "Requires Roll No")
	public Student getStudentByRollNo(@PathVariable(value = "id") int rollNo) {
		return repository.findById(rollNo).orElseThrow(() -> new ResourceNotFoundException("Student", "id", rollNo));
	}

	// get students by first name
	@GetMapping("students/name/{name}")
	@ApiOperation(value = "Get All Students by the First Name", notes = "Requires Name")
	public List<Student> getStudentsByFirstName(@PathVariable(value = "name") String name) {
		return repository.findByFirstNameContaining("%" + name + "%");
	}

	// get students by last name
	@GetMapping("students/lastname/{name}")
	@ApiOperation(value = "Get All Students by the last Name", notes = "Requires Name")
	public List<Student> getStudentsByLastName(@PathVariable(value = "name") String name) {
		return repository.findAllByLastNameIgnoreCaseContaining(name);
	}

	// get math's toper
	@GetMapping("student/maths")
	@ApiOperation(value = "Get Maths Toper", notes = "Will return Student Record")
	public Student getMathsTopper() {
		Student student = (Student) repository.getMaxMathsScorer().get(0);
		return student;
	}

	// get Science toper
	@GetMapping("student/science")
	@ApiOperation(value = "Get Science Toper", notes = "Will return Student Record")
	public Student getScienceTopper() {
		Student student = (Student) repository.getMaxScienceScorer().get(0);
		return student;
	}

	// get English toper
	@GetMapping("student/english")
	@ApiOperation(value = "Get English Toper", notes = "Will return Student Record")
	public Student getEnglishTopper() {
		Student student = (Student) repository.getMaxEnglishScorer().get(0);
		return student;
	}

	// update a student
	@PutMapping("student/{id}")
	@ApiOperation(value = "Update Student record", notes = "Requires Roll No")
	public Student updateStudent(@PathVariable(value = "id") int rollNo, @Valid @RequestBody Student studentDetails) {

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
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@DeleteMapping("/student/{id}")
	@ApiOperation(value = "Delete Student record", notes = "Requires Roll No")
	public ResponseEntity<?> deleteStudent(@PathVariable(value = "id") int rollNo) {
		Student student = repository.findById(rollNo)
				.orElseThrow(() -> new ResourceNotFoundException("Student", "id", rollNo));

		repository.delete(student);

		return new ResponseEntity("Student deleted successfully", HttpStatus.OK);
	}

	// sorted list by first name ascending
	@GetMapping("/students/sorted/firstname/asc")
	@ApiOperation(value = "Gell all students sorted by First Name Ascending", notes = "Will return all students ascending sorted First Name")
	public List<Student> sortedListFirstNameAsc() {
		return repository.getStudentsSortedFirstName();
	}

	// sorted list by last name ascending
	@GetMapping("/students/sorted/lastname/asc")
	@ApiOperation(value = "Gell all students sorted by First Name Decending", notes = "Will return all students decending sorted First Name")
	public List<Student> sortedListLastNameAsc() {
		return repository.getStudentsSortedLastName();
	}

	// sorted list by first name descending
	@GetMapping("/students/sorted/firstname/desc")
	@ApiOperation(value = "Gell all students sorted by Last Name Ascending", notes = "Will return all students ascending sorted Last Name")
	public List<Student> sortedListFirstNameDesc() {
		return repository.getStudentsSortedFirstNameDesc();
	}

	// sorted list by last name descending
	@GetMapping("/students/sorted/lastname/desc")
	@ApiOperation(value = "Gell all students sorted by Last Name Decending", notes = "Will return all students decending sorted Last Name")
	public List<Student> sortedListLastNameDesc() {
		return repository.getStudentsSortedLastNameDesc();
	}
}

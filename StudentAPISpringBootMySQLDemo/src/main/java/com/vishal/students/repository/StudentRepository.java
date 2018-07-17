package com.vishal.students.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vishal.students.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer>{

	
}

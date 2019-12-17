package com.example.demoorderservice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JwtResponseRepository extends JpaRepository<JwtResponse, Long> {

	JwtResponse findByUsername(String subject);

}

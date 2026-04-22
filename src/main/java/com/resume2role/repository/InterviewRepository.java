package com.resume2role.repository;

import com.resume2role.model.Interview;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface InterviewRepository extends MongoRepository<Interview, String> {
    List<Interview> findByUserId(String userId);

}
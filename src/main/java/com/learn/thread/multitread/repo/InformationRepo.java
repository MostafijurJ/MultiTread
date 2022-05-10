package com.learn.thread.multitread.repo;

import com.learn.thread.multitread.models.Information;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InformationRepo extends JpaRepository<Information, Integer> {
}

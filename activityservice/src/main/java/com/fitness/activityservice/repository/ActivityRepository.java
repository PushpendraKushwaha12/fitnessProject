package com.fitness.activityservice.repository;

import com.fitness.activityservice.models.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepository extends JpaRepository<Activity, String> {
}

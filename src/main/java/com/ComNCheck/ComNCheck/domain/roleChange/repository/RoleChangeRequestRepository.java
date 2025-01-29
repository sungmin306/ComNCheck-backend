package com.ComNCheck.ComNCheck.domain.roleChange.repository;

import com.ComNCheck.ComNCheck.domain.roleChange.model.entity.RoleChangeRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleChangeRequestRepository extends JpaRepository<RoleChangeRequest, Long> {
}

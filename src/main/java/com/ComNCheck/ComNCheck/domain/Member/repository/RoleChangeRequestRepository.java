package com.ComNCheck.ComNCheck.domain.Member.repository;

import com.ComNCheck.ComNCheck.domain.Member.model.entity.Role;
import com.ComNCheck.ComNCheck.domain.Member.model.entity.RoleChangeRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleChangeRequestRepository extends JpaRepository<RoleChangeRequest, Long> {
}

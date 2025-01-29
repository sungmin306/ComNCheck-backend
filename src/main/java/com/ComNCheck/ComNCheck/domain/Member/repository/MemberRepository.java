package com.ComNCheck.ComNCheck.domain.Member.repository;

import com.ComNCheck.ComNCheck.domain.Member.model.entity.Member;
import com.ComNCheck.ComNCheck.domain.Member.model.entity.Role;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
    Optional<Member> findById(Long id);
    //Optional<Member> findBySub(String email);

    Optional<Member> findByRole(Role role);

    List<Member> findAllByRole(Role role);

}

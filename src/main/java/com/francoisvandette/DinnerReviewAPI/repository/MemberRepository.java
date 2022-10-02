package com.francoisvandette.DinnerReviewAPI.repository;

import com.francoisvandette.DinnerReviewAPI.model.Member;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface MemberRepository extends CrudRepository<Member, Long> {

    Optional<Member> findByUsername(String username);


}

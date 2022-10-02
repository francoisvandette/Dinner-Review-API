package com.francoisvandette.DinnerReviewAPI.controller;

import com.francoisvandette.DinnerReviewAPI.model.Member;
import com.francoisvandette.DinnerReviewAPI.repository.MemberRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class MemberController {

    private final MemberRepository memberRepository;

    public MemberController(MemberRepository memberRepository) { this.memberRepository = memberRepository; }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Member createUser(@RequestBody Member member) {
        Optional<Member> userExistCheckOptional = this.memberRepository.findByUsername(member.getUsername());
        if (userExistCheckOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exists.");
        }
        return this.memberRepository.save(member);
    }

    @PutMapping("/{username}/update")
    @ResponseStatus(HttpStatus.OK)
    public Member updateUser(@PathVariable(name="username") String username, @RequestBody Member newMemberInfo) {
        Optional<Member> userToUpdateOptional = this.memberRepository.findByUsername(username);
        if (userToUpdateOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Member could not be found.");
        }
        return this.memberRepository.save(newMemberInfo);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Member> getAllUsers() {
        return this.memberRepository.findAll();
    }

    @GetMapping("/{username}")
    @ResponseStatus(HttpStatus.OK)
    public Member getUser(@PathVariable(name="username") String username) {
        Optional<Member> userOptional = this.memberRepository.findByUsername(username);
        if (userOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Member could not be found.");
        }
        return userOptional.get();
    }

    @GetMapping("/exists/{username}")
    @ResponseStatus(HttpStatus.OK)
    public boolean doesUserExist(@PathVariable(name="username") String username) {
        return this.memberRepository.findByUsername(username).isPresent();
    }
}

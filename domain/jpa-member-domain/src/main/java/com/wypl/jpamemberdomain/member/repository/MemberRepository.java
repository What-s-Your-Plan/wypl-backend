package com.wypl.jpamemberdomain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wypl.jpamemberdomain.member.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}

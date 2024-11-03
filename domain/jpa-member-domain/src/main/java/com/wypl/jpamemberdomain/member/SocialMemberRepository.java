package com.wypl.jpamemberdomain.member;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wypl.jpamemberdomain.member.domain.SocialMember;

public interface SocialMemberRepository extends JpaRepository<SocialMember, Long> {
	Optional<SocialMember> findByOauthProviderAndOauthId(OauthProvider provider, String oauthId);
}

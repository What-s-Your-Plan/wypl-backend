package com.wypl.wyplcore.member.service;

import java.time.LocalDate;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wypl.googleoauthclient.GoogleOAuthClient;
import com.wypl.googleoauthclient.data.response.GoogleUserInfoResponse;
import com.wypl.googleoauthclient.domain.AuthMember;
import com.wypl.jpamemberdomain.member.OauthProvider;
import com.wypl.jpamemberdomain.member.data.MemberSaveDto;
import com.wypl.jpamemberdomain.member.data.SocialMemberSaveDto;
import com.wypl.jpamemberdomain.member.domain.Member;
import com.wypl.jpamemberdomain.member.domain.SocialMember;
import com.wypl.jpamemberdomain.member.repository.MemberRepository;
import com.wypl.jpamemberdomain.member.repository.SocialMemberRepository;
import com.wypl.jpamemberdomain.member.utils.SocialMemberRepositoryUtils;
import com.wypl.wyplcore.member.data.MemberEventDto;

import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MemberService {
	private final GoogleOAuthClient googleOAuthClient;
	private final MemberRepository memberRepository;
	private final SocialMemberRepository socialMemberRepository;
	private final ApplicationEventPublisher applicationEventPublisher;

	@Transactional
	public void deleteMember(AuthMember authMember) {
		// Todo : 회원 탈퇴 로직 변경 필요
		memberRepository.deleteById(authMember.id());
		applicationEventPublisher.publishEvent(new MemberEventDto(authMember.accessToken()));
	}

	@Transactional
	public long findMemberIdOrSaveMember(String accessToken, GoogleUserInfoResponse googleUserInfoResponse) {
		if (isNewMember(googleUserInfoResponse)) {
			LocalDate birthday = googleOAuthClient.fetchBirthday(accessToken);

			MemberSaveDto memberSaveDto = MemberSaveDto.builder()
				.email(googleUserInfoResponse.email())
				.birthday(birthday)
				.nickname(googleUserInfoResponse.name())
				.profileImage(googleUserInfoResponse.picture())
				.build();

			SocialMemberSaveDto socialMemberSaveDto = SocialMemberSaveDto.builder()
				.oauthProvider(OauthProvider.GOOGLE)
				.oauthId(googleUserInfoResponse.id())
				.build();

			return saveNewMember(memberSaveDto, socialMemberSaveDto);
		}

		return SocialMemberRepositoryUtils.getSocialMember(socialMemberRepository, googleUserInfoResponse.id()).getId();
	}

	private boolean isNewMember(GoogleUserInfoResponse googleUserInfoResponse) {
		return !socialMemberRepository.existsByOauthProviderAndOauthId(OauthProvider.GOOGLE,
			googleUserInfoResponse.id());
	}

	private long saveNewMember(MemberSaveDto memberSaveDto, SocialMemberSaveDto socialMemberSaveDto) {
		Member newMember = memberRepository.save(Member.of(memberSaveDto));
		SocialMember socialMember = socialMemberRepository.save(SocialMember.of(newMember, socialMemberSaveDto));
		return socialMember.getId();
	}
}

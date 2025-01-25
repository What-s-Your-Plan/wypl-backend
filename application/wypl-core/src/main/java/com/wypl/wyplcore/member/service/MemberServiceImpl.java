package com.wypl.wyplcore.member.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wypl.authdomain.auth.service.AuthDomainServiceImpl;
import com.wypl.googleoauthclient.GoogleOAuthClient;
import com.wypl.googleoauthclient.data.response.GoogleUserInfoResponse;
import com.wypl.googleoauthclient.domain.AuthMember;
import com.wypl.jpamemberdomain.member.OauthProvider;
import com.wypl.jpamemberdomain.member.data.MemberSaveDto;
import com.wypl.jpamemberdomain.member.data.SocialMemberSaveDto;
import com.wypl.jpamemberdomain.member.repository.MemberRepository;
import com.wypl.jpamemberdomain.member.repository.SocialMemberRepository;
import com.wypl.jpamemberdomain.member.utils.SocialMemberRepositoryUtils;

import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MemberServiceImpl {
	private final MemberRepository memberRepository;
	private final GoogleOAuthClient googleOAuthClient;
	private final AuthDomainServiceImpl authDomainService;
	private final SocialMemberRepository socialMemberRepository;

	@Transactional
	public void deleteMember(AuthMember authMember) {
		memberRepository.deleteById(authMember.id());
	}

	public long findMemberIdAfterSaveMember(String accessToken, GoogleUserInfoResponse googleUserInfoResponse) {
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

			return authDomainService.saveAuthData(memberSaveDto, socialMemberSaveDto);
		}

		return SocialMemberRepositoryUtils.getSocialMember(socialMemberRepository, googleUserInfoResponse.id()).getId();
	}

	// todo:
	private boolean isNewMember(GoogleUserInfoResponse googleUserInfoResponse) {
		return !socialMemberRepository.existsByOauthProviderAndOauthId(OauthProvider.GOOGLE,
			googleUserInfoResponse.id());
	}
}

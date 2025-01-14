package com.ComNCheck.ComNCheck.domain.Member.service;
import com.ComNCheck.ComNCheck.domain.Member.model.dto.CustomOAuth2Member;
import com.ComNCheck.ComNCheck.domain.Member.model.dto.response.MemberDTO;
import com.ComNCheck.ComNCheck.domain.Member.model.entity.Member;
import com.ComNCheck.ComNCheck.domain.Member.model.entity.Role;
import com.ComNCheck.ComNCheck.domain.Member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CustomOAuthMemberService extends DefaultOAuth2UserService {
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        System.out.println(oAuth2User);
        // 구글에서 받은 사용자 정보
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");


        // 이메일 변경 생각해서 이메일로 찾으면 안될꺼같음
        Member member = memberRepository.findByEmail(email).orElseGet(() -> {
            Member newMember = Member.builder()
                    .email(email)
                    .name(name)
                    .major("컴공")
                    .role(Role.ROLE_STUDENT)
                    .studentNumber(123124)
                    .build();
            memberRepository.save(newMember);
            return newMember;
            });

            MemberDTO memberDTO = new MemberDTO();
            memberDTO.setEmail(member.getEmail());
            memberDTO.setName(member.getName());
            memberDTO.setMajor(member.getMajor());
            memberDTO.setRole(member.getRole());
            memberDTO.setStudentNumber(member.getStudentNumber());

            return new CustomOAuth2Member(memberDTO);
    }
}


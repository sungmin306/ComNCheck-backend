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
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomOAuthUserService extends DefaultOAuth2UserService {
    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        System.out.println(oAuth2User);
        // 구글에서 받은 사용자 정보
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");

        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setEmail(email);
        memberDTO.setName(name);
        memberDTO.setMajor("컴공");
        memberDTO.setRole(Role.ROLE_STUDENT);
        memberDTO.setStudentNumber(12314);

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

        return new CustomOAuth2Member(memberDTO);
    }
}


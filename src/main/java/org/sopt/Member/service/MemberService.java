package org.sopt.Member.service;


import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.sopt.Member.domain.Member;
import org.sopt.Member.domain.MemberAppender;
import org.sopt.Member.domain.MemberDeleter;
import org.sopt.Member.domain.MemberReader;
import org.sopt.Member.domain.MemberUpdater;
import org.sopt.security.oauth2.memberInfo.OAuth2MemberInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberReader memberReader;
    private final MemberUpdater memberUpdater;
    private final MemberDeleter memberDeleter;
    private final MemberAppender memberAppender;

    public Member getMemberById(Long id) {
        return memberReader.read(id);
    }

    public Optional<Member> findMemberById(Long id) {
        return memberReader.find(id);
    }

    @Transactional
    public Member findOrCreateByOAuth2(OAuth2MemberInfo memberInfo) {
        return memberReader.findByEmail(memberInfo.getEmail())
                .orElseGet(() -> memberAppender.append(memberInfo));
    }

    public List<Member> getMemberAll() {
        return memberReader.readAll();
    }


    public Map<Long, Member> getMemberMapByIds(Set<Long> MemberIds) {
        return memberReader.readAllByIds(MemberIds);
    }

    @Transactional
    public void upadateMember(Long id, String name, String bio) {
        memberUpdater.update(id, name, bio);
    }

    @Transactional
    public void delete(Long id) {
        memberDeleter.delete(id);
    }
}

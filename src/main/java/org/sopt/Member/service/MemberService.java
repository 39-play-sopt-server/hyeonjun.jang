package org.sopt.Member.service;


import java.util.List;
import java.util.Map;
import java.util.Set;
import org.sopt.Member.domain.Member;
import org.sopt.Member.domain.MemberAppender;
import org.sopt.Member.domain.MemberDeleter;
import org.sopt.Member.domain.MemberReader;
import org.sopt.Member.domain.MemberUpdater;
import org.sopt.Member.service.vo.CreateMemberCommand;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class MemberService {
    private final MemberReader memberReader;
    private final MemberUpdater memberUpdater;
    private final MemberDeleter memberDeleter;
    private final MemberAppender memberAppender;

    public MemberService(MemberReader memberReader, MemberUpdater memberUpdater, MemberDeleter memberDeleter,
                         MemberAppender memberAppender) {
        this.memberReader = memberReader;
        this.memberUpdater = memberUpdater;
        this.memberDeleter = memberDeleter;
        this.memberAppender = memberAppender;
    }


    @Transactional
    public void createMember(CreateMemberCommand command) {
        memberAppender.append(
                command.email(),
                command.name(),
                command.bio(),
                command.gender(),
                command.role()
        );
    }

    public Member getMemberById(Long id) {
        return memberReader.read(id);
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

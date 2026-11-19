package org.sopt.Member.persistence;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.sopt.Member.domain.Member;
import org.sopt.Member.enums.Gender;
import org.sopt.Member.enums.Role;
import org.sopt.common.entity.BaseTimeEntity;
import org.sopt.security.oauth2.provider.OAuth2Provider;

@Getter
@Entity(name = "Member")
@Table(name = "members")
@SQLRestriction("deleted_at IS NULL")
@SQLDelete(sql = "UPDATE members SET deleted_at = NOW() WHERE id = ?")
public class MemberJpaEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    // oAuth 의 경우 pasword가 없어야해서 null ㄱㄴ
    private String password;
    private String name;
    private String bio;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;


    @Enumerated(EnumType.STRING)
    private OAuth2Provider oAuth2Provider;
    private String oAuth2Id;

    protected MemberJpaEntity() {
    } // JPA 전용 생성자

    public MemberJpaEntity(Long id, String email, String password, String name, String bio, Gender gender, Role role,
                           OAuth2Provider oAuth2Provider,
                           String oAuth2Id) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.bio = bio;
        this.gender = gender;
        this.role = role;
        this.oAuth2Provider = oAuth2Provider;
        this.oAuth2Id = oAuth2Id;
    }

    public static MemberJpaEntity fromDomain(Member member) {
        return new MemberJpaEntity(member.getId(), member.getEmail(), member.getPassword(), member.getName(),
                member.getBio(),
                member.getGender(), member.getRole(), member.getOAuth2Provider(),
                member.getOAuth2Id());
    }


    public Member toDomain() {
        return Member.createWithId(id, email, password, name, bio, gender, role, oAuth2Provider, oAuth2Id);
    }

}

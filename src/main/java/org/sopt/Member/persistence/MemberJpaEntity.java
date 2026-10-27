package org.sopt.Member.persistence;


import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.sopt.Member.domain.Member;
import org.sopt.Member.enums.Gender;
import org.sopt.Member.enums.Role;
import org.sopt.common.entity.BaseTimeEntity;


@Entity(name = "Member")
@Table(name = "Member")
@SQLRestriction("deleted_at")
@SQLDelete(sql = "UPDATE member SET deleted_at = NOW() WHERE id = ?")
public class MemberJpaEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String name;
    private String bio;
    private Gender gender;
    private Role role;

    protected MemberJpaEntity() {
    } // JPA 전용 생성자

    public MemberJpaEntity(Long id, String email, String name, String bio, Gender gender, Role role) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.bio = bio;
        this.gender = gender;
        this.role = role;
    }

    public static MemberJpaEntity fromDomain(Member member) {
        return new MemberJpaEntity(member.getId(), member.getEmail(), member.getName(), member.getBio(),
                member.getGender(), member.getRole());
    }


    // getter
    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getBio() {
        return bio;
    }

    public Gender getGender() {
        return gender;
    }

    public Role getRole() {
        return role;
    }

    public Long getId() {
        return id;
    }

    public Member toDomain() {
        return Member.creatWithId(id, email, name, bio, gender, role);
    }
}

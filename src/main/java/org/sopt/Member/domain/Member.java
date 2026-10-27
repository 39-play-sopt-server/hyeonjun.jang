package org.sopt.Member.domain;

import org.sopt.Member.enums.Gender;
import org.sopt.Member.enums.Role;


public class Member {
    private final Long id;
    private final String email;
    private String name;
    private String bio;
    private final Gender gender;
    private final Role role;

    public Member(Long id, String email, String name, String bio, Gender gender, Role role) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.bio = bio;
        this.gender = gender;
        this.role = role;
    }


    // DB에서 조회한 데이터를 다시 도메인 객체로 복원할 때 사용된다.
    public static Member creatWithId(Long id, String email, String name, String bio, Gender gender, Role role) {
        return new Member(id, email, name, bio, gender, role);
    }


    // DB 저장되지 않았을 때 만약 DB에 저장된다면 id는 JPA에서 자동으로 채워짐
    public static Member create(String email, String name, String bio, Gender gender, Role role) {
        return new Member(null, email, name, bio, gender, role);
    }

    public void update(String name, String bio) {
        this.name = name;
        this.bio = bio;
    }

    //Getter
    public Long getId() {
        return id;
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

    public String getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }
}

package org.sopt.Member.service.vo;

public record UpdateMemberCommand(
        String name,
        String bio
) {
    public static UpdateMemberCommand of(
            String name,
            String bio
    ){
        return new UpdateMemberCommand(name, bio);
    }
}

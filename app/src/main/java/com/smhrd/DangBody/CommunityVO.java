package com.smhrd.DangBody;

public class CommunityVO {

    private int profile, post;
    private String nick, content;

    public CommunityVO(int profile, int post, String nick, String content) {
        this.profile = profile;
        this.post = post;
        this.nick = nick;
        this.content = content;
    }

    public CommunityVO(String nick, String content) {
        this.nick = nick;
        this.content = content;
    }

    public CommunityVO() { }

    public int getProfile() {
        return profile;
    }

    public int getPost() {
        return post;
    }

    public String getNick() {
        return nick;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "CommunityVO{" +
                "profile=" + profile +
                ", post=" + post +
                ", nick='" + nick + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}

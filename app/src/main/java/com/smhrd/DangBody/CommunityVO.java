package com.smhrd.DangBody;

public class CommunityVO {

    private int profile, post, likes;
    private String nick, content;

    public CommunityVO(int profile, int post, int likes, String nick, String content) {
        this.profile = profile;
        this.post = post;
        this.likes = likes;
        this.nick = nick;
        this.content = content;
    }

    public CommunityVO(int likes, String nick, String content) {
        this.likes = likes;
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

    public int getLikes() {return likes; }

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
                ", likes=" + likes +
                ", nick='" + nick + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}

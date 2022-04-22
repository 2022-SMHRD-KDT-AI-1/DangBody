package com.smhrd.DangBody;

public class CommunityVO {
    int seq, likes;
    String nick, content, img;

    public CommunityVO(int likes, String nick, String content, String img) {
        this.likes = likes;
        this.nick = nick;
        this.content = content;
        this.img = img;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    //    private int profile, post, likes;
//    private String nick, content;
//
//    public CommunityVO(int profile, int post, int likes, String nick, String content) {
//        this.profile = profile;
//        this.post = post;
//        this.likes = likes;
//        this.nick = nick;
//        this.content = content;
//    }
//
//    public CommunityVO(int likes, String nick, String content) {
//        this.likes = likes;
//        this.nick = nick;
//        this.content = content;
//    }
//
//    public CommunityVO() { }
//
//    public int getProfile() {
//        return profile;
//    }
//
//    public int getPost() {
//        return post;
//    }
//
//    public int getLikes() {return likes; }
//
//    public String getNick() {
//        return nick;
//    }
//
//    public String getContent() {
//        return content;
//    }
//
//    @Override
//    public String toString() {
//        return "CommunityVO{" +
//                "profile=" + profile +
//                ", post=" + post +
//                ", likes=" + likes +
//                ", nick='" + nick + '\'' +
//                ", content='" + content + '\'' +
//                '}';
//    }

}

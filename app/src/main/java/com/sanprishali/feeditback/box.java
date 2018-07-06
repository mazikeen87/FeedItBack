package com.sanprishali.feeditback;

import org.w3c.dom.Comment;

public class box {
    String desgn, tag, time, name,comment,rate;

    public box() {
    }

    public box(String name, String desgn , String tag, String time, String rate,String Comment) {
        this.name = name;
        this.desgn = desgn;
        this.tag = tag;
        this.time = time;
        this.comment=comment;
        this.rate=rate;
    }

    public String getname() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesgn() {
        return desgn;
    }

    public void setDesgn(String desgn) {
        this.desgn = desgn;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }
}




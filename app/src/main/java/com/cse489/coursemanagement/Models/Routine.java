package com.cse489.coursemanagement.Models;

public class Routine {
    private String MW;
    private String SR;
    private String ST;
    private String TR;
    private String course_id;

    public Routine() {
    }

    public Routine(String MW, String SR, String ST, String TR, String course_id) {
        this.MW = MW;
        this.SR = SR;
        this.ST = ST;
        this.TR = TR;
        this.course_id = course_id;
    }

    public String getMW() {
        return MW;
    }

    public String getSR() {
        return SR;
    }

    public String getST() {
        return ST;
    }

    public String getTR() {
        return TR;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setMW(String MW) {
        this.MW = MW;
    }

    public void setSR(String SR) {
        this.SR = SR;
    }

    public void setST(String ST) {
        this.ST = ST;
    }

    public void setTR(String TR) {
        this.TR = TR;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }
}

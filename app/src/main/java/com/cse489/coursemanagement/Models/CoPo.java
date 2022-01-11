package com.cse489.coursemanagement.Models;

public class CoPo {
    private String co1;
    private String co2;
    private String co3;
    private String co4;
    private String co5;
    private String co6;
    private String course_id;
    private String po1;
    private String po2;
    private String po3;
    private String po4;
    private String po5;
    private String po6;

    public CoPo(String co1, String co2, String co3, String co4, String co5, String co6, String course_id, String po1, String po2, String po3, String po4, String po5, String po6) {
        this.co1 = co1;
        this.co2 = co2;
        this.co3 = co3;
        this.co4 = co4;
        this.co5 = co5;
        this.co6 = co6;
        this.course_id = course_id;
        this.po1 = po1;
        this.po2 = po2;
        this.po3 = po3;
        this.po4 = po4;
        this.po5 = po5;
        this.po6 = po6;
    }

    public CoPo() {
    }


    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public String getCourse_id() {
        return course_id;
    }

    public String getCo1() {
        return co1;
    }

    public String getCo2() {
        return co2;
    }

    public String getCo3() {
        return co3;
    }

    public String getCo4() {
        return co4;
    }

    public String getCo5() {
        return co5;
    }

    public String getCo6() {
        return co6;
    }

    public String getPo1() {
        return po1;
    }

    public String getPo2() {
        return po2;
    }

    public String getPo3() {
        return po3;
    }

    public String getPo4() {
        return po4;
    }

    public String getPo5() {
        return po5;
    }

    public String getPo6() {
        return po6;
    }


    public void setCo1(String co1) {
        this.co1 = co1;
    }

    public void setCo2(String co2) {
        this.co2 = co2;
    }

    public void setCo3(String co3) {
        this.co3 = co3;
    }

    public void setCo4(String co4) {
        this.co4 = co4;
    }

    public void setCo5(String co5) {
        this.co5 = co5;
    }

    public void setCo6(String co6) {
        this.co6 = co6;
    }

    public void setPo1(String po1) {
        this.po1 = po1;
    }

    public void setPo2(String po2) {
        this.po2 = po2;
    }

    public void setPo3(String po3) {
        this.po3 = po3;
    }

    public void setPo4(String po4) {
        this.po4 = po4;
    }

    public void setPo5(String po5) {
        this.po5 = po5;
    }

    public void setPo6(String po6) {
        this.po6 = po6;
    }
}

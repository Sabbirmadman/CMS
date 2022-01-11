package com.cse489.coursemanagement.Models;


public class Course {

    private String course_id;
    private String course_Name;
    private String course_Credit;
    private String created_by;
    private String resource_id;
    private String desc;
    private String students;
    public Course() {
    }



    public Course(String course_id, String course_Name, String course_Credit, String created_by, String desc, String resource_id,String students) {
        this.course_id = course_id;
        this.course_Name = course_Name;
        this.course_Credit = course_Credit;
        this.created_by = created_by;
        this.resource_id = resource_id;
        this.desc = desc;
        this.students=students;
    }

    public String getStudents() {
        return students;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
    public String getDesc() {
        return desc;
    }

    public void setStudents(String students) {
        this.students = students;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public void setCourse_Name(String course_Name) {
        this.course_Name = course_Name;
    }

    public void setCourse_Credit(String course_Credit) {
        this.course_Credit = course_Credit;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public void setResource_id(String resource_id) {
        this.resource_id = resource_id;
    }

    public String getCourse_id() {
        return course_id;
    }

    public String getCourse_Name() {
        return course_Name;
    }

    public String getCourse_Credit() {
        return course_Credit;
    }

    public String getCreated_by() {
        return created_by;
    }

    public String getResource_id() {
        return resource_id;
    }
}

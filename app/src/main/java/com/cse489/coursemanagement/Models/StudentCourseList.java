package com.cse489.coursemanagement.Models;

import java.util.ArrayList;

public class StudentCourseList {
    private ArrayList<String> values = new ArrayList<>();

    public StudentCourseList() {
    }

    public StudentCourseList(ArrayList<String> values) {
        this.values = values;
    }

    public ArrayList<String> getValues() {
        return values;
    }

    public void setValues(ArrayList<String> values) {
        this.values = values;
    }
}

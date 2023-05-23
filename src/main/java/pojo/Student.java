package pojo;

import java.util.Arrays;

public class Student {
    private String id;
    private String name;
    private String sex;
    private String college;
    private String major;
    private String grade;

    private byte[] face_feature;

    public Student(String id, String name, String sex, String college, String major, String grade, byte[] face_feature) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.college = college;
        this.major = major;
        this.grade = grade;
        this.face_feature = face_feature;
    }


    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", college='" + college + '\'' +
                ", major='" + major + '\'' +
                ", grade='" + grade + '\'' +
                ", face_feature=" + Arrays.toString(face_feature) +
                '}';
    }

    public byte[] getFace_feature() {
        return face_feature;
    }

    public void setFace_feature(byte[] face_feature) {
        this.face_feature = face_feature;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}

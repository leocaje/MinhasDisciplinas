package com.minhasdisciplinas.model;

public class DisciplineDTO {
    private String id;
    private String course;
    private String disciplineName;
    private String professor;
    private int period;
    private int workloadHours;
    private String disciplineType;
    private String disciplineStatus;
    private String description;
    private int conclusionDate;
    private double grade;

    public DisciplineDTO() {

    }

    public DisciplineDTO(String id, String course, String disciplineName, String professor, int period, int workloadHours,
                         String disciplineType, String disciplineStatus, String description, int conclusionDate, double grade) {
        this.id = id;
        this.course = course;
        this.disciplineName = disciplineName;
        this.professor = professor;
        this.period = period;
        this.workloadHours = workloadHours;
        this.disciplineType = disciplineType;
        this.disciplineStatus = disciplineStatus;
        this.description = description;
        this.conclusionDate = conclusionDate;
        this.grade = grade;
    }

    public DisciplineDTO(String course, String disciplineName, String professor, int period, int workloadHours,
                         String disciplineType, String disciplineStatus, String description, int conclusionDate, double grade) {
        this.course = course;
        this.disciplineName = disciplineName;
        this.professor = professor;
        this.period = period;
        this.workloadHours = workloadHours;
        this.disciplineType = disciplineType;
        this.disciplineStatus = disciplineStatus;
        this.description = description;
        this.conclusionDate = conclusionDate;
        this.grade = grade;
    }

    public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getCourse() {
        return this.course;
    }
    public void setCourse(String course) {
        this.course = course;
    }

    public String getDisciplineName() {
        return this.disciplineName;
    }
    public void setDisciplineName(String disciplineName) {
        this.disciplineName = disciplineName;
    }

    public String getProfessor() {
        return this.professor;
    }
    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public int getPeriod() {
        return this.period;
    }
    public void setPeriod(int period) {
        this.period = period;
    }

    public int getWorkloadHours() {
        return this.workloadHours;
    }
    public void setWorkloadHours(int workloadHours) {
        this.workloadHours = workloadHours;
    }

    public String getDisciplineType() {
        return this.disciplineType;
    }
    public void setDisciplineType(String disciplineType) {
        this.disciplineType = disciplineType;
    }

    public String getDisciplineStatus() {
        return disciplineStatus;
    }
    public void setDisciplineStatus(String disciplineStatus) {
        this.disciplineStatus = disciplineStatus;
    }

    public String getDescription() {
        return this.description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public int getConclusionDate() {
        return this.conclusionDate;
    }
    public void setConclusionDate(int conclusionDate) {
        this.conclusionDate = conclusionDate;
    }

    public double getGrade() {
        return this.grade;
    }
    public void setGrade(double grade) {
        this.grade = grade;
    }

}

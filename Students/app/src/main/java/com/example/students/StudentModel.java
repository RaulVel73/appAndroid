package com.example.students;

public class StudentModel {

    String noControl, name, apMaterno, apPaterno, edad, carrera, semestre, email;

    public StudentModel() {
    }

    public StudentModel(String noControl, String name, String apMaterno, String apPaterno, String edad, String carrera, String semestre, String email) {
        this.noControl = noControl;
        this.name = name;
        this.apMaterno = apMaterno;
        this.apPaterno = apPaterno;
        this.edad = edad;
        this.carrera = carrera;
        this.semestre = semestre;
        this.email = email;
    }

    public String getNoControl() {
        return noControl;
    }

    public void setNoControl(String noControl) {
        this.noControl = noControl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getApMaterno() {
        return apMaterno;
    }

    public void setApMaterno(String apMaterno) {
        this.apMaterno = apMaterno;
    }

    public String getApPaterno() {
        return apPaterno;
    }

    public void setApPaterno(String apPaterno) {
        this.apPaterno = apPaterno;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public String getSemestre() {
        return semestre;
    }

    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

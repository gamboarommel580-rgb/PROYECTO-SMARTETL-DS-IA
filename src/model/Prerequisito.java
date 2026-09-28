package model;
public class Prerequisito { private String origen,dependiente; public Prerequisito(String o,String d){origen=o;dependiente=d;} public String getOrigen(){return origen;} public String getDependiente(){return dependiente;} public String toCsv(){return origen+","+dependiente;} public String toString(){return origen+" -> "+dependiente;} }

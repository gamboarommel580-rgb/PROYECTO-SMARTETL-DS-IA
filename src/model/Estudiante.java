package model;
public class Estudiante {
 private String id,nombres,apellidos,carrera; private int edad,semestre; private double promedio;
 public Estudiante(String id,String n,String a,int e,String c,int s,double p){this.id=id;nombres=n;apellidos=a;edad=e;carrera=c;semestre=s;promedio=p;}
 public String getId(){return id;} public String getNombres(){return nombres;} public String getApellidos(){return apellidos;} public int getEdad(){return edad;} public String getCarrera(){return carrera;} public int getSemestre(){return semestre;} public double getPromedio(){return promedio;}
 public String toCsv(){return String.join(",",id,nombres,apellidos,""+edad,carrera,""+semestre,String.format(java.util.Locale.US,"%.2f",promedio));}
 public String toString(){return id+" | "+nombres+" "+apellidos+" | "+carrera+" | sem="+semestre+" | promedio="+promedio;}
}

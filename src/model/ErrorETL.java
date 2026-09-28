package model;
public class ErrorETL { private String fuente,id,motivo,detalle; public ErrorETL(String f,String i,String m,String d){fuente=f;id=i;motivo=m;detalle=d;} public String getMotivo(){return motivo;} public String toString(){return "["+motivo+"] "+fuente+" | "+id+" | "+detalle;} }

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ausiasmarch.bean;

import java.util.Date;

/**
 *
 * @author raznara
 */
public class PostBean {
    private Integer id;
    private String titulo;
    private String cuerpo;
    private String etiquetas;
    private Date fecha;

    public Integer getId() {
        return id;
    }

    public void setId(Integer Id) {
        this.id = Id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String Titulo) {
        this.titulo = Titulo;
    }

    public String getCuerpo() {
        return cuerpo;
    }

    public void setCuerpo(String Cuerpo) {
        this.cuerpo = Cuerpo;
    }

    public String getEtiquetas() {
        return etiquetas;
    }

    public void setEtiquetas(String Etiquetas) {
        this.etiquetas = Etiquetas;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date Fecha) {
        this.fecha = Fecha;
    }
    
}

package net.ausiasmarch.bean;

import com.google.gson.annotations.Expose;
import java.util.Date;

public class PostBean implements BeanInterface {

    @Expose
    private Integer id;
    @Expose
    private String titulo;
    @Expose
    private String cuerpo;
    @Expose
    private String etiquetas;
    @Expose
    private Date fecha;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getCuerpo() {
        return cuerpo;
    }

    public void setCuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
    }

    public String getEtiquetas() {
        return etiquetas;
    }

    public void setEtiquetas(String etiquetas) {
        this.etiquetas = etiquetas;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

}

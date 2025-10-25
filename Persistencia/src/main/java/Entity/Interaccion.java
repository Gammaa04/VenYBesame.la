/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import DTO.InteraccionDTO;
import Entity.Enum.Reaccion;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Jesus Gammael Soto Escalante 248336
 */
@Entity
public class Interaccion implements Serializable {

    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(nullable = false)
    private Reaccion reaccion;
    
    @Column(nullable = false)
    private Date fecha;
    
    @ManyToOne()
    @JoinColumn(referencedColumnName = "id",name = "id_estudiante")
    @Column(nullable = false)
    private Estudiante estudiante;

   
    
    
    public Interaccion(Long id, Reaccion reaccion, Date fecha, Estudiante estudiante) {
        this.id = id;
        this.reaccion = reaccion;
        this.fecha = fecha;
        this.estudiante = estudiante;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    

    public Reaccion getReaccion() {
        return reaccion;
    }

    public void setReaccion(Reaccion reaccion) {
        this.reaccion = reaccion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
}

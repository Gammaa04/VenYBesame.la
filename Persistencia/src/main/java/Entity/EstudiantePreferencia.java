/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Set;

/**
 *
 * @author Jesus Gammael Soto Escalante 248336
 */
@Entity
public class EstudiantePreferencia implements Serializable {

    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    
    @ManyToOne ( )
    @JoinColumn(referencedColumnName = "id",name = "id_estudiante")
    private Estudiante estudiante;
    @ManyToOne()
    @JoinColumn(referencedColumnName = "id",name = "id_preferencia")
    private Preferencia preferencia;

    public EstudiantePreferencia(Long id, Estudiante estudiante, Preferencia preferencia) {
        this.id = id;
        this.estudiante = estudiante;
        this.preferencia = preferencia;
    }

   

    public EstudiantePreferencia() {
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiantes(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public Preferencia getPreferencia() {
        return preferencia;
    }

    public void setPreferencia(Preferencia preferencia) {
        this.preferencia = preferencia;
    }

    
    
    
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
}

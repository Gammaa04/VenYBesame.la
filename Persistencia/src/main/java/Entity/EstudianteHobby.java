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
import org.hibernate.annotations.ManyToAny;

/**
 *
 * @author Jesus Gammael Soto Escalante 248336
 */
@Entity
public class EstudianteHobby implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @ManyToOne()
    @JoinColumn(referencedColumnName = "id", name = "id_estudiante")
    private Estudiante estudiante;
    
    
    @ManyToOne()
    @JoinColumn(referencedColumnName = "id", name = "id_hobby")
    private Hobby hobby;

    public EstudianteHobby(Long id, Estudiante estudiante, Hobby hobby) {
        this.id = id;
        this.estudiante = estudiante;
        this.hobby = hobby;
    }

    

    public EstudianteHobby() {
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiantes(Estudiante estudiantes) {
        this.estudiante = estudiantes;
    }

    public Hobby getHobby() {
        return hobby;
    }

    public void setHobby(Hobby hobby) {
        this.hobby = hobby;
    }
    
    

    
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
}

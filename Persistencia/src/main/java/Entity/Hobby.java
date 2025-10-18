/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import Entity.Enum.TipoHobbies;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.io.Serializable;
import java.util.Set;
import org.hibernate.annotations.ManyToAny;

/**
 *
 * @author Jesus Gammael Soto Escalante 248336
 */
@Entity
public class Hobby implements Serializable {

    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    
    private TipoHobbies hobbie;
    
    @OneToMany(mappedBy = "hobby")
    private Set<EstudianteHobby> estudiante;

    public Hobby(Long id, TipoHobbies hobbie, Set<EstudianteHobby> estudiante) {
        this.id = id;
        this.hobbie = hobbie;
        this.estudiante = estudiante;
    }
    
    
    
    
    public Set<EstudianteHobby> getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Set<EstudianteHobby> estudiante) {
        this.estudiante = estudiante;
    }

    

    public Hobby() {
    }

    public TipoHobbies getHobbie() {
        return hobbie;
    }

    public void setHobbie(TipoHobbies hobbie) {
        this.hobbie = hobbie;
    }

    
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
}

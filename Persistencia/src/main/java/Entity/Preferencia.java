/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import DTO.Enum.Sexo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.io.Serializable;
import java.util.Set;

/**
 *
 * @author Jesus Gammael Soto Escalante 248336
 */
@Entity
public class Preferencia implements Serializable {

    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    /**
     * Preferencia al sexo que quiere la persona. Ej: soy hombre y me gustan las mujeres.."
     */
    private Sexo sexo;
    private String contenido;
    
    
    @OneToMany(mappedBy = "preferencia")
    @Column(nullable = false)
    private Set<EstudiantePreferencia> preferencias;
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Set<EstudiantePreferencia> getPreferencias() {
        return preferencias;
    }

    public void setPreferencias(Set<EstudiantePreferencia> preferencias) {
        this.preferencias = preferencias;
    }
    
    
}

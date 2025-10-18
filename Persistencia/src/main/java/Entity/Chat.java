/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.io.Serializable;
import java.util.List;
import org.hibernate.annotations.ManyToAny;

/**
 *
 * @author Jesus Gammael Soto Escalante 248336
 */
@Entity
public class Chat implements Serializable {

    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @ManyToOne()
    @JoinColumn(referencedColumnName = "id",name = "id_estudiante1")
    private Estudiante e1;
    
    @ManyToOne()
    @JoinColumn(referencedColumnName = "id", name = "id_estudiante2")
    private Estudiante e2;
    
    
    @OneToMany(mappedBy = "chat")
    private List<Mensaje> mensajes;

    public Chat(Long id, Estudiante e1, Estudiante e2, List<Mensaje> mensajes) {
        this.id = id;
        this.e1 = e1;
        this.e2 = e2;
        this.mensajes = mensajes;
    }

    public Chat() {
    }

    public Estudiante getE1() {
        return e1;
    }

    public void setE1(Estudiante e1) {
        this.e1 = e1;
    }

    public Estudiante getE2() {
        return e2;
    }

    public void setE2(Estudiante e2) {
        this.e2 = e2;
    }

    public List<Mensaje> getMensajes() {
        return mensajes;
    }

    public void setMensajes(List<Mensaje> mensajes) {
        this.mensajes = mensajes;
    }
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
}

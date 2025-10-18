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
import java.util.Date;
import java.util.List;
import org.hibernate.annotations.ManyToAny;

/**
 *
 * @author Jesus Gammael Soto Escalante 248336
 */
@Entity
public class Mensaje implements Serializable {

    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Date fechaMensaje;
    private String contenido;
    private Estudiante estudiante;
    @ManyToOne()
    @JoinColumn(referencedColumnName = "id",name = "id_chat")
    private Chat chat;
    
    
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
}

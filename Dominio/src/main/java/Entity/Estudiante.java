/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import DTO.Enum.Carrera;
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
public class Estudiante implements Serializable {

    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(nullable = false)
    private Carrera carrera;
    
    @Column(nullable = false)
    private String descripcion;
    
    @Column()
    private String foto;
    
    @Column(length = 100,nullable = false)
    private String nombre;
    
    @Column(length = 70,nullable = false, name = "ap_paterno")
    private String apPaterno;
    
    @Column(length = 70,nullable = false,name = "ap_materno")
    private String apMaterno;
    
    @Column(nullable = false,unique = true)
    private String correo;
    
    @Column(nullable = true)
    private String contraseña;
    
    
    @OneToMany(mappedBy = "e1")
    private Set<Chat> matchs;
    
    @OneToMany(mappedBy = "estudiante")
    private Set<Interaccion> interaccion;
    
    @OneToMany(mappedBy = "estudiante")
    private Set<EstudianteHobby> Hobbys;
    
    
    @OneToMany(mappedBy = "estudiante")
    private Set<EstudiantePreferencia> preferencias;
    
    
    
    public Estudiante(Long id, Carrera carrera, String descripcion, String foto, String nombre, String apPaterno, String apMaterno, String correo, String contraseña, Set<Interaccion> interaccion, Set<Chat> matchs, Set<EstudianteHobby> Hobbys, Set<EstudiantePreferencia> preferencias) {
        this.id = id;
        this.carrera = carrera;
        this.descripcion = descripcion;
        this.foto = foto;
        this.nombre = nombre;
        this.apPaterno = apPaterno;
        this.apMaterno = apMaterno;
        this.correo = correo;
        this.contraseña = contraseña;
        this.interaccion = interaccion;
        this.matchs = matchs;
        this.Hobbys = Hobbys;
        this.preferencias = preferencias;
    }

    

    public Estudiante() {
    }

    public Carrera getCarrera() {
        return carrera;
    }

    public void setCarrera(Carrera carrera) {
        this.carrera = carrera;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApPaterno() {
        return apPaterno;
    }

    public void setApPaterno(String apPaterno) {
        this.apPaterno = apPaterno;
    }

    public String getApMaterno() {
        return apMaterno;
    }

    public void setApMaterno(String apMaterno) {
        this.apMaterno = apMaterno;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public Set<Interaccion> getInteraccion() {
        return interaccion;
    }

    public void setInteraccion(Set<Interaccion> interaccion) {
        this.interaccion = interaccion;
    }

    public Set<Chat> getMatchs() {
        return matchs;
    }

    public void setMatchs(Set<Chat> matchs) {
        this.matchs = matchs;
    }

    public Set<EstudianteHobby> getHobbys() {
        return Hobbys;
    }

    public void setHobbys(Set<EstudianteHobby> Hobbys) {
        this.Hobbys = Hobbys;
    }

    public Set<EstudiantePreferencia> getPreferencias() {
        return preferencias;
    }

    public void setPreferencias(Set<EstudiantePreferencia> preferencias) {
        this.preferencias = preferencias;
    }

    
    
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    
    
    
    
}

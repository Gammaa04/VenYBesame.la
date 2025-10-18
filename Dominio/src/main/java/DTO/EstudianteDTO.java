/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

import DTO.Enum.Carrera;
import java.util.Set;

/**
 *
 * @author Jesus Gammael Soto Escalante 248336
 */
public record EstudianteDTO(
        Carrera carrera,
        String descripcion,
        String foto,
        String nombre,
        String apPaterno,
        String apMaterno,
        String correo,
        String contraseña,
        Set<ChatDTO> matchs,
        Set<InteraccionDTO> interaccion,
        Set<EstudianteHobbyDTO> Hobbys,
        Set<EstudiantePreferenciaDTO> preferencias) {

    public static  class BuiderEstudiante {

        private Carrera carrera;
        private String descripcion;
        private String foto;
        private String nombre;
        private String apPaterno;
        private String apMaterno;
        private String correo;
        private String contraseña;
        private Set<ChatDTO> matchs;
        private Set<InteraccionDTO> interaccion;
        private Set<EstudianteHobbyDTO> Hobbys;
        private Set<EstudiantePreferenciaDTO> preferencias;

        public BuiderEstudiante carrera(Carrera carrera) {
            this.carrera = carrera;
            return this;
        }

        public BuiderEstudiante descripcion(String descripcion) {
            this.descripcion = descripcion;
            return this;
        }

        public BuiderEstudiante foto(String foto) {
            this.foto = foto;
            return this;
        }

        public BuiderEstudiante nombre(String nombre) {
            this.nombre = nombre;
            return this;
        }

        public BuiderEstudiante apPaterno(String apPaterno) {
            this.apPaterno = apPaterno;
            return this;
        }

        public BuiderEstudiante apMaterno(String apMaterno) {
            this.apMaterno = apMaterno;
            return this;
        }

        public BuiderEstudiante correo(String correo) {
            this.correo = correo;
            return this;
        }

        public BuiderEstudiante contraseña(String contraseña) {
            this.contraseña = contraseña;
            return this;
        }

        public BuiderEstudiante catchs(Set<ChatDTO> matchs) {
            this.matchs = matchs;
            return this;
        }

        public BuiderEstudiante cnteraccion(Set<InteraccionDTO> interaccion) {
            this.interaccion = interaccion;
            return this;
        }

        public BuiderEstudiante hobbys(Set<EstudianteHobbyDTO> Hobbys) {
            this.Hobbys = Hobbys;
            return this;
        }

        public BuiderEstudiante preferencias(Set<EstudiantePreferenciaDTO> preferencias) {
            this.preferencias = preferencias;
            return this;
        }

        public EstudianteDTO build() {
            return new EstudianteDTO( carrera, descripcion, foto, nombre, apPaterno, apMaterno, correo, contraseña, matchs, interaccion, Hobbys, preferencias);
        }

    }

}

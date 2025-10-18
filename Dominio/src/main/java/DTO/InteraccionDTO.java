/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

import DTO.Enum.Reaccion;
import java.util.Date;

/**
 *
 * @author Jesus Gammael Soto Escalante 248336
 */
public record InteraccionDTO(
        Reaccion reaccion,
        Date fecha,
        EstudianteDTO estudiante
        ) {

    public static class BuilderInteraccion {
        private Reaccion reaccion;
        private Date fecha;
        private EstudianteDTO estudiante;

        public BuilderInteraccion reaccion(Reaccion reaccion) {
            this.reaccion = reaccion;
            return this;
        }

        public BuilderInteraccion fecha(Date fecha) {
            this.fecha = fecha;
            return this;
        }

        public BuilderInteraccion estudiante(EstudianteDTO estudiante) {
            this.estudiante = estudiante;
            return this;
        }

        public InteraccionDTO build(){
            return new InteraccionDTO(reaccion, fecha, estudiante);
        }
        
    }

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author Jesus Gammael Soto Escalante 248336
 */
public record EstudiantePreferenciaDTO(
        EstudianteDTO estudiante,
        PreferenciaDTO preferencia) {

    public static class BuilderEstudiantePreferencia {
        private EstudianteDTO estudiante;
        private PreferenciaDTO preferencia;

        public BuilderEstudiantePreferencia estudiante(EstudianteDTO estudiante) {
            this.estudiante = estudiante;
            return this;
        }

        public BuilderEstudiantePreferencia preferencia(PreferenciaDTO preferencia) {
            this.preferencia = preferencia;
            return this;
        }

        public EstudiantePreferenciaDTO build(){
            return new EstudiantePreferenciaDTO(estudiante, preferencia);
        }
        
    }

}

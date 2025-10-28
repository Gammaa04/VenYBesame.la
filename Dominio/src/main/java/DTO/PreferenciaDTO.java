/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

import DTO.Enum.Sexo;
import java.util.Set;

/**
 *
 * @author Jesus Gammael Soto Escalante 248336
 */
public record PreferenciaDTO(
        /**
         * Preferencia al sexo que quiere la persona. Ej: soy hombre y me gustan
         * las mujeres.."
         */
        Sexo sexo,
        String contenido,
        Set<EstudiantePreferenciaDTO> preferencias
        ) {

    public static class BuiderPreferencia {
        private Sexo sexo;
        private String contenido;
        private Set<EstudiantePreferenciaDTO> preferencias;

        public void sexo(Sexo sexo) {
            this.sexo = sexo;
        }

        public void contenido(String contenido) {
            this.contenido = contenido;
        }

        public void preferencias(Set<EstudiantePreferenciaDTO> preferencias) {
            this.preferencias = preferencias;
        }
        
        public PreferenciaDTO build(){
            return new PreferenciaDTO(sexo, contenido, preferencias);
        }
        
        
        
    }

}

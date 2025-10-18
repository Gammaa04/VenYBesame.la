/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

import DTO.Enum.TipoHobbies;
import java.util.Set;

/**
 *
 * @author Jesus Gammael Soto Escalante 248336
 */
public record HobbyDTO(
        TipoHobbies hobbie,
        Set<EstudianteHobbyDTO> estudiante
        ) {

    public static class BuiderHobby {

        private TipoHobbies hobbie;
        private Set<EstudianteHobbyDTO> estudiante;

        public BuiderHobby hobbie(TipoHobbies hobbie) {
            this.hobbie = hobbie;
            return this;

        }

        public BuiderHobby estudiante(Set<EstudianteHobbyDTO> estudiante) {
            this.estudiante = estudiante;
            return this;
        }

        public HobbyDTO build() {
            return new HobbyDTO(hobbie, estudiante);
        }

    }

}

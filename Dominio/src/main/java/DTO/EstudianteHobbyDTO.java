/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author Jesus Gammael Soto Escalante 248336
 */
public record EstudianteHobbyDTO(
        EstudianteDTO estudiante,
        HobbyDTO hobby) {

    public static  class BuiderEstudianteHobby {
        private EstudianteDTO estudiante;
        private HobbyDTO hobby;


        public BuiderEstudianteHobby estudiante(EstudianteDTO estudiante) {
            this.estudiante = estudiante;
            return this;
        }

        public BuiderEstudianteHobby hobby(HobbyDTO hobby) {
            this.hobby = hobby;
            return this;
        }
        
        public EstudianteHobbyDTO build(){
            return new EstudianteHobbyDTO(estudiante, hobby);
            
            
        }
        
        
    }

}

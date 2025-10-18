/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

import java.util.List;

/**
 *
 * @author Jesus Gammael Soto Escalante 248336
 */
public record ChatDTO(
        EstudianteDTO e1,
        EstudianteDTO e2,
        List<MensajeDTO> mensajes
        ) {

    public static class BuilderChat {

        private EstudianteDTO e1;
        private EstudianteDTO e2;
        private List<MensajeDTO> mensajes;

        public BuilderChat e1(EstudianteDTO e1) {
            this.e1 = e1;
            return this;
        }

        public BuilderChat e2(EstudianteDTO e2) {
            this.e2 = e2;
            return this;
        }

        public BuilderChat mensajes(List<MensajeDTO> mensajes) {
            this.mensajes = mensajes;
            return this;
        }

        public ChatDTO build() {
            return new ChatDTO(e1, e2, mensajes);
        }

    }
}

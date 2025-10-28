/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

import java.util.Date;

/**
 *
 * @author Jesus Gammael Soto Escalante 248336
 */
public record MensajeDTO(
        Date fechaMensaje,
        String contenido,
        EstudianteDTO estudiante,
        ChatDTO chat
        ) {

    public static  class BuilderMensaje {

        private Date fechaMensaje;
        private String contenido;
        private EstudianteDTO estudiante;
        private ChatDTO chat;

        public void fechaMensaje(Date fechaMensaje) {
            this.fechaMensaje = fechaMensaje;
        }

        public void contenido(String contenido) {
            this.contenido = contenido;
        }

        public void estudiante(EstudianteDTO estudiante) {
            this.estudiante = estudiante;
        }

        public void chat(ChatDTO chat) {
            this.chat = chat;
        }

        public MensajeDTO build() {
            return new MensajeDTO(fechaMensaje, contenido, estudiante, chat);
        }

    }
}

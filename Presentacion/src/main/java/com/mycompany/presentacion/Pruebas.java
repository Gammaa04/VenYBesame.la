package com.mycompany.presentacion;

import BO.ChatBO;
import BO.EstudianteBO;
import BO.InteraccionBO;
import InterfacesDAO.IChatDAO;
import InterfacesDAO.IEstudianteDAO;
import InterfacesDAO.IEstudianteHobbyDAO;
import InterfacesDAO.IEstudiantePreferenciaDAO;
import InterfacesDAO.IHobbyDAO;
import InterfacesDAO.IInteraccionDAO;
import InterfacesDAO.IMensajeDAO;
import InterfacesDAO.IPreferenciaDAO;
import DAO.imp.ChatDAO;
import DAO.imp.EstudianteDAO;
import DAO.imp.EstudianteHobbyDAO;
import DAO.imp.EstudiantePreferenciaDAO;
import DAO.imp.HobbyDAO;
import DAO.imp.InteraccionDAO;
import DAO.imp.MensajeDAO;
import DAO.imp.PreferenciaDAO;
import DTO.Enum.Carrera;
import DTO.Enum.Reaccion;
import DTO.Enum.Sexo;
import DTO.Enum.TipoHobbies;
import DTO.EstudianteDTO;
import DTO.MensajeDTO;
import Entity.Chat;
import Entity.Estudiante;
import Entity.Hobby;
import Entity.Mensaje;
import Entity.Preferencia;
import InterfacesBO.IEstudianteHobbyBO;
import InterfacesBO.IEstudiantePreferenciaBO;
import JPAUtil.JpaUtil;
import Util.Mapper;
import jakarta.persistence.EntityManagerFactory;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jesus Gammael Soto Escalante 248336
 */
public class Pruebas {

    private static final Logger LOGGER = Logger.getLogger(Pruebas.class.getName());
    private static final EntityManagerFactory EMF = JpaUtil.getEntityManagerFactory();
    private static final Random RANDOM = new Random();

    // Capa de Negocio (Interfaces BO)
    private static InterfacesBO.IEstudianteBO usuarioService;
    private static InterfacesBO.IInteraccionBO interaccionService;
    private static InterfacesBO.IChatBO chatService;
    private static InterfacesBO.IEstudianteHobbyBO estudianteHobbyService; 
    private static InterfacesBO.IEstudiantePreferenciaBO estudiantePreferenciaService;

    // Capa de Persistencia (Interfaces DAO)
    private static IEstudianteDAO estudianteDAO;
    private static IInteraccionDAO interaccionDAO;
    private static IChatDAO chatDAO;
    private static IMensajeDAO mensajeDAO;
    private static IHobbyDAO hobbyDAO;
    private static IPreferenciaDAO preferenciaDAO;
    private static IEstudianteHobbyDAO estudianteHobbyDAO;
    private static IEstudiantePreferenciaDAO estudiantePreferenciaDAO;

    public static void main(String[] args) {

        inicializarServicios();

        System.out.println("=================================================");
        System.out.println("       INICIO DE PRUEBA FUNCIONAL Y CARGA        ");
        System.out.println("=================================================");

        try {
            // Se asume que la base de datos esta limpia
            List<Estudiante> estudiantesPrueba = cargarEstudiantesManuales();

            demostrarFuncionalidad(estudiantesPrueba);

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "ERROR FATAL en la demostracion: " + e.getMessage(), e);
        } finally {
            JpaUtil.closeEntityManagerFactory();
        }
    }

    private static void inicializarServicios() {
        // Inicializacion de DAOs
        estudianteDAO = new EstudianteDAO(EMF);
        interaccionDAO = new InteraccionDAO(EMF);
        chatDAO = new ChatDAO(EMF);
        mensajeDAO = new MensajeDAO(EMF);
        hobbyDAO = new HobbyDAO(EMF);
        preferenciaDAO = new PreferenciaDAO(EMF);
        IEstudianteHobbyDAO ehDAO = new EstudianteHobbyDAO(EMF); // DAO de enlace
        IEstudiantePreferenciaDAO epDAO = new EstudiantePreferenciaDAO(EMF); // DAO de enlace
        estudianteHobbyDAO = ehDAO;
        estudiantePreferenciaDAO = epDAO;


        // Inicializacion de Servicios (BO) - Inyeccion de Dependencias
        usuarioService = new EstudianteBO(estudianteDAO, interaccionDAO, ehDAO, epDAO, chatDAO); 
        interaccionService = new InteraccionBO(interaccionDAO, estudianteDAO, chatDAO);
        chatService = new ChatBO(chatDAO, mensajeDAO, estudianteDAO);
        
        // CORRECCION: Inicializacion de los servicios
        estudianteHobbyService = new BO.EstudianteHobbyBO(ehDAO, estudianteDAO, hobbyDAO); 
        estudiantePreferenciaService = new BO.EstudiantePreferenciaBO(epDAO, estudianteDAO, preferenciaDAO);

        LOGGER.info("Capa de Servicios Inicializada.");
    }

    // ====================================================================
    // 1. CARGA MANUAL Y REALISTA DE 10 ESTUDIANTES
    // ====================================================================
    private static List<Estudiante> cargarEstudiantesManuales() throws IllegalArgumentException {
        System.out.println("\n--- 1. REGISTRO MANUAL DE 10 ESTUDIANTES ---");

        EstudianteDTO[] dtos = new EstudianteDTO[]{
            new EstudianteDTO.BuiderEstudiante().carrera(Carrera.ING_SOFTWARE).nombre("Sofía").apPaterno("García").apMaterno("López").correo("sofia@potros.itson.edu.mx").contraseña("s0f1a123").descripcion("Busco grupo de estudio para estructuras de datos.").build(),
            new EstudianteDTO.BuiderEstudiante().carrera(Carrera.LIC_ARQUITECTURA).nombre("Ricardo").apPaterno("Martínez").apMaterno("Sánchez").correo("ricardo@potros.itson.edu.mx").contraseña("r1c4rd0").descripcion("Fan del diseño y los videojuegos. Abierto a todos.").build(),
            new EstudianteDTO.BuiderEstudiante().carrera(Carrera.LIC_PSICOLOGIA).nombre("Andrea").apPaterno("Hernández").apMaterno("Ruiz").correo("andrea@potros.itson.edu.mx").contraseña("andreaPaz").descripcion("Interesada en psicología deportiva y música indie.").build(),
            new EstudianteDTO.BuiderEstudiante().carrera(Carrera.ING_INDUSTRIAL).nombre("Carlos").apPaterno("Díaz").apMaterno("Vargas").correo("carlos@potros.itson.edu.mx").contraseña("carlos7").descripcion("Solo hablo de eficiencia y fútbol (deporte).").build(),
            new EstudianteDTO.BuiderEstudiante().carrera(Carrera.LIC_NUTRICION).nombre("Mariana").apPaterno("Rojas").apMaterno("Mendoza").correo("mariana@potros.itson.edu.mx").contraseña("marianaNut").descripcion("Me gusta cocinar y los videojuegos retro.").build(),
            new EstudianteDTO.BuiderEstudiante().carrera(Carrera.ING_MECATRONICA).nombre("Javier").apPaterno("Soto").apMaterno("Pérez").correo("javier@potros.itson.edu.mx").contraseña("javierMec").descripcion("Drones y electrónica avanzada. Necesito un grupo de estudio de cálculo.").build(),
            new EstudianteDTO.BuiderEstudiante().carrera(Carrera.ING_CIVIL).nombre("Laura").apPaterno("Torres").apMaterno("Gómez").correo("laura@potros.itson.edu.mx").contraseña("lauraCivil").descripcion("Cerámica y manualidades. Busco gente que le guste el arte.").build(),
            new EstudianteDTO.BuiderEstudiante().carrera(Carrera.LIC_CONTADURIA).nombre("Miguel").apPaterno("Ramos").apMaterno("Chávez").correo("miguel@potros.itson.edu.mx").contraseña("miguelCont").descripcion("Mi hobby es dormir y salir de fiesta.").build(),
            new EstudianteDTO.BuiderEstudiante().carrera(Carrera.ING_SOFTWARE).nombre("Elena").apPaterno("Paredes").apMaterno("Silva").correo("elena@potros.itson.edu.mx").contraseña("elenaSoft").descripcion("Amante del anime y las películas de ciencia ficción.").build(),
            new EstudianteDTO.BuiderEstudiante().carrera(Carrera.ING_INDUSTRIAL).nombre("David").apPaterno("Velasco").apMaterno("Cruz").correo("david@potros.itson.edu.mx").contraseña("davidInd").descripcion("Busco amigos para ir de compras y socializar.").build()
        };

        for (EstudianteDTO dto : dtos) {
            usuarioService.registrarUsuario(dto);
            System.out.printf(" Creado: %s (%s)\n", dto.nombre(), dto.carrera());
        }

        try {
            // Obtenemos la lista de entidades recien creadas para usarlas en las pruebas
            return estudianteDAO.findEntities(dtos.length, 0);
        } catch (SQLException e) {
            throw new RuntimeException("Fallo al obtener la lista de estudiantes.", e);
        }
    }

    private static void enviarMensajesDePrueba(Chat chat) throws Exception {
        Long id1 = chat.getE1().getId();
        Long id2 = chat.getE2().getId();

        MensajeDTO m1 = new MensajeDTO(new Date(), "Hola, el sistema de mensajeria funciona!", Mapper.toDTO(chat.getE1()), Mapper.toDTO(chat));
        chatService.enviarMensaje(chat.getId(), id1, m1);

        MensajeDTO m2 = new MensajeDTO(new Date(), "¡Confirmado! Logica de chat persistente OK.", Mapper.toDTO(chat.getE2()), Mapper.toDTO(chat));
        chatService.enviarMensaje(chat.getId(), id2, m2);

        System.out.println(" Mensajes enviados al Chat: " + chat.getId());
    }

    private static Estudiante getRandomEstudiante(List<Estudiante> list) {
        return list.get(RANDOM.nextInt(list.size()));
    }

    // DEMOSTRACION FUNCIONAL DE CASOS DE USO
    private static void demostrarFuncionalidad(List<Estudiante> estudiantes) throws Exception {
        if (estudiantes.size() < 2) {
            System.out.println(" ERROR: La prueba requiere al menos 2 estudiantes.");
            return;
        }

        Estudiante eSofia = estudiantes.get(0); // Sofia (ING_SOFTWARE)
        Estudiante eRicardo = estudiantes.get(1); // Ricardo (LIC_ARQUITECTURA)
        Estudiante eAndrea = estudiantes.get(2); // Andrea (LIC_PSICOLOGIA)

        // DEMOSTRACION DE CARGA
        Hobby hVideojuegos = new Hobby();
        hVideojuegos.setHobbie(TipoHobbies.VIDEOJUEGOS);

        Preferencia pMujeres = new Preferencia();
        pMujeres.setSexo(Sexo.MUJER);
        pMujeres.setContenido("Mujeres");

        // Persistir Catalogos
        hVideojuegos = hobbyDAO.create(hVideojuegos);
        pMujeres = preferenciaDAO.create(pMujeres);
        System.out.printf("\n CATALOGOS: Creados Hobby (%s) y Preferencia (%s).\n", hVideojuegos.getHobbie(), pMujeres.getSexo());

        // ASIGNACION DE ENLACES (Para validar EnlaceDAO)
        estudianteHobbyService.agregarHobbyAEstudiante(eRicardo.getId(), hVideojuegos.getId());
        estudiantePreferenciaService.agregarPreferenciaAEstudiante(eRicardo.getId(), pMujeres.getId());
        System.out.printf(" ENLACES: %s tiene Hobby y Preferencia asignados.\n", eRicardo.getNombre());

        // DEMOSTRACION CRUD (UPDATE / READ) ---
        EstudianteDTO updateDto = new EstudianteDTO.BuiderEstudiante()
                .carrera(eSofia.getCarrera())
                .nombre("SOFIA CARSON")
                .apPaterno(eSofia.getApPaterno())
                .apMaterno(eSofia.getApMaterno())
                .correo(eSofia.getCorreo())
                .contraseña(eSofia.getContraseña())
                .descripcion("Estudiante 5to semestre de ing. software.")
                .build();

        usuarioService.actualizarPerfil(eSofia.getId(), updateDto);
        System.out.printf("\n--- 2. DEMOSTRACION CRUD (UPDATE/READ) ---\n");
        System.out.printf(" UPDATE: Estudiante %d actualizado a: %s\n", eSofia.getId(), usuarioService.buscarPorId(eSofia.getId()).getNombre());

        // VALIDACION: Correo Duplicado (Regla: No Duplicar Datos)
        EstudianteDTO dtoDuplicado = new EstudianteDTO.BuiderEstudiante()
                .carrera(Carrera.LIC_ARQUITECTURA).nombre("X").apPaterno("Y").apMaterno("Z")
                .correo(eSofia.getCorreo())
                .contraseña("123456").build();
        try {
            usuarioService.registrarUsuario(dtoDuplicado);
        } catch (IllegalArgumentException e) {
            System.out.println(" VALIDACION (No Duplicar Correo): Capturado error. OK.");
        }

        //  DEMOSTRACION DE LÓGICA DE MATCH (INTERACCIONES) ---
        System.out.println("\n--- 3. DEMOSTRACION DE MATCH/MENSAJERIA ---");

        // Interaccion 1 (LIKE de Ricardo a Sofía)
        interaccionService.registrarInteraccionYBuscarMatch(eRicardo.getId(), eSofia.getId(), Reaccion.LIKE);
        System.out.printf("  ➡️ LIKE 1: %s gusta de %s (No Match)\n", eRicardo.getNombre(), eSofia.getNombre());

        // Interaccion 2 (LIKE de Sofía a Ricardo -> ¡MATCH!)
        Optional<Chat> match = interaccionService.registrarInteraccionYBuscarMatch(eSofia.getId(), eRicardo.getId(), Reaccion.LIKE);

        if (match.isPresent()) {
            System.out.printf(" LIKE 2: %s gusta de %s. -> ¡MATCH CREADO! (Chat ID: %d)\n", eSofia.getNombre(), eRicardo.getNombre(), match.get().getId());

            // DEMOSTRACION DE MENSAJERIA ---
            enviarMensajesDePrueba(match.get());

        } else {
            System.out.println("  ERROR: No se pudo generar el Match de prueba.");
        }
        //  DEMOSTRACION DE CONSULTAS COMPLEJAS ---
        System.out.println("\n--- 5. DEMOSTRACION DE CONSULTAS COMPLEJAS ---");

        // CONSULTA: Listar Matches de Sofia
        List<Chat> matchesEA = interaccionService.obtenerMatchesDeEstudiante(eSofia.getId());
        System.out.printf(" CONSULTA: %s tiene %d Match(es) activos.\n", eSofia.getNombre(), matchesEA.size());

        // CONSULTA: Explorar Candidatos (Excluye vistos)
        List<Estudiante> candidatos = interaccionService.explorarCandidatos(eSofia.getId(), 5);
        System.out.printf("  CONSULTA: Exploracion de %s. Vio %d candidatos.\n", eSofia.getNombre(), candidatos.size());

        // CONSULTA: Historial de Chat
        if (!matchesEA.isEmpty()) {
            List<Mensaje> historial = chatService.historialChat(matchesEA.get(0).getId(), 10);
            System.out.printf(" CONSULTA: Historial del Chat %d. Mensajes: %d.\n", matchesEA.get(0).getId(), historial.size());
        }

        // CONSULTA: Listar Hobbies del Estudiante (Prueba de Enlace)
        List<Hobby> hobbies = estudianteHobbyDAO.buscarHobbiesPorEstudiante(eRicardo.getId());
        System.out.printf(" CONSULTA: Ricardo tiene %d hobbies registrados (Esperado: 1).\n", hobbies.size());

        // DEMOSTRACION DE DELETE ---
        usuarioService.eliminarCuenta(eAndrea.getId());
        System.out.printf("\n--- 6. DEMOSTRACION DE DELETE ---\n");
        System.out.printf(" DELETE: Estudiante %s eliminado. )\n", eAndrea.getNombre());
    }
}

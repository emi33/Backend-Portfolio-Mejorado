package com.portfolio.maker.service;

import com.portfolio.maker.dto.auth.AuthResponse;
import com.portfolio.maker.dto.auth.LoginRequest; // <-- NUEVO
import com.portfolio.maker.dto.auth.RegisterRequest;
import com.portfolio.maker.entity.Direccion;
import com.portfolio.maker.entity.Persona;
import com.portfolio.maker.entity.Usuario;
import com.portfolio.maker.entity.enums.RolEnum;
import com.portfolio.maker.entity.enums.SexoEnum;
import com.portfolio.maker.repository.DireccionRepository;
import com.portfolio.maker.repository.PersonaRepository;
import com.portfolio.maker.repository.UsuarioRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
// --- IMPORTACIONES NUEVAS ---
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// ----------------------------
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PersonaRepository personaRepository;
    private final DireccionRepository direccionRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager; // <-- NUEVO

    /**
     * REGISTRO: Solo crea el usuario. No devuelve token.
     */
    @Transactional
    public void register(RegisterRequest request) { // <-- CAMBIO: Devuelve void

        // --- Validaciones (igual que antes) ---
        if (usuarioRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El email ya está registrado para un usuario.");
        }
        if (personaRepository.existsByEmail(request.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Una persona con este email ya existe.");
        }

        // --- PASO 1: Crear Direccion (igual que antes) ---
        Direccion direccion = new Direccion();
        direccion.setPais(request.getPais());
        direccion.setProvincia(request.getProvincia());
        direccion.setLocalidad(request.getLocalidad());
        direccion.setDireccion(request.getDireccion());
        direccion.setNumero(request.getNumero());
        direccion.setPiso(request.getPiso());
        direccion.setDepto(request.getDepto());
        Direccion savedDireccion = direccionRepository.save(direccion);

        // --- PASO 2: Crear Persona (igual que antes) ---
        Persona persona = new Persona();
        persona.setNombre(request.getNombre());
        persona.setApellido(request.getApellido());
        persona.setEmail(request.getEmail());
        persona.setDireccion(savedDireccion);
        persona.setSexo(SexoEnum.valueOf(request.getSexo()));
        persona.setNumeroTel(request.getNumeroTel());
        persona.setEdad(request.getEdad());
        persona.setOcupacion(request.getOcupacion());
        Persona savedPersona = personaRepository.save(persona);

        // --- PASO 3: Crear Usuario (igual que antes) ---
        Usuario usuario = Usuario.builder()
                .nombreUsuario(request.getNombre() + " " + request.getApellido())
                .email(request.getEmail())
                .contrasenaHash(passwordEncoder.encode(request.getContrasena()))
                .persona(savedPersona)
                .rol(RolEnum.USUARIO)
                .build();
        usuarioRepository.save(usuario);

        // --- CAMBIO: Ya no se genera ni devuelve el token ---
    }

    /**
     * LOGIN: Autentica al usuario y devuelve el token.
     */
    public AuthResponse login(LoginRequest request) {

        // 1. Validación: Usuario no existente
        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no existente."));

        // 2. Validación: Contraseña incorrecta
        try {
            // --- ESTA ES LA LÍNEA CORREGIDA ---
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getContrasena()
                    )
            );
            // ----------------------------------
        } catch (BadCredentialsException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Contraseña incorrecta.");
        }

        // --- LÓGICA DE TOKEN ACTUALIZADA ---

        // 1. Generar ambos tokens
        String jwtToken = jwtService.generateToken(usuario);
        String refreshToken = jwtService.generateRefreshToken(usuario); // (Asegúrate que este método exista en JwtService)

        // 2. Guardar el refresh token en la BD
        usuario.setRefreshToken(refreshToken);
        usuarioRepository.save(usuario);

        // 3. Devolver ambos
        return AuthResponse.builder()
                .token(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthResponse refreshToken(String refreshToken) {
        // 1. Validar el refresh token (expiración, firma) y extraer email
        String userEmail = jwtService.extractUsername(refreshToken);

        // 2. Buscar al usuario y verificar que el token coincide con el de la BD
        Usuario usuario = usuarioRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuario no encontrado"));

        if (!usuario.getRefreshToken().equals(refreshToken)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Refresh token inválido");
        }

        // 3. (Opcional pero recomendado: Rotación de Token)
        // Invalidar este refresh token (borrarlo)
        // usuario.setRefreshToken(null);
        // usuarioRepository.save(usuario);

        // 4. Generar un NUEVO access token (y un nuevo refresh token si haces rotación)
        String newAccessToken = jwtService.generateToken(usuario);
        // String newRefreshToken = jwtService.generateRefreshToken(usuario);
        // usuario.setRefreshToken(newRefreshToken);
        // usuarioRepository.save(usuario);

        return AuthResponse.builder()
                .token(newAccessToken)
                .refreshToken(refreshToken) // o newRefreshToken si haces rotación
                .build();
    }

    public void logout(HttpServletRequest request) {
        // 1. Extraer el Access Token del header "Authorization"
        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return; // No hay token, no hay nada que hacer
        }
        final String jwt = authHeader.substring(7);

        // 2. Extraer el email del token
        String userEmail = jwtService.extractUsername(jwt);

        // 3. Buscar al usuario y borrar su refresh token
        Usuario usuario = usuarioRepository.findByEmail(userEmail).orElse(null);

        if (usuario != null) {
            usuario.setRefreshToken(null);
            usuarioRepository.save(usuario);
        }
    }
}
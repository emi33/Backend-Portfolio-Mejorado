package com.portfolio.maker.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "categoriasoft")
@Getter
@Setter
@NoArgsConstructor
public class CategoriaSoft {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria_soft") // <--- ACTUALIZADO
    private Integer idCategoriaSoft;

    @Column(unique = true, nullable = false, length = 100, name = "nombre_categoria") // <--- ACTUALIZADO
    private String nombreCategoria;

    @Column(name = "fecha_creacion", nullable = false, updatable = false) // <--- ACTUALIZADO
    @CreationTimestamp
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion", nullable = false) // <--- ACTUALIZADO
    @UpdateTimestamp
    private LocalDateTime fechaActualizacion;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    // --- Relación Inversa (Categoria -> Habilidad) ---
    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<HabilidadSoft> habilidades = new HashSet<>();
}

package com.efoxdev.forohub.controller;

import com.efoxdev.forohub.domain.topico.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

    @GetMapping
    public ResponseEntity<Page<DatosListadoTopico>> listadoDeTopicos(@PageableDefault(size = 10) Pageable paginacion) {
        return ResponseEntity.ok( topicoRepository.findByActivoTrue(paginacion).map(DatosListadoTopico::new) );
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosRepuestaTopico> listadoDeTopicos(@PathVariable Long id) {
        Topico topico = topicoRepository.getReferenceById(id);
        var datosTopico = new DatosRepuestaTopico(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaDeCreacion(),
                topico.getEstado(),
                topico.getAutor(),
                topico.getCurso().toString()
        );
        return ResponseEntity.ok(datosTopico);
    }

    @PostMapping
    public ResponseEntity<DatosRepuestaTopico> registrarTopico(@RequestBody DatosRegistroTopico datosRegistroTopico, UriComponentsBuilder uriComponentsBuilder) {
        Topico topico = topicoRepository.save( new Topico(datosRegistroTopico) );
        DatosRepuestaTopico datosRepuestaTopico = new DatosRepuestaTopico(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaDeCreacion(),
                topico.getEstado(),
                topico.getAutor(),
                topico.getCurso().toString()
        );
        URI url = uriComponentsBuilder
                .path("/topicos/{id}")
                .buildAndExpand( topico.getId() )
                .toUri();
        return ResponseEntity.created(url).body(datosRepuestaTopico);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DatosRepuestaTopico> actualizarTopico(@RequestBody DatosActualizarTopico datosActualizarTopico) {
        Topico topico = topicoRepository.getReferenceById( datosActualizarTopico.id() );
        topico.actualizarDatos(datosActualizarTopico);
        return ResponseEntity.ok(new DatosRepuestaTopico(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaDeCreacion(),
                topico.getEstado(),
                topico.getAutor(),
                topico.getCurso().toString()
            )
        );
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarTopico(@PathVariable Long id) {
        Topico topico = topicoRepository.getReferenceById(id);
        topico.cerrarTopico();
        return ResponseEntity.noContent().build();
    }

}

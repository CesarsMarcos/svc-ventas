package com.svc.ventas.controller.mantenimiento;

import jakarta.validation.Valid;

import com.svc.ventas.models.mapstruct.dto.EmpleadoPostDto;
import com.svc.ventas.service.IEmpleadoService;
import com.svc.ventas.util.Constantes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.svc.ventas.message.response.Response;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/empleados/")
public class EmpleadoController {

	private final IEmpleadoService empleadoService;

	@GetMapping
	public ResponseEntity<?> empleados() {
		return new ResponseEntity<>(empleadoService.lista(), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Response> agregar(@Valid @RequestBody EmpleadoPostDto empleado) {

		if(empleadoService.isSaved(empleado.getPersona().getNumDocumento())){
			return ResponseEntity.status(409).body(Response.builder().mensaje(String.format(Constantes.CONFLICTO_REGISTRO, "Empleado")).build());
		}

		Response response = empleadoService.agregar(empleado);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PutMapping("{id}")
	public ResponseEntity<Response> agregar(@PathVariable Integer id,@RequestBody @Valid EmpleadoPostDto empleado) {
		Response response = empleadoService.modificar(id,empleado);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<?> obtener(@PathVariable Integer id){
		return new ResponseEntity<>(empleadoService.obtener(id), HttpStatus.OK);
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<Void>  eliminar(@PathVariable int id) {
		empleadoService.obtener(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
}

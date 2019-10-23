package com.nelioalves.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.nelioalves.cursomc.domain.Categoria;
import com.nelioalves.cursomc.repositories.CategoriaRepository;
import com.nelioalves.cursomc.services.exception.DataIntegrityException;
import com.nelioalves.cursomc.services.exception.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repo;
	
	public Categoria find(Integer id) {
		
		Optional<Categoria> obj = repo.findById(id);

		return obj.orElseThrow( () -> new ObjectNotFoundException( "Objeto não encontrado! Id: " + id + 
										", Tipo: " + Categoria.class.getName() )
				
				);
	
	}
	
	public Categoria insert(Categoria obj) {
		obj.setId(null);
		return repo.save(obj);
	}
	
	public void delete(Integer id) throws DataIntegrityException{
		find(id);
		
		try {
			repo.deleteById(id);
		}catch(DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma Categoria que possui produtos.");
		}
	}
	
	public Page<Categoria> findPage(Integer page, Integer linesForPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesForPage, Direction.valueOf(direction) , orderBy);
		
		return repo.findAll(pageRequest);
	}
	
	public List<Categoria> findAll() {
		return repo.findAll();
	}
	
}

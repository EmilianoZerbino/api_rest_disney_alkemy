package system.repositories.interfaces;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import system.entities.Role;

public interface IRoleRepository extends JpaRepository<Role, Integer>{
	
	public Optional<Role> findByName(String name);
}

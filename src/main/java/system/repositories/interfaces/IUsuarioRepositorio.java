package system.repositories.interfaces;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import system.entities.User;

public interface IUsuarioRepositorio extends JpaRepository<User, Long>{

	public Optional<User> findByUsername(String username);
	public Optional<User> findByEmail(String email);
	public Optional<User> findByUsernameOrEmail(String username, String email);
	
	public Boolean existsByUsername(String username);
	public Boolean existsByEmail(String email);
}

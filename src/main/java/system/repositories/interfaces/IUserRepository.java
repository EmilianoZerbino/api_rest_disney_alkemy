package system.repositories.interfaces;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import system.entities.User;

public interface IUserRepository extends JpaRepository<User, Integer>{

	public Optional<User> findByUsername(String username);
	public Optional<User> findByEmail(String Email);
	public Optional<User> findByUsernameOrEmail(String username, String email);
	
	public Boolean existsByUsername(String username);
	public Boolean existsByEmail(String email);
}

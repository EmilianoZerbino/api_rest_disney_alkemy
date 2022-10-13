package system.repositories.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;

import system.entities.Character;

public interface ICharacterRepository extends JpaRepository<Character, Integer>{


}

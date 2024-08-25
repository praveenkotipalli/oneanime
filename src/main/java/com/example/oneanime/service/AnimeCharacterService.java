package com.example.oneanime.service;



import com.example.oneanime.model.AnimeCharacter;
// import com.example.oneanime.repository.AnimeCharacterRepository;
// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface AnimeCharacterService {
    AnimeCharacter addCharacter(AnimeCharacter character);
    List<AnimeCharacter> getAllCharacters();
    Optional<AnimeCharacter> getCharacterById(Long id);
    AnimeCharacter updateCharacter(Long id, AnimeCharacter character);
    boolean deleteCharacter(Long id);
}

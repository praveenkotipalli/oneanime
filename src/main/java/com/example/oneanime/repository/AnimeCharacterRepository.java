package com.example.oneanime.repository;



import com.example.oneanime.model.AnimeCharacter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimeCharacterRepository extends JpaRepository<AnimeCharacter, Long> {
}


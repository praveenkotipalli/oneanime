package com.example.oneanime.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.oneanime.model.AnimeCharacter;
import com.example.oneanime.service.AnimeCharacterService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/characters")
public class AnimeCharacterController {

    @Autowired
    private AnimeCharacterService service;

    // private static final String UPLOAD_DIR = "uploads/";

    // Create a new character
    @PostMapping
    public ResponseEntity<AnimeCharacter> addCharacter(@RequestParam String name,
                                                       @RequestParam String animeName,
                                                       @RequestParam(value = "photo", required = false) MultipartFile photo) throws IOException {
        String photoUrl = photo != null && !photo.isEmpty() ? savePhoto(photo) : null;
        AnimeCharacter character = new AnimeCharacter();
        character.setName(name);
        character.setAnimeName(animeName);
        character.setPhotoUrl(photoUrl);
        AnimeCharacter createdCharacter = service.addCharacter(character);
        return ResponseEntity.ok(createdCharacter);
    }

    // Read all characters
    @GetMapping
    public List<AnimeCharacter> getAllCharacters() {
        return service.getAllCharacters();
    }

    // Read a single character by ID
    @GetMapping("/{id}")
    public ResponseEntity<AnimeCharacter> getCharacterById(@PathVariable Long id) {
        Optional<AnimeCharacter> character = service.getCharacterById(id);
        return character.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update a character
    @PutMapping("/{id}")
    public ResponseEntity<AnimeCharacter> updateCharacter(@PathVariable Long id,
                                                           @RequestParam String name,
                                                           @RequestParam String animeName,
                                                           @RequestParam(value = "photo", required = false) MultipartFile photo) throws IOException {
        Optional<AnimeCharacter> existingCharacterOpt = service.getCharacterById(id);
        if (existingCharacterOpt.isPresent()) {
            AnimeCharacter existingCharacter = existingCharacterOpt.get();
            existingCharacter.setName(name);
            existingCharacter.setAnimeName(animeName);
            if (photo != null && !photo.isEmpty()) {
                String photoUrl = savePhoto(photo);
                existingCharacter.setPhotoUrl(photoUrl);
            }
            AnimeCharacter updatedCharacter = service.updateCharacter(id, existingCharacter);
            return ResponseEntity.ok(updatedCharacter);
        }
        return ResponseEntity.notFound().build();
    }

    // Delete a character
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCharacter(@PathVariable Long id) {
        if (service.deleteCharacter(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    private String savePhoto(MultipartFile photo) throws IOException {
        Path path = Paths.get("uploads/" + photo.getOriginalFilename());
        Files.createDirectories(path.getParent()); // Ensure directory exists
        Files.write(path, photo.getBytes());
        return  photo.getOriginalFilename(); // Adjust according to your URL or file access setup
    }
}

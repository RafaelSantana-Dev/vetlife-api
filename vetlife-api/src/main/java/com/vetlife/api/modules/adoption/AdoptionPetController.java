package com.vetlife.api.modules.adoption;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/api/v1/adoption") @RequiredArgsConstructor
public class AdoptionPetController {
    private final AdoptionPetRepository repository;
    @GetMapping public List<AdoptionPet> list() { return repository.findAll(); }
    @PostMapping public AdoptionPet create(@RequestBody AdoptionPet pet) { return repository.save(pet); }
}
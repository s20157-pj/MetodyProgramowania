package pl.pjatk.gameplay.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.pjatk.gameplay.model.Player;
import pl.pjatk.gameplay.service.PlayerService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/player")
public class PlayerController {

    private PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping
    public ResponseEntity<List<Player>> findAll() {
        return ResponseEntity.ok(playerService.findAll());
    }
    
    @GetMapping("/test")
        public ModelAndView showForm (Model model) {

            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("test");
            model.addAttribute("player", new Player());
            return modelAndView;
    }

    @PostMapping("/test")
    public ModelAndView sendForm(@ModelAttribute ("player") Player player, Model model){

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("result");
        model.addAttribute("player", player);
        ////tu jakas walidacja by sie przydala
        playerService.save(player);
        return modelAndView;
    }


    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("Hello world");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Player> findById(@PathVariable Long id) {
        Optional<Player> player = playerService.findById(id);
        if (player.isPresent()) {
            return ResponseEntity.ok(player.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{attackerID}/{defenderID}")
    public ResponseEntity<Player> attackPlayer(@PathVariable Long attackerID, @PathVariable Long defenderID) {
        return ResponseEntity.ok(playerService.attackPlayer(attackerID, defenderID));
    }


    @PostMapping
    public ResponseEntity<Player> save (@RequestBody Player player){
        return ResponseEntity.ok(playerService.save(player));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        playerService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Player> update(@RequestBody Player player){
        return ResponseEntity.ok(playerService.update(player));
    }




    ///////utworzyc nowy mapping w ktorym przyjmiemy id 2 graczy  i gracz nr 1  zaatakuje gracza nr2
}

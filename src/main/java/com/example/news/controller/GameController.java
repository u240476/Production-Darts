package com.example.news.controller;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.GameService;
import com.example.GameService.BotView;
import com.example.GameService.GameView;

/**
 * REST endpoints for the dart game. All game rules live in the com.example game classes;
 * this class only turns HTTP requests into calls to {@link GameService}.
 *
 * <pre>
 * GET    /api/bots                   the pro bots and their accuracy
 * POST   /api/games                  {"startScore":501,"opponent":"Luke Littler"}  (opponent optional)
 * GET    /api/games/{id}             current state
 * POST   /api/games/{id}/dart        {"dart":60}   a human player's dart
 * POST   /api/games/{id}/bot-dart    the bot throws one dart
 * POST   /api/games/{id}/undo        take back the last dart of the current human turn
 * DELETE /api/games/{id}
 * </pre>
 */
@RestController
@RequestMapping("/api")
public class GameController {

    public record CreateGameRequest(Integer startScore, String opponent) {}

    public record DartRequest(Integer dart) {}

    private final GameService games = new GameService();

    @GetMapping("/bots")
    public List<BotView> bots() {
        return games.bots();
    }

    @PostMapping("/games")
    public GameView create(@RequestBody CreateGameRequest request) {
        return games.create(request.startScore(), request.opponent());
    }

    @GetMapping("/games/{id}")
    public GameView get(@PathVariable("id") String id) {
        return games.get(id);
    }

    @PostMapping("/games/{id}/dart")
    public GameView dart(@PathVariable("id") String id, @RequestBody DartRequest request) {
        return games.throwDart(id, request.dart());
    }

    @PostMapping("/games/{id}/bot-dart")
    public GameView botDart(@PathVariable("id") String id) {
        return games.botDart(id);
    }

    @PostMapping("/games/{id}/undo")
    public GameView undo(@PathVariable("id") String id) {
        return games.undo(id);
    }

    @DeleteMapping("/games/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        games.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Errors use the same {"error": "..."} shape as NewsController.

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> badRequest(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Map<String, String>> notFound(NoSuchElementException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Map<String, String>> conflict(IllegalStateException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", e.getMessage()));
    }
}

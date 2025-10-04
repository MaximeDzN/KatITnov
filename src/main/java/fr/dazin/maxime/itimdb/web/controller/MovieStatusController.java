package fr.dazin.maxime.itimdb.web.controller;

import fr.dazin.maxime.itimdb.service.MovieStatusService;
import fr.dazin.maxime.itimdb.web.dto.MovieWithStatusDto;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}/movies")
public class MovieStatusController {

    private final MovieStatusService movieStatusService;

    public MovieStatusController(MovieStatusService movieStatusService) {
        this.movieStatusService = movieStatusService;
    }

    @GetMapping( "favorites")
    public List<MovieWithStatusDto> listFavorites(@Parameter(example = "1") @PathVariable long userId, @RequestParam(defaultValue = "releaseDate") String sortBy, @RequestParam(defaultValue = "asc") String order) {
        return this.movieStatusService.listFavorites(userId,sortBy,order);
    }

    @PutMapping("/{movieId}/favorite")
    public ResponseEntity<Void> updateFavorite(@Parameter(example = "1") @PathVariable long userId, @Parameter(example = "10")@PathVariable long movieId, @RequestBody boolean favorite) {
        this.movieStatusService.updateFavorite(userId,movieId,favorite);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{movieId}/seen")
    public ResponseEntity<Void> updateSeen(@Parameter(example = "1") @PathVariable long userId, @Parameter(example = "10") @PathVariable long movieId) {
        this.movieStatusService.updateSeen(userId,movieId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/seen")
    public List<MovieWithStatusDto> listSeen(@Parameter(example = "1") @PathVariable long userId) {
        return this.movieStatusService.listSeenMovies(userId);
    }
    @GetMapping("/unseen")
    public List<MovieWithStatusDto> listUnseen(@Parameter(example = "1") @PathVariable long userId) {
        return this.movieStatusService.listUnseenMovies(userId);
    }




}

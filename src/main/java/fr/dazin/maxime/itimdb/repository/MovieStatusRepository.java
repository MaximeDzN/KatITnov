package fr.dazin.maxime.itimdb.repository;

import fr.dazin.maxime.itimdb.domain.Movie;
import fr.dazin.maxime.itimdb.domain.MovieStatus;
import fr.dazin.maxime.itimdb.domain.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MovieStatusRepository extends JpaRepository<MovieStatus,Long> {
    Optional<MovieStatus> findByUserAndMovie(User user, Movie movie);
    List<MovieStatus> findByUserAndFavoriteTrue(User user, Sort sort);
    List<MovieStatus> findByUserAndSeen(User user,boolean seen);
}

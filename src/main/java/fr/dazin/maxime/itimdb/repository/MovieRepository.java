package fr.dazin.maxime.itimdb.repository;

import fr.dazin.maxime.itimdb.domain.Movie;
import fr.dazin.maxime.itimdb.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie,Long> {
    @Query("""
            SELECT m FROM Movie m
            WHERE m.id NOT IN (
                  SELECT ms.movie.id FROM MovieStatus ms
                  WHERE ms.user = :user AND ms.seen = true
                        )
            """)
    List<Movie> findUnseenByUser(@Param("user") User user);
}

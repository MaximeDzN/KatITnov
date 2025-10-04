package fr.dazin.maxime.itimdb.config;

import fr.dazin.maxime.itimdb.domain.Movie;
import fr.dazin.maxime.itimdb.domain.MovieStatus;
import fr.dazin.maxime.itimdb.domain.User;
import fr.dazin.maxime.itimdb.repository.MovieRepository;
import fr.dazin.maxime.itimdb.repository.MovieStatusRepository;
import fr.dazin.maxime.itimdb.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(MovieRepository movieRepository, UserRepository userRepository, MovieStatusRepository movieStatusRepository) {

        return args -> {
            User user = User.builder().userName("MaximeDzN").build();

            userRepository.save(user);

            List<Movie> movies = new ArrayList<>();

            movies.add(
                    Movie.builder()
                            .title("Oppenheimer")
                            .description("Un film explosif")
                            .rating(9.0)
                            .releaseDate(LocalDate.of(2023, 7, 19))
                            .build());

            movies.add(
                    Movie.builder()
                            .title("Inception")
                            .description("Un thriller onirique")
                            .rating(8.8)
                            .releaseDate(LocalDate.of(2010, 7, 21))
                            .build());

            movies.add(
                    Movie.builder()
                            .title("Interstellar")
                            .description("Voyage au-delà des étoiles")
                            .rating(8.6)
                            .releaseDate(LocalDate.of(2014, 11, 5))
                            .build());

            movies.add(
                    Movie.builder()
                            .title("The Dark Knight : Le Chevalier Noir")
                            .description("Batman contre le Joker")
                            .rating(9.0)
                            .releaseDate(LocalDate.of(2008, 7, 18))
                            .build());

            movies.add(
                    Movie.builder()
                            .title("Matrix")
                            .description("La matrice se dévoile")
                            .rating(8.7)
                            .releaseDate(LocalDate.of(1999, 3, 31))
                            .build());

            movies.add(
                    Movie.builder()
                            .title("Le Parrain")
                            .description("La famille Corleone en guerre")
                            .rating(9.2)
                            .releaseDate(LocalDate.of(1972, 3, 24))
                            .build());

            movies.add(
                    Movie.builder()
                            .title("Le Parrain, 2ᵉ partie")
                            .description("La saga continue")
                            .rating(9.0)
                            .releaseDate(LocalDate.of(1974, 12, 20))
                            .build());

            movies.add(
                    Movie.builder()
                            .title("Pulp Fiction")
                            .description("Un film culte de Tarantino")
                            .rating(8.9)
                            .releaseDate(LocalDate.of(1994, 10, 14))
                            .build());

            movies.add(
                    Movie.builder()
                            .title("Fight Club")
                            .description("Ne jamais parler du Fight Club")
                            .rating(8.8)
                            .releaseDate(LocalDate.of(1999, 10, 15))
                            .build());

            movies.add(
                    Movie.builder()
                            .title("Forrest Gump")
                            .description("La vie est comme une boîte de chocolats")
                            .rating(8.8)
                            .releaseDate(LocalDate.of(1994, 7, 6))
                            .build());

            movies.add(
                    Movie.builder()
                            .title("Le Seigneur des anneaux : La Communauté de l'anneau")
                            .description("Un voyage commence en Terre du Milieu")
                            .rating(8.8)
                            .releaseDate(LocalDate.of(2001, 12, 19))
                            .build());

            movies.add(
                    Movie.builder()
                            .title("Le Seigneur des anneaux : Les Deux Tours")
                            .description("La guerre pour la Terre du Milieu s’intensifie")
                            .rating(8.7)
                            .releaseDate(LocalDate.of(2002, 12, 18))
                            .build());

            movies.add(
                    Movie.builder()
                            .title("Le Seigneur des anneaux : Le Retour du roi")
                            .description("La fin d'une épopée")
                            .rating(8.9)
                            .releaseDate(LocalDate.of(2003, 12, 17))
                            .build());

            movies.add(
                    Movie.builder()
                            .title("Gladiator")
                            .description("Un général devenu esclave, puis gladiateur")
                            .rating(8.5)
                            .releaseDate(LocalDate.of(2000, 5, 5))
                            .build());

            movies.add(
                    Movie.builder()
                            .title("Les Évadés")
                            .description("Un espoir en prison")
                            .rating(9.3)
                            .releaseDate(LocalDate.of(1994, 9, 23))
                            .build());

            movies.add(
                    Movie.builder()
                            .title("Seven")
                            .description("Un tueur en série obsédé par les 7 péchés")
                            .rating(8.6)
                            .releaseDate(LocalDate.of(1995, 9, 22))
                            .build());

            movies.add(
                    Movie.builder()
                            .title("Le Silence des agneaux")
                            .description("Le face-à-face entre Clarice et Hannibal")
                            .rating(8.6)
                            .releaseDate(LocalDate.of(1991, 2, 14))
                            .build());

            movies.add(
                    Movie.builder()
                            .title("Parasite")
                            .description("Un thriller social sud-coréen")
                            .rating(8.6)
                            .releaseDate(LocalDate.of(2019, 5, 30))
                            .build());

            movies.add(
                    Movie.builder()
                            .title("La Ligne verte")
                            .description("Un prisonnier aux dons mystérieux")
                            .rating(8.6)
                            .releaseDate(LocalDate.of(1999, 12, 10))
                            .build());

            movies.add(
                    Movie.builder()
                            .title("Whiplash")
                            .description("Un batteur de jazz sous pression")
                            .rating(8.5)
                            .releaseDate(LocalDate.of(2014, 10, 10))
                            .build());


            movieRepository.saveAll(movies);

            MovieStatus movieStatus = MovieStatus.builder().user(user).movie(movies.get(2)).favorite(true).seen(true).build();
            MovieStatus movieStatus2 = MovieStatus.builder().user(user).movie(movies.get(0)).favorite(true).seen(true).build();
            MovieStatus movieStatus3 = MovieStatus.builder().user(user).movie(movies.get(1)).favorite(false).seen(true).build();

            movieStatusRepository.save(movieStatus);
            movieStatusRepository.save(movieStatus2);
            movieStatusRepository.save(movieStatus3);

        };

    }

}

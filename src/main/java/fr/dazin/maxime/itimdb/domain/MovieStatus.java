package fr.dazin.maxime.itimdb.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "moviestatuses")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private User user;

    @ManyToOne(optional = false)
    private Movie movie;


    private boolean favorite = false;

    private boolean seen = false;

}

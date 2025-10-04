package fr.dazin.maxime.itimdb.service.util;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class SortFactory {

    public Sort buildSort(String sortBy, String order) {
        Sort.Direction direction = "desc".equalsIgnoreCase(order) ? Sort.Direction.DESC : Sort.Direction.ASC;
        return switch (sortBy.toLowerCase()){
            case "rating" -> Sort.by(direction,"movie.rating");
            default -> Sort.by(direction,"movie.releaseDate");
        };
    }

}

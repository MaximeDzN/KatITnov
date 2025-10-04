package fr.dazin.maxime.itimdb.service.util;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class SortFactoryTest {

    SortFactory sortFactory = new SortFactory();

    @Test
    void whenSortByRatingAndOrderAsc_ThenReturnAscendingRatingSort(){
        Sort sort = sortFactory.buildSort("rating", "asc");
        Sort.Order order = sort.getOrderFor("movie.rating");

        assertThat(order).isNotNull();
        assertThat(order.getDirection()).isEqualTo(Sort.Direction.ASC);
    }

    @Test
    void whenSortByRatingAndOrderDesc_ThenReturnDescendingRatingSort(){
        Sort sort = sortFactory.buildSort("rating", "desc");
        Sort.Order order = sort.getOrderFor("movie.rating");

        assertThat(order).isNotNull();
        assertThat(order.getDirection()).isEqualTo(Sort.Direction.DESC);
    }

    @Test
    void whenSortByReleaseDateAndOrderAsc_ThenReturnAscendingReleaseDateSort(){
        Sort sort = sortFactory.buildSort("releaseDate", "asc");
        Sort.Order order = sort.getOrderFor("movie.releaseDate");

        assertThat(order).isNotNull();
        assertThat(order.getDirection()).isEqualTo(Sort.Direction.ASC);
    }

    @Test
    void whenSortByReleaseDateAndOrderDesc_thenReturnDescendingReleaseDateSort() {
        Sort sort = sortFactory.buildSort("releaseDate", "desc");
        Sort.Order order = sort.getOrderFor("movie.releaseDate");

        assertThat(order).isNotNull();
        assertThat(order.getDirection()).isEqualTo(Sort.Direction.DESC);
    }

    @Test
    void whenSortByIsNull_thenThrowException() {
        assertThatThrownBy(() -> sortFactory.buildSort(null, "asc"))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void whenSortByIsDefault_thenReturnDefaultSort() {
        Sort sort = sortFactory.buildSort("randomstring", "asc");
        Sort.Order order = sort.getOrderFor("movie.releaseDate");

        assertThat(order).isNotNull();
        assertThat(order.getDirection()).isEqualTo(Sort.Direction.ASC);
    }


    @Test
    void whenOrderIsNull_thenDefaultToAsc() {
        Sort sort = sortFactory.buildSort("rating", null);
        Sort.Order order = sort.getOrderFor("movie.rating");

        assertThat(order).isNotNull();
        assertThat(order.getDirection()).isEqualTo(Sort.Direction.ASC);
    }

}
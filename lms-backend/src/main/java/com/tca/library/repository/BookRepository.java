package com.tca.library.repository;

import com.tca.library.jpa.Books;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Books, Integer> {
    List<Books> findByTitleContainingIgnoreCase(String title);
}

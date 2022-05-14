package com.example.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.Contact;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

@Repository
public interface ProcessorRepository extends PagingAndSortingRepository<Contact, String> {
    
    @Query("SELECT c FROM Contact c WHERE LOWER(c.name) like CONCAT('%',LOWER(:name),'%')")
    public Page<Contact> searchByName(@Param("name") String name, Pageable pageable);

}

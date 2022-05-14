package com.example.services;

import com.example.model.Contact;
import com.example.repositories.ProcessorRepository;
import com.example.utils.BussinessException;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProcessorService {

    private static final Logger LOG = Logger.getLogger(ProcessorService.class.getName());

    @Autowired
    private ProcessorRepository repository;

    public boolean processFile(MultipartFile file) throws IOException, BussinessException, CsvException {
        List<String[]> contacts;
        Reader reader = new InputStreamReader(file.getInputStream());
        try (CSVReader readerCvs = new CSVReader(reader)) {
            contacts = readerCvs.readAll();
            contacts.forEach(x -> System.out.println(Arrays.toString(x)));
        }
        boolean success = true;
        int index = 0;
        for (String[] cont : contacts) {
            Contact contact = new Contact();
            try {
                String name = cont[0];
                String url = cont[1];

                if (StringUtils.hasText(url) && Objects.nonNull(url) && StringUtils.hasText(name) && url.trim().startsWith("http")) {
                    contact.setName(name.trim());
                    contact.setUrl(url.trim());
                    repository.save(contact);
                    index++;
                } else {
                    LOG.warning(String.format("ERROR: name-%s index-%d message-%s", name, index, "Contact missed"));
                }
            } catch (Exception e) {
                success = false;
                LOG.warning(String.format("ERROR: name-%s index-%d message-%s", cont[0], index, e.getLocalizedMessage()));
            }
        }
        System.out.println("Lenght of contacts: " + index);
        return success;
    }

    public Page<Contact> listContacts(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<Contact> searchByName(String name, Pageable pageable) throws Exception {
        return repository.searchByName(name, pageable);
    }

    public void reset() {
        repository.deleteAll();
    }

    @EventListener(ApplicationReadyEvent.class)
    public void insertContacts() throws IOException, BussinessException, CsvException {
        InputStream file = getClass().getResourceAsStream("/people.csv");
        final MultipartFile multipartFile = new MockMultipartFile("people", "people.csv", MediaType.TEXT_PLAIN_VALUE, file);
        processFile(multipartFile);
    }
}

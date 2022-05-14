package com.example.controllers;

import com.example.model.Contact;
import com.example.services.ProcessorService;
import com.example.utils.BussinessException;
import com.opencsv.exceptions.CsvException;
import io.micrometer.core.instrument.util.StringUtils;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin("http://localhost:4200")
public class ProcessorController {

    @Autowired
    private ProcessorService service;

    @PostMapping(path = "/processFile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = "application/json")
    public ResponseEntity<?> processFile(@RequestParam("file") MultipartFile file) throws IOException, BussinessException, CsvException {
        return service.processFile(file)
                ? new ResponseEntity<String>(HttpStatus.OK) : new ResponseEntity<String>(HttpStatus.NOT_FOUND);
    }


    @PostMapping(path = "/listContacts", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<Contact>> listContacts(@RequestParam("name") String name, Pageable pageable) throws IOException, Exception {
        if(StringUtils.isEmpty(name)){
            return ResponseEntity.ok(service.listContacts(pageable));
        }
        return ResponseEntity.ok(service.searchByName(name, pageable));
    }

    @DeleteMapping(path = "/reset")
    public void reset() {
        service.reset();
    }
}

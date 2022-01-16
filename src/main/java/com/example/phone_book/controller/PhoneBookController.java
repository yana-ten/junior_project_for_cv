package com.example.phone_book.controller;

import com.example.phone_book.Contact;
import com.example.phone_book.dao.ContactDAO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/phonebook")
public class PhoneBookController {
    private final ContactDAO contactDAO;

    public PhoneBookController(ContactDAO contactDAO) {
        this.contactDAO = contactDAO;
    }

    private static class DefaultResp {
        private final String msg;
        private final int code;

        public DefaultResp(String msg, int code) {
            this.msg = msg;
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public int getCode() {
            return code;
        }
    }

    @ExceptionHandler(Exception.class)
    private ResponseEntity<DefaultResp> handleException(Exception e) {
        e.printStackTrace();

        DefaultResp resp = new DefaultResp(e.getMessage(), 500);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resp);
    }


    @PostMapping(value = "/contact")
    public ResponseEntity<?> create(@RequestBody Contact contact) {
        contactDAO.createContact(contact);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/contact")
    public ResponseEntity<Contact> read(@RequestParam(name = "phone") String phone) {
        System.out.println("phone " + phone);
        Contact contact = contactDAO.getContact(phone);
        return contact != null
                ? new ResponseEntity<>(contact, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/contacts")
    public ResponseEntity<List<Contact>> read() {
        List<Contact> contacts = contactDAO.getAllContacts();
        return contacts != null
                ? new ResponseEntity<>(contacts, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/contact")
    public ResponseEntity<?> update(@RequestBody Contact contact) {
        boolean updated = contactDAO.updateContact(contact);
        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "/contact")
    public ResponseEntity<?> delete(@RequestParam(name = "phone") String phone) {
        boolean deleted = contactDAO.deleteContact(phone);
        return deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}

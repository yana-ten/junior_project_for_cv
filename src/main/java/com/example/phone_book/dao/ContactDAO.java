package com.example.phone_book.dao;

import com.example.phone_book.Contact;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ContactDAO {
    private final Logger logger = LoggerFactory.getLogger(ContactDAO.class);

    private final NamedParameterJdbcTemplate template;

    @Autowired
    public ContactDAO(DataSource dataSource) {
        template = new NamedParameterJdbcTemplate(dataSource);
    }

    public Contact getContact(String phone) {
        Map<String, Object> param = new HashMap<>();
        param.put("phone", phone);

        logger.info(String.format("Getting a contact with the phone number %s", phone));

        String SELECT_QUERY = "select * from contacts where phone = :phone";
        List<Contact> contacts = template.query(SELECT_QUERY, param, new BeanPropertyRowMapper<>(Contact.class));

        if (contacts.size() > 0)
            return contacts.get(0);
        else throw new IllegalArgumentException(String.format("Contact with the phone number %s not found", phone));
    }

    public List<Contact> getAllContacts() {
        logger.info("Getting all contacts from the phone book");
        List<Contact> contacts = template.query("select * from contacts", new BeanPropertyRowMapper<>(Contact.class));
        return contacts;
    }

    public void createContact(Contact contact) {
        String name= contact.getName();
        String phone = contact.getPhone();
        String email = contact.getEmail();

        logger.info(String.format("Adding a new contact: name %s, phone %s, email %s", name, phone, email));

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("name", name)
                .addValue("phone", phone)
                .addValue("email", email);

        String INSERT_QUERY = "insert into contacts (name, phone, email) values (:name, :phone, :email)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        int status = template.update(INSERT_QUERY, params, keyHolder, new String[] { "id" });

        if (status > 0) {
            int key = keyHolder.getKey().intValue();
            logger.info(String.format("The new contact has been added: id %s, name %s, phone %s, email %s", key, name, phone, email));
        }
    }

    public boolean updateContact(Contact contact) {
        Map<String, Object> params = new HashMap<>();
        params.put("name", contact.getName());
        params.put("phone", contact.getPhone());

        logger.info(String.format("Updating a contact with the phone number %s", params.get("phone")));

        String UPDATE_QUERY = "update contacts set name= :name where phone = :phone";
        int status = template.update(UPDATE_QUERY, params);

        if (status == 0) {
            logger.info(String.format("Contact with the phone number %s not found", params.get("phone")));
            throw new IllegalArgumentException(String.format("Contact with the phone number %s not found", params.get("phone")));
        }

        logger.info(String.format("The contact has been updated: name %s, phone %s", params.get("name"), params.get("phone")));

        return true;
    }

    public boolean deleteContact(String phone) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("phone", phone);

        logger.info(String.format("Deleting a contact with the phone number %s", phone));

        String DELETE_QUERY = "delete from contacts where phone = :phone";
        int status = template.update(DELETE_QUERY, params);

        if (status == 0) {
            logger.info(String.format("Contact with the phone number %s not found", phone));
            throw new IllegalArgumentException(String.format("Contact with the phone number %s not found", phone));
        }


        logger.info(String.format("The contact with the phone number %s has been deleted", phone));
        return true;
    }

    public Logger getLogger() {
        return logger;
    }
}

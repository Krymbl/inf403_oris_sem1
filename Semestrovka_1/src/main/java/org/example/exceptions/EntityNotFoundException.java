package org.example.exceptions;

import java.sql.SQLException;

public class EntityNotFoundException extends SQLException {

    public EntityNotFoundException(String entityName, Long id) {
        super(entityName + " c id " + id + " не найден");
    }

    public EntityNotFoundException(String entityName, String name) {
        super(entityName + " c name " + name + " не найден");
    }
}

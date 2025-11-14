package org.example.exceptions;

import java.sql.SQLException;

public class EntityUpdateException extends SQLException {

    public EntityUpdateException(String entityName) {
        super("Не удалось обновить " + entityName);
    }
}

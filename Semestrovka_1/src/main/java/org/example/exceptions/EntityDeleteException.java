package org.example.exceptions;

import java.sql.SQLException;

public class EntityDeleteException extends SQLException {

    public EntityDeleteException(String entityName) {
        super("Не удалось удалить " + entityName);
    }
}

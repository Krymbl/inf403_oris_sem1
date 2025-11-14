package org.example.exceptions;

import java.sql.SQLException;

public class EntitySaveException extends SQLException {

    public EntitySaveException(String entityName) {
        super("Не удалось сохранить " + entityName );
    }
}

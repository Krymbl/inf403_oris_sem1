package org.example.exceptions;

import java.sql.SQLException;

public class NoDataFoundException extends SQLException {

    public NoDataFoundException(String entityName) {
        super("В базе отсутствуют данные о " +  entityName);
    }

    public NoDataFoundException(String entityName, String condition) {
        super("Не найдены данные о" + entityName + " по условию: " + condition);
    }
}

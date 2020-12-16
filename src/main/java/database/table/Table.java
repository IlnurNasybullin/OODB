package database.table;

import database.column.Column;

import java.util.List;

public interface Table {
    String getName();
    List<Column> getAllColumns();
}

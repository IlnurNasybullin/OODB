package lab_8.dbManager;

import java.sql.SQLException;

public class RelationMappingException extends SQLException {
    public RelationMappingException(String reason, String SQLState, int vendorCode) {
        super(reason, SQLState, vendorCode);
    }

    public RelationMappingException(String reason, String SQLState) {
        super(reason, SQLState);
    }

    public RelationMappingException(String reason) {
        super(reason);
    }

    public RelationMappingException() {
    }

    public RelationMappingException(Throwable cause) {
        super(cause);
    }

    public RelationMappingException(String reason, Throwable cause) {
        super(reason, cause);
    }

    public RelationMappingException(String reason, String sqlState, Throwable cause) {
        super(reason, sqlState, cause);
    }

    public RelationMappingException(String reason, String sqlState, int vendorCode, Throwable cause) {
        super(reason, sqlState, vendorCode, cause);
    }
}

package airlines.userTypes;

import annotations.Column;
import annotations.Entity;
import annotations.TypeComponent;
import annotations.UserType;

@UserType
public enum TicketType {
    @TypeComponent
    FIRST,
    @TypeComponent
    BUSINESS,
    @TypeComponent
    ECONOMY
}

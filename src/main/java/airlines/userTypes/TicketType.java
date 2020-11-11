package airlines.userTypes;

import annotations.EnumType;
import annotations.TypeComponent;

@EnumType
public enum TicketType {
    @TypeComponent
    FIRST,
    @TypeComponent
    BUSINESS,
    @TypeComponent
    ECONOMY
}

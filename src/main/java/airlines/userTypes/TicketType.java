package airlines.userTypes;

import annotations.Column;
import annotations.Entity;

@Entity
public enum TicketType {
    @Column
    FIRST,
    @Column
    BUSINESS,
    @Column
    ECONOMY
}

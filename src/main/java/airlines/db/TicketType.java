package airlines.db;

import lab_6.annotations.Column;
import lab_6.annotations.Entity;

@Entity
public enum TicketType {
    @Column
    FIRST,
    @Column
    BUSINESS,
    @Column
    ECONOMY
}

package airlines.db;

import lab_6.annotations.Column;
import lab_6.annotations.Entity;

@Entity
public enum StaffPosition {
    @Column
    PILOT,
    @Column
    ASSISTANT,
    @Column
    STEWARDESS
}

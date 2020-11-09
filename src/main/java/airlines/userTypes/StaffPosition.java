package airlines.userTypes;

import annotations.Column;
import annotations.Entity;

@Entity
public enum StaffPosition {
    @Column
    PILOT,
    @Column
    ASSISTANT,
    @Column
    STEWARDESS
}

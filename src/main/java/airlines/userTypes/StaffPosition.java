package airlines.userTypes;

import annotations.Column;
import annotations.Entity;
import annotations.TypeComponent;
import annotations.UserType;

@UserType
public enum StaffPosition {
    @TypeComponent
    PILOT,
    @TypeComponent
    ASSISTANT,
    @TypeComponent
    STEWARDESS
}

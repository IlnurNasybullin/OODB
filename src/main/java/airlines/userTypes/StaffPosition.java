package airlines.userTypes;

import annotations.EnumType;
import annotations.TypeComponent;

@EnumType
public enum StaffPosition {
    @TypeComponent
    PILOT,
    @TypeComponent
    ASSISTANT,
    @TypeComponent
    STEWARDESS
}

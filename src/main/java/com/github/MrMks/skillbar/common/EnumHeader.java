package com.github.MrMks.skillbar.common;

public enum EnumHeader {
    Undefined,
    Discover,
    Setting,
    Enable,
    Disable,
    Account,
    ListSkill,
    AddSkill,
    RemoveSkill,
    UpdateSkill,
    ListBar,
    SaveBar,
    Clean,
    EnterCondition,
    LeaveCondition,
    Cast,
    CoolDown;

    public byte byteOrder(){
        return (byte) ordinal();
    }
}

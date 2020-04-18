package com.github.MrMks.skillbar.common.handler;

import com.github.MrMks.skillbar.common.SkillInfo;

import java.util.List;
import java.util.Map;

public interface IClientHandler {
    void onDiscover(int version);
    void onSetting(int maxSize);

    void onAccount(int activeId, int skillSize);
    void onEnable();
    void onCleanUp(int activeId);
    void onDisable();

    void onListSkill(List<SkillInfo> aList, List<String> reList);
    void onEnforceListSkill(int id, List<SkillInfo> list);
    void onUpdateSkill(SkillInfo info);
    void onEnforceUpdateSKill(int id, SkillInfo info);
    void onAddSkill(List<SkillInfo> aList);
    void onListBar(Map<Integer, String> map);
    void onEnterCondition(int size, boolean fix, boolean free, Map<Integer, String> fixMap, List<Integer> freeList);
    void onLeaveCondition();
    @Deprecated void onFixBar(boolean fix);
    @Deprecated void onFreeSlot(List<Integer> list);

    void onCast(String key, boolean exist, boolean suc, byte code);
    void onCoolDown(Map<String, Integer> map);
}

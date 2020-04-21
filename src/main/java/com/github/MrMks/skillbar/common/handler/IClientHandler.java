package com.github.MrMks.skillbar.common.handler;

import com.github.MrMks.skillbar.common.SkillInfo;

import java.util.List;
import java.util.Map;

public interface IClientHandler {
    void onDiscover(int version);
    void onSetting(int maxSize);

    void onAccount(int activeId, int skillSize);
    void onCleanUp(int activeId);
    void onEnable();
    void onDisable();

    void onListSkill(List<SkillInfo> list);
    void onAddSkill(List<SkillInfo> list);
    void onRemoveSkill(List<String> list);
    void onUpdateSkill(SkillInfo info);
    void onListBar(Map<Integer, String> map);
    void onEnterCondition(int size, boolean fix, boolean free, Map<Integer, String> fixMap, List<Integer> freeList);
    void onLeaveCondition();

    void onCast(String key, boolean exist, boolean suc, byte code);
    void onCoolDown(Map<String, Integer> map);
}

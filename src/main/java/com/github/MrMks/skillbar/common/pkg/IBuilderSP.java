package com.github.MrMks.skillbar.common.pkg;

import com.github.MrMks.skillbar.common.ByteBuilder;
import com.github.MrMks.skillbar.common.ICondition;
import com.github.MrMks.skillbar.common.SkillInfo;

import java.util.List;
import java.util.Map;

public interface IBuilderSP extends IBuilder{
    ByteBuilder buildDiscover();
    ByteBuilder buildSetting(int maxBarSize);
    ByteBuilder buildAccount(int active, int size);
    ByteBuilder buildCleanUp(int active);
    ByteBuilder buildEnable();
    ByteBuilder buildDisable();

    ByteBuilder buildListSkill(List<SkillInfo> list);
    ByteBuilder buildAddSkill(List<SkillInfo> list);
    ByteBuilder buildRemoveSkill(List<String> list);
    ByteBuilder buildUpdateSkill(SkillInfo info);
    ByteBuilder buildListBar(Map<Integer, ? extends CharSequence> map);
    ByteBuilder buildEnterCondition(ICondition condition);
    ByteBuilder buildLeaveCondition();

    ByteBuilder buildCast(CharSequence key, boolean exist, boolean suc);
    ByteBuilder buildCoolDown(Map<? extends CharSequence, Integer> map);
}

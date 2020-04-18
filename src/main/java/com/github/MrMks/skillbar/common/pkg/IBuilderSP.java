package com.github.MrMks.skillbar.common.pkg;

import com.github.MrMks.skillbar.common.ByteAllocator;
import com.github.MrMks.skillbar.common.ByteBuilder;
import com.github.MrMks.skillbar.common.SkillInfo;

import java.util.List;
import java.util.Map;

public interface IBuilderSP {
    ByteBuilder buildDiscover(ByteAllocator allocator, int version);
    ByteBuilder buildSetting(ByteAllocator allocator, int maxBarSize);
    ByteBuilder buildAccount(ByteAllocator allocator, int active, int size);
    ByteBuilder buildEnable(ByteAllocator allocator);
    ByteBuilder buildCleanUp(ByteAllocator allocator, int active);
    ByteBuilder buildDisable(ByteAllocator allocator);

    ByteBuilder buildListSkill(ByteAllocator allocator, List<SkillInfo> aList, List<String> reList);
    ByteBuilder buildEnforceListSkill(ByteAllocator allocator, int active, List<SkillInfo> list);
    ByteBuilder buildUpdateSkill(ByteAllocator allocator, SkillInfo info);
    ByteBuilder buildEnforceUpdateSkill(ByteAllocator allocator, int active, SkillInfo info);
    ByteBuilder buildAddSkill(ByteAllocator allocator, List<SkillInfo> aList);
    ByteBuilder buildListBar(ByteAllocator allocator, Map<Integer, String> map);
    ByteBuilder buildEnterCondition(ByteAllocator allocator, CharSequence key, int size, boolean fix, List<Integer> fList);
    ByteBuilder buildLeaveCondition(ByteAllocator allocator, CharSequence key);
    ByteBuilder buildFixBar(ByteAllocator allocator, boolean fix);
    ByteBuilder buildFreeSlots(ByteAllocator allocator, List<Integer> list);

    ByteBuilder buildCast(ByteAllocator allocator, String key, boolean exist, boolean suc, byte code);
    ByteBuilder buildCoolDown(ByteAllocator allocator, Map<String, Integer> map);
}

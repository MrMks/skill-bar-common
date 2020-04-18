package com.github.MrMks.skillbar.common.pkg;

import com.github.MrMks.skillbar.common.ByteAllocator;
import com.github.MrMks.skillbar.common.ByteBuilder;

import java.util.List;
import java.util.Map;

public interface IBuilderCP {
    ByteBuilder buildDiscover(ByteAllocator allocator);

    ByteBuilder buildListSkill(ByteAllocator allocator, List<? extends CharSequence> list);
    ByteBuilder buildUpdateSkill(ByteAllocator allocator, CharSequence key);
    ByteBuilder buildListBar(ByteAllocator allocator);
    ByteBuilder buildSaveBar(ByteAllocator allocator, Map<Integer, ? extends CharSequence> map);

    ByteBuilder buildCast(ByteAllocator allocator, CharSequence key);
}

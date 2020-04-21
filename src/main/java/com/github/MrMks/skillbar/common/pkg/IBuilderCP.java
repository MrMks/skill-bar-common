package com.github.MrMks.skillbar.common.pkg;

import com.github.MrMks.skillbar.common.ByteBuilder;

import java.util.List;
import java.util.Map;

public interface IBuilderCP extends IBuilder{
    ByteBuilder buildDiscover();

    ByteBuilder buildListSkill(List<? extends CharSequence> list);
    ByteBuilder buildUpdateSkill(CharSequence key);
    ByteBuilder buildListBar();
    ByteBuilder buildSaveBar(Map<Integer, ? extends CharSequence> map);

    ByteBuilder buildCast(CharSequence key);
}

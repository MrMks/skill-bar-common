package com.github.MrMks.skillbar.common;

import java.util.List;
import java.util.Map;

public interface ICondition {
    int getBarSize();
    boolean isEnableFix();
    Map<Integer, String> getFixMap();
    boolean isEnableFree();
    List<Integer> getFreeList();
}

package com.github.MrMks.skillbar.common.pkg;

import com.github.MrMks.skillbar.common.ByteAllocator;

public interface IBuilder {
    void init(ByteAllocator allocator);
    boolean isInitialized();
}

package com.github.MrMks.skillbar.common.pkg;

import com.github.MrMks.skillbar.common.ByteAllocator;
import com.github.MrMks.skillbar.common.ByteBuilder;
import com.github.MrMks.skillbar.common.ByteDecoder;
import com.github.MrMks.skillbar.common.EnumHeader;
import com.github.MrMks.skillbar.common.handler.IServerHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CPackage {
    public static Builder BUILDER = new Builder();
    public static Decoder DECODER = new Decoder();
    public static class Builder implements IBuilderCP {
        private Builder(){}
        private ByteAllocator allocator;

        @Override
        public void init(ByteAllocator allocator) {
            this.allocator = allocator;
        }

        @Override
        public boolean isInitialized() {
            return this.allocator != null;
        }

        @Override
        public ByteBuilder buildDiscover() {
            return allocator.build(EnumHeader.Discover.byteOrder());
        }

        @Override
        public ByteBuilder buildListSkill(List<? extends CharSequence> list) {
            return allocator.build(EnumHeader.ListSkill.byteOrder()).writeCharSequenceList(list);
        }

        @Override
        public ByteBuilder buildUpdateSkill(CharSequence key) {
            return allocator.build(EnumHeader.UpdateSkill.byteOrder()).writeCharSequence(key);
        }

        @Override
        public ByteBuilder buildListBar() {
            return allocator.build(EnumHeader.ListBar.byteOrder());
        }

        @Override
        public ByteBuilder buildSaveBar(Map<Integer, ? extends CharSequence> map) {
            ByteBuilder builder = allocator.build(EnumHeader.SaveBar.byteOrder()).writeInt(map.size());
            for (Map.Entry<Integer, ? extends CharSequence> entry : map.entrySet()){
                builder.writeInt(entry.getKey()).writeCharSequence(entry.getValue());
            }
            return builder;
        }

        @Override
        public ByteBuilder buildCast(CharSequence key) {
            return allocator.build(EnumHeader.Cast.byteOrder()).writeCharSequence(key);
        }
    }
    public static class Decoder implements IDecoderCP {
        private Decoder(){}
        public void decodeDiscover(IServerHandler handler, ByteDecoder decoder) {
            handler.onDiscover();
        }

        @Override
        public void decodeListSkill(IServerHandler handler, ByteDecoder decoder) {
            handler.onListSkill(decoder.readCharSequenceList());
        }

        @Override
        public void decodeUpdateSkill(IServerHandler handler, ByteDecoder decoder) {
            handler.onUpdateSkill(decoder.readCharSequence());
        }

        @Override
        public void decodeListBar(IServerHandler handler, ByteDecoder decoder) {
            handler.onListBar();
        }

        @Override
        public void decodeSaveBar(IServerHandler handler, ByteDecoder decoder) {
            int size = decoder.readInt();
            Map<Integer, String> map = new HashMap<>();
            for (int i = 0; i < size; i++){
                int index = decoder.readInt();
                String key = decoder.readCharSequence();
                map.put(index,key);
            }
            handler.onSaveBar(map);
        }

        @Override
        public void decodeCast(IServerHandler handler, ByteDecoder decoder) {
            handler.onCast(decoder.readCharSequence());
        }
    }
}

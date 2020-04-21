package com.github.MrMks.skillbar.common.pkg;

import com.github.MrMks.skillbar.common.*;
import com.github.MrMks.skillbar.common.handler.IClientHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.github.MrMks.skillbar.common.Constants.*;

public class SPackage {
    public static Builder BUILDER = new Builder();
    public static Decoder DECODER = new Decoder();
    public static class Builder implements IBuilderSP {
        private Builder(){}
        private ByteAllocator allocator = null;
        @Override
        public void init(ByteAllocator allocator) {
            this.allocator = allocator;
        }

        @Override
        public boolean isInitialized() {
            return allocator != null;
        }

        @Override
        public ByteBuilder buildDiscover() {
            return allocator.build(EnumHeader.Discover.byteOrder()).writeInt(VERSION);
        }

        @Override
        public ByteBuilder buildSetting(int maxBarSize) {
            return allocator.build(EnumHeader.Setting.byteOrder()).writeInt(maxBarSize);
        }

        @Override
        public ByteBuilder buildEnable() {
            return allocator.build(EnumHeader.Enable.byteOrder());
        }

        @Override
        public ByteBuilder buildAccount(int active, int size) {
            return allocator.build(EnumHeader.Account.byteOrder()).writeInt(active).writeInt(size);
        }

        @Override
        public ByteBuilder buildCleanUp(int active) {
            return allocator.build(EnumHeader.Clean.byteOrder()).writeInt(active);
        }

        @Override
        public ByteBuilder buildDisable() {
            return allocator.build(EnumHeader.Disable.byteOrder());
        }

        @Override
        public ByteBuilder buildListSkill(List<SkillInfo> list) {
            return allocator.build(EnumHeader.ListSkill.byteOrder()).writeSkillInfoList(list);
        }

        @Override
        public ByteBuilder buildAddSkill(List<SkillInfo> list) {
            return allocator.build(EnumHeader.AddSkill.byteOrder()).writeSkillInfoList(list);
        }

        @Override
        public ByteBuilder buildRemoveSkill(List<String> list) {
            return allocator.build(EnumHeader.RemoveSkill.byteOrder()).writeCharSequenceList(list);
        }

        @Override
        public ByteBuilder buildUpdateSkill(SkillInfo info) {
            return allocator.build(EnumHeader.UpdateSkill.byteOrder()).writeSkillInfo(info);
        }

        @Override
        public ByteBuilder buildListBar(Map<Integer, ? extends CharSequence> map) {
            ByteBuilder builder = allocator.build(EnumHeader.ListBar.byteOrder()).writeInt(map.size());
            for (Map.Entry<Integer,? extends CharSequence> entry : map.entrySet()) {
                builder.writeInt(entry.getKey()).writeCharSequence(entry.getValue());
            }
            return builder;
        }

        @Override
        public ByteBuilder buildEnterCondition(ICondition condition) {
            ByteBuilder builder = allocator.build(EnumHeader.EnterCondition.byteOrder())
                    .writeInt(condition.getBarSize())
                    .writeBoolean(condition.isEnableFix())
                    .writeBoolean(condition.isEnableFree());
            builder.writeInt(condition.getFixMap().size());
            condition.getFixMap().forEach((k,v)->builder.writeInt(k).writeCharSequence(v));
            builder.writeInt(condition.getFreeList().size());
            condition.getFreeList().forEach(builder::writeInt);
            return builder;
        }

        @Override
        public ByteBuilder buildLeaveCondition() {
            return allocator.build(EnumHeader.LeaveCondition.byteOrder());
        }

        @Override
        public ByteBuilder buildCast(CharSequence key, boolean exist, boolean suc) {
            return allocator.build(EnumHeader.Cast.byteOrder()).writeCharSequence(key).writeBoolean(exist).writeBoolean(suc);
        }

        @Override
        public ByteBuilder buildCoolDown(Map<? extends CharSequence, Integer> map) {
            ByteBuilder builder = allocator.build(EnumHeader.CoolDown.byteOrder()).writeInt(map.size());
            for (Map.Entry<? extends CharSequence,Integer> entry : map.entrySet()){
                builder.writeCharSequence(entry.getKey()).writeInt(entry.getValue());
            }
            return builder;
        }
    }
    public static class Decoder implements IDecoderSP {
        private Decoder(){}
        @Override
        public void decodeDiscover(IClientHandler handler, ByteDecoder decoder) {
            int version = decoder.readInt();
            if (version == VERSION) handler.onDiscover(version);
        }

        @Override
        public void decodeSetting(IClientHandler handler, ByteDecoder decoder) {
            int size = decoder.readInt();
            handler.onSetting(size);
        }

        @Override
        public void decodeEnable(IClientHandler handler, ByteDecoder decoder) {
            handler.onEnable();
        }

        @Override
        public void decodeAccount(IClientHandler handler, ByteDecoder decoder) {
            int activeId = decoder.readInt();
            int size = decoder.readInt();
            handler.onAccount(activeId, size);
        }

        @Override
        public void decodeCleanUp(IClientHandler handler, ByteDecoder decoder) {
            handler.onCleanUp(decoder.readInt());
        }

        @Override
        public void decodeDisable(IClientHandler handler, ByteDecoder decoder) {
            handler.onDisable();
        }

        @Override
        public void decodeListSkill(IClientHandler handler, ByteDecoder decoder) {
            List<SkillInfo> aList = decoder.readSkillInfoList();
            handler.onListSkill(aList);
        }

        @Override
        public void decodeAddSkill(IClientHandler handler, ByteDecoder decoder) {
            handler.onAddSkill(decoder.readSkillInfoList());
        }

        @Override
        public void decodeRemoveSkill(IClientHandler handler, ByteDecoder decoder) {
            handler.onRemoveSkill(decoder.readCharSequenceList());
        }

        @Override
        public void decodeUpdateSkill(IClientHandler handler, ByteDecoder decoder) {
            SkillInfo info = decoder.readSkillInfo();
            handler.onUpdateSkill(info);
        }

        @Override
        public void decodeListBar(IClientHandler handler, ByteDecoder decoder) {
            int size = decoder.readInt();
            Map<Integer, String> map = new HashMap<>();
            for (int i = 0; i < size ; i++){
                int index = decoder.readInt();
                String key = decoder.readCharSequence();
                map.put(index,key);
            }
            handler.onListBar(map);
        }

        @Override
        public void decodeEnterCondition(IClientHandler handler, ByteDecoder decoder) {
            int size = decoder.readInt();
            boolean fix = decoder.readBoolean();
            boolean free = decoder.readBoolean();
            int mapSize = decoder.readInt();
            Map<Integer, String> fixMap = new HashMap<>();
            for (int i = 0; i < mapSize; i++) fixMap.put(decoder.readInt(), decoder.readCharSequence());
            int listSize = decoder.readInt();
            List<Integer> list = new ArrayList<>();
            for (int i = 0; i < listSize; i++) list.add(decoder.readInt());
            handler.onEnterCondition(size, fix, free, fixMap, list);
        }

        @Override
        public void decodeLeaveCondition(IClientHandler handler, ByteDecoder decoder) {
            handler.onLeaveCondition();
        }

        @Override
        public void decodeCast(IClientHandler handler, ByteDecoder decoder) {
            String key = decoder.readCharSequence();
            boolean exist = decoder.readBoolean();
            boolean suc = decoder.readBoolean();
            handler.onCast(key,exist,suc);
        }

        @Override
        public void decodeCoolDown(IClientHandler handler, ByteDecoder decoder) {
            int size = decoder.readInt();
            Map<String, Integer> map = new HashMap<>();
            for (int i = 0; i < size; i++){
                String key = decoder.readCharSequence();
                int cd = decoder.readInt();
                map.put(key,cd);
            }
            handler.onCoolDown(map);
        }
    }
}

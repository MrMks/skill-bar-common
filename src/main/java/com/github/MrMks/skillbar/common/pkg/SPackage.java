package com.github.MrMks.skillbar.common.pkg;

import com.github.MrMks.skillbar.common.ByteAllocator;
import com.github.MrMks.skillbar.common.ByteBuilder;
import com.github.MrMks.skillbar.common.ByteDecoder;
import com.github.MrMks.skillbar.common.SkillInfo;
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
        @Override
        public ByteBuilder buildDiscover(ByteAllocator allocator, int version) {
            return allocator.build(DISCOVER).writeInt(version);
        }

        @Override
        public ByteBuilder buildSetting(ByteAllocator allocator, int maxBarSize) {
            return allocator.build(SETTING).writeInt(maxBarSize);
        }

        @Override
        public ByteBuilder buildEnable(ByteAllocator allocator) {
            return allocator.build(ENABLE);
        }

        @Override
        public ByteBuilder buildAccount(ByteAllocator allocator, int active, int size) {
            return allocator.build(ACCOUNT).writeInt(active).writeInt(size);
        }

        @Override
        public ByteBuilder buildCleanUp(ByteAllocator allocator, int active) {
            return allocator.build(CLEAN).writeInt(active);
        }

        @Override
        public ByteBuilder buildDisable(ByteAllocator allocator) {
            return allocator.build(DISABLE);
        }

        @Override
        public ByteBuilder buildListSkill(ByteAllocator allocator, List<SkillInfo> aList, List<String> reList) {
            return allocator.build(LIST_SKILL).writeSkillInfoList(aList).writeCharSequenceList(reList);
        }

        @Override
        public ByteBuilder buildEnforceListSkill(ByteAllocator allocator, int active, List<SkillInfo> list) {
            return allocator.build(ENFORCE_LIST_SKILL).writeInt(active).writeSkillInfoList(list);
        }

        @Override
        public ByteBuilder buildUpdateSkill(ByteAllocator allocator, SkillInfo info) {
            return allocator.build(UPDATE_SKILL).writeSkillInfo(info);
        }

        @Override
        public ByteBuilder buildEnforceUpdateSkill(ByteAllocator allocator, int active, SkillInfo info) {
            return allocator.build(ENFORCE_UPDATE_SKILL).writeInt(active).writeSkillInfo(info);
        }

        @Override
        public ByteBuilder buildAddSkill(ByteAllocator allocator, List<SkillInfo> aList) {
            return allocator.build(ADD_SKILL).writeSkillInfoList(aList);
        }

        @Override
        public ByteBuilder buildListBar(ByteAllocator allocator, Map<Integer, String> map) {
            ByteBuilder builder = allocator.build(LIST_BAR).writeInt(map.size());
            for (Map.Entry<Integer,String> entry : map.entrySet()) {
                builder.writeInt(entry.getKey()).writeCharSequence(entry.getValue());
            }
            return builder;
        }

        @Override
        @Deprecated
        public ByteBuilder buildFixBar(ByteAllocator allocator, boolean fix) {
            return allocator.build(FIX_BAR).writeBoolean(fix);
        }

        @Override
        public ByteBuilder buildRemoveBar(ByteAllocator allocator, List<Integer> list) {
            ByteBuilder builder = allocator.build(REMOVE_BAR).writeInt(list.size());
            list.forEach(builder::writeInt);
            return builder;
        }

        @Override
        public ByteBuilder buildCast(ByteAllocator allocator, String key, boolean exist, boolean suc, byte code) {
            return allocator.build(CAST).writeCharSequence(key).writeBoolean(exist).writeBoolean(suc).write(code);
        }

        @Override
        public ByteBuilder buildCoolDown(ByteAllocator allocator, Map<String, Integer> map) {
            ByteBuilder builder = allocator.build(COOLDOWN).writeInt(map.size());
            for (Map.Entry<String,Integer> entry : map.entrySet()){
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
            handler.onDiscover(version);
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
            List<CharSequence> reList = decoder.readCharSequenceList();
            handler.onListSkill(aList, reList);
        }

        @Override
        public void decodeEnforceListSkill(IClientHandler handler, ByteDecoder decoder) {
            int active = decoder.readInt();
            List<SkillInfo> list = decoder.readSkillInfoList();
            handler.onEnforceListSkill(active, list);
        }

        @Override
        public void decodeUpdateSkill(IClientHandler handler, ByteDecoder decoder) {
            SkillInfo info = decoder.readSkillInfo();
            handler.onUpdateSkill(info);
        }

        @Override
        public void decodeEnforceUpdateSkill(IClientHandler handler, ByteDecoder decoder) {
            int active = decoder.readInt();
            SkillInfo info = decoder.readSkillInfo();
            handler.onEnforceUpdateSKill(active, info);
        }

        @Override
        public void decodeAddSkill(IClientHandler handler, ByteDecoder decoder) {
            handler.onAddSkill(decoder.readSkillInfoList());
        }

        @Override
        public void decodeListBar(IClientHandler handler, ByteDecoder decoder) {
            int size = decoder.readInt();
            Map<Integer, CharSequence> map = new HashMap<>();
            for (int i = 0; i < size ; i++){
                int index = decoder.readInt();
                CharSequence key = decoder.readCharSequence();
                map.put(index,key);
            }
            handler.onListBar(map);
        }

        @Override
        @Deprecated
        public void decodeFixBar(IClientHandler handler, ByteDecoder decoder) {
            handler.onFixBar(decoder.readBoolean());
        }

        @Override
        public void decodeRemoveBar(IClientHandler handler, ByteDecoder decoder) {
            int size = decoder.readInt();
            List<Integer> list = new ArrayList<>();
            for (int i = 0; i < size; i++) list.add(decoder.readInt());
            handler.onRemoveBar(list);
        }

        @Override
        public void decodeCast(IClientHandler handler, ByteDecoder decoder) {
            CharSequence key = decoder.readCharSequence();
            boolean exist = decoder.readBoolean();
            boolean suc = decoder.readBoolean();
            byte code = decoder.read();
            handler.onCast(key,exist,suc,code);
        }

        @Override
        public void decodeCoolDown(IClientHandler handler, ByteDecoder decoder) {
            int size = decoder.readInt();
            Map<CharSequence, Integer> map = new HashMap<>();
            for (int i = 0; i < size; i++){
                CharSequence key = decoder.readCharSequence();
                int cd = decoder.readInt();
                map.put(key,cd);
            }
            handler.onCoolDown(map);
        }
    }
}

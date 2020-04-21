package com.github.MrMks.skillbar.common;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ByteDecoder {
    private Charset utf8 = StandardCharsets.UTF_8;
    private ByteBuf buf;
    private EnumHeader header;
    public ByteDecoder(byte[] bytes) throws IndexOutOfBoundsException{
        buf = PooledByteBufAllocator.DEFAULT.buffer(bytes.length - 1);
        buf.writeBytes(bytes,1,bytes.length - 1);
        byte i = buf.readByte();
        header = (i >= 0 && i < EnumHeader.values().length) ? EnumHeader.values()[i] : EnumHeader.Undefined;
    }

    public ByteDecoder(ByteBuf buf){
        this.buf = buf;
        byte i = buf.readByte();
        header = (i >= 0 && i < EnumHeader.values().length) ? EnumHeader.values()[i] : EnumHeader.Undefined;
    }

    public EnumHeader getHeader(){
        return header;
    }

    public byte read() throws IndexOutOfBoundsException{
        return buf.readByte();
    }

    public short readShort() throws IndexOutOfBoundsException{
        return buf.readShort();
    }

    public int readInt() throws IndexOutOfBoundsException{
        return buf.readInt();
    }

    public long readLong() throws IndexOutOfBoundsException{
        return buf.readLong();
    }

    public boolean readBoolean() throws IndexOutOfBoundsException{
        return buf.readBoolean();
    }

    public String readCharSequence() throws IndexOutOfBoundsException{
        return buf.readCharSequence(readInt(),utf8).toString();
    }

    public List<String> readCharSequenceList() throws IndexOutOfBoundsException{
        List<String> list = new ArrayList<>();
        int size = readInt();
        for (int i = 0; i < size; i++){
            list.add(readCharSequence());
        }
        return list;
    }

    public SkillInfo readSkillInfo() throws IndexOutOfBoundsException{
        String key = readCharSequence();
        boolean exist = readBoolean();
        boolean isUnlock = readBoolean();
        boolean canCast = readBoolean();
        int itemId = readInt();
        short damage = readShort();
        String display = readCharSequence();
        List<String> lore = readCharSequenceList();
        return new SkillInfo(key,exist,isUnlock,canCast,itemId,damage,display,lore);
    }

    public List<SkillInfo> readSkillInfoList() throws IndexOutOfBoundsException{
        int size = readInt();
        ArrayList<SkillInfo> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++){
            list.add(readSkillInfo());
        }
        return list;
    }
}

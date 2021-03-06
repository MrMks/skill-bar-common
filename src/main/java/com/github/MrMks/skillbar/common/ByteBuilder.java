package com.github.MrMks.skillbar.common;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

public abstract class ByteBuilder {
    private static final Charset utf8 = StandardCharsets.UTF_8;

    private ByteBuf buf = Unpooled.buffer();
    public ByteBuilder(byte header){
        buf.writeByte(header);
    }

    public ByteBuilder write(byte v){
        buf.writeByte(v);
        return this;
    }

    public ByteBuilder writeShort(short v){
        buf.writeShort(v);
        return this;
    }

    public ByteBuilder writeInt(int v){
        buf.writeInt(v);
        return this;
    }

    public ByteBuilder writeLong(long v){
        buf.writeLong(v);
        return this;
    }

    public ByteBuilder writeBoolean(boolean v){
        buf.writeBoolean(v);
        return this;
    }

    public ByteBuilder writeCharSequence(CharSequence v){
        if (v == null) {
            buf.writeInt(0);
        } else {
            buf.writeInt(v.toString().getBytes(utf8).length);
            buf.writeCharSequence(v, utf8);
        }
        return this;
    }

    public ByteBuilder writeCharSequenceList(List<? extends CharSequence> v){
        if (v == null) {
            writeInt(0);
        } else {
            writeInt(v.size());
            for (CharSequence c : v){
                writeCharSequence(c);
            }
        }
        return this;
    }

    public ByteBuilder writeSkillInfo(SkillInfo info){
        writeCharSequence(info.getKey());
        writeBoolean(info.isExist());
        writeBoolean(info.isUnlock());
        writeBoolean(info.canCast());
        writeInt(info.getItemId());
        writeShort(info.getDamage());
        writeCharSequence(info.getDisplay());
        writeCharSequenceList(info.getLore());
        return this;
    }

    public ByteBuilder writeSkillInfoList(List<SkillInfo> infos){
        writeInt(infos.size());
        for (SkillInfo info : infos) writeSkillInfo(info);
        return this;
    }

    protected int readableBytes(){
        return buf.readableBytes();
    }

    protected void readBytes(byte[] dst, int dstIndex, int length){
        buf.readBytes(dst,dstIndex,length);
    }

    public byte[][] build(byte partId){
        throw new UnsupportedOperationException();
    }

    public ByteBuf buildBuf(){
        throw new UnsupportedOperationException();
    }

    protected ByteBuf getBuf(){
        return buf;
    }
}

package com.fanxing.server.persist.impl;

import java.util.Arrays;

/**
 * @brief һ���ܹ����DataOutputStream�Լ�DataInputStream���࣬�ӿڻ�������������
 * @note �����¾���д�����,�ܹ���DataOutputStream�Լ�ByteArrayOutputStream����ϡ����Ҿ�Ȼ���з�����Ҫcatch IOException.\n
 * �����������������ֽ����Ĳ���,����ʵ����������һ��byte����,������������ǿ����Զ���չ��.����������ǹ��Ᵽ���˸�DataOutputStream\n
 * �Լ�DataInputStream�Ľӿ�һ��.�������еĽӿڶ������׳�����IOException
 * @author Jeffrey
 */
public class Stream {
	
	protected static final int DEFAULT_CAPACITY = 10;
	
	protected byte[] buf;
	
	protected int readerIndex;
	
	protected int writerIndex;
	
	public Stream(){
		this(DEFAULT_CAPACITY);
	}
	
	public Stream(int capacity) {
		this.buf = new byte[capacity];
	}
	
	public Stream(byte[] b) {
		this.buf = b;
		this.writerIndex = this.buf.length;
	}
	
	protected void setReaderIndex(int readerIndex) {
		if (readerIndex < 0 || readerIndex > writerIndex) 
			throw new IndexOutOfBoundsException();
		this.readerIndex = readerIndex;
	}
	
	protected void checkReadable(int count) {
		if(writerIndex - readerIndex < count) 
			throw new IndexOutOfBoundsException();
	}
	
	protected void expand(int count) {
		if(count < 0)
			throw new IllegalArgumentException();
		int capacity = writerIndex + count;
		if(buf.length < capacity) {
			capacity = Integer.highestOneBit(capacity);
			capacity <<= 1;
			if(capacity < 0) {
				capacity = Integer.MAX_VALUE;
			}
			byte[] newBuf = new byte[capacity];
			System.arraycopy(buf, 0, newBuf, 0, buf.length);
			buf = newBuf;
		}
	}
	
	
	public void write(int b) {
		expand(1);
		this.buf[this.writerIndex++] = (byte)b;
	}
	
	
	public void write(byte[] b, int off, int len) {
		expand(len);
		System.arraycopy(b, off, this.buf, writerIndex, len);
		this.writerIndex += len;
	}
	
	
	public void write(byte[] b) {
		write(b, 0 , b.length);
	}
	
	public void writeBoolean(boolean v) {
		write(v ? 1 : 0);
	}
	
	public void writeByte(int v) {
		write(v);
	}

	public void writeChar(int v) {
		expand(2);
		this.buf[this.writerIndex++] = (byte)((v >>> 8) & 0xFF);
		this.buf[this.writerIndex++] = (byte)((v >>> 0) & 0xFF);
	}
	
	public void writeShort(int v) {
		expand(2);
		this.buf[this.writerIndex++] = (byte)((v >>> 8) & 0xFF);
		this.buf[this.writerIndex++] = (byte)((v >>> 0) & 0xFF);
	}
	public void writeInt(int v) {
		expand(4);
		this.buf[this.writerIndex++] = (byte)((v >>> 24) & 0xFF);
		this.buf[this.writerIndex++] = (byte)((v >>> 16) & 0xFF);
		this.buf[this.writerIndex++] = (byte)((v >>> 8) & 0xFF);
		this.buf[this.writerIndex++] = (byte)((v >>> 0) & 0xFF);
	}
	
	public void writeLong(long v) {
		expand(8);
		this.buf[this.writerIndex++] = (byte)((v >>> 56) & 0xFF);
		this.buf[this.writerIndex++] = (byte)((v >>> 48) & 0xFF);
		this.buf[this.writerIndex++] = (byte)((v >>> 40) & 0xFF);
		this.buf[this.writerIndex++] = (byte)((v >>> 32) & 0xFF);
		this.buf[this.writerIndex++] = (byte)((v >>> 24) & 0xFF);
		this.buf[this.writerIndex++] = (byte)((v >>> 16) & 0xFF);
		this.buf[this.writerIndex++] = (byte)((v >>> 8) & 0xFF);
		this.buf[this.writerIndex++] = (byte)((v >>> 0) & 0xFF);
	}
	
	public void writeFloat(float v) {
		writeInt(Float.floatToIntBits(v));
	}
	
	public void writeDouble(double v) {
		writeLong(Double.doubleToLongBits(v));
	}
	
	public void writeUTF(String str) {
        int strlen = str.length();
    	int utflen = 0;
    	int c, count = 0;
     
    	for (int i = 0; i < strlen; i++) {
                c = str.charAt(i);
    	    if ((c >= 0x0001) && (c <= 0x007F)) {
    		utflen++;
    	    } else if (c > 0x07FF) {
    		utflen += 3;
    	    } else {
    		utflen += 2;
    	    }
    	}

    	if (utflen > 65535)
    		throw new IllegalArgumentException();

		byte[] bytearr = new byte[utflen + 2];
         
    	bytearr[count++] = (byte) ((utflen >>> 8) & 0xFF);
    	bytearr[count++] = (byte) ((utflen >>> 0) & 0xFF);  
            
		int i = 0;
		for (i = 0; i < strlen; i++) {
			c = str.charAt(i);
			if (!((c >= 0x0001) && (c <= 0x007F)))
				break;
			bytearr[count++] = (byte) c;
		}
    	
		for (; i < strlen; i++) {
			c = str.charAt(i);
			if ((c >= 0x0001) && (c <= 0x007F)) {
				bytearr[count++] = (byte) c;

			} else if (c > 0x07FF) {
				bytearr[count++] = (byte) (0xE0 | ((c >> 12) & 0x0F));
				bytearr[count++] = (byte) (0x80 | ((c >> 6) & 0x3F));
				bytearr[count++] = (byte) (0x80 | ((c >> 0) & 0x3F));
			} else {
				bytearr[count++] = (byte) (0xC0 | ((c >> 6) & 0x1F));
				bytearr[count++] = (byte) (0x80 | ((c >> 0) & 0x3F));
			}
		}
		write(bytearr, 0, utflen + 2);
	}
	
	public void read(byte[] b) {
		checkReadable(b.length);
		System.arraycopy(this.buf, readerIndex, b, 0, b.length);
		this.readerIndex += b.length;
	}
	
	public void read(byte[] b, int off, int len) {
		checkReadable(len);
		System.arraycopy(this.buf, readerIndex, b, off, len);
		this.readerIndex += len;
	}
	
	public void skilBytes(int n) {
		checkReadable(n);
		this.readerIndex += n;
	}
	
	public boolean readBoolean() {
		checkReadable(1);
		return this.buf[this.readerIndex++] == 0 ? false : true;
	}
	
	public byte readByte() {
		checkReadable(1);
		return this.buf[this.readerIndex++];
	}
	
	public char readChar() {
		checkReadable(2);
		return (char)(((this.buf[this.readerIndex++] & 0xFF << 8)) + (this.buf[this.readerIndex++] & 0xFF));
		
	}
	
	
	public int readUnsignedByte() {
		checkReadable(1);
		return this.buf[this.readerIndex++] & 0xFF;
	}
	
	public short readShort() {
		checkReadable(2);
		return (short)(((this.buf[this.readerIndex++]  & 0xFF) << 8) + (this.buf[this.readerIndex++] & 0xFF));
	}
	
	public int readUnsignedShort() {
		checkReadable(2);
		return (((this.buf[this.readerIndex++]  & 0xFF) << 8) + (this.buf[this.readerIndex++] & 0xFF));
	}
	public int readInt() {
		checkReadable(4);
		return ((this.buf[this.readerIndex++] & 0xFF) << 24) + ((this.buf[this.readerIndex++] & 0xFF) << 16) + ((this.buf[this.readerIndex++] & 0xFF) << 8) + ((this.buf[this.readerIndex++] & 0xFF) << 0);
	}
	
	public long readUnsignedInt() {
		return readInt() & 0xFFFFFFFFL;
	}

	public long readLong() {
		checkReadable(8);
		return (long)((this.buf[this.readerIndex++] & 0xFFL) << 56)
				+ (long)((this.buf[this.readerIndex++] & 0xFFL) << 48)
				+ (long)((this.buf[this.readerIndex++] & 0xFFL) << 40)
				+ (long)((this.buf[this.readerIndex++] & 0xFFL) << 32)
				+ (long)((this.buf[this.readerIndex++] & 0xFFL) << 24)
				+ (long)((this.buf[this.readerIndex++] & 0xFFL) << 16)
				+ (long)((this.buf[this.readerIndex++] & 0xFFL) << 8)
				+ (long)((this.buf[this.readerIndex++] & 0xFFL) << 0);
	}
	
	public float readFloat() {
		return Float.intBitsToFloat(readInt());
	}
	
	public double readDouble() {
		return Double.longBitsToDouble(readLong());
	}
	
	public String readUTF() {
		int utflen = readUnsignedShort();
		byte[] bytearr = new byte[utflen];
		char[] chararr = new char[utflen];
		int c, char2, char3;
		int count = 0;
		int chararr_count = 0;

		read(bytearr);

		while (count < utflen) {
			c = (int) bytearr[count] & 0xff;
			if (c > 127)
				break;
			count++;
			chararr[chararr_count++] = (char) c;
		}

		while (count < utflen) {
			c = (int) bytearr[count] & 0xff;
			switch (c >> 4) {
			case 0:
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
				count++;
				chararr[chararr_count++] = (char) c;
				break;
			case 12:
			case 13:
				count += 2;
				if (count > utflen)
					throw new RuntimeException();
				char2 = (int) bytearr[count - 1];
				if ((char2 & 0xC0) != 0x80)
					throw new RuntimeException();
				chararr[chararr_count++] = (char) (((c & 0x1F) << 6) | (char2 & 0x3F));
				break;
			case 14:
				count += 3;
				if (count > utflen)
					throw new RuntimeException();
				char2 = (int) bytearr[count - 2];
				char3 = (int) bytearr[count - 1];
				if (((char2 & 0xC0) != 0x80) || ((char3 & 0xC0) != 0x80))
					throw new RuntimeException();
				chararr[chararr_count++] = (char) (((c & 0x0F) << 12)
						| ((char2 & 0x3F) << 6) | ((char3 & 0x3F) << 0));
				break;
			default:
				throw new RuntimeException();
			}
		}
		return new String(chararr, 0, chararr_count);
	}
	
	public int size() {
		return writerIndex;
	}
	
	public byte[] toArray() {
		return Arrays.copyOf(this.buf, writerIndex);
	}
}

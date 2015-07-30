package com.fanxing.server.persist.impl;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import org.hibernate.EntityMode;
import org.hibernate.persister.entity.EntityPersister;
import org.hibernate.type.ComponentType;
import org.hibernate.type.CustomType;
import org.hibernate.type.Type;

import com.fanxing.server.persist.BlobUserType;

public class PropertyPersister implements SleepyCatPersister{
	
	protected static final int INT = 105; //ascii 'i'
	
	protected static final int SHORT = 115; //ascii 's'
	
	protected static final int BYTE = 98; //ascii 'b'
	
	protected static final int STRING = 83; //ascii 'S'
	
	protected static final int DATE = 68; //ascii 'D'
	
	protected static final int LONG =  108; //ascii 'l'
	
	protected static final int BOOLEAN = 116; //ascii 't'
	
	protected static final int COMPONENT = 67; //ascii 'c'
	
	protected static final int CUSTOM = 85; //ascii 'U'

	@Override
	public byte[] getData(Object value, EntityPersister ep) {
		String[] names = ep.getPropertyNames();
		Object[] values = ep.getPropertyValues(value, EntityMode.POJO);
		Type[] types = ep.getPropertyTypes();
		Stream stream = new Stream();
		stream.write(1);
		stream.writeUTF(ep.getEntityName());
		writePropertyValue(stream, ep.getIdentifierType(), ep.getIdentifier(value, EntityMode.POJO));
		stream.writeShort(names.length);
		for(int i = 0; i < names.length; i++) {
			stream.writeUTF(names[i]);
			writePropertyValue(stream, types[i] , values[i]);
		}
		return stream.toArray();
	}
	
	protected void writePropertyValue(Stream stream, Type type, Object value) {
		if (type.isComponentType()) {
			stream.write(COMPONENT);
			ComponentType ct = (ComponentType) type;
			Type[] types = ct.getSubtypes();
			Object[] values = ct.getPropertyValues(value, EntityMode.POJO);
			stream.writeShort(values.length);
			for (int i = 0; i < types.length; i++) {
				writePropertyValue(stream, types[i], values[i]);
			}
		} else {
			if (type instanceof CustomType) {
				CustomType ct = (CustomType) type;
				BlobUserType ut = (BlobUserType) ct.getComparator();
				stream.write(CUSTOM);
				byte[] bytes = ut.makeBytes(value);
				stream.writeShort(bytes.length);
				stream.write(bytes);
			} else {
				@SuppressWarnings("rawtypes")
				Class retClass = type.getReturnedClass();
				if (retClass == Integer.class) {
					stream.write(INT); // ascii 'i'
					stream.writeInt(((Integer) value).intValue());
				} else if (retClass == Short.class) {
					stream.write(SHORT); // ascii 's'
					stream.writeShort(((Short) value).shortValue());
				} else if (retClass == Byte.class) {
					stream.write(BYTE); // ascii 'b'
					stream.write(((Byte) value).byteValue());
				} else if (retClass == String.class) {
					stream.write(STRING); // ascii 'S'
					if (value == null) {
						stream.writeShort(-1);
					} else {
						try {
							byte[] bytes = ((String) value).getBytes("utf-8");
							stream.writeShort(bytes.length);
							stream.write(bytes);
						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
						}
					}
				} else if (retClass == Date.class) {
					stream.write(DATE); // ascii 'D'
					if (value == null) {
						stream.writeLong(-1);
					} else {
						stream.writeLong(((Date) value).getTime());
					}
				} else if (retClass == Boolean.class) {
					stream.write(BOOLEAN);
					stream.write(((Boolean) value) == Boolean.TRUE ? 1 : 0);
				} else if (retClass == Long.class) {
					stream.write(LONG);
					stream.writeLong(((Long) value).longValue());
				}
			}
		}
	}

	@Override
	public Object getObject(byte[] bytes, EntityPersister ep) throws Exception{
		Stream stream = new Stream(bytes);
		@SuppressWarnings("unused")
		int version = stream.readByte();
		String entityName = stream.readUTF();
		Object o = null;
		try {
			o = Class.forName(entityName).newInstance();
		} catch (InstantiationException e) {
			throw new IllegalArgumentException(e);
		} catch (IllegalAccessException e) {
			throw new IllegalArgumentException(e);
		} catch (ClassNotFoundException e) {
			throw new IllegalArgumentException(e);
		}
		Serializable id = (Serializable) readPropertyValue(stream, ep.getIdentifierType(), o);
		int count = stream.readShort();
		Object[] values = new Object[count];
		for (int i = 0; i < count; i++) {
			String propertyName = stream.readUTF();
			values[i] = readPropertyValue(stream,
					ep.getPropertyType(propertyName), o);
		}

		ep.setIdentifier(o, id, EntityMode.POJO);
		ep.setPropertyValues(o, values, EntityMode.POJO);
		return o;
	}
	
	protected void checkType(int type1, int type2) {
		if (type1 != type2) {
			throw new IllegalArgumentException();
		}
	}
	
	protected Object readPropertyValue(Stream stream, Type type, Object owner) throws Exception{
		@SuppressWarnings("rawtypes")
		Class retClass = type.getReturnedClass();
		int typeSign = stream.readByte() & 0xFF;
		if (typeSign == COMPONENT) {
			try {
				ComponentType ct = (ComponentType)type;
				Object component = retClass.newInstance();
				int count = stream.readShort();
				Object[] values = new Object[count];
				Type[] types = ct.getSubtypes();
				for(int i = 0; i < count; i++) {
					values[i] = readPropertyValue(stream, types[i],owner);
				}
				ct.setPropertyValues(component, values, EntityMode.POJO);
				return component;
			} catch (InstantiationException e) {
				throw new IllegalArgumentException(e);
			} catch (IllegalAccessException e) {
				throw new IllegalArgumentException(e);
			}
		}
		else if(typeSign == CUSTOM) {
			CustomType ct = (CustomType) type;
			BlobUserType ut = (BlobUserType) ct.getComparator();
			int len = stream.readShort();
			byte[] bytes = new byte[len];
			stream.read(bytes);
			return ut.makeObject(bytes, owner);
		} else {
			if (retClass == Integer.class) {
				checkType(typeSign, INT);
				return new Integer(stream.readInt());
			} else if (retClass == Short.class) {
				checkType(typeSign, SHORT);
				return new Short(stream.readShort());
			} else if (retClass == Byte.class) {
				checkType(typeSign, BYTE);
				return new Byte(stream.readByte());
			} else if (retClass == String.class) {
				checkType(typeSign, STRING);
				short c = stream.readShort();
				if (c == -1) {
					return null;
				} else {
					byte[] bytes = new byte[c];
					stream.read(bytes);
					try {
						return new String(bytes, "utf-8");
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
						return null;
					}
				}
			} else if (retClass == Date.class) {
				checkType(typeSign, DATE);
				long t = stream.readLong();
				if (t == -1)
					return null;
				else {
					return new Date(t);
				}
			} else if (retClass == Long.class) {
				checkType(typeSign, LONG);
				return new Long(stream.readLong());
			} else {
				throw new IllegalArgumentException("TypeSign:" + typeSign);
			}
		}
	}
	
}

package persist.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.hibernate.persister.entity.EntityPersister;

public class SerializablePersister implements SleepyCatPersister {

	@Override
	public byte[] getData(Object value, EntityPersister ep) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(value);
			oos.flush();
			return baos.toByteArray();
		} catch (IOException e) {
			return new  byte[0];
		}
	}

	@Override
	public Object getObject(byte[] bytes, EntityPersister ep) {
		try {
			ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
			return ois.readObject();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}

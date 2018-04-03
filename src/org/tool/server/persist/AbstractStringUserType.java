package org.tool.server.persist;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.UserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractStringUserType<T> implements UserType {
	
	protected static final Logger log = LoggerFactory.getLogger(AbstractStringUserType.class);
	
	private static final int[] SQL_TYPES = {Types.JAVA_OBJECT};

	@Override
	public int[] sqlTypes() {
		return SQL_TYPES;
	}

	@Override
	public boolean equals(Object x, Object y) throws HibernateException {
		return x == null ? y == null : x.equals(y);
	}

	@Override
	public int hashCode(Object x) throws HibernateException {
		return x.hashCode();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner)
			throws HibernateException, SQLException {
		String str = rs.getString(names[0]);
		try {
			return str == null ? 
					owner == null ? makeObject() : makeObject((T) owner) : 
						owner == null ? makeObject((String) owner) : makeObject(str, (T) owner);
		} catch (Exception e) {
			throw new HibernateException(e);
		}
	}

	@Override
	public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session)
			throws HibernateException, SQLException {
		try {
			@SuppressWarnings("unchecked")
			String str = value == null ? makeString() : makeString((T) value);
			if (str != null) {
				st.setString(index, str);
			} else {
				st.setNull(index, Types.JAVA_OBJECT);
			}
		} catch (Exception e) {
			throw new HibernateException(e);
		}
	}

	@Override
	public boolean isMutable() {
		return true;
	}

	@Override
	public Serializable disassemble(Object value) throws HibernateException {
		return (Serializable) value;
	}

	@Override
	public Object assemble(Serializable cached, Object owner) throws HibernateException {
		return cached;
	}

	@Override
	public Object replace(Object original, Object target, Object owner) throws HibernateException {
		return original;
	}
	
	protected abstract Object makeObject(String str, T owner) throws Exception;
	
	protected abstract Object makeObject(String str) throws Exception;
	
	protected abstract Object makeObject(T owner) throws Exception;
	
	protected abstract Object makeObject() throws Exception;
	
	protected abstract String makeString(T object) throws Exception;
	
	protected abstract String makeString() throws Exception;

}

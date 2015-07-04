package persist.impl.cache;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.je.Sequence;
import com.sleepycat.je.SequenceConfig;

public class SleepyCat {
	
	protected static Database SEQ_DATABASE = null;
	
	protected static DatabaseConfig DB_CONFIG = null;
	
	protected static SequenceConfig SEQ_CONFIG = null;
	
	protected static Map<String, Sequence> SEQUENCES = new HashMap<String, Sequence>();
	
	protected static Map<String, Database> DATABASES = new HashMap<String, Database>();
	
	protected static Environment ENV = null;
	
	protected static final String SEQUENCE_DB_NAME = "SEQUENCE";
	
	public static void init(File dbRoot) throws Exception{
        EnvironmentConfig envConfig = new EnvironmentConfig();
        envConfig.setTransactional(false);
        envConfig.setAllowCreate(true);
        ENV = new Environment(dbRoot, envConfig);
        DB_CONFIG = new DatabaseConfig();
        DB_CONFIG.setTransactional(false);
        DB_CONFIG.setAllowCreate(true);
        DB_CONFIG.setDeferredWrite(false);
	}
	
	public static void openSequenceDB() throws Exception{
        SEQ_DATABASE = ENV.openDatabase(null, SEQUENCE_DB_NAME, DB_CONFIG);
        SEQ_CONFIG = new SequenceConfig();
        SEQ_CONFIG.setAllowCreate(true);
        SEQ_CONFIG.setInitialValue(1L); // ´Ó1¿ªÊ¼
	}
	
	public static Database openDatabase(String name) throws Exception{
		Database database = DATABASES.get(name);
		if (database == null) {
			database =  ENV.openDatabase(null, name, DB_CONFIG);
			DATABASES.put(name, database);
		}
		return database;
	}
	
	public static void openSequence(String seqName) throws Exception{
        DatabaseEntry key = new DatabaseEntry(seqName.getBytes("UTF-8"));
        SEQUENCES.put(seqName, SEQ_DATABASE.openSequence(null, key, SEQ_CONFIG));
	}
	
	public static int nextSequence(String seqName){
		Sequence seq = SEQUENCES.get(seqName);
		try {
			return (int)seq.get(null, 1);
		} catch (DatabaseException e) {
			throw new RuntimeException(e);
		} 
	}
	
	public static void sync(){
		try {
			ENV.sync();
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
	}
	
	public static void close(){
		try {
			ENV.cleanLog();
			ENV.sync();
		} catch (DatabaseException e1) {
			e1.printStackTrace();
		}
		for(Sequence seq:SEQUENCES.values()){
			try {
				seq.close();
			} catch (DatabaseException e) {
				e.printStackTrace();
			}
		}
		SEQUENCES.clear();
		for(Database db:DATABASES.values()){
			try {
				db.close();
			} catch (DatabaseException e) {
				e.printStackTrace();
			}
		}
		DATABASES.clear();
		if(SEQ_DATABASE != null){
			try {
				SEQ_DATABASE.close();
			} catch (DatabaseException e) {
				e.printStackTrace();
			}finally{
				SEQ_DATABASE = null;
			}
		}
		try {
			ENV.close();
		} catch (DatabaseException e) {
			e.printStackTrace();
		} finally{
			ENV = null;
		}
	}
	
}

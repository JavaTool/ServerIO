package persist;

/**
 * @note 数据持久化管理器,通过此接口来进行数据的持久化工作(包括存储数据到数据库，从数据库取出数据，删除数据等等)
 * @author 	Jeffrey
 */
public interface EntityManagerEx extends EntityManager {
	
	/**
	 * @note 异步更新对象。调用此方法后，对象被更新到本地，如果需要将对象更新到远程数据库，还需要调用sync方法
	 * @param entity 需要被更新的对象
	 */
	void update(Object entity);
	/**
	 * @note 将本地库中的数据跟远程的数据库进行同步，每同步一条，将会从本地库中删除一条
	 */
	void sync();
	
	@SuppressWarnings("rawtypes")
	void clearLocal(Class klass);
	
}

package persist;

/**
 * @note ���ݳ־û�������,ͨ���˽ӿ����������ݵĳ־û�����(�����洢���ݵ����ݿ⣬�����ݿ�ȡ�����ݣ�ɾ�����ݵȵ�)
 * @author 	Jeffrey
 */
public interface EntityManagerEx extends EntityManager {
	
	/**
	 * @note �첽���¶��󡣵��ô˷����󣬶��󱻸��µ����أ������Ҫ��������µ�Զ�����ݿ⣬����Ҫ����sync����
	 * @param entity ��Ҫ�����µĶ���
	 */
	void update(Object entity);
	/**
	 * @note �����ؿ��е����ݸ�Զ�̵����ݿ����ͬ����ÿͬ��һ��������ӱ��ؿ���ɾ��һ��
	 */
	void sync();
	
	@SuppressWarnings("rawtypes")
	void clearLocal(Class klass);
	
}

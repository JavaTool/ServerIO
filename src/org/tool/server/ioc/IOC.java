package org.tool.server.ioc;

import static com.google.common.base.Preconditions.checkArgument;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tool.server.utils.ClassUtil;

import com.google.common.collect.ClassToInstanceMap;
import com.google.common.collect.ImmutableClassToInstanceMap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.MutableClassToInstanceMap;

public class IOC {
	
	protected static final Logger log = LoggerFactory.getLogger(IOC.class);
	
	protected final ClassToInstanceMap<Object> beans;
	
	public IOC() {
		beans = MutableClassToInstanceMap.create();
	}
	
	public void load(String pkg, ClassToInstanceMap<Object> objects) throws Exception {
		load(pkg, null, objects);
	}
	
	public void load(String pkg, String type, ClassToInstanceMap<Object> objects) throws Exception {
		load(pkg, IOCBean.class, type, objects);
	}
	
	public void load(String pkg, Class<? extends IOCBean> annotation, String type, ClassToInstanceMap<Object> objects) throws Exception {
		List<Class<?>> list = ClassUtil.getClassList(pkg, true, annotation);
		if (type != null && type.length() > 0) {
			ImmutableList.copyOf(list).forEach(v -> {
				if (!v.getAnnotation(annotation).type().equals(type)) {
					list.remove(v);
				}
			});
		}
		load(list, annotation, objects);
	}
	
	private void load(List<Class<?>> list, Class<? extends IOCBean> annotation, ClassToInstanceMap<Object> objects) throws Exception {
		loadBean(list, annotation);
		if (objects != null) {
			loadFields(list, objects);
		}
		loadMethod(list);
	}
	
	private void loadBean(List<Class<?>> list, Class<? extends IOCBean> annotation) throws Exception {
		for (Class<?> clz : list) {
			Class<?> key = clz.getAnnotation(annotation).registerClass();
			key = key.equals(Class.class) ? clz : key;
			checkArgument(!beans.containsKey(key), "Repeat IOCBean : " + key);
			Constructor<?> constructor = clz.getDeclaredConstructor();
			constructor.setAccessible(true);
			beans.put(key, constructor.newInstance());
			log.info("Create ioc bean : {}.", clz.getName());
		}
	}
	
	private void loadFields(List<Class<?>> list, ClassToInstanceMap<Object> objects) throws Exception {
		for (Class<?> clz : list) {
			Object bean = beans.get(clz.getAnnotation(IOCBean.class).registerClass());
			for (Field field : ClassUtil.getDeclaredFields(clz, Lists.newLinkedList())) {
				if (field.isAnnotationPresent(IOCField.class)) {
					Class<?> type = field.getType();
					field.setAccessible(true);
					field.set(bean, objects.containsKey(type) ? objects.get(type) : beans.get(type));
				}
			}
		}
	}
	
	private void loadMethod(List<Class<?>> list) throws Exception {
		invoke((level, clz, bean) -> {
			for (Method method : getDeclaredMethods(bean.getClass(), Lists.newLinkedList())) {
				if (method.isAnnotationPresent(IOCMethod.class)) {
					method.setAccessible(true);
					method.invoke(bean);
				}
			}
		});
	}
	
	public void invoke(LevelLoader levelLoader) throws Exception {
		List<Integer> levels = Lists.newArrayList(beans.size());
		Multimap<Integer, Class<?>> levelSort = LinkedListMultimap.create();
		for (Class<?> clz : beans.keySet()) {
			int level = beans.get(clz).getClass().getAnnotation(IOCBean.class).level();
			if (!levels.contains(level)) {
				levels.add(level);
			}
			levelSort.put(level, clz);
		}
		Collections.sort(levels);
		for (Integer level : levels) {
			for (Class<?> clz : levelSort.get(level)) {
				Object bean = beans.get(clz);
				levelLoader.load(level, clz, bean);
			}
		}
	}
	
	public static interface LevelLoader {
		
		void load(int level, Class<?> clz, Object bean) throws Exception;
		
	}
	
	private List<Method> getDeclaredMethods(Class<?> clz, List<Method> methods) {
		if (clz != null && !clz.equals(Object.class)) {
			methods.addAll(Arrays.asList(clz.getDeclaredMethods()));
			for (Class<?> in : clz.getInterfaces()) {
				getDeclaredMethods(in, methods);
			}
			return getDeclaredMethods(clz.getSuperclass(), methods);
		} else {
			return methods;
		}
	}
	
	/**
	 * 获取Bean
	 * @param 	clz
	 * 			服务类型
	 * @return	服务
	 */
	@SuppressWarnings("unchecked")
	public <X, Y extends X> Y getBean(Class<X> clz) {
		return (Y) beans.get(clz);
	}
	
	public ClassToInstanceMap<Object> getAll() {
		return ImmutableClassToInstanceMap.copyOf(beans);
	}

}

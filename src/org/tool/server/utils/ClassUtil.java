package org.tool.server.utils;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Lists.newLinkedList;
import static java.lang.Class.forName;
import static java.lang.Thread.currentThread;
import static java.lang.reflect.Modifier.isFinal;
import static java.lang.reflect.Modifier.isStatic;
import static java.lang.reflect.Modifier.isTransient;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public final class ClassUtil {
	
	private ClassUtil() {}
	
	public static List<Field> getDeclaredFields(Class<?> clz, List<Field> fields) {
		if (clz != null && !clz.equals(Object.class)) {
			fields.addAll(Arrays.asList(clz.getDeclaredFields()));
			return getDeclaredFields(clz.getSuperclass(), fields);
		} else {
			return fields;
		}
	}
	
	public static List<Field> getNormalDeclaredFields(Class<?> clz, List<Field> fields) {
		if (clz != null && !clz.equals(Object.class)) {
			int oldSize = fields.size();
			fields.addAll(Arrays.asList(clz.getDeclaredFields()));
			for (int i = fields.size() - 1;i >= oldSize;i--) {
				Field f = fields.get(i);
				int modifiers = f.getModifiers();
				if (isStatic(modifiers) || isFinal(modifiers) || isTransient(modifiers)) {
					fields.remove(i);
				}
			}
			return getNormalDeclaredFields(clz.getSuperclass(), fields);
		} else {
			return fields;
		}
	}
	
	public static List<Class<?>> getClassList(String pkgName , boolean isRecursive) throws Exception {
		return getClassList(pkgName, isRecursive, null);
	}
	
	public static List<Class<?>> getClassList(String pkgName , boolean isRecursive, Class<? extends Annotation> annotation) throws Exception {
        List<Class<?>> classList = newLinkedList();
        ClassLoader loader = currentThread().getContextClassLoader();
        // 按文件的形式去查找
        String strFile = pkgName.replaceAll("\\.", "/");
        Enumeration<URL> urls = loader.getResources(strFile);
        while (urls.hasMoreElements()) {
            URL url = urls.nextElement();
            if (url != null) {
                String protocol = url.getProtocol();
                String pkgPath = url.getPath();
                if ("file".equals(protocol)) { // 本地自己可见的代码
                    findClassName(classList, pkgName, pkgPath, isRecursive, annotation);
                } else if ("jar".equals(protocol)) { // 引用第三方jar的代码
                    findClassName(classList, pkgName, url, isRecursive, annotation);
                }
            }
        }
        return classList;  
    }  
      
    public static void findClassName(List<Class<?>> clazzList, String pkgName, String pkgPath, boolean isRecursive, Class<? extends Annotation> annotation) throws Exception {
        checkNotNull(clazzList, "findClassName but clazzList is null.");
        File[] files = filterClassFiles(pkgPath);// 过滤出.class文件及文件夹
        if (files != null) {
            for (File f : files) {
                String fileName = f.getName();
                if (f.isFile()) { // .class 文件的情况
                    String clazzName = getClassName(pkgName, fileName);
                    addClassName(clazzList, clazzName, annotation);
                } else { // 文件夹的情况
                    if (isRecursive) { // 需要继续查找该文件夹/包名下的类
                        String subPkgName = pkgName +"."+ fileName;
                        String subPkgPath = pkgPath +"/"+ fileName;
                        findClassName(clazzList, subPkgName, subPkgPath, true, annotation);
                    }
                }
            }
        }
    }
      
    /** 
     * 第三方Jar类库的引用。<br/> 
     * @throws IOException  
     * */  
    public static void findClassName(List<Class<?>> clazzList, String pkgName, URL url, boolean isRecursive, Class<? extends Annotation> annotation) throws Exception {
        JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
        JarFile jarFile = jarURLConnection.getJarFile();
        Enumeration<JarEntry> jarEntries = jarFile.entries();
        while (jarEntries.hasMoreElements()) {
            JarEntry jarEntry = jarEntries.nextElement();
            String jarEntryName = jarEntry.getName(); // 类似：sun/security/internal/interfaces/TlsMasterSecret.class
            String clazzName = jarEntryName.replace("/", ".");
            int endIndex = clazzName.lastIndexOf(".");
            String prefix = null;
            String prefix_name = null;
            if (endIndex > 0) {
                prefix_name = clazzName.substring(0, endIndex);
                endIndex = prefix_name.lastIndexOf(".");
                if (endIndex > 0) {
                    prefix = prefix_name.substring(0, endIndex);
                }
            }  
            if (prefix != null && jarEntryName.endsWith(".class")) {
                if (prefix.equals(pkgName)) {
                    addClassName(clazzList, clazzName, annotation);  
                } else if (isRecursive && prefix.startsWith(pkgName)) { // 遍历子包名：子类  
                    addClassName(clazzList, prefix_name, annotation);  
                }  
            }  
        }  
    }  
      
    private static File[] filterClassFiles(String pkgPath) {
        checkNotNull(pkgPath, "filterClassFiles but pkgPath is null.");
        // 接收 .class 文件 或 类文件夹  
        return new File(pkgPath).listFiles(file -> {
            return (file.isFile() && file.getName().endsWith(".class")) || file.isDirectory();
        });  
    }  
      
    private static String getClassName(String pkgName, String fileName) {
        int endIndex = fileName.lastIndexOf(".");
        String clazz = endIndex >= 0 ? fileName.substring(0, endIndex) : null;
        String clazzName = clazz == null ? null : pkgName + "." + clazz;
        return clazzName;
    }
      
    private static void addClassName(List<Class<?>> clazzList, String clazzName, Class<? extends Annotation> annotation) throws Exception {
        if (clazzList != null && clazzName != null && !clazzName.contains("$")) {
            Class<?> clazz = forName(clazzName);
            if (clazz != null && (annotation == null || clazz.isAnnotationPresent(annotation))) {
                clazzList.add(clazz);
            }
        }
    }
    
    public static void main(String[] args) {
        String pkg = "com.fanxing.server"; // 指定的包名
        try {
        	List<Class<?>> list = getClassList(pkg, true, null);
	        for (int i = 0;i < list.size(); i ++) {
	            System.out.println(i + " : "+ list.get(i));
	        }
        } catch (Exception e) {
        	e.printStackTrace();
        }
    }

}

package org.tool.server.utils;

import java.util.List;
import java.util.Random;
import java.util.Set;

import org.tool.server.collection.list.IRangeList;
import org.tool.server.collection.list.RangeLists;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.common.primitives.Ints;

public class RandomUtil {
	
	private static Random random = new Random();

	/**
	 * 按比例随机
	 * 
	 * @param rates
	 * @return
	 */
	public static int getRatioRandom(List<Integer> rates) {
		int total = 0;
		for (int rate : rates) {
			total += rate;
		}
		if (total <= 0) {
			return -1;
		}
		int n = random.nextInt(total);

		total = -1;
		int index = -1;
		for (int rate : rates) {
			total += rate;
			index++;
			if (n <= total) {
				return index;
			}
		}
		return index;
	}

	/**
	 * 顺序随机(随机一个N以内的数) List<Integer> rates={1,10，100}则N为100，第几次随机出来的值满足条件则返回当前下标
	 * 
	 * @param rates
	 * @return
	 */
	public static int getOrderRandom(List<Integer> rates, int n) {
		int index = -1;

		int value = random.nextInt(n);
		for (int rate : rates) {
			index++;
			if (value <= rate) {
				return index;
			}
		}
		return index;
	}

	/**
	 * 顺序随机
	 * 
	 * @param rates
	 * @return
	 */
	public static int getOrderRandom(List<Integer> rates, int n, Random random) {
		int index = -1;

		int value = random.nextInt(n);
		for (int rate : rates) {
			index++;
			if (value <= rate) {
				return index;
			}
		}
		return index;
	}

	/**
	 * 成功几率随机
	 * 
	 * @param rates
	 * @return
	 */
	public static int getSuccessRandom(int ratios, int n) {
		int index = 0;
		int value = random.nextInt(n);
		if (value <= ratios) {
			index = 1;
		}
		return index;
	}

	/**
	 * 成功几率随机(带随机数种子)
	 * 
	 * @param rates
	 * @return
	 */
	public static int getSuccessRandom(Random r, int ratios, int n) {
		int index = 0;
		int value = r.nextInt(n);
		if (value <= ratios) {
			index = 1;
		}
		return index;
	}

	/**
	 * 区间随机 [start,end) 取count个
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public static Set<Integer> regionRandom(int start, int end, int count, int max) {
		if (start < 0 || end < 0 || start >= end) {
			return null;
		}

		Set<Integer> randomNum = Sets.newHashSet();
		int dif = end - start;
		if (dif <= count) {
			// 如果总的数量 取的数量小于
			for (int i = start; i < end; i++) {
				randomNum.add(i);
			}
			return randomNum;
		}
		int value = count * 3;
		if (dif <= value) {
			List<Integer> list = Lists.newArrayListWithCapacity(value);
			for (int i = start; i < end; i++) {
				list.add(i);
			}
			list = norepeatRrandomByList(list, count);
			randomNum.addAll(list);
			return randomNum;
		}

		for (int i = 0; i < max; i++) {
			int r = random.nextInt(dif) + start;
			if (randomNum.contains(r)) {
				continue;
			}
			randomNum.add(r);
			if (randomNum.size() >= count) {
				break;
			}
		}
		return randomNum;
	}

	/**
	 * 区间随机 [start,end) 取1个(带随机数种子)
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public static int regionRandom(int start, int end, Random r) {
		if (start < 0 || end < 0 || start >= end) {
			return 0;
		}
		int dif = end - start;
		int rand = r.nextInt(dif) + start;
		return rand;
	}

	/**
	 * 区间随机 [start,end) 取1个(带随机数种子)
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public static int regionRandom(int start, int end) {
		if (start < 0 || end < 0 || start >= end) {
			return 0;
		}
		int dif = end - start;
		int rand = random.nextInt(dif) + start;
		return rand;
	}

	public static int generalRrandom(int value) {
		return random.nextInt(value);
	}

	public static int generalRrandom() {
		return random.nextInt();
	}

	public static List<Integer> norepeatRandomByRandomNum(int randomNum, int n) {
		return norepeatRandomByRandomNum(0, randomNum, n);
	}

	public static List<Integer> norepeatRandomByRandomNum(int min, int randomNum, int n) {
		List<Integer> clist = Lists.newArrayListWithCapacity(randomNum);
		for (int i = 0; i < randomNum; i++) {
			clist.add(min + i);
		}
		return norepeatRrandomByList(clist, n);
	}

	/**
	 * 按比例probabilityList随机n个list的内容
	 * 
	 * @param value
	 * @return
	 */
	public static <E> List<E> norepeatRrandomByList(List<E> list, List<Integer> probabilityList, int n) {
		if (list == null || list.isEmpty() || probabilityList == null || probabilityList.isEmpty() || probabilityList.size() != list.size() || n <= 0 || n > list.size()) {
			return Lists.newLinkedList();
		}

		IRangeList<E> rangeList = RangeLists.createRangeList();
		for (int i = 0, count = probabilityList.size();i < count;i++) {
			rangeList.add(probabilityList.get(i), list.get(i));
		}

		List<E> chooseList = Lists.newLinkedList();
		for (int i = 0;i < n;i++) {
			int randomNum = random.nextInt(rangeList.getMax());
			chooseList.add(rangeList.get(randomNum));
		}
		return chooseList;
	}

	public static void main(String args[]) {
		// List<Integer> list = new ArrayList<Integer>();
		// list.add(3);
		// list.add(3);
		// list.add(3);
		// list.add(1);
		// System.out.println(getRatioRandom(list));
//		Random random = new Random(100);
//		for (int i = 0; i < 20; i++) {
//			System.out.print(random.nextInt(10));
//			System.out.print(",");
//		}
		System.out.println("============ norepeatRrandomByList(List, List, n) ============");
		for (Integer value : norepeatRrandomByList(Ints.asList(new int[]{2, 1, 3, 9, 4, 6, 7, 5}), Ints.asList(new int[]{2, 1, 3, 9, 4, 6, 7, 5}), 3)) {
			System.out.println(value);
		}
		System.out.println("============ norepeatRrandomByList(List, n) ============");
		for (Integer value : norepeatRrandomByList(Ints.asList(new int[]{2, 1, 3, 9, 4, 6, 7, 5}), 3)) {
			System.out.println(value);
		}
	}

	/**
	 * 不重复随机，取n个值
	 * 
	 * @param value
	 * @return
	 */
	public static <E> List<E> norepeatRrandomByList(List<E> list, int n) {
		// 无效参数
		if (list == null || list.isEmpty() || n <= 0 || n > list.size()) {
			return Lists.newLinkedList();
		}
		// 创建一个索引集合，包含参数list的索引
		List<Integer> clist = Lists.newLinkedList();
		for (int i = 0, count = list.size();i < count;i++) {
			clist.add(i);
		}
		// 从索引集合中随机移除n个索引，每移除一个索引保存该索引的元素
		List<E> chooseList = Lists.newArrayListWithCapacity(n);
		for (int i = 0; i < n; i++) {
			int index = random.nextInt(clist.size());
			chooseList.add(list.get(clist.remove(index)));
		}
		return chooseList;
	}

}

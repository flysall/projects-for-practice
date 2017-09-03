package loop;

import java.util.*;

public class LinkQueue {
	private static HashSet<Object> visited = new HashSet<>();
	private static Queue<Object> unVisited = new Queue<>();

	public static Queue<Object> getUnvisited() {
		return unVisited;
	}

	/**
	 * 向已访问的集合中添加元素
	 * 
	 * @param obj
	 */
	public static void addVisited(Object obj) {
		visited.add(obj);
	}

	/**
	 * 从已访问的集合visited中移除已访问的obj
	 * 
	 * @param obj
	 */
	public static void removeVisited(Object obj) {
		visited.remove(obj);
	}

	/**
	 * 保证每个obj只被访问一次
	 * 
	 * @param
	 */
	public static void addUnvisited(Object obj) {
		if (obj != null && !visited.contains(obj) && !unVisited.contains(obj)) {
			unVisited.offer(obj);
		}
	}

	/**
	 * 出队
	 */
	public static Object removeUnvisited() {
		return unVisited.remove();
	}

	/**
	 * 返回已访问网址的数量
	 * 
	 * @return visited为集合长度
	 */
	public static int getVisitedSize() {
		return visited.size();
	}

	/**
	 * 判断未访问集合是否为空
	 * 
	 * @return 为空返回true
	 */
	public static boolean isUnVisitedEmpty() {
		return unVisited.isEmpty();
	}

}

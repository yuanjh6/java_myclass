package com.yuan.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;


public class DTree {

	// 根节点

	TreeNode root;

	// 可见性数组

	private boolean[] visable;

	// 未找到节点

	private static final int NO_FOUND = -1;

	// 训练集

	private Object[] trainingArray;

	// 节点索引

	private int nodeIndex;

	public static void main(String[] args) {
		Object[] array = new Object[] {
				new String[] { "大于1.2", "是", "否", "是" },
				new String[] { "小于0.8", "否", "是", "是" },
				new String[] {  "大于1.2", "是", "是", "是" },
				new String[] { "0.8-1.2", "否", "否", "是" },
				new String[] {  "0.8-1.2", "否", "否", "否" },
				new String[] {  "大于1.2", "是", "否", "是" },
				new String[] { "小于0.8", "否", "是", "是" },
				new String[] {  "大于1.2", "否", "否", "否" },
				new String[] {"0.8-1.2", "是", "是", "是" },
				new String[] {  "0.8-1.2", "是", "是", "是" },
				new String[] {"0.8-1.2", "否", "是", "是" },
				new String[] {  "0.8-1.2", "是", "否", "否" },
				new String[] {  "小于0.8", "否", "是", "否" },
				new String[] { "小于0.8", "否", "否", "否" } };

		DTree tree = new DTree();
		tree.create(array, 3);

		System.out.println("===============END PRINT TREE===============");
		tree.compare(new String[] {"0.8-1.2", "否", "是", "是" },tree.root);

	}
	
	//根据传入数据进行预测
		public void compare(String[] printData, TreeNode node)
		{ 
			int index = getNodeIndex(node.nodeName);
			if (index == NO_FOUND)
			{
				System.out.println(node.nodeName);
			}
			TreeNode[] childs = node.childNodes;
			for (int i = 0; i <childs.length; i++)
			{
				if (childs[i] != null)
				{
					if (childs[i].parentArrtibute.equals(printData[index]))
					{
						this.compare(printData,childs[i]);
					}
				}
			}
		}

	// 创建

	public void create(Object[] array, int index) {
		this.trainingArray = array;
		init(array, index);
		createDTree(array);
		printDTree(root);
	}

	// 得到最大信息增益

	public Object[] getMaxGain(Object[] array) {
		Object[] result = new Object[2];
		double gain = 0;
		int index = -1;

		for (int i = 0; i < visable.length; i++) {
			if (!visable[i]) {
				double value = gain(array, i);
				if (gain < value) {
					gain = value;
					index = i;
				}
			}
		}
		result[0] = gain;
		result[1] = index;
		if (index != -1) {
			visable[index] = true;
		}
		return result;
	}

	// 创建决策树

	public void createDTree(Object[] array) {
		Object[] maxgain = getMaxGain(array);
		if (root == null) {
			root = new TreeNode();
			root.parent = null;
			root.parentArrtibute = null;
			root.arrtibutes = getArrtibutes(((Integer) maxgain[1]).intValue());
			root.nodeName = getNodeName(((Integer) maxgain[1]).intValue());
			root.childNodes = new TreeNode[root.arrtibutes.length];
			insertTree(array, root);
		}
	}

	// 插入到决策树

	public void insertTree(Object[] array, TreeNode parentNode) {
		String[] arrtibutes = parentNode.arrtibutes;
		for (int i = 0; i < arrtibutes.length; i++) {
			Object[] pickArray = pickUpAndCreateArray(array, arrtibutes[i],
					getNodeIndex(parentNode.nodeName));
			Object[] info = getMaxGain(pickArray);
			double gain = ((Double) info[0]).doubleValue();
			if (gain != 0) {
				int index = ((Integer) info[1]).intValue();
				TreeNode currentNode = new TreeNode();
				currentNode.parent = parentNode;
				currentNode.parentArrtibute = arrtibutes[i];
				currentNode.arrtibutes = getArrtibutes(index);
				currentNode.nodeName = getNodeName(index);
				currentNode.childNodes = new TreeNode[currentNode.arrtibutes.length];
				parentNode.childNodes[i] = currentNode;
				insertTree(pickArray, currentNode);
			} else {
				TreeNode leafNode = new TreeNode();
				leafNode.parent = parentNode;
				leafNode.parentArrtibute = arrtibutes[i];
				leafNode.arrtibutes = new String[0];
				leafNode.nodeName = getLeafNodeName(pickArray);
				leafNode.childNodes = new TreeNode[0];
				parentNode.childNodes[i] = leafNode;

			}
		}
	}

	// 打印决策树

	public void printDTree(TreeNode node) {
		System.out.println(node.nodeName);
		TreeNode[] childs = node.childNodes;
		for (int i = 0; i < childs.length; i++) {
			if (childs[i] != null) {
				System.out.println(childs[i].parentArrtibute);
				printDTree(childs[i]);
			}
		}
	}

	// 初始化

	public void init(Object[] dataArray, int index) {
		this.nodeIndex = index;
		// 数据初始化
		visable = new boolean[((String[]) dataArray[0]).length];
		for (int i = 0; i < visable.length; i++) {
			if (i == index) {
				visable[i] = true;
			} else {
				visable[i] = false;
			}
		}
	}

	// 剪取数组

	public Object[] pickUpAndCreateArray(Object[] array, String arrtibute,
			int index) {
		List<String[]> list = new ArrayList<String[]>();
		for (int i = 0; i < array.length; i++) {
			String[] strs = (String[]) array[i];
			if (strs[index].equals(arrtibute)) {
				list.add(strs);
			}
		}
		return list.toArray();
	}

	// Entropy(S)

	public double gain(Object[] array, int index) {
		String[] playBalls = getArrtibutes(this.nodeIndex);
		int[] counts = new int[playBalls.length];
		for (int i = 0; i < counts.length; i++) {
			counts[i] = 0;
		}
		for (int i = 0; i < array.length; i++) {
			String[] strs = (String[]) array[i];
			for (int j = 0; j < playBalls.length; j++) {
				if (strs[this.nodeIndex].equals(playBalls[j])) {
					counts[j]++;
				}
			}
		}

		// Entropy(S) = S -p(I) log2 p(I)

		double entropyS = 0;
		for (int i = 0; i < counts.length; i++) {
			entropyS += DTreeUtil.sigma(counts[i], array.length);
		}
		String[] arrtibutes = getArrtibutes(index);
		/**
		 * total ((|Sv| / |S|) * Entropy(Sv))
		 */
		double sv_total = 0;
		for (int i = 0; i < arrtibutes.length; i++) {
			sv_total += entropySv(array, index, arrtibutes[i], array.length);
		}
		return entropyS - sv_total;
	}

	// ((|Sv| / |S|) * Entropy(Sv))

	public double entropySv(Object[] array, int index, String arrtibute,
			int allTotal) {
		String[] playBalls = getArrtibutes(this.nodeIndex);
		int[] counts = new int[playBalls.length];
		for (int i = 0; i < counts.length; i++) {
			counts[i] = 0;
		}

		for (int i = 0; i < array.length; i++) {
			String[] strs = (String[]) array[i];
			if (strs[index].equals(arrtibute)) {
				for (int k = 0; k < playBalls.length; k++) {
					if (strs[this.nodeIndex].equals(playBalls[k])) {
						counts[k]++;
					}
				}
			}
		}

		int total = 0;
		double entropySv = 0;
		for (int i = 0; i < counts.length; i++) {
			total += counts[i];
		}
		for (int i = 0; i < counts.length; i++) {
			entropySv += DTreeUtil.sigma(counts[i], total);
		}
		return DTreeUtil.getPi(total, allTotal) * entropySv;
	}

	// 取得属性数组

	public String[] getArrtibutes(int index) {
		TreeSet<String> set = new TreeSet<String>();
		for (int i = 0; i < trainingArray.length; i++) {
			String[] strs = (String[]) trainingArray[i];
			set.add(strs[index]); // add the specified element to this set if it
									// is not already present
		}
		String[] result = new String[set.size()];
		return set.toArray(result);
	}

	// 取得节点名

	public String getNodeName(int index) {
		String[] strs = new String[] { "单价", "内环内", "房龄>5", "是否投资" };
		for (int i = 0; i < strs.length; i++) {
			if (i == index) {
				return strs[i];
			}
		}
		return null;
	}

	// 取得页节点名

	public String getLeafNodeName(Object[] array) {
		if (array != null && array.length > 0) {
			String[] strs = (String[]) array[0];
			return strs[nodeIndex];
		}
		return null;
	}

	// 取得节点索引

	public int getNodeIndex(String name) {
		String[] strs = new String[] {"单价", "内环内", "房龄>5", "是否投资" };
		for (int i = 0; i < strs.length; i++) {
			if (name.equals(strs[i])) {
				return i;
			}
		}
		return NO_FOUND;
	}
}

class TreeNode {

	// 父节点

	TreeNode parent;

	// 指向父的属性

	String parentArrtibute;

	// 节点名

	String nodeName;

	// 属性数组

	String[] arrtibutes;

	// 节点数组

	TreeNode[] childNodes;

}

class DTreeUtil {

	// 属性值熵的计算 Info(T)=(i=1...k)pi*log（2）pi

	public static double sigma(int x, int total) {
		if (x == 0) {
			return 0;
		}
		double x_pi = getPi(x, total);
		return -(x_pi * logYBase2(x_pi));
	}

	// log2y

	public static double logYBase2(double y) {
		return Math.log(y) / Math.log(2);
	}

	// pi是当前这个属性出现的概率（=出现次数/总数）

	public static double getPi(int x, int total) {
		return x * Double.parseDouble("1.0") / total;
	}

}

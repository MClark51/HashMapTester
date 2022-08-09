import java.util.Comparator;
/**
 * Class BST that implements the binary search tree
 * data structure using linked nodes
 * Each node has the type TreeNode (inner private class)
 */
public class TreeMap<K extends Comparable<K>, V> {
	private TreeNode root;
	private int size;
	private Comparator<K> comp;
	public static int iterations;
	/**
	 * Inner class used for the BST nodes
	 */
	private class TreeNode{
		MapEntry<K,V> value;
		TreeNode left;
		TreeNode right;
		TreeNode(K key, V val){
			value = new MapEntry<K,V>(key, val);
			left = right = null;
		}
	}
	/**
	 * Default constructor
	 * creates an empty BST
	 */
	TreeMap(Comparator<K> c){
		root = null;
		size = 0; 
		comp = c;
	}
	/**
	 * Method size
	 * @return the number of nodes in the BST
	 */
	public int size() {
		return size; 
	}
	/**
	 * Method isEmpty
	 * @return true if the tree is empty, false otherwise
	 */
	public boolean isEmpty() {
		return (size == 0); 
	}
	/**
	 * Method clear
	 * resets root to null and size to 0
	 */
	public void clear() {
		root = null; 
		size = 0;
	}
	/**
	 * Search method
	 * @param value being searched
	 * @return true if value is found, false otherwise
	 */
	public boolean containsKey(K key) {
		TreeNode node = root;
		while (node != null) {
			if(comp.compare(key,node.value.getKey()) < 0)
				node = node.left;
			else if (comp.compare(key,node.value.getKey()) > 0)
				node = node.right;
			else
				return true;
		}
		return false;
	}
	public V get(K key){
		iterations = 0;
		TreeNode node = root;
		while (node != null) {
			iterations++;
			if(comp.compare(key,node.value.getKey()) < 0)
				node = node.left;
			else if (comp.compare(key,node.value.getKey()) > 0)
				node = node.right;
			else
				return node.value.getValue();
		}
		return null;
	}
	/**
	 * Add a new node to the tree if the node does not exist
	 * @param value of the new node to be added
	 * @return true if a node is added successfully, 
	 *         false if the node exists already in the tree
	 */
	public boolean add(K key, V val) {
		if (root == null)
			root = new TreeNode(key, val);
		else {
			TreeNode parent, node;
			parent = null; node = root;
			while (node != null) {
				parent = node;
				if(comp.compare(key,node.value.getKey()) < 0) {
					node = node.left; 
				}
				else if (comp.compare(key,node.value.getKey()) > 0) {
					node = node.right; 
				}
				else
					return false;
			}
			if (key.compareTo(parent.value.getKey())< 0)
				parent.left = new TreeNode(key, val);
			else
				parent.right = new TreeNode(key, val);
		}
		size++;
		return true; 
	}
	/**
	 * Remove the node with value if found
	 * @param value of the node to be removed
	 * @return true if a node with value is found and removed
	 *         false if no node is not found
	 */
	public boolean remove(K key) {
		TreeNode parent, node;
		parent = null; node = root;
		// Find value first
		while (node != null) {
			if (comp.compare(key,node.value.getKey()) < 0) {
				parent = node;
				node = node.left;
			}
			else if (comp.compare(key,node.value.getKey()) > 0) {
				parent = node;
				node = node.right;
			}
			else
				break;
		}
		if (node == null)
			return false;

		// Case 1: node has no children
		if(node.left == null && node.right == null){
			if(parent == null){
				root = null;
			}
			else{
				changeChild(parent, node, null);
			}
		}
		//case 2: node has one right child
		else if(node.left == null){
			if (parent == null){
				root = node.right;
			}
			else{
				changeChild(parent, node, node.right);
			}
		}
		//case 2: node has one left child
		else if(node.right == null){
			if (parent == null){
				root = node.left;
			}
			else{
				changeChild(parent, node, node.left);
			}
		}
		//case 3: node has two children
		else {
			TreeNode rightMostParent = node;
			TreeNode rightMost = node.left;
			while (rightMost.right != null) {
				rightMostParent = rightMost;
				rightMost = rightMost.right;
			}
			node.value = rightMost.value;
			changeChild(rightMostParent, rightMost, 
					rightMost.left);
		}
		size--;
		return true;
	}
	/**
	 * Private method used by the remove method
	 * to update the links from parent to child
	 * @param parent of the node being deleted
	 * @param node the node being deleted
	 * @param newChild the node that will replace node as the child of parent
	 */
	private void changeChild(TreeNode parent,
			TreeNode node, TreeNode newChild){
		if(parent.left == node)
			parent.left = newChild;
		else
			parent.right = newChild;
	}
	/**
	 * Recursive method to display the list of the tree nodes inorder
	 */
	public void inorder() {
		inorder(root);
	}
	private void inorder(TreeNode node) {
		if (node != null) {
			inorder(node.left);
			System.out.print(node.value + " ");
			inorder(node.right);
		}
	}
	/**
	 * Recursive method to display the list of the tree nodes preorder
	 */
	public void preorder() {
		preorder(root);
	}
	private void preorder(TreeNode node) {
		if (node != null) {
			System.out.print(node.value + " ");
			preorder(node.left);
			preorder(node.right);
		}
	}
	/**
	 * Recursive method to display the list of the tree nodes postorder
	 */
	public void postorder() {
		postorder(root);
	}
	private void postorder(TreeNode node)  {
		if (node != null) {
			postorder(node.left);
			postorder(node.right);
			System.out.print(node.value + " ");	
		}
	}
}
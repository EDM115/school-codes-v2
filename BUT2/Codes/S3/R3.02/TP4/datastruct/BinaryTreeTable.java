package datastruct;

import ihm.TreeDraw;

/**
 * Implementation of a table of key/data pairs using a binary tree
 * @param <E> the type of the keys
 * @param <T> the type of the data
 * @see Table
 */
public class BinaryTreeTable<E extends Comparable<E>, T> implements Table<E, T> {
	/**
	 * Root node of the binary tree
	 */
	private Node root;

	/**
	 * Balance flag
	 */
    private boolean balance;

	/**
	 * Constructs an empty binary tree table
	 */
    public BinaryTreeTable() {
        this.root = null;
        this.balance = false;
		assert invariant();
    }

	/**
	 * Checks the invariant of the class
	 * @return true if the invariant is respected, false otherwise
	 */
	private boolean invariant() {
        return invariantHelper(root, null, null);
    }

	/**
	 * Helper method for the invariant
	 * @param node the node to check
	 * @param min the minimum value
	 * @param max the maximum value
	 * @return true if the invariant is respected, false otherwise
	 */
    private boolean invariantHelper(Node node, E min, E max) {
        if (node == null) {
			return true;
		}
        if (min != null && node.key.compareTo(min) <= 0) {
			return false;
		}
        if (max != null && node.key.compareTo(max) >= 0) {
			return false;
		}

        return invariantHelper(node.lSon, min, node.key) && invariantHelper(node.rSon, node.key, max);
    }

	/**
	 * Node class for the binary tree
	 */
	public class Node {
		/**
		 * Left child node
		 */
		private Node lSon;

		/**
		 * Right child node
		 */
		private Node rSon;

		/**
		 * Parent node (null if root)
		 */
		private Node father;

		/**
		 * Data stored in the node
		 */
		private T theValue;

		/**
		 * Unique key for the node
		 */
		private E key;

		/**
		 * Constructs a new node
		 * @param key the key of the node
		 * @param value the data of the node
		 * @param father the parent of the node
		 */
		public Node(E key, T value, Node father) {
			this.key = key;
			this.theValue = value;
			this.father = father;
			this.lSon = null;
			this.rSon = null;
		}

		/**
		 * Returns the key of the node (label for the node)
		 * @return the key of the node as a string
		 */
		public String getLabel() {
			return key.toString();
		}
	
		/**
		 * Returns the left child of the node
		 * @return the left child of the node
		 */
		public Node getLeft() {
			return lSon;
		}
	
		/**
		 * Returns the right child of the node
		 * @return the right child of the node
		 */
		public Node getRight() {
			return rSon;
		}
	
		/**
		 * Clones the current node
		 * @return the cloned node
		 */
		@Override
		public Node clone() {
			Node clonedNode = new Node(this.key, this.theValue, this.father);

			clonedNode.lSon = this.lSon; // Shallow copy
			clonedNode.rSon = this.rSon; // Shallow copy

			return clonedNode;
		}
	}

	/**
	 * If the key does not already exist in the table, this method inserts a new node with the key and data passed as parameters. Returns false if the insertion was not possible.
	 * @param key the key to insert
	 * @param data the data to insert
	 * @return true if the insertion was successful, false otherwise
	 * @throws IllegalArgumentException if the key or the data is null
	 */
	@Override
	public boolean insert(E key, T data) throws IllegalArgumentException {
		if (key == null || data == null) {
			throw new IllegalArgumentException("BinaryTreeTable.insert() : Arguments cannot be null");
		}

		if (root == null) {
			root = new Node(key, data, null);

			return true;
		}

		Node nodeFather = seekFather(key);
		
		// If the key already exists or no valid nodeFather is found
		if (nodeFather == null || key.compareTo(nodeFather.key) == 0) {
			return false;
		}

		Node newNode = new Node(key, data, nodeFather);

		if (key.compareTo(nodeFather.key) < 0) {
			nodeFather.lSon = newNode;
		} else {
			nodeFather.rSon = newNode;
		}

		setBalance(!isAVL());

		if (balance) {
			balanceTheTree(newNode);
		}

		assert invariant() : "BinaryTreeTable.insert() : Post-condition violated!";

		return true;
	}

	/**
	 * Destroys the node corresponding to the key passed as a parameter. Returns false if the deletion was not possible (i.e. the key does not exist).
	 * @param key the key to delete
	 * @return true if the deletion was successful, false otherwise
	 * @throws IllegalArgumentException if the key is null
	 */
	@Override
	public boolean delete(E key) {
		if (key == null) {
			throw new IllegalArgumentException("BinaryTreeTable.delete() : Argument cannot be null");
		}

		Node nodeToDel = findNode(root, key);

		if (nodeToDel == null) {
			return false; // Node not found.
		}
		deleteNode(nodeToDel);

		assert invariant() : "BinaryTreeTable.delete() : Post-condition violated!!";

		return true;
	}

	/**
	 * Deletes a node from the tree
	 * @param theNode the node to delete
	 */
	private void deleteNode(Node theNode) {
		if (theNode.lSon == null && theNode.rSon == null) {
			// Case 1: Node is a leaf
			if (theNode.father == null) { // Node is root
				root = null;
			} else if (theNode.father.lSon == theNode) {
				theNode.father.lSon = null;
			} else {
				theNode.father.rSon = null;
			}
		} else if (theNode.lSon == null || theNode.rSon == null) {
			// Case 2: Node has only one child
			Node child = (theNode.lSon != null) ? theNode.lSon : theNode.rSon;

			if (theNode.father == null) { // Node is root
				root = child;
			} else if (theNode.father.lSon == theNode) {
				theNode.father.lSon = child;
			} else {
				theNode.father.rSon = child;
			}
			child.father = theNode.father;
		} else {
			// Case 3: Node has two children
			Node theGNode = getMaxNode(theNode.lSon);

			theNode.key = theGNode.key;
			theNode.theValue = theGNode.theValue;
			deleteNode(theGNode); // Recursively delete theGNode
		}
	}

	/**
	 * Returns the node with the maximum key in the tree
	 * @param theNode the node to start the search from
	 * @return the node with the maximum key in the tree
	 */
	private Node getMaxNode(Node theNode) {
		if (theNode == null) {
			return null;
		}

		while (theNode.rSon != null) {
			theNode = theNode.rSon;
		}

		assert invariant() : "BinaryTreeTable.getMaxNode() : Post-condition violated!";

		return theNode;
	}
	
	/**
	 * Returns the root node of the tree
	 * @return the root node of the tree
	 */
	public Node getTheRoot() {
		return root;
	}

	/**
	 * Returns a string representation of the tree
	 * @return a string representation of the tree
	 */
	@Override
	public String toString() {
		return getInfo(root);
	}


	/**
	 * Returns a string representation of a node and its childrens
	 * @param theN the node to represent
	 * @return a string representation of a node and its childrens
	 */
	private String getInfo(Node theN) {
		if (theN == null) {
			return "";
		}
		String infosLNode = getInfo(theN.lSon);
		String infosRNode = getInfo(theN.rSon);
		String infosNode = "\nclé=" + theN.key.toString() + "\tdata=" + theN.theValue.toString();

		assert invariant() : "BinaryTreeTable.getInfo() : Post-condition violated!";

		return infosLNode + infosNode + infosRNode;
	}

	/**
	 * Clones the tree
	 * @return the cloned tree
	 */
	public BinaryTreeTable<E, T> clone() {
		BinaryTreeTable<E, T> clonedTree = new BinaryTreeTable<>();

		computeClone(root, null, clonedTree);

		assert invariant() : "BinaryTreeTable.clone() : Post-condition violated!";

		return clonedTree;
	}
	
	/**
	 * Helper method for the clone method
	 * @param nodeToCopy the node to copy
	 * @param newFather the father of the node to copy
	 * @param newTree the tree to copy to
	 */
	private void computeClone(Node nodeToCopy, Node newFather, BinaryTreeTable<E, T> newTree) {
		if (nodeToCopy == null) {
			return;
		}
		Node clonedNode = new Node(nodeToCopy.key, nodeToCopy.theValue, newFather);

		if (newFather == null) {
			newTree.root = clonedNode;
		} else if (newFather.key.compareTo(clonedNode.key) > 0) {
			newFather.lSon = clonedNode;
		} else {
			newFather.rSon = clonedNode;
		}
		computeClone(nodeToCopy.lSon, clonedNode, newTree);
		computeClone(nodeToCopy.rSon, clonedNode, newTree);
	}

	/**
	 * Shows the tree in a window, using the TreeDraw class
	 * @see TreeDraw
	 */
	public void showTree() {
        TreeDraw<E, T> treeDrawer = new TreeDraw<>(this.clone().root);

        treeDrawer.drawTree();
    }

	/**
	 * Returns the father of the node that would be created if the key was inserted in the tree
	 * @param key the key to insert
	 * @return the father of the node that would be created if the key was inserted in the tree
	 * @throws IllegalArgumentException if the key is null
	 */
	private Node seekFather(E key) throws IllegalArgumentException {
		if (key == null) {
			throw new IllegalArgumentException("BinaryTreeTable.seekFather() : Argument cannot be null");
		}
		Node currentNode = root;
		Node potentialFather = null;
	
		while (currentNode != null) {
			potentialFather = currentNode;
			int comparison = key.compareTo(currentNode.key);
			if (comparison == 0) {
				return null; // Key already exists in the tree
			} else if (comparison < 0) {
				currentNode = currentNode.lSon;
			} else {
				currentNode = currentNode.rSon;
			}
		}

		assert invariant() : "BinaryTreeTable.seekFather() : Post-condition violated!";
	
		return potentialFather;
	}

	/**
	 * This method returns the data contained in the node corresponding to the search key key passed as a parameter. Returns null if the key does not exist
	 * @param key the key to search for
	 * @return the data associated with the key, or null if the key does not exist
	 * @throws IllegalArgumentException if the key is null
	 */
	@Override
	public T select(E key) throws IllegalArgumentException {
		if (key == null) {
			throw new IllegalArgumentException("BinaryTreeTable.select() : Argument cannot be null");
		}
		Node foundNode = findNode(root, key);

		assert invariant() : "BinaryTreeTable.select() : Post-condition violated!";

		return (foundNode != null) ? foundNode.theValue : null;
	}

	/**
	 * Returns the node corresponding to the key passed as a parameter. Returns null if the key does not exist
	 * @param theNode the node to start the search from
	 * @param key the key to search for
	 * @return the node corresponding to the key passed as a parameter. Returns null if the key does not exist
	 * @throws IllegalArgumentException if the key is null
	 */
	private Node findNode(Node theNode, E key) throws IllegalArgumentException {
		if (key == null) {
			throw new IllegalArgumentException("BinaryTreeTable.findNode() : key cannot be null");
		}
		if (theNode == null) {
			return null;
		}
		int comparison = key.compareTo(theNode.key);

		if (comparison == 0) {
			return theNode;
		} else if (comparison < 0) {
			return findNode(theNode.lSon, key);
		} else {
			return findNode(theNode.rSon, key);
		}
	}

	/**
	 * Computes the height of the tree
	 * @param theN the node to start the computation from
	 * @return the height of the tree
	 */
	public int computeH(Node theN) {
		if (theN == null) {
			return 0;
		}

		return Math.max(computeH(theN.lSon), computeH(theN.rSon)) + 1;
	}

	/**
	 * Checks if the tree is balanced
	 * @return true if the tree is balanced, false otherwise
	 */
	public boolean isAVL() {
		return isAVL(root);
	}
	
	/**
	 * Checks if the tree is balanced
	 * @param theN the node to start the check from
	 * @return true if the tree is balanced, false otherwise
	 */
	private boolean isAVL(Node theN) {
		if (theN == null) {
			return true;
		}
		int balanceFactor = computeH(theN.lSon) - computeH(theN.rSon);

		assert invariant() : "BinaryTreeTable.isAVL() : Post-condition violated!";

		return Math.abs(balanceFactor) <= 1 && isAVL(theN.lSon) && isAVL(theN.rSon);
	}

	/**
	 * Performs a right rotation on the node passed as a parameter
	 * @param theN the node to rotate
	 * @throws IllegalArgumentException if the node is null
	 */
	private void rightRotation(Node theN) throws IllegalArgumentException {
		if (theN == null) {
			throw new IllegalArgumentException("BinaryTreeTable.rightRotation() : Argument cannot be null");
		}
		Node temp = theN.lSon;
		theN.lSon = temp.rSon;
		
		// If theN's left child exists, update its father reference
		if (theN.lSon != null) {
			theN.lSon.father = theN;
		}
		temp.rSon = theN;
	
		// Update theN's father to point to temp
		if (theN.father != null) {
			if (theN.father.lSon == theN) {
				theN.father.lSon = temp;
			} else {
				theN.father.rSon = temp;
			}
		}
		temp.father = theN.father;
		theN.father = temp;
		
		if (temp.father == null) {
			root = temp;
		}

		assert invariant() : "BinaryTreeTable.rightRotation() : Post-condition violated!";
	}
	
	/**
	 * Performs a left rotation on the node passed as a parameter
	 * @param theN the node to rotate
	 * @throws IllegalArgumentException if the node is null
	 */
	private void leftRotation(Node theN) throws IllegalArgumentException {
		if (theN == null) {
			throw new IllegalArgumentException("BinaryTreeTable.leftRotation() : Argument cannot be null");
		}
		Node temp = theN.rSon;

		theN.rSon = temp.lSon;
		// If theN's right child exists, update its father reference
		if (theN.rSon != null) {
			theN.rSon.father = theN;
		}
		temp.lSon = theN;
		// Update theN's father to point to temp
		if (theN.father != null) {
			if (theN.father.lSon == theN) {
				theN.father.lSon = temp;
			} else {
				theN.father.rSon = temp;
			}
		}
		temp.father = theN.father;
		theN.father = temp;

		if (temp.father == null) {
			root = temp;
		}

		assert invariant() : "BinaryTreeTable.leftRotation() : Post-condition violated!";
	}	
	
	/**
	 * Performs a left-right rotation on the node passed as a parameter
	 * @param theN the node to rotate
	 * @throws IllegalArgumentException if the node is null
	 */
	private void leftRightRotation(Node theN) throws IllegalArgumentException {
		if (theN == null) {
			throw new IllegalArgumentException("BinaryTreeTable.leftRightRotation() : Argument cannot be null");
		}
		leftRotation(theN.lSon);
		rightRotation(theN);
	}
	
	/**
	 * Performs a right-left rotation on the node passed as a parameter
	 * @param theN the node to rotate
	 * @throws IllegalArgumentException if the node is null
	 */
	private void rightLeftRotation(Node theN) throws IllegalArgumentException {
		if (theN == null) {
			throw new IllegalArgumentException("BinaryTreeTable.rightLeftRotation() : Argument cannot be null");
		}
		rightRotation(theN.rSon);
		leftRotation(theN);
	}

	/**
	 * Sets the balance flag
	 * @param bal the value of the balance flag
	 */
	public void setBalance(boolean bal) {
		this.balance = bal;
	}

	/**
	 * Returns the value of the balance flag
	 * @return the value of the balance flag
	 */
	public boolean getBalance() {
		return this.balance;
	}
	
	/**
	 * Balances the tree
	 * @param theN the node to start the balancing from
	 */
	private void balanceTheTree(Node theN) {
		while (theN != null) {
			int balanceFactor = computeH(theN.lSon) - computeH(theN.rSon);

			if (balanceFactor > 1) {
				if (computeH(theN.lSon.lSon) < computeH(theN.lSon.rSon)) {
					leftRightRotation(theN);
				} else {
					rightRotation(theN);
				}
			} else if (balanceFactor < -1) {
				if (computeH(theN.rSon.rSon) < computeH(theN.rSon.lSon)) {
					rightLeftRotation(theN);
				} else {
					leftRotation(theN);
				}
			}
			theN = theN.father;
		}

		assert invariant() : "BinaryTreeTable.balanceTheTree() : Post-condition violated!";
	}
}

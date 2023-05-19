package BinarySearchTree;

import java.util.LinkedList;
import java.util.Queue;

public class BST<T extends Comparable<T>> {

    private class Node {
        T value;
        Node left, right, parent;

        public Node(T value, Node left, Node right, Node parent) {
            this.value = value;
            this.left = left;
            this.right = right;
            this.parent = parent;
        }
    }

    private Node root = null;

    public BST() {
    }

    public T getElement(T toFind) {
        Node node;
        if ((node = getNode(toFind, this.root)) != null) {
            return node.value;
        }
        return null;
    }

    private Node getNode(T toFind, Node currentRoot) {
        if (currentRoot == null || toFind.equals(currentRoot.value)) {
            return currentRoot;
        }

        if (toFind.compareTo(currentRoot.value) > 0) {
            return getNode(toFind, currentRoot.right);
        }

        return getNode(toFind, currentRoot.left);
    }

    public T successor(T elem) {
        Node currentNode = getNode(elem, root);
        if (currentNode == null) {
            return null;
        }

        if (currentNode.right != null) {
            // jesli jest prawe dziecko to szukamy min w jego subtree
            currentNode = currentNode.right;
            while (currentNode.left != null) {
                currentNode = currentNode.left;
            }
            return currentNode.value;
        } else {
            // szukamy pierwszego przodka ktory jest wiekszy niz szukana wartosc
            while (currentNode.parent != null && currentNode.parent.left != currentNode) {
                currentNode = currentNode.parent;
            }

            if (currentNode.parent != null) {
                // jesli istnieje przodek z wartosciq wieksza to zwrocamy jego wartosc
                return currentNode.parent.value;
            }
        }
        return null;
    }

    private void inOrderWalk(StringBuilder sb, Node currentNode) {
        if (currentNode != null) {
            inOrderWalk(sb, currentNode.left);
            sb.append(currentNode.value.toString()).append(", ");
            inOrderWalk(sb, currentNode.right);
        }
    }

    private void preOrderWalk(StringBuilder sb, Node currentNode) {
        if (currentNode != null) {
            sb.append(currentNode.value.toString()).append(", ");
            preOrderWalk(sb, currentNode.left);
            preOrderWalk(sb, currentNode.right);
        }
    }

    private void postOrderWalk(StringBuilder sb, Node currentNode) {
        if (currentNode != null) {
            postOrderWalk(sb, currentNode.left);
            postOrderWalk(sb, currentNode.right);
            sb.append(currentNode.value.toString()).append(", ");
        }
    }

    public String toStringLevelOrder(){
        StringBuilder sb = new StringBuilder();
        if (root!=null){
            Queue<Node> queue = new LinkedList<>();
            queue.add(root);
            while (!queue.isEmpty()){
                Node curr = queue.poll();
                sb.append(curr.value).append(", ");

                if (curr.left!=null){
                    queue.add(curr.left);
                }
                if (curr.right!=null){
                    queue.add(curr.right);
                }
            }
            return sb.substring(0, sb.toString().length()-2);
        }
        return null;
    }

    public String toStringInOrder() {
        StringBuilder sb = new StringBuilder();
        inOrderWalk(sb, root);
        String retStr = sb.toString();

        if (retStr.length() > 2) {
            return retStr.substring(0, retStr.length() - 2);
        }

        return retStr;
    }

    public String toStringPreOrder() {
        StringBuilder sb = new StringBuilder();
        preOrderWalk(sb, root);
        String retStr = sb.toString();

        if (retStr.length() > 2) {
            return retStr.substring(0, retStr.length() - 2);
        }

        return retStr;
    }

    public String toStringPostOrder() {
        StringBuilder sb = new StringBuilder();
        postOrderWalk(sb, root);
        String retStr = sb.toString();

        if (retStr.length() > 2) {
            return retStr.substring(0, retStr.length() - 2);
        }

        return retStr;
    }

    public boolean add(T elem) {
        if (root == null) {
            root = new Node(elem, null, null, null);
            return true;
        } else {
            Node currentNode = null;
            Node temp = root;

            while (temp != null) {
                currentNode = temp;
                if (elem.compareTo(temp.value) > 0) {
                    temp = temp.right;
                } else {
                    temp = temp.left;
                }
            }
            if (elem.compareTo(currentNode.value) == 0) {
                return false;
            } else {
                Node nodeToAdd = new Node(elem, null, null, currentNode);
                if (elem.compareTo(currentNode.value) > 0) {
                    currentNode.right = nodeToAdd;
                } else {
                    currentNode.left = nodeToAdd;
                }
            }
        }
        return true;
    }


    public T remove(T value) {
        Node toRemove = getNode(value, root); // szukamy refernecji do wart z parametru

        if (toRemove == null) {
            return null;
        }

        Node temp = toRemove;

        if (toRemove.right != null && toRemove.left != null) {
            toRemove = getNode(successor(value), root);
        }

        Node child;

        if (toRemove.left != null) {
            child = toRemove.left;
        } else {
            child = toRemove.right;
        }

        if (child != null) {
            child.parent = toRemove.parent;
        }

        if (toRemove.parent == null) {
            root = child;
        } else {
            if (toRemove.parent.left == toRemove) {
                toRemove.parent.left = child;
            } else {
                toRemove.parent.right = child;
            }
        }

        if (toRemove != temp) {
            T val = toRemove.value;
            toRemove.value = temp.value;
            temp.value = val;
        }

        return toRemove.value;
    }

    private int numberOfDoubleParentsRecursive(Node node) {
        if (node == null)
            return 0;
        if (node.left != null && node.right != null) {
            return numberOfDoubleParentsRecursive(node.left) + 1 + numberOfDoubleParentsRecursive(node.right);
        }
        return numberOfDoubleParentsRecursive(node.left) + numberOfDoubleParentsRecursive(node.right);
    }

    public int numberOfDoubleParents() {
        return numberOfDoubleParentsRecursive(root);
    }

    public void clear() {
        root = null;
    }

    public int size() {
        return smallTreeSize(root);
    }

    private int smallTreeSize(Node currentRoot) {
        if (currentRoot == null) {
            return 0;
        }

        return smallTreeSize(currentRoot.left) + 1 + smallTreeSize(currentRoot.right);
    }

    public T searchMin() {
        Node min = root;
        while (min.left != null) {
            min = min.left;
        }
        return min.value;
    }

    public T searchMax() {
        Node max = root;
        while (max.right != null) {
            max = max.right;
        }
        return max.value;
    }

    private int getHeight(Node node) {
        int leftHeight, rightHeight;
        if (node == null) {
            return 0;
        } else {
            leftHeight = getHeight(node.left);
            rightHeight = getHeight(node.right);
            return Math.max(leftHeight, rightHeight) + 1;
        }
    }

    public int getHeight() {
        return getHeight(root);
    }

    private int evenKeys(Node node) {
        if (node == null) {
            return 0;
        }

        int count = 0;
        if (node.value.hashCode() % 2 == 0) {
            count++;
        }

        count += evenKeys(node.left) + evenKeys(node.right);

        return count;
    }

    public int evenKeys() {
        return evenKeys(root);
    }

    private int nodesWithOneChild(Node node) {
        if (node == null) {
            return 0;
        }

        int count = 0;

        // nor ktory mowi ze ma byc wezel z lewym albo prawym dzieckiem
        if ((node.left != null && node.right == null) || (node.left == null && node.right != null)) {
            count++;
        }

        count += nodesWithOneChild(node.left) + nodesWithOneChild(node.right);

        return count;
    }

    public int nodeWithOneChild() {
        return nodesWithOneChild(root);
    }

    private int getColumns(int h) {
        if (h == 1)
            return 1;
        return getColumns(h - 1) + getColumns(h - 1) + 1;
    }

    private void printTree(Object[][] M, Node root, int col, int row, int height) {
        if (root == null)
            return;
        M[row][col] = root.value;
        printTree(M, root.left, col - (int)Math.pow(2, height - 2), row + 1, height - 1);
        printTree(M, root.right, col + (int)Math.pow(2, height - 2), row + 1, height - 1);
    }

    public void printTree() {
        int h = getHeight();
        int col = getColumns(h);
        Object [][]M = new Object[h][col];
        printTree(M, root, col / 2, 0, h);
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < col; j++) {
                if (M[i][j]==null) {
                    System.out.print("  ");
                }
                else {
                    System.out.print(M[i][j] + " ");
                }
            }
            System.out.println();
        }
    }
}

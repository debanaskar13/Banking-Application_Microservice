package binary.tree.general;

import java.util.LinkedList;
import java.util.Queue;

public class BinaryTreeDemo {
    static class Node{
        int data;
        Node left;
        Node right;

        Node(int data){
            this.data = data;
            left = null;
            right = null;
        }
    }

    static class BinaryTree{

        private static int index = -1;

        public static Node buildTree(int[] nodes){
            index++;
            if(nodes[index] == -1){
                return null;
            }

            Node newNode = new Node(nodes[index]);
            newNode.left = buildTree(nodes);
            newNode.right = buildTree(nodes);

            return newNode;
        }

        public static void preOrder(Node root){
            if(root == null){
                return;
            }

            System.out.print(root.data + " ");
            preOrder(root.left);
            preOrder(root.right);
        }

        public static void inOrder(Node root){
            if(root == null){
                return;
            }
            inOrder(root.left);
            System.out.print(root.data + " ");
            inOrder(root.right);
        }

        public static void postOrder(Node root){
            if(root == null){
                return;
            }
            postOrder(root.left);
            postOrder(root.right);
            System.out.print(root.data + " ");
        }

        public static void levelOrder(Node root){
            Queue<Node> queue = new LinkedList<>();
            queue.add(root);
            queue.add(null);
            while(!queue.isEmpty()){
                Node currentNode = queue.poll();
                if(currentNode == null) {
                    System.out.println();
                    if(queue.isEmpty()){
                        break;
                    }
                    queue.add(null);
                    continue;
                }
                System.out.print(currentNode.data + " ");
                if(currentNode.left != null){
                    queue.add(currentNode.left);
                }
                if(currentNode.right != null){
                    queue.add(currentNode.right);
                }
            }
        }

        public static int sumOfNodes(Node root){
            if(root == null){
                return 0;
            }

            return sumOfNodes(root.left) + sumOfNodes(root.right) + root.data;
        }

        public static int heightOfTree(Node root){
            if(root == null){
                return 0;
            }

            return Math.max(heightOfTree(root.left), heightOfTree(root.right)) + 1;
        }

        public static int[] diameterOfTree(Node root){
            if(root == null){
                return new int[]{0, 0};
            }

            int[] left = diameterOfTree(root.left);
            int[] right = diameterOfTree(root.right);

            int leftHeight = left[0];
            int rightHeight = right[0];

            int leftDiameter = left[1];
            int rightDiameter = right[1];

            int height = Math.max(leftHeight, rightHeight) + 1;

            int diameter = Math.max(leftHeight + rightHeight + 1, Math.max(leftDiameter, rightDiameter));

            return new int[]{height, diameter};
        }
    }


    public static void main(String[] args) {
        int[] nodes = {1,2,4,-1,-1,5,-1,-1,3,-1,6,-1,-1};
        BinaryTree tree = new BinaryTree();
        Node node = tree.buildTree(nodes);
        System.out.println(node.data);

        System.out.println(tree.sumOfNodes(node));
        System.out.println(tree.heightOfTree(node));

        System.out.println(tree.diameterOfTree(node)[1]);


    }
}

public class HW6_1{    
    private Node root = null;
    
    public Node BTrebuild(String inString, String postString) {
        String[] inStr = inString.split(" ");
        int[] inOrder = new int[inStr.length];
        for (int i = 0; i < inStr.length; i++) {
            inOrder[i] = Integer.parseInt(inStr[i]);
        }
        String[] postStr = postString.split(" ");
        int[] postOrder = new int[postStr.length];
        for (int i = 0; i < postStr.length; i++) {
            postOrder[i] = Integer.parseInt(postStr[i]);
        }

        Node root = buildTree(inOrder, 0, inOrder.length - 1, postOrder, 0, postOrder.length - 1);

        return root;
    }

    public Node buildTree(int[] inOrder, int inStart, int inEnd, int[] postOrder, int postStart, int postEnd) {
        if (postStart > postEnd) {
            return null;
        }

        Node node = new Node(postOrder[postEnd], postOrder[postEnd]);

        int index = 0;
        for (int i = inStart; i <= inEnd; i++) {
            if (postOrder[postEnd] == inOrder[i]) {
                index = i;
                break;
            }
        }

        int numNode = index - inStart;

        node.setLeft(buildTree(inOrder, inStart, index - 1, postOrder, postStart, (postStart + numNode) - 1));
        node.setRight(buildTree(inOrder, index + 1, inEnd, postOrder, postStart + numNode, postEnd - 1));

        return node;
    }
}

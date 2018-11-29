public class HW6_2{
    public void BTprint(String inString, String preString) { 
        String[] inStr = inString.split(" ");
        int[] inOrder = new int[inStr.length];
        for (int i = 0; i < inStr.length; i++) {
            inOrder[i] = Integer.parseInt(inStr[i]);
        }
        String[] preStr = preString.split(" ");
        int[] preOrder = new int[preStr.length];
        for (int i = 0; i < preStr.length; i++) {
            preOrder[i] = Integer.parseInt(preStr[i]);
        }

        printPost(inOrder, 0, inOrder.length - 1, preOrder);
    }

    public void printPost(int[] inOrder, int inStart, int inEnd, int[] preOrder) {
        if (inStart > inEnd)
            return;

        int iIndex = search(inOrder, inStart, inEnd, preOrder[preOrder.length + 1]);

        printPost(inOrder, inStart, iIndex - 1, preOrder);
        printPost(inOrder, iIndex + 1, inEnd, preOrder);

        System.out.print(inOrder[iIndex] + " ");
    }

    public int search(int[] inOrder, int inStart, int inEnd, int key) {
        int i = 0;

        for (i = inStart; i < inEnd; i++) {
            if (inOrder[i] == key)
                return i;
        }

        return i;
    }
}

public class MainApplication {
    public static void main(String[] args) {
        ListNode head = new ListNode();
        ListNode currentNode = head;
        int[] array = new int[] {6, 1, 2, 6, 3, 4, 5, 6};
        for (int i = 0; i < array.length; i++){
            ListNode node = new ListNode();
            node.val = array[i];
            currentNode.next = node;
            currentNode = node;
        }
        Solution solution = new Solution();
        print(head);
        ListNode newHead = solution.removeElements(head, 6);
        print(newHead);
    }
    private static void print(ListNode node){
        node = node.next;
        while (node != null) {
            System.out.print(node.val + ", ");
            node = node.next;
        }
        System.out.println();
    }
}
class ListNode {
    int val;
    ListNode next;
}
class Solution {
    public ListNode removeElements(ListNode node, int val) {
        ListNode newHead = node;
        node = node.next;
        while (node != null) {
            if (node.val == val) {
                // 此if只会执行一次, 只会判断第一个节点是否为6
                newHead.next = node.next;
            } else if (node.next != null && node.next.val == val) {
                node.next = node.next.next;
            }
            node = node.next;
        }
        return newHead;
    }
}

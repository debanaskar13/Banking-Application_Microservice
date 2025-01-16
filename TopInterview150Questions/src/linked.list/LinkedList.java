package linked.list;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class LinkedList {

    class Node{
        String data;
        Node next;
        Node(String data){
            this.data = data;
            this.next = null;
        }
    }

    Node head = null;
    int size = 0;

    public void addFirst(String data){
        Node newNode = new Node(data);
        if(head == null){
            head = newNode;
            this.size++;
            return;
        }
        newNode.next = head;
        head = newNode;
        this.size++;
    }

    public void addLast(String data){
        Node newNode = new Node(data);
        if(head == null){
            head = newNode;
            this. size++;
            return;
        }
        Node currNode = head;
        while(currNode.next != null){
            currNode = currNode.next;
        }
        currNode.next = newNode;
        this.size++;
    }

    public boolean delete(String data){

        if(head == null){
            System.out.println("The list is empty");
            return false;
        }

        if(head.data.equals(data)){
            head = head.next;
            this.size--;
            return true;
        }

        Node currNode = head;

        while(currNode != null){
            if(currNode.next.data.equals(data)){
                currNode.next = currNode.next.next;
                this.size--;
                return true;
            }
            currNode = currNode.next;
        }
        return false;
    }

    public void print(){

        if(head == null){
            System.out.println("The list is empty");
            return;
        }

        Node temp = head;
        while(temp != null){
            if(temp.next != null){
                System.out.print(temp.data+" -> ");
            }else{
                System.out.println(temp.data + " -> NULL");
            }

            temp = temp.next;
        }
    }



    public static void main(String[] args) {

//        LinkedList list = new LinkedList();
//
//        list.addFirst("5");
//        list.addLast("5");
//        list.addFirst("45");
//        list.addLast("45");
//        list.addFirst("51");
//        list.addLast("51");
//        list.addFirst("27");
//        list.addLast("27");
//
//        list.print();
//        System.out.println(list.size);
//
//
//        list.delete("27");
//        list.delete("27");
//
//        list.print();
//        System.out.println(list.size);
////        list.delete();
//        HashMap<Integer,Integer> map = new HashMap<>();
//
//        Integer.MAX_VALUE


        int[] prices = {7,1,5,3,6,4};


        String s= "the sky is blue";
        String[] words = s.split(" ");
        StringBuilder sb = new StringBuilder();
        for(int i=words.length-1;i>=0;i--){
            sb.append(words[i]);
            sb.append(" ");
        }

        String reversed = sb.toString();
        System.out.println(reversed.trim());
























    }



}

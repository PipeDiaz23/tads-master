package co.edu.umanizales.tads.model;

import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
@Getter
public class OwnerList {
    private Node head;
    private int size;
    private List<Owner> owners;

    public void add(Owner owner){
        if (head != null){

            Node temp=head;
            while(temp.getNext() != null)
            {
                temp=temp.getNext();
            }
            //parado en el ultimo
            Node newNode = new Node(owner);
            temp.setNext(newNode);
        }
        else{
            head= new Node(owner);
        }
        size ++;
    }
    public Owner getOwnerById(String id) {
        Node temp = head;
        while (temp != null) {
            if (temp.getDataOwner().getId().equals(id)) {
                return temp.getDataOwner();
            }
            temp = temp.getNext();
        }
        return null;
    }



}
package co.edu.umanizales.tads.model;

import lombok.Data;

@Data
public class Node {
    private Kid data;
    private Owner dataOwner;
    private Node next;

    public Node(Kid data){

        this.data= data;
    }
    public Node(Owner dataOwner){
        this.dataOwner=dataOwner;
    }
}

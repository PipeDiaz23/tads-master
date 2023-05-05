package co.edu.umanizales.tads.model;

import lombok.Data;

@Data
public class NodeDE {
    private Pet data;
    private NodeDE prev;
    private NodeDE next;

    public NodeDE(Pet data){
        this.data=data;
  }
}


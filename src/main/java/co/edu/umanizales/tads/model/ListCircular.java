package co.edu.umanizales.tads.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Data
public class ListCircular {
    private List<Pet> petsRound;
    private List<Owner> owners;

    private NodeDE head;
    private int size;

    public void add(Pet newPet) {
        NodeDE newNode = new NodeDE(newPet);

        if(head == null) {
            head = newNode;
            newNode.setNext(head);
            newNode.setPrev(head);
        }
        NodeDE temp= head;
        if(temp.getPrev() == head){
            temp.setNext(newNode);
            temp.setPrev(newNode);
            newNode.setNext(temp);
            newNode.setPrev(temp);
        }else{

            temp.getPrev().setNext(newNode);
            newNode.setPrev(temp.getPrev());
            temp.setPrev(newNode);
            newNode.setNext(temp);
        }

        size++;
    }
    public void addStar(Pet newPet) {
        NodeDE newNode = new NodeDE(newPet);

        if(head == null) {
            head = newNode;
            newNode.setNext(head);
            newNode.setPrev(head);
        }
        NodeDE temp= head;
        if(temp.getPrev() == head){
            temp.setNext(newNode);
            temp.setPrev(newNode);
            newNode.setNext(temp);
            newNode.setPrev(temp);
            head=newNode;
        }else{

            temp.getPrev().setNext(newNode);
            newNode.setPrev(temp.getPrev());
            temp.setPrev(newNode);
            newNode.setNext(temp);
            head=newNode;
        }

        size++;
    }

    public void addByPosition(Pet pet, int position) {
        NodeDE newNode = new NodeDE(pet);

        if (position <= 1 || head == null) {
            if (head == null) {
                head = newNode;
                newNode.setNext(head);
                newNode.setPrev(head);
            } else {
                NodeDE temp = head.getPrev();
                newNode.setNext(head);
                newNode.setPrev(temp);
                head.setPrev(newNode);
                temp.setNext(newNode);
                head = newNode;
            }
        } else {
            NodeDE prevNode = head;
            for (int i = 1; i < position && prevNode.getNext() != head; i++) {
                prevNode = prevNode.getNext();
            }

            NodeDE currentNode = prevNode.getNext();
            prevNode.setNext(newNode);
            newNode.setPrev(prevNode);
            newNode.setNext(currentNode);
            if (currentNode != head) {
                currentNode.setPrev(newNode);
            } else {
                head.setPrev(newNode);
            }
        }

        size++;
    }

    public List<Pet> getPetNext(){
        petsRound = new ArrayList<>();
        if(head != null){
            NodeDE temp = head;
            petsRound.add(temp.getData());
            temp = temp.getNext();
            while (temp != head){
                petsRound.add(temp.getData());
                temp = temp.getNext();
            }
        }
        return petsRound;
    }

    public Pet takeShower(char direction) {

        Random rand = new Random();
        int randomNum = rand.nextInt(size + 1);

        NodeDE temp = head;
        if (direction == 'd') {
            for (int i = 1; i < randomNum; i++) {
                temp = temp.getNext();
            }
        }
        if (direction == 'i') {
            for (int i = size; i > randomNum; i--) {
                temp = temp.getPrev();
            }
        }
        Pet pet = temp.getData();
        if (pet.getStatus() != true) {
            temp.getData().setStatus(true);
            return pet;
        } else {
            return null;
        }
    }


    public boolean checkPetById(String id){
        if(head==null){
            return false;
        }

        if(head.getData().getId().equals(id)){
            return true;
        }

        NodeDE temp = head.getNext();
        while (temp !=head){
            if(temp.getData().getId().equals(id)){
                return true;
            }
            temp= temp.getNext();
        }
        return false;
    }

}

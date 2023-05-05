package co.edu.umanizales.tads.model;


import lombok.Data;

import java.util.List;
@Data
public class ListDE {
    private List<Pet> pets;
    List<Owner> owners;
    NodeDE head;
    int size;

    public void add(Pet pet) {
        if (head != null) {

            NodeDE temp = head;
            while (temp.getNext() != null) {
                temp = temp.getNext();
            }
            //parado en el ultimo
            NodeDE newNode = new NodeDE(pet);
            temp.setNext(newNode);
            temp.getNext().setPrev(temp);
        } else {
            head = new NodeDE(pet);
        }
        size++;
    }

    public void addToStart(Pet pet) {
        NodeDE newNode = new NodeDE(pet);
        if (head != null) {
            head.setPrev(newNode);
            newNode.setNext(head);
        }
        head = newNode;
        size++;
    }

    public void invert() {
        if (head != null) {
            NodeDE temp = head;
            NodeDE current = null;
            while (temp != null) {
                current = temp.getPrev();
                temp.setPrev(temp.getNext());
                temp.setNext(current);
                temp = temp.getPrev();
            }
            if (current != null) {
                head = current.getPrev();
            }
        }
    }

    public void orderBoysToStart() {
        if (this.head != null) {
            ListDE listCp = new ListDE();
            NodeDE temp = this.head;
            while (temp != null) {
                if (temp.getData().getGender() == 'M') {
                    listCp.addToStart(temp.getData());
                } else {
                    listCp.add(temp.getData());
                }

                temp = temp.getNext();
            }
            this.head = listCp.getHead();
            this.head.setPrev(null);
        }
    }

    public void changeExtremes() {
        if (this.head != null && this.head.getNext() != null) {
            NodeDE first = this.head;
            NodeDE last = this.head;

            // Mover al último nodo
            while (last.getNext() != null) {
                last = last.getNext();
            }

            // Intercambiar datos entre el primer y el último nodo
            Pet temp = first.getData();
            first.setData(last.getData());
            last.setData(temp);
        }
    }

    public int getCountKidsByLocationCode(String code) {
        int count = 0;
        if (this.head != null) {
            NodeDE temp = this.head;
            while (temp != null) {
                if (temp.getData().getLocation().getCode().equals(code)) {
                    count++;
                }
                temp = temp.getNext();
            }
        }
        return count;
    }

    public boolean checkPetByIdentification(String Id) {
        if (this.head != null) {
            NodeDE temp = this.head;
            while (temp != null) {
                if (temp.getData().getId().equals(Id)) {
                    return true;
                }
                temp = temp.getNext();
            }
        }
        return false;
    }

    public void addByPosition(Pet pet, int position) {
        NodeDE newNode = new NodeDE(pet);
        if (position <= 1) {
            addToStart(pet);
        } else {
            NodeDE current = head;
            for (int i = 1; i < position - 1 && current.getNext() != null; i++) {
                current = current.getNext();
            }
            if (current.getNext() != null) {
                current.getNext().setPrev(newNode);
            }
            newNode.setNext(current.getNext());
            newNode.setPrev(current);
            current.setNext(newNode);
        }

    }
    public double getAverageAge() {
        int totalAge = 0;
        int numPets = 0;
        NodeDE temp = this.head;
        while (temp != null) {
            Pet pet = temp.getData();
            if (pet.getAge() != null) {
                totalAge += pet.getAge();
                numPets++;
            }
            temp = temp.getNext();
        }

        if (numPets > 0) {
            return (double) totalAge / numPets;
        } else {
            return 0.0;
        }
    }



}





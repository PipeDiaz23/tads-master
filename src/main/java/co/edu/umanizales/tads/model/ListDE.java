package co.edu.umanizales.tads.model;


import co.edu.umanizales.tads.controller.dto.ReportPetsLocationDTO;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
public class ListDE {
    private List<Pet> pets;
    private List<Owner> owners;

    private NodeDE head;
    private int size;

    public List<Pet> getPets(){
        pets= new ArrayList<>();
        NodeDE temp= head;
        pets.add(temp.getData());
        while (temp.getNext()!=null){
            temp=temp.getNext();
            pets.add(temp.getData());
        }
        return pets;
    }

    public List<Pet> getPetsPrev(){
        pets= new ArrayList<>();
        NodeDE temp= head;
        while (temp.getNext()!=null){
            temp=temp.getNext();
        }
        pets.add(temp.getData());
        while (temp.getPrev()!=null){
            temp=temp.getPrev();
            pets.add(temp.getData());
        }
        return pets;
    }


    public void add(Pet pet) throws IllegalArgumentException {
        try {
            Objects.requireNonNull(pet, "Pet object cannot be null");

            if (head != null) {
                NodeDE temp = head;
                while (temp.getNext() != null) {
                    temp = temp.getNext();
                }
                // parado en el último
                NodeDE newNode = new NodeDE(pet);
                temp.setNext(newNode);
                temp.getNext().setPrev(temp);
            } else {
                head = new NodeDE(pet);
            }
            size++;
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("Pet object cannot be null");
        }
    }


    public boolean checkPetById(String id) {
        try {
            Objects.requireNonNull(id, "Pet ID cannot be null");

            if (this.head != null) {
                NodeDE temp = this.head;

                while (temp != null) {
                    if (temp.getData().getId().equals(id)) {
                        return true;
                    }
                    temp = temp.getNext();
                }
            }
            return false;
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("Pet ID cannot be null");
        }
    }

    public void addToStart(Pet pet) throws IllegalArgumentException {
        try {
            Objects.requireNonNull(pet, "Pet object cannot be null");

            NodeDE newNode = new NodeDE(pet);
            if (head != null) {
                head.setPrev(newNode);
                newNode.setNext(head);
            }
            head = newNode;
            size++;
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("Pet object cannot be null");
        }
    }

    /*ELIMINAR NIÑO POR ID NUEVA VERSION

     verificamoss si la lista tiene elementos:
     llamo un nodo temporal que va a ser igual al elemento que estoy buscando
     -si el primer elemento "cabeza" contiene el elemento que deseo eliminar, establecemos el siguiente como la "cabeza",
     y establecemos en nodo previo de la  nueva cabeza como nulo
     -si el elemento se enccuentra en otra posicion que no sea cabeza
           -recorro la lista hasta  encontrar el elemento que deseo eleminar
           -parado en el elemento que voy a elimiar, a mi previo le digo que tome a mi siguiente, a mi siguiente le digo que
           tome mi previo
        si el elemento es el ultimo:
           -parado en el elemento que voy a eliminar, si su siguiente es nulo significa que es el ultimo elemento de la lista
           - y el previo de elemento lo suelto, y mi previo lo vuelvo nulo
    * */
    public Pet deletePetByIdTwo(String id) {
        if (head == null) {
            return null;
        } else {
            NodeDE current = head;
            while (current != null && !current.getData().getId().equalsIgnoreCase(id)) {
                current = current.getNext();
            }
            if (current == null) {
                return null;
            } else {
                Pet petToDelete = current.getData();
                if (current.getPrev() == null) {
                    head = current.getNext();
                    if (head != null) {
                        head.setPrev(null);
                    }
                } else {
                    current.getPrev().setNext(current.getNext());
                    if (current.getNext() != null) {
                        current.getNext().setPrev(current.getPrev());
                    }
                }
                // Verificar si petToDelete es null antes de intentar llamar a setId()
                if (petToDelete != null) {
                    petToDelete.setId(null);
                }
                return petToDelete;
            }
        }
    }




    public void deletePetById(String id) {
        if (head != null) {
            NodeDE temp = head;

            if (temp.getData().getId().equalsIgnoreCase(id)) {
                temp = temp.getNext();
                head = temp;
                if (size != 1) {
                    head.setPrev(null);
                }
            } else {
                while (temp.getNext() != null) {
                    if (temp.getNext().getData().getId().equalsIgnoreCase(id)) {
                        if (temp.getNext().getNext() != null) {
                            temp.getNext().getNext().setPrev(temp);
                        }
                        temp.setNext(temp.getNext().getNext());
                        size--;
                        return;
                    }
                    temp = temp.getNext();
                }
            }
            size--;
        }
    }



    public void deleteByAge(int age) {
        if (head == null) {
            return;
        }

        // Eliminar nodos en la cabeza de la lista con la edad dada
        while (head != null && head.getData().getAge() == age) {
            head = head.getNext();
            if (head != null) {
                head.setPrev(null);
            }
        }

        NodeDE current = head;
        while (current != null) {
            if (current.getData().getAge() == age) {
                NodeDE prev = current.getPrev();
                NodeDE next = current.getNext();
                if (prev != null) {
                    prev.setNext(next);
                }
                if (next != null) {
                    next.setPrev(prev);
                }
            }
            current = current.getNext();
        }
    }
    public void addByPosition(Pet pet, int position) {
        try {
            Objects.requireNonNull(pet, "Pet object cannot be null");

            NodeDE newNode = new NodeDE(pet);
            if (position <= 1) {
                addToStart(pet);
            } else {
                NodeDE current = head;
                for (int i = 1; i < position - 1 && current != null; i++) {
                    current = current.getNext();
                }
                if (current != null) {
                    newNode.setNext(current.getNext());
                    newNode.setPrev(current);
                    if (current.getNext() != null) {
                        current.getNext().setPrev(newNode);
                    }
                    current.setNext(newNode);
                }
            }
            size++;
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("Pet object cannot be null");
        }
    }

    public double getAverageAge() {
        int totalAge = 0;
        int numPets = 0;
        NodeDE temp = head;

        try {
            while (temp != null) {
                Pet pet = temp.getData();
                Objects.requireNonNull(pet, "El objeto no puede ser vacio");
                totalAge += pet.getAge();
                numPets++;
                temp = temp.getNext();
            }

            if (numPets > 0) {
                return (double) totalAge / numPets;
            } else {
                return 0.0;
            }
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("El objeto no puede ser vacio");
        }
    }
    public void changeExtremes() {
        try {
            if (head != null && head.getNext() != null) {
                List<Pet> copyList = new ArrayList<>();
                NodeDE current = head;
                while (current != null) {
                    copyList.add(current.getData());
                    current = current.getNext();
                }

                // Intercambiar los datos de los nodos extremos
                Pet tempData = copyList.get(0);
                copyList.set(0, copyList.get(copyList.size()-1));
                copyList.set(copyList.size()-1, tempData);

                // Actualizar los datos en la lista original
                current = head;
                for (int i = 0; i < copyList.size(); i++) {
                    current.setData(copyList.get(i));
                    current = current.getNext();
                }
            } else {
                throw new Exception("La lista tiene menos de 2 elementos.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }




    public void gainPosition(String id, int position, ListDE listDE) {
        try {
            Objects.requireNonNull(id, "El objeto no puede ser vacio");

            if (head != null) {
                NodeDE temp = head;
                int count = 1;

                while (temp != null && !temp.getData().getId().equals(id)) {
                    temp = temp.getNext();
                    count++;
                }
                int newPosition = position - count;
                Pet listCopy = temp.getData();
                listDE.deletePetById(temp.getData().getId());
                listDE.addByPosition(listCopy, newPosition);

            }
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("El objeto no puede ser vacio");
        }
    }


    public void backPosition(String id, int position, ListDE listDE) {
        try {
            if (head != null) {
                NodeDE temp = head;
                int count = 1;

                while (temp != null && !temp.getData().getId().equals(id)) {
                    temp = temp.getNext();
                    count++;
                }

                int newPosition = position + count;
                Pet listCopy = temp.getData();
                listDE.deletePetById(temp.getData().getId());
                listDE.addByPosition(listCopy, newPosition);
            }
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("El objeto no puede ser vacio");
        }
    }



    public void orderBoysToStart() {
        try {
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
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("La cabeza no puede ser nula");
        }
    }

    public void intercalateByGender() {
        try {
            ListDE boysList = new ListDE();
            ListDE girlsList = new ListDE();

            NodeDE current = head;
            while (current != null) {
                if (current.getData().getGender() == 'M') {
                    boysList.add(current.getData());
                } else {
                    girlsList.add(current.getData());
                }
                current = current.getNext();
            }

            ListDE combinedList = new ListDE();
            NodeDE boysCurrent = boysList.getHead();
            NodeDE girlsCurrent = girlsList.getHead();
            while (boysCurrent != null && girlsCurrent != null) {
                combinedList.add(boysCurrent.getData());
                combinedList.add(girlsCurrent.getData());
                boysCurrent = boysCurrent.getNext();
                girlsCurrent = girlsCurrent.getNext();
            }

            while (boysCurrent != null) {
                combinedList.add(boysCurrent.getData());
                boysCurrent = boysCurrent.getNext();
            }

            while (girlsCurrent != null) {
                combinedList.add(girlsCurrent.getData());
                girlsCurrent = girlsCurrent.getNext();
            }

            head = combinedList.getHead();
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("La lista no puede ser nula");
        }
    }

    public int getCountPetsByLocation(String code) {
        try {
            Objects.requireNonNull(code, "El código de localización no puede ser nulo");
            int count = 0;
            NodeDE current = head;
            while (current != null) {
                if (current.getData().getLocation().getCode().equals(code)) {
                    count++;
                }
                current = current.getNext();
            }
            return count;
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("El código de localización no puede ser nulo");
        }
    }


    public int getCountPetByDeptoCode(String code) {
        int count = 0;
        if (head != null) {
            NodeDE temp = head;
            while (temp != null) {
                try {
                    String locationCode = temp.getData().getLocation().getCode();
                    if (locationCode.substring(0, 5).equals(code)) {
                        count++;
                    }
                } catch (NullPointerException e) {

                    System.out.println("Error: La locacion no existe");
                }
                temp = temp.getNext();
            }
        }
        return count;
    }

    public void getReportPetsByLocationByGendersByAge(byte age, ReportPetsLocationDTO report) {
        try {
            if (head != null) {
                NodeDE temp = head;
                while (temp != null) {
                    if (temp.getData().getAge() > age) {
                        report.updateQuantity(temp.getData().getLocation().getName(), temp.getData().getGender());
                    }
                    temp = temp.getNext();
                }
            }
        } catch (Exception e) {
            System.out.println("Se ha producido un error al generar el informe: " + e.getMessage());
        }
    }
    public int informRangeByAge(int first, int last) throws IllegalArgumentException {
        if (first < 0 || last < 0 || first > last) {
            throw new IllegalArgumentException("Invalid age range");
        }
        NodeDE temp = head;
        int count = 0;
        while (temp != null){
            if (temp.getData().getAge() >= first && temp.getData().getAge() <= last){
                count ++;
            }
            temp = temp.getNext();
        }
        return count;
    }

}
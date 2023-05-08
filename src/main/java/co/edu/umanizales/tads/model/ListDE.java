package co.edu.umanizales.tads.model;


import co.edu.umanizales.tads.controller.dto.ReportAgeQuantityPetsDTO;
import co.edu.umanizales.tads.controller.dto.ReportPetsLocationDTO;
import lombok.Data;

import java.util.List;
import java.util.Objects;

@Data
public class ListDE {
    private List<Pet> pets;
    private List<Owner> owners;

    private NodeDE head;
    private int size;
    private NodeDE tail;

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


    public void deletePetById(String id) throws IllegalArgumentException {
        try {
            Objects.requireNonNull(id, "Pet ID cannot be null");

            if (head == null) {
                return;
            }
            if (head.getData().getId().equals(id)) {
                head = head.getNext();
                if (head != null) {
                    head.setPrev(null);
                }
                size--;
                return;
            }
            NodeDE current = head.getNext();
            while (current != null) {
                if (current.getData().getId().equals(id)) {
                    current.getPrev().setNext(current.getNext());
                    if (current.getNext() != null) {
                        current.getNext().setPrev(current.getPrev());
                    }
                    size--;
                    return;
                }
                current = current.getNext();
            }
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("Pet ID cannot be null");
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
            if (head == null || head.getNext() == null) {
                return;
            }
            NodeDE temp = head;
            while (temp.getNext() != null) {
                temp = temp.getNext();
            }
            // temp está en el último nodo
            NodeDE tempPrev = temp.getPrev();
            NodeDE tempNext = head.getNext();

            // Actualizar el enlace previo del nodo temporal
            tempPrev.setNext(null);
            temp.setPrev(null);

            // Actualizar enlaces del nodo de la cabeza
            head.setNext(null);
            head.setPrev(temp);
            temp.setNext(head);

            // Actualizar enlaces del nodo final
            tempNext.setPrev(head);
            tempPrev.setNext(temp);

            // Actualizar la cabeza
            head = temp;
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("El objeto no puede ser vacio");
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

                if (temp != null) {
                    int newPosition = position - count;
                    Pet listCopy = temp.getData();
                    listDE.deletePetById(temp.getData().getId());
                    listDE.addByPosition(listCopy, newPosition);
                }
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


    /*
   Se verifica si la lista tiene elementos, es decir, si head" es igual a null. Si no hay elementos en la lista,
   el método retorna.

   Se crea un nodo temporal temp que va a ser igual al elemento que se está buscando en la lista.
   Se inicializa el nodo temporal con null y se establece su identificador igual al identificador que se desea eliminar.


   Se verifica si el primer elemento cabeza contiene el elemento que se desea eliminar.
   Si es así, se establece el siguiente elemento como la nueva cabeza y se establece el nodo previo de la nueva cabeza como nulo.
   Luego, el método retorna.

   Si el elemento que se desea eliminar no es la cabeza de la lista,
   se busca en la lista el nodo que contiene el identificador que se desea eliminar.

   Si el nodo que contiene el identificador se encuentra en la lista,
   se establece el nodo siguiente del nodo previo como el nodo siguiente del nodo actual y
   se establece el nodo previo del nodo siguiente como el nodo previo del nodo actual.

   Si el nodo que contiene el identificador no se encuentra en la lista, el método retorna sin hacer nada.
   */

    public void deletePetByIdTwo(String id) {
        // Verificamos si la lista tiene elementos
        if (head == null) {
            return;
        }
        // Creamos un nodo temporal que va a ser igual al elemento que estoy buscando
        NodeDE temp = new NodeDE(null);
        temp.getData().setId(id);

        // Si el primer elemento "cabeza" contiene el elemento que deseo eliminar
        if (head.getData().getId().equals(id)) {
            // Establecemos el siguiente como la "cabeza" y establecemos el nodo previo de la nueva cabeza como nulo
            head = head.getNext();
            if (head != null) {
                head.setPrev(null);
            }
            return;
        }


        public void intercalateByGender () {
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

        int getCountPetsByLocationStringString;
        String String;
        (String String code;
        String = code;){
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

        public int getCountPetByDeptoCode (String code){
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

        public void getReportPetsByLocationByGendersByAge ( byte age, ReportPetsLocationDTO
        ReportPetsLocationDTO report;
        report){
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

        public void getReportPetsByAgeByGender ( byte age, ReportAgeQuantityPetsDTO report){
            try {
                if (head != null) {
                    NodeDE temp = head;
                    while (temp != null) {
                        if (temp.getData().getAge() == age) {
                            report.updateQuantity(temp.getData().getAge(), temp.getData().getGender());
                        }
                        temp = temp.getNext();
                    }
                }
            } catch (Exception e) {
                System.out.println("Error obteniendo reporte: " + e.getMessage());
            }
        }

    }

    public void getReportPetsByAgeByGender(byte age, ReportAgeQuantityPetsDTO report) {
    }

    public void getReportPetsByLocationByGendersByAge(byte age, ReportPetsLocationDTO report) {
    }
}





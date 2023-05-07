package co.edu.umanizales.tads.model;

import co.edu.umanizales.tads.controller.dto.*;
import co.edu.umanizales.tads.service.ListSEService;
import lombok.Data;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

@Data
@Getter
public class ListSE {
    private Node head;

    private int size;
    private List<Kid> kids;

    /*
     Algoritmo de adicionar al final

     como Entreada recibimos a
        un niño
     si hay datos // pueden haber dos opciones por las que puedo tomar

     si
            llamar a un ayudante que se posicione en la cabeza y  compruebe si en el brazo
            hay algo, si existe ese algo, paso al siguiente
            hasta llegar al ultimo

           al llegar al ultimo meter al niño en un costal (nuevo costal)
            y le digo al ultimo que tome el nuevo costal y lo ubique en el ultimo lugar

      no

      metemos al niño en ese costal y ese costal es la cabeza

     */
    public void add(Kid kid) throws IllegalArgumentException {
        Objects.requireNonNull(kid, "Kid object cannot be null");

        try {
            Node newNode = new Node(kid);
            Optional<Node> lastNode = Optional.ofNullable(head);
            while (lastNode.isPresent() && lastNode.get().getNext() != null) {
                lastNode = Optional.ofNullable(lastNode.get().getNext());
            }
            lastNode.orElseThrow(NullPointerException::new).setNext(newNode);
        } catch (NullPointerException e) {
            head = new Node(kid);
        }
        size++;
    }




    /*
    Adicionar al inicio
    si hay datos
    puedo tener dos opciones
    si
    meto al niño en un costal (nuevo costal)
    le digo a nuevo costal que tome con su brazo a la cabeza
    cabea es igual a nuevo costal
    no
    meto al niño en un cosata y lo asigno a la cabezza
    */

    public void addToStart(Kid kid) {
        Node newNode = new Node(kid);
        try {
            newNode.setNext(head);
        } catch (NullPointerException e) {
            head = newNode;
        }
        size++;
    }


    public void deleteByIdentification(String identification) throws NoSuchElementException {
        Objects.requireNonNull(identification, "La cadena de identificación no puede ser nula");

        try {
            if (head.getData().getIdentification().equals(identification)) {
                head = head.getNext();
                size--;
                return;
            }
        } catch (NullPointerException e) {
            throw new NoSuchElementException("La lista está vacía");
        }

        Node current = head.getNext();
        Node previous = head;

        while (current != null) {
            try {
                if (current.getData().getIdentification().equals(identification)) {
                    previous.setNext(current.getNext());
                    size--;
                    return;
                }
            } catch (NullPointerException e) {
                throw new NoSuchElementException("Identificación no encontrada");
            }
            previous = current;
            current = current.getNext();
        }

        throw new NoSuchElementException("Identificación no encontrada");
    }

    public void deleteByAge(int age) {
        try {
            while (head != null && head.getData().getAge() == age) {
                System.out.println("Eliminando un niño con la edad de " + age);
                head = head.getNext();
            }

            Node current = head;
            Node previous = null;

            while (current != null) {
                if (current.getData().getAge() == age) {
                    System.out.println("Eliminando un niño con la edad de " + age);
                    previous.setNext(current.getNext());
                } else {
                    previous = current;
                }
                current = current.getNext();
            }
        } catch (NullPointerException e) {
            System.out.println("La lista está vacía.");
        }
    }



    public boolean checkKidByIdentification(String identification) {
        try {
            Node temp = head;
            while (temp != null) {
                if (temp.getData().getIdentification().equals(identification)) {
                    return true;
                }
                temp = temp.getNext();
            }
        } catch (NullPointerException e) {
            System.out.println("Error: La lista está vacía.");
        }
        return false;
    }



    //metodo para añadir por posicion
    public void addByPosition(Kid kid, int position) {
        Node newNode = new Node(kid);
        try {
            if (position <= 1) {
                addToStart(kid);
            } else {
                Node current = head;
                for (int i = 1; i < position - 1; i++) {
                    current = current.getNext();
                }
                newNode.setNext(current.getNext());
                current.setNext(newNode);
            }
        } catch (NullPointerException e) {
            System.out.println("La lista está vacía.");
        }
    }



    public double getAverageAge() {
        int totalAge = 0;
        int numKids = 0;

        try {
            Node temp = head;

            while (temp != null) {
                Kid kid = temp.getData();
                totalAge += kid.getAge();
                numKids++;
                temp = temp.getNext();
            }

            return (double) totalAge / numKids;

        } catch (NullPointerException e) {
            System.out.println("La lista está vacía.");
            return 0.0;
        }
    }


    //metodo para intercambiar extremos
    public void changeExtremes() {
        try {
            if (head != null && head.getNext() != null) {
                Node temp = head;
                while (temp.getNext() != null) {
                    temp = temp.getNext();
                }
                // temp está en el último nodo
                Kid copy = head.getData();
                head.setData(temp.getData());
                temp.setData(copy);
            } else {
                throw new Exception("La lista tiene menos de 2 elementos.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void invert() {
        try {
            ListSE listCp = new ListSE();
            Node temp = this.head;
            while (temp != null) {
                listCp.addToStart(temp.getData());
                temp = temp.getNext();
            }
            this.head = listCp.head;
        } catch (NullPointerException e) {
            System.out.println("La lista está vacía.");
        }
    }

    public void gainPosition(String id, int position, ListSE listSE) {
        try {
            Node temp = this.head;
            int count = 1;

            while (temp != null && !temp.getData().getIdentification().equals(id)) {
                temp = temp.getNext();
                count++;
            }

            if (temp == null) {
                throw new IllegalArgumentException("No se encontró un niño con la identificación especificada.");
            }

            int newPosition = position - count;

            if (newPosition < 1) {
                throw new IllegalArgumentException("La nueva posición debe ser mayor o igual a 1.");
            }

            Kid listCopy = temp.getData();
            this.deleteByIdentification(id);
            listSE.addByPosition(listCopy, newPosition);
        } catch (NullPointerException e) {
            System.out.println("La lista está vacía.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }


    public void backPosition(String id, int position, ListSE listSE){
        try {
            Node temp = this.head;
            int count = 1;

            while (temp != null && ! temp.getData().getIdentification().equals(id)){
                temp = temp.getNext();
                count ++;
            }
            int newPosition = position+count-1;
            Kid listCopy = temp.getData();
            listSE.deleteByIdentification(temp.getData().getIdentification());
            listSE.addByPosition(listCopy , newPosition);
        } catch (NullPointerException e) {
            System.out.println("La lista está vacía o el nodo con la identificación especificada no existe.");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("La posición especificada está fuera de los límites de la lista.");
        }
    }

    public void orderBoysToStart() {
        try {
            ListSE listCp = new ListSE();
            Node temp = this.head;
            while (temp != null) {
                if (temp.getData().getGender() == 'M') {
                    listCp.addToStart(temp.getData());
                } else {
                    listCp.add(temp.getData());
                }
                temp = temp.getNext();
            }
            this.head = listCp.getHead();
        } catch (NullPointerException e) {
            System.out.println("La lista está vacía.");
        }
    }

    public void intercalateByGender() {
        try {
            ListSE boysList = new ListSE();
            ListSE girlsList = new ListSE();

            Node current = head;
            while(current != null) {
                if(current.getData().getGender() == 'M') {
                    boysList.add(current.getData());
                } else {
                    girlsList.add(current.getData());
                }
                current = current.getNext();
            }

            ListSE combinedList = new ListSE();
            Node boysCurrent = boysList.getHead();
            Node girlsCurrent = girlsList.getHead();
            while(boysCurrent != null && girlsCurrent != null) {
                combinedList.add(boysCurrent.getData());
                combinedList.add(girlsCurrent.getData());
                boysCurrent = boysCurrent.getNext();
                girlsCurrent = girlsCurrent.getNext();
            }

            while(boysCurrent != null) {
                combinedList.add(boysCurrent.getData());
                boysCurrent = boysCurrent.getNext();
            }

            while(girlsCurrent != null) {
                combinedList.add(girlsCurrent.getData());
                girlsCurrent = girlsCurrent.getNext();
            }

            head = combinedList.getHead();
        } catch (Exception e) {
            System.out.println("Error intercalating by gender: " + e.getMessage());
            e.printStackTrace();
        }
    }




    public int getCountKidsByLocation(String code) {
        int count = 0;
        try {
            Node temp = this.head;
            while (temp != null && !temp.getData().getLocation().getCode().equals(code)) {
                temp = temp.getNext();
            }
            count = temp != null ? 1 : 0;
        } catch (NullPointerException e) {
            System.out.println("Error: Argumento nulo pasado al método.");
        }
        return count;
    }



    public int getCountKidsByDeptoCode(String code){
        int count = 0;
        Node temp = this.head;
        while(temp != null){
            try {
                String deptoCode = temp.getData().getLocation().getCode().substring(0, 5);
                if (deptoCode.equals(code)) {
                    count++;
                }
            } catch (StringIndexOutOfBoundsException e) {
                // do nothing, just skip this kid
            }
            temp = temp.getNext();
        }
        return count;
    }



    public void getReportKidsByLocationByGendersByAge(byte age, ReportKidsLocationDTO report) {
        try {
            if (head != null) {
                Node temp = this.head;
                while (temp != null) {
                    if (temp.getData().getAge() > age) {
                        report.updateQuantity(temp.getData().getLocation().getName(), temp.getData().getGender());
                    }
                    temp = temp.getNext();
                }
            }
        } catch (NullPointerException e) {
            System.out.println("Error: Argumento nulo pasado al método.");
        }
    }

    public void getReportKidsByAgeByGender(byte age, ReportAgeQuantityKidsDTO report) {
        try {
            if (this.head == null) {
                throw new NullPointerException("Lista vacía");
            }

            Node temp = this.head;
            while (temp != null) {
                if (temp.getData().getAge() == age) {
                    report.updateQuantity(temp.getData().getAge(), temp.getData().getGender());
                }
                temp = temp.getNext();
            }

        } catch (NullPointerException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


}

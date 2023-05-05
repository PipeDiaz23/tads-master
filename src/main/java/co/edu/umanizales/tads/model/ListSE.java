package co.edu.umanizales.tads.model;

import co.edu.umanizales.tads.controller.dto.KidByPositionDTO;
import co.edu.umanizales.tads.controller.dto.ReportKidsLocationGenderDTO;
import co.edu.umanizales.tads.controller.dto.ResponseDTO;
import lombok.Data;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
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
    Entrada
        un niño
    si hay datos
    si
        llamo a un ayudante y le digo que se posicione en la cabeza
        mientras en el brazo exista algo
            pasese al siguiente
        va estar ubicado en el ùltimo

        meto al niño en un costal (nuevo costal)
        y le digo al ultimo que tome el nuevo costal
    no
        metemos el niño en el costal y ese costal es la cabeza
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

    /* Adicionar al inicio
    si hay datos
    si
        meto al niño en un costal (nuevocostal)
        le digo a nuevo costal que tome con su brazo a la cabeza
        cabeza es igual a nuevo costal
    no
        meto el niño en un costal y lo asigno a la cabeza
     */
    public void addToStart(Kid kid)
    {
        if(head !=null)
        {
            Node newNode = new Node(kid);
            newNode.setNext(head);
            head = newNode;
        }
        else {
            head = new Node(kid);
        }
        size++;
    }

    public void invert(){
        if(this.head !=null){
            ListSE listCp = new ListSE();
            Node temp = this.head;
            while(temp != null){
                listCp.addToStart(temp.getData());
                temp = temp.getNext();
            }
            this.head = listCp.getHead();
        }
    }

    public void orderBoysToStart(){
        if(this.head !=null){
            ListSE listCp = new ListSE();
            Node temp = this.head;
            while(temp != null){
                if(temp.getData().getGender()=='M')
                {
                    listCp.addToStart(temp.getData());
                }
                else{
                    listCp.add(temp.getData());
                }

                temp = temp.getNext();
            }
            this.head = listCp.getHead();
        }
    }

    public void changeExtremes(){
        if(this.head !=null && this.head.getNext() !=null)
        {
            Node temp = this.head;
            while(temp.getNext()!=null)
            {
                temp = temp.getNext();
            }
            //temp está en el último
            Kid copy = this.head.getData();
            this.head.setData(temp.getData());
            temp.setData(copy);
        }

    }

    public int getCountKidsByLocationCode(String code){
        int count =0;
        if( this.head!=null){
            Node temp = this.head;
            while(temp != null){
                if(temp.getData().getLocation().getCode().equals(code)){
                    count++;
                }
                temp = temp.getNext();
            }
        }
        return count;
    }

    public boolean checkKidByIdentification(String identification) {
        if (this.head != null) {
            Node temp = this.head;
            while (temp != null) {
                if (temp.getData().getIdentification().equals(identification)) {
                    return true;
                }
                temp = temp.getNext();
            }
        }
        return false;
    }

    //metodo para añadir por posicion
    public void addByPosition(Kid kid , int position){
        Node newNode= new Node(kid);
        if(position<=1){
            addToStart(kid);
        }else{
            Node current= head;
            for (int i=1 ; i<position-1;i++){
                current=current.getNext();
            }
            newNode.setNext(current.getNext());
            current.setNext(newNode);
        }
    }

    public double getAverAge() {
        int totalAge=0;
        int numKids=0;
        Node temp = this.head;
        while(temp != null){
            Kid kid = temp.getData();
            totalAge +=kid.getAge();
            numKids++;
            temp = temp.getNext();
        }

        if (numKids > 0) {
            return (double) totalAge / numKids;
        } else {
            return 0.0;
        }
    }


   public int getCountKidsByCityByAgeBygender(String code,char gender,byte age){
        int count=0;
        if (this.head !=null){
            Node temp = this.head;
            while (temp!=null){
                if (temp.getData().getLocation().getCode().equals(code)
                          && temp.getData().getGender() == gender && temp.getData().getAge() > age){
                    count++;
                }
                temp = temp.getNext();
            }
        }
        return count;
    }

    public int getCountKidsByDeptoCode(String code){
        int count =0;
        if( this.head!=null){
            Node temp = this.head;
            while (temp != null){
                if (temp.getData().getLocation().getCode().substring(0,5).equals(code)){
                    count++;
                }
                temp = temp.getNext();
            }
        }
        return count;
    }

    public void getReportKidsBylocationGendersByAge(byte age, ReportKidsLocationGenderDTO report){
        if(head !=null){
            Node temp = this.head;
            while (temp!=null){
                if (temp.getData().getAge()>age){
                    report.updateQuantity(temp.getData().getLocation().getName(),temp.getData().getGender());
                }
                    temp = temp.getNext();
            }
        }
    }

}

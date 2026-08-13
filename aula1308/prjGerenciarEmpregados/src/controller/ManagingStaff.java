package controller;

import model.Employee;

import java.util.Arrays;

public class ManagingStaff {

    public ManagingStaff(){

    }

    public static void main(String[] args) {
        Employee[] employees = {
                new Employee("Marcos", "Moraes", 20000, "TI"),
                new Employee("Joao", "Silva", 3000, "TI"),
                new Employee("Maria", "Santos", 5000, "MEC"),
                new Employee("Ana", "Goncalves", 8000, "MEC")
        };
        var lista = Arrays.asList(employees);
        System.out.println("Lista de todos os empregados");
        lista
                .stream()
                .filter(emp -> emp.getDepartment().equals("TI"))
                .forEach(System.out::println);
    }
}

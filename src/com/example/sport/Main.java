package com.example.sport;

import java.util.Scanner;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;



/*
2020
Code Written By: Rayhan B.
*/

public class Main extends AbstractCollective {
    private static Scanner scanner = new Scanner(System.in);
    static ReentrantLock lock = new ReentrantLock();
    private static final int classsId = 0;    //Identification purposes; not implemented yet
    private static Scanner stateScanner = new Scanner(System.in);
    private static Scanner countryScanner = new Scanner(System.in);




    public static void main(String[] args) {

        printList();
        int choice = scanner.nextInt();

        switch (choice) {
            case 0:
                lock.lock(); //Thread-Safe precaution
                try {
                    System.out.println("Enter a valid state in the USA to get data for");
                    String getState = stateScanner.nextLine();

                    System.out.println("How many times a day do you want the data?");
                    int choiceValueOfState = stateScanner.nextInt();

                    new Thread(new ThreeAtOnceState(choiceValueOfState, getState)).start(); //Threads used to allow more functionality and sophistication
                }finally {
                    lock.unlock();
                }
                break;
            case 1:
                lock.lock();//Thread-Safe precaution
                try{
                    new Thread(new ThreeAtOnce()).start();

                }finally {
                    lock.unlock();
                }

                break;
            case 2:
                lock.lock();//Thread-Safe precaution
                try{
                    System.out.println("Enter full name of country to get data for");
                    String getCountry = countryScanner.nextLine();
                    System.out.println("How many times a day do you want the data?");
                    int choiceValueOfCountry = countryScanner.nextInt();
                    new Thread(new ThreeAtOnceCountry(choiceValueOfCountry, getCountry)).start();
                }finally {
                    lock.unlock();
                }
                break;
            default://If user input is not recognized
                System.out.println("Invalid Argument, enter valid number");
                break;
        }
    }
    public static int getClasssId() {
        return classsId;
    }
    public static void printList() {
        System.out.println("Press 0 if you want to get data for a state in the USA");
        System.out.println("Press 1 if you want to get worldwide data");
        System.out.println("Press 2 if you want individual country's data");
    }
}

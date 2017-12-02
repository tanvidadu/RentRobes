package com.example.tanvidadu.learnit;

/**
 * Created by Dell on 12/2/2017.
 */
import java.util.Collection;
import java.util.HashSet;
import java.util.Random;

public class UniqueRandom {

    static Random rnd=new Random();


    private static Collection<Integer> alreadyChosen = new HashSet<Integer>();




    public static int getNextUniqueRandom(){
        if (alreadyChosen.size()==90000){ //hardcoded 5 figure numbers, consider making a variable
            throw new RuntimeException("All 5 figure IDs used");
        }


        boolean unique=false;
        int value=0;
        while(unique==false){
            value=rnd.nextInt(90000)+10000;
            unique=!alreadyChosen.contains(value);
        }
        alreadyChosen.add(value);
        return value;
    }

}
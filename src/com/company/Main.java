package com.company;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final List<Integer> depthValueList = new ArrayList<>();

    public static void main(String[] args) {
        Path path = FileSystems.getDefault().getPath("data", "melyseg.txt");

        try(Scanner input = new Scanner(Files.newBufferedReader(path))) {
            while(input.hasNextLine()) {
                int value = Integer.parseInt(input.nextLine());
                depthValueList.add(value);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //1. feladat
        System.out.printf("1. feladat\nA fájl adatainak száma: %d\n\n", depthValueList.size());

        //2. feladat
        System.out.println("2. feladat\nAdjon meg egy távolságértéket!");
        Scanner input = new Scanner(System.in);
        int value = input.nextInt() - 1;
//        int value = 9;

        System.out.printf("Ezen a helyen a felszín %d méter mélyen van.\n\n", depthValueList.get(value));

        //3. feladat
        int count = 0;
        for(int i : depthValueList) {
            if(i == 0) count++;
        }

        System.out.printf("3. feladat\nAz érintetlen terület arány %.2f%%.\n\n", (float) count / depthValueList.size() * 100);

        //5. feladat
        Path outputPath = FileSystems.getDefault().getPath("data", "godrok.txt");

        int y = 0;
        List<Pot> potholeList = new ArrayList<>();
        List<Integer> tempList = new ArrayList<>();
        int start, end;
        while(y < depthValueList.size()) {
            if(depthValueList.get(y) == 0) y++;
            else {
                start = y;
                while(depthValueList.get(y) != 0) {
                    tempList.add(depthValueList.get(y));
                    y++;
                }
                end = y - 1;

                potholeList.add(new Pot(tempList, start, end));

                tempList = new ArrayList<>();
            }
        }

        try(BufferedWriter output = Files.newBufferedWriter(outputPath)) {
            for(int i=0; i<potholeList.size(); i++) {
                for(int j : potholeList.get(i).getDepthList()) {
                    output.write(j+" ");
                }
                if(i != potholeList.size()-1) output.write("\r\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //5. feladat
        System.out.printf("5. feladat\nA gödrök száma: %d\n\n", potholeList.size());

        //6. feladat
        System.out.println("6.feladat");

        Pot foundPot = null;
        for(Pot p : potholeList) {
            if(p.insidePot(value)) {
                foundPot = p;
                break;
            }
        }

        if(foundPot != null) {
            //a)
            System.out.printf("a)\nA gödör kezdete: %d méter, a gödör vége: %d méter.\n", foundPot.getStartingIndex(), foundPot.getEndingIndex());

            //b)
            if(foundPot.gettingDeaper()) {
                System.out.println("b)\nFolyamatosan mélyül.");
            } else {
                System.out.println("b)\nNem mélyül folyamatosan.");
            }
            //c)
            System.out.printf("c)\nA legnagyobb mélysége %d méter.\n", foundPot.getDeapestPoint());
            //d)
            System.out.printf("d)\nA térfogata %d m^3.\n", foundPot.getVolume());
            //e)
            System.out.printf("e)\nA vízmennyiség %d m^3.\n", foundPot.getWaterLevel());
        } else {
            System.out.println("a)\nAz adott helyen nincs gödör\n");
        }
    }
}
package com.example.yuriy.multithredsorter.incommingData;

import android.content.Context;


import com.example.yuriy.multithredsorter.model.Car;
import com.example.yuriy.multithredsorter.model.Mechanizm;
import com.example.yuriy.multithredsorter.model.Plane;
import com.example.yuriy.multithredsorter.model.Ship;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

import static com.example.yuriy.multithredsorter.incommingData.DataParser.parseForMechanizmData;


public class TaskGeneratorImpl implements TaskGenerator {

    private final static String TAG = TaskGeneratorImpl.class.getSimpleName();

    private static final int SHIP = 0;
    private static final int PLANE = 1;
    private static final int CAR = 2;
    private static final int TASKS_SIZE = 3;  // 0,1,2
    private static final int ARRAY_CAPACITY_MAX = 50;
    private static final int ARRAY_CAPACITY_MIN = 1;

    private ArrayList<String> carsDataSource;
    private ArrayList<String> planesDataSource;
    private ArrayList<String> shipsDataSource;

    private Context applicationContext;

    private static TaskGeneratorImpl instance;

    private TaskGeneratorImpl(Context context) {
        applicationContext = context;
        initDataSource();
    }


    //should run from application context
    public static void init(Context context) {
        instance = new TaskGeneratorImpl(context);
    }

    public static TaskGeneratorImpl get() {
        if (instance == null) {
            throw new RuntimeException("task generator should be initialized before in Application context");
        }
        return instance;
    }

    @Override

    public Mechanizm[][] getMultiTask() {
        Mechanizm[][] multiTaskData = new Mechanizm[TASKS_SIZE][];
        for (int i = 0; i <multiTaskData.length ; i++) {
            multiTaskData[i] = getSingleTask();
        }

        return multiTaskData;
    }

    public Mechanizm[] getSingleTask() {
        int type = generateRandomType();
        int arraySize = generateRandomArraySize();
        Mechanizm[] mechanizmList = new Mechanizm[arraySize];

        populateArrayWithData(mechanizmList, type);

        return mechanizmList;
    }


    private void initDataSource() {
        String carsJson = readJsonFromAssets("cars");
        String planesJson = readJsonFromAssets("planes");
        String shipsJson = readJsonFromAssets("ships");

        carsDataSource = parseForMechanizmData(carsJson);
        planesDataSource = parseForMechanizmData(planesJson);
        shipsDataSource = parseForMechanizmData(shipsJson);

    }


    private int generateRandomType() {
        int randomType = new Random().nextInt(3);  // [0...2] [min = 0, max = 2]
        return randomType;
    }

    private int generateRandomArraySize() {
        int arrayCapacity = new Random().nextInt((ARRAY_CAPACITY_MAX - ARRAY_CAPACITY_MIN) + 1) + ARRAY_CAPACITY_MIN;
        return arrayCapacity;
    }

    private void populateArrayWithData(Mechanizm[] mechanizmList, int type) {

        switch (type) {
            case SHIP:
                for (int i = 0; i < mechanizmList.length; i++) {
                    Ship newEntity = new Ship();
                    newEntity.setId(i);
                    String mehanizmName = getRandomMechanizmName(shipsDataSource);
                    newEntity.setName(mehanizmName);
                    mechanizmList[i] = newEntity;
                }
                break;
            case PLANE:
                for (int i = 0; i < mechanizmList.length; i++) {
                    Plane newEntity = new Plane();
                    newEntity.setId(i);
                    String mehanizmName = getRandomMechanizmName(planesDataSource);
                    newEntity.setName(mehanizmName);
                    mechanizmList[i] = newEntity;
                }
                break;

            case CAR:
                for (int i = 0; i < mechanizmList.length; i++) {
                    Car newEntity = new Car();
                    newEntity.setId(i);
                    String mehanizmName = getRandomMechanizmName(carsDataSource);
                    newEntity.setName(mehanizmName);
                    mechanizmList[i] = newEntity;
                }
                break;
            default:
                try {
                    throw new Exception("unsupported mechanizm type");
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
    }

    private String getRandomMechanizmName(ArrayList<String> dataNames) {

//        Log.e("wrf", dataNames.size() + "");

        int randomIndex = new Random().nextInt(dataNames.size());  // [0...2] [min = 0, max = 2]
//        Log.e("wrf", randomIndex + "");
//        Log.e("wrf", dataNames.get(randomIndex) + "");
        return dataNames.get(randomIndex);
    }

    public String readJsonFromAssets(String fileName) {
        String json = null;
        try {
            InputStream is = applicationContext.getAssets().open(fileName + ".json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return json;
    }
}

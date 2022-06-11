package com.api.aircraftsimulationapi.model.helpers.test;

import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Random;

@Getter
@Setter
public class ParameterGenSample extends Thread {
    private String code;
    private int samplingRate;
    private Integer minValue;
    private Integer maxValue;
    private double value;
    private Random generatorValue;

    public ParameterGenSample(String code, int samplingRate, int minValue, int maxValue) {
        this.code = code;
        this.samplingRate = samplingRate;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.generatorValue = new Random();
    }

    public void writeToFile(String result) {
        // Finding file created
        try{
            TestEngine.file = Paths.get(TestEngine.fileName);
        }catch(InvalidPathException e){
            e.printStackTrace();
        }

        //Getting timestamp
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        Date date = new Date();

        // Writing on file
        try{
            String sample = formatter.format(date) +
                    "," +
                    TestEngine.aircraft.getAircraftCode() +
                    "," +
                    TestEngine.testNumber +
                    "," +
                    this.code +
                    "," +
                    this.minValue +
                    "," +
                    this.maxValue +
                    "," +
                    this.value +
                    "," +
                    result +
                    "\n";
            Files.writeString(TestEngine.file,sample,StandardOpenOption.APPEND);
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    public double generateSample() {
        // Generate sample
        double min = this.minValue - (this.minValue * 0.01);
        double max = this.maxValue + (this.maxValue * 0.01);

        this.value =  (min) + (max - min) * this.generatorValue.nextDouble();
        return this.value;
    }
}

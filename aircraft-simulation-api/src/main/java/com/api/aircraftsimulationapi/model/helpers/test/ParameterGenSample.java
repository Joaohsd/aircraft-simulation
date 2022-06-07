package com.api.aircraftsimulationapi.model.helpers.test;

import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Date;
import java.util.Random;

@Getter
@Setter
public class ParameterGenSample extends Thread{
    private String code;
    private int samplingRate;
    private Integer minValue;
    private Integer maxValue;
    private double value;
    private boolean sampleGenerated;
    private Random generatorValue;

    public ParameterGenSample(String code, int samplingRate, int minValue, int maxValue) {
        this.code = code;
        this.samplingRate = samplingRate;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.sampleGenerated = true;
        this.generatorValue = new Random();
    }

    @Override
    public void run() {
        this.sampleGenerated = false;
        // Generate sample
        this.value =  this.minValue + (this.maxValue - this.minValue) * this.generatorValue.nextDouble();
        System.out.println(this.value);

        // Finding file created
        try{
            TestEngine.file = Paths.get(TestEngine.fileName);
        }catch(InvalidPathException e){
            e.printStackTrace();
        }

        // Writing on file
        try{
            String sample = System.currentTimeMillis() +
                              "," +
                              TestEngine.aircraft.getAircraftCode() +
                              "," +
                              TestEngine.testNumber +
                              "," +
                              this.code +
                              "," +
                              this.value +
                              "\n";
            Files.writeString(TestEngine.file,sample,StandardOpenOption.APPEND);
        } catch(IOException e){
            e.printStackTrace();
        }

        // Wait a time to get the next sample
        try {
            Thread.sleep(TestEngine.CONVERT_TO_MILLIS/this.samplingRate);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        this.sampleGenerated = true;
    }
}

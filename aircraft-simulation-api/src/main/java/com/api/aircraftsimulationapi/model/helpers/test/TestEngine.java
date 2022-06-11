package com.api.aircraftsimulationapi.model.helpers.test;

import com.api.aircraftsimulationapi.model.entities.Aircraft;
import com.api.aircraftsimulationapi.model.entities.Parameter;
import com.api.aircraftsimulationapi.model.services.TestDataService;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Stream;

@Getter
@Setter
public abstract class TestEngine {
    public static Aircraft aircraft;
    public static long time;
    public static int testNumber;
    public static String fileName;
    public static Path file;
    public static List<ParameterGenSample> parameterGenSamples= new Vector<>();
    public static final int SECONDS_PER_MINUTE = 60;
    public static final int CONVERT_TO_MILLIS = 1000;

    public static TestClient testClient;

    public static void runTest(){
        Set<Parameter> parameters = aircraft.getParameters();

        parameterGenSamples.clear();

        time*=SECONDS_PER_MINUTE*CONVERT_TO_MILLIS;

        long initial = System.currentTimeMillis();
        long end = initial+time;

        parameters.forEach((parameter) -> {
            ParameterGenSample parameterGenSample = new ParameterGenSample(parameter.getCode(),
                                                                           parameter.getSamplingRate(),
                                                                           parameter.getMinValue(),
                                                                           parameter.getMaxValue());
            parameterGenSamples.add(parameterGenSample);
        });

        Date date = new Date();
        fileName = aircraft.getAircraftCode()+ "_" +testNumber+ "_" + date.getDay() + "-" + date.getMonth() + "-" + date.getYear() + ".csv";

        // Finding file created
        try{
            file = Paths.get(fileName);
        }catch(InvalidPathException e){
            e.printStackTrace();
        }

        // Writing on file
        try{
            Files.writeString(file,"timeStamp,aircraftCode,testNumber,parameterCode,minValue,maxValue,currentValue,Result\n",StandardOpenOption.CREATE);
        }catch(IOException e){
            e.printStackTrace();
        }

        // Start generating of samples
        for (int i = 0; i < parameterGenSamples.size(); i++) {
            System.out.println(parameterGenSamples.get(i).getCode());
            TestParameter p = new TestParameter();
            p.setParameters(parameterGenSamples.get(i), end);
            p.start();
        }

        try {
            Thread.sleep(time+1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void registryTestData() {
        // Creating a testClient
        try {
            testClient = new TestClient("http://localhost:8080/test-data");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        // Finding file created
        try{
            file = Paths.get(fileName);
        }catch(InvalidPathException e){
            e.printStackTrace();
        }

        // Saving data on db
        try {
            // Initializing lines stream
            Stream<String> stream = Files.lines(file);

            // Applying filters and printing all ines in json format
            stream.map(String::trim)
                    .filter(l -> !l.isEmpty())
                    .forEach((sample) ->{
                        String[] sampleSplitted = sample.split(",");

                        if(sampleSplitted[7].equals("ERRO"))
                            sampleSplitted[7] = "false";
                        else sampleSplitted[7] = "true";

                        String jsonSampleString = "{\"timeStamp\""+": " + "\"" + sampleSplitted[0] + "\"" + ", " +
                                                    "\"aircraftCode\""+": " + "\"" + sampleSplitted[1] + "\"" + ", " +
                                                    "\"testNumber\""+": " + "\"" + sampleSplitted[2] + "\"" + ", " +
                                                    "\"parameterCode\""+": " + "\"" + sampleSplitted[3] + "\"" + ", " +
                                                    "\"value\""+": " + "\"" + sampleSplitted[6] + "\"" + ", " +
                                                    "\"status\""+": " + "\"" + sampleSplitted[7] + "\"" +
                                                    "}";
                        System.out.println(jsonSampleString);
                        testClient.saveTestDataOnDB(jsonSampleString);
                    });

            // Closing the stream
            stream.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            // Deleting file
            deleteFile();
        }
    }

    private static void deleteFile(){
        try {
            Files.delete(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

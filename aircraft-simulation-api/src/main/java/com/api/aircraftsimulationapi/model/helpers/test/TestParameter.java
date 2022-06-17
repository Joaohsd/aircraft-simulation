package com.api.aircraftsimulationapi.model.helpers.test;

public class TestParameter extends Thread {
    private ParameterGenSample parameter;
    private long end;

    public void setParameters(ParameterGenSample parameter, long end) {
        this.parameter = parameter;
        this.end = end;
    }

    @Override
    public void run() {
        //System.out.println(this.parameter.getState());
        System.out.println(this.parameter.getCode());
        while(System.currentTimeMillis() <= this.end){
            String code = this.parameter.getCode();
            int samplingRate = this.parameter.getSamplingRate();
            int minValue = this.parameter.getMinValue();
            int maxValue = this.parameter.getMaxValue();
            double value = this.parameter.generateSample();

            if (value < minValue || value > maxValue) {
                parameter.writeToFile("ERRO");
            }
            else {
                parameter.writeToFile("SUCESSO!");
            }

            // Wait a time to get the next sample
            try {
                Thread.sleep(TestEngine.CONVERT_TO_MILLIS/samplingRate);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

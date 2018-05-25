package eim.systems.cs.pub.ro.practicaltest;

/**
 * Created by student on 25.05.2018.
 */

class WeatherInfo {
    private String temperature;
    private String windSpeed;
    private String condition;
    private String humidity;

    public WeatherInfo(String temperature, String windSpeed, String condition, String humidity) {
        this.temperature = temperature;
        this.windSpeed = windSpeed;
        this.condition = condition;
        this.humidity = humidity;
    }

    @Override
    public String toString() {
        return temperature + " " + windSpeed + " " + condition + " " + humidity;
    }
}
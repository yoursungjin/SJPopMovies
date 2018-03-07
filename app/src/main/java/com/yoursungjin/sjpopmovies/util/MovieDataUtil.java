package com.yoursungjin.sjpopmovies.util;


public class MovieDataUtil {

    public static String posterURL(String path) {
        return "http://image.tmdb.org/t/p/w185/" + path;
    }

    public static String voteAverageTen(float voteAverage) {
        return voteAverage + "/10";
    }

    public static String releaseYear(String releaseDate) {
        String[] strings = releaseDate.split("-");
        return strings[0];
    }
    public static String runningTimeMinutes(int runningTime) {
        return runningTime + "min";
    }

}

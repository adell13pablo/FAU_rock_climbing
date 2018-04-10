package com.example.pablo.fau_rock_climbing;

/**
 * Created by Pablo on 3/27/2018.
 */

public class URL {

    private static final String baseURL = "http://lamp.cse.fau.edu/~padell2017/rock_climbing/"; //Here append the general API php file adding ?apicall= and
                //changing the values of the rest of the string to match the values within the switch statement

    public static final String registerStudent = baseURL + "prueba_API_student_register.php?";
    public static final String registerGuest = baseURL + "prueba_API_guest_register.php?";
    public static final String loginStudent = baseURL + "prueba_API_student_login.php?";
    public static final String loginGuest = baseURL + "prueba_API_guest_login.php?";
    public static final String makePayment = baseURL + "make_payment_API.php?";
    public static final String getMyTrips = baseURL + "getMytrips.php?";
    public static final String getMyTrips_Student = baseURL + "getMyTrips_Student.php?";
    public static final String getTrips = baseURL + "getTrips.php?";
    public static final String getCourses = baseURL + "getCourses.php?";
    public static final String getMyCourses = baseURL + "getMyCourses.php?";
    public static final String getMyCourses_Student = baseURL + "getMyCourses_Student.php?";
    public static final String paymentCourses = baseURL + "payment_Course.php?";
    public static final String paymentTrip = baseURL + "payment_Trip.php?";
}

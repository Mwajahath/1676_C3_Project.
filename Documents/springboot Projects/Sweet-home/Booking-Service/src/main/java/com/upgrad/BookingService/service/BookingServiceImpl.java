package com.upgrad.BookingService.service;

import com.upgrad.BookingService.dao.BookingDao;
import com.upgrad.BookingService.dto.BookingDTO;
import com.upgrad.BookingService.dto.PaymentDTO;
import com.upgrad.BookingService.entity.Booking;
import com.upgrad.BookingService.exceptions.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.Random;

@Service
public class BookingServiceImpl implements BookingService {

    private int pricePerRoomPerDay = 1000;

    @Autowired
    BookingDao bookingDao;


    @Autowired
    RestTemplate restTemplate;

    @Value("${url.service.payment}")
    private String paymentServiceUrl;

    //Generates random room numbers
    public static ArrayList<String> getRoomNumbers(int numOfRooms) {
        Random rand = new Random();
        int upperBound = 100;
        ArrayList<String> roomList = new ArrayList<String>();
        for (int i = 0; i < numOfRooms; i++) {
            roomList.add(String.valueOf(rand.nextInt(upperBound)));
        }
        return roomList;
    }


    // logic to book hotel room
    @Override
    public Booking bookingDetails(BookingDTO bookingRequest) throws Exception {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        int requestedNumOfRooms = bookingRequest.getNumOfRooms();

        String roomNumbers = String.join(",", getRoomNumbers(requestedNumOfRooms));
        Booking bookingInfo = new Booking();
        bookingInfo.setRoomNumbers(roomNumbers);
        bookingInfo.setFromDate(bookingRequest.getFromDate());
        bookingInfo.setToDate(bookingRequest.getToDate());
        bookingInfo.setBookedOn(date);
        bookingInfo.setAadharNumber(bookingRequest.getAadharNumber());
        long numberOfDays = (bookingInfo.getToDate().getTime() - bookingRequest.getFromDate().getTime()) / (1000 * 60 * 60 * 24) % 365;
        System.out.println("Number of Days: " + numberOfDays);
        bookingInfo.setRoomPrice((int) (bookingRequest.getNumOfRooms() * pricePerRoomPerDay * numberOfDays));
        System.out.println(bookingInfo.toString());
        return bookingDao.save(bookingInfo);
    }

    @Override
    public Booking doPayment(PaymentDTO paymentDetails) throws Exception {

        // call payment service and get paymentID to save in booking.
        System.out.println(paymentDetails.toString());
        String url = this.paymentServiceUrl + "/payment/transaction" ;

        if(!(paymentDetails.getPaymentMode().trim().equalsIgnoreCase("UPI") | paymentDetails.getPaymentMode().trim().equalsIgnoreCase("CARD"))){
            throw new CustomException("Invalid mode of payment");
        }
        int bookingId = paymentDetails.getBookingId();
        Optional<Booking> bookingInfoOptional = bookingDao.findById(bookingId);
        if(bookingInfoOptional.isPresent()) {
            Booking bookingInfo = bookingInfoOptional.get();
            int trancationId = restTemplate.postForObject(url, paymentDetails, Integer.class);
            bookingInfo.setTransactionId(trancationId);
            bookingDao.save(bookingInfo);
            String message = "Booking confirmed for user with aadhaar number: " + bookingInfo.getAadharNumber() +
                    "    |    " + "Here are the booking details:    " + bookingInfo.toString();
            //producer.send(new ProducerRecord<String, String>("message","message", message));
            return bookingInfo;
        }else {
            throw new CustomException("Invalid Booking Id");
        }
    }



}

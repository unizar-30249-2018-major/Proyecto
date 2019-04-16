package com.unizar.major.application.service;

import com.unizar.major.application.dtos.BookingDto;
import com.unizar.major.domain.Booking;
import com.unizar.major.domain.Period;
import com.unizar.major.domain.User;
import com.unizar.major.domain.repository.BookingRepository;
import com.unizar.major.domain.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;


@Service
public class BookingService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookingRepository bookingRepository;

    Logger logger = LoggerFactory.getLogger(BookingService.class);

    public BookingService(){}

    @Transactional
    public String createNewBooking(Long id, BookingDto bookingDto){
        Optional<User> user = userRepository.findById(id);

        if (user.isPresent()){
            User u = user.get();

            Period period = new Period(bookingDto.getPeriod().getstartDate(),bookingDto.getPeriod().getEndDate());
            Collection<Period> p = new ArrayList<>();
            p.add(period);

            Booking booking = new Booking(bookingDto.isIsPeriodic(), bookingDto.getReason(),p);
            booking.setActive(true);
            booking.setState("inicial");
            booking.setFinalDate(null);
            booking.setPeriodRep(null);
            booking.setUser(u);

            bookingRepository.save(booking);
        }
        else{
            return "User with id "+ id + "not exist";
        }

        return "Booking is created";

    }

    @Transactional
    public String createNewBookingPeriodic(Long id, BookingDto bookingDto){
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()){
            User u = user.get();
            Collection<Period> p = calculatePeriods(bookingDto.getPeriodRep(), bookingDto.getFinalDate(),bookingDto.getPeriod().getstartDate(),bookingDto.getPeriod().getEndDate());
            Booking booking = new Booking(bookingDto.isIsPeriodic(), bookingDto.getReason(),p, bookingDto.getPeriodRep(), bookingDto.getFinalDate());
            booking.setActive(true);
            booking.setState("inicial");
            booking.setUser(u);

            bookingRepository.save(booking);
        }
        else{
            return "User with id "+ id + "not exist";
        }

        return "Booking is created";

    }

    public Collection<Period> calculatePeriods(String periodRep, Date finalDate, Date startDate, Date endDate) {



        Collection<Period> p = new ArrayList<>();
        Calendar calendar;

        Period period = new Period();
        period.setstartDate(startDate);
        period.setEndDate(endDate);
        p.add(period);

        switch (periodRep){

            case "diaria":

                calendar = Calendar.getInstance();
                calendar.setTime(startDate);
                calendar.add(Calendar.DATE,1);

                while (calendar.getTime().compareTo(finalDate)<0){

                    Period period2 = new Period();
                    period2.setstartDate(calendar.getTime());
                    startDate=calendar.getTime();
                    calendar.setTime(endDate);
                    calendar.add(Calendar.DATE,1);
                    period2.setEndDate(calendar.getTime());
                    endDate=calendar.getTime();
                    p.add(period2);
                    calendar.setTime(startDate);
                    calendar.add(Calendar.DATE,1);
                }

                break;

            case "semanal":


                calendar = Calendar.getInstance();
                calendar.setTime(startDate);
                calendar.add(Calendar.DATE,7);

                while (calendar.getTime().compareTo(finalDate)<0){

                    Period period2 = new Period();
                    period2.setstartDate(calendar.getTime());
                    startDate=calendar.getTime();
                    calendar.setTime(endDate);
                    calendar.add(Calendar.DATE,7);
                    period2.setEndDate(calendar.getTime());
                    endDate=calendar.getTime();
                    p.add(period2);
                    calendar.setTime(startDate);
                    calendar.add(Calendar.DATE,7);
                }

                break;
            case "mensual":

                calendar = Calendar.getInstance();
                calendar.setTime(startDate);
                int semana = calendar.get(Calendar.WEEK_OF_MONTH);
                int dia = calendar.get(Calendar.DAY_OF_WEEK);


                //calendar.setMinimalDaysInFirstWeek(1);
                calendar.add(Calendar.MONTH,1);
                calendar.set(Calendar.DAY_OF_WEEK,dia);
                calendar.set(Calendar.WEEK_OF_MONTH,semana);

                while (calendar.getTime().compareTo(finalDate)<0){

                    Period period2 = new Period();
                    period2.setstartDate(calendar.getTime());
                    startDate=calendar.getTime();
                    calendar.setTime(endDate);
                    calendar.add(Calendar.MONTH,1);
                    calendar.set(Calendar.DAY_OF_WEEK,dia);
                    calendar.set(Calendar.WEEK_OF_MONTH,semana);
                    period2.setEndDate(calendar.getTime());
                    endDate=calendar.getTime();
                    p.add(period2);
                    calendar.setTime(startDate);
                    calendar.add(Calendar.MONTH,1);
                    calendar.set(Calendar.DAY_OF_WEEK,dia);
                    calendar.set(Calendar.WEEK_OF_MONTH,semana);
                }

                break;
            case "quincenal":

                calendar = Calendar.getInstance();
                calendar.setTime(startDate);
                calendar.add(Calendar.DATE,14);

                while (calendar.getTime().compareTo(finalDate)<0){

                    Period period2 = new Period();
                    period2.setstartDate(calendar.getTime());
                    startDate=calendar.getTime();
                    calendar.setTime(endDate);
                    calendar.add(Calendar.DATE,14);
                    period2.setEndDate(calendar.getTime());
                    endDate=calendar.getTime();
                    p.add(period2);
                    calendar.setTime(startDate);
                    calendar.add(Calendar.DATE,14);
                }

                break;

        }

        return p;
    }


        public Optional<Booking> getBookingById(long id){

        Optional<Booking> booking = bookingRepository.findById(id);
        if (booking.isPresent()) {
            return booking;
        }
        else {
            return null;
        }

    }

    @Transactional
    public String updateBooking(long id, BookingDto bookingDto){

        Optional<Booking> b =bookingRepository.findById(id);
        if (b.isPresent()){
            Booking booking =  b.get();
            Collection<Period> p = calculatePeriods(bookingDto.getPeriodRep(), bookingDto.getFinalDate(),bookingDto.getPeriod().getstartDate(),bookingDto.getPeriod().getEndDate());
            booking.setPeriod(p);
            booking.setPeriodRep(bookingDto.getPeriodRep());
            booking.setIsPeriodic(bookingDto.isIsPeriodic());
            booking.setReason(bookingDto.getReason());
            booking.setFinalDate(bookingDto.getFinalDate());
            bookingRepository.save(booking);

            return "Booking is update";
        }
        else{
            return "Booking not exists";
        }





    }

    @Transactional
    public String deleteBooking(long id){

        Optional<Booking> booking = bookingRepository.findById(id);

        if(booking.isPresent()){
            Booking b = booking.get();
            b.setActive(false);
            bookingRepository.save(b);
            return "Booking is deleted";
        }else{
            return "Booking not exist";
        }


    }

    public List<Booking> getAllBookings(){

        List<Booking> booking = bookingRepository.findAll();

        return booking;

    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.time.LocalDate;
import java.time.Year;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import db.RoomDB;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.IsoFields;
import java.time.temporal.TemporalAdjusters;
import static java.time.temporal.TemporalQueries.localDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import modal.Room;

/**
 *
 * @author Admin
 */
public class BookingController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet BookingController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet BookingController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Calendar calendar = Calendar.getInstance();

        String raw_change = request.getParameter("change");
        int week = - 1;
        if (raw_change == null) {
            week = calendar.get(Calendar.WEEK_OF_YEAR);
        } else {
            int raw_week = Integer.parseInt(request.getParameter("week"));
            if (raw_change.equals("prv")) {
                week = raw_week - 1;
            } else {
                week = raw_week + 1;
            }
        }
        RoomDB rdb = new RoomDB();
        ArrayList<Room> room = rdb.getElem();

//        int week = calendar.get(Calendar.WEEK_OF_YEAR);
        List<LocalDate> datesOfWeeks = getAllDaysOfTheWeek(week, Locale.US);

        ArrayList<String> dates = new ArrayList<>();
        for (LocalDate datesOfWeek : datesOfWeeks) {
            System.out.println(datesOfWeek);
            String rangeDate = datesOfWeek.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            dates.add(rangeDate);
        }
        
        request.setAttribute("thisweek", calendar.get(Calendar.WEEK_OF_YEAR));
        request.setAttribute("week", week);
        request.setAttribute("dates", dates);
        request.setAttribute("ro", room);
        request.getRequestDispatcher("booking.jsp").forward(request, response);

    }

//    public static void main(String[] args) {
//        Calendar calendar = Calendar.getInstance();
//        System.out.println(calendar.getTime().toString());
//        int dayOfWeek[];
//        int day = -1;
//        int week = calendar.get(Calendar.WEEK_OF_MONTH);
//        System.out.println(week);
////        while (week == 4) {            
////            
////        }
//        final long calendarWeek = 34;
//        LocalDate desiredDate = LocalDate.now()
//                .with(IsoFields.WEEK_OF_WEEK_BASED_YEAR, calendarWeek)
//                .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
//        System.out.println(desiredDate);
//
////        int end = day+
//    }
//    public static void main(String[] args) {
//
//        try {
//            Calendar calendar = Calendar.getInstance();
//            int week = calendar.get(Calendar.WEEK_OF_YEAR);
//            List<LocalDate> datesOfWeeks = getAllDaysOfTheWeek(week, Locale.US);
//
//            for (LocalDate datesOfWeek : datesOfWeeks) {
//                String from = "2022-03-25";
//                String to  = "2022-03-27";
//                System.out.println(datesOfWeek);
//                String rangeDate = datesOfWeek.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
//                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//                boolean inRange = sdf.parse(rangeDate).compareTo(sdf.parse(to)) <= 0 && sdf.parse(rangeDate).compareTo(sdf.parse(from)) >= 0;
//                                                                //range < to && range > from
//                if(inRange) System.out.print("x");
//            }
//
//        } catch (ParseException ex) {
//            Logger.getLogger(BookingController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//    }
    static LocalDate getFirstDayOfWeek(int weekNumber, Locale locale) {
        return LocalDate
                .of(Year.now().getValue(), 2, 1)
                .with(WeekFields.of(locale).getFirstDayOfWeek())
                .with(WeekFields.of(locale).weekOfWeekBasedYear(), weekNumber);
    }

    static List<LocalDate> getAllDaysOfTheWeek(int weekNumber, Locale locale) {
        LocalDate firstDayOfWeek = getFirstDayOfWeek(weekNumber, locale);
        return IntStream
                .rangeClosed(0, 6)
                .mapToObj(i -> firstDayOfWeek.plusDays(i))
                .collect(Collectors.toList());
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

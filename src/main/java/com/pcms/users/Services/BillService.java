package com.pcms.users.Services;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.pcms.users.Config.Model.MonthYearComparator;
import com.pcms.users.Config.Model.UsageResponse;
import com.pcms.users.Config.Model.UserPlans;
import com.pcms.users.Repository.UserPlansRepository;
import com.pcms.users.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class BillService {

    @Autowired
    private UserPlansRepository userPlansRepository;

    @Autowired
    private UserRepository usersRepository;

    public ByteArrayInputStream generateBill(Long userPlanId) {
        UserPlans userPlan = userPlansRepository.findById(userPlanId).orElseThrow(() -> new RuntimeException("UserPlan not found"));

        if (!userPlan.isSubscribed()) {
            throw new RuntimeException("Plan is not subscribed");
        }

        LocalDate fromDate = userPlan.getRequiredFrom().toInstant().atZone(ZoneId.systemDefault())
                .toLocalDate();
        LocalDate toDate = LocalDate.now(); // Use today's date
        long daysUsed = ChronoUnit.DAYS.between(fromDate, toDate);
        double totalAmount = daysUsed * userPlan.getPlans().getPrice();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(out);
        Document document = new Document(new com.itextpdf.kernel.pdf.PdfDocument(writer));

        try {
            PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
            document.setFont(font);

            // Add header
            Paragraph header = new Paragraph("Bill Summary")
                    .setFontSize(20)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER);
            document.add(header);

            // Add user and plan details in a table
            Table table = new Table(UnitValue.createPercentArray(new float[]{1, 2}))
                    .setWidth(UnitValue.createPercentValue(80))
                    .setTextAlignment(TextAlignment.CENTER)
                    .setHorizontalAlignment(com.itextpdf.layout.property.HorizontalAlignment.CENTER);

            table.addCell(new Cell().add(new Paragraph("Plan Name:")).setBold());
            table.addCell(new Cell().add(new Paragraph(userPlan.getPlans().getPlanName())));
            table.addCell(new Cell().add(new Paragraph("User:")).setBold());
            table.addCell(new Cell().add(new Paragraph(userPlan.getUsers().getFullName())));
            table.addCell(new Cell().add(new Paragraph("Plan Price:")).setBold());
            table.addCell(new Cell().add(new Paragraph("$" + userPlan.getPlans().getPrice())));
            table.addCell(new Cell().add(new Paragraph("Days:")).setBold());
            table.addCell(new Cell().add(new Paragraph(String.valueOf(daysUsed))));
            table.addCell(new Cell().add(new Paragraph("Total Amount:")).setBold());
            table.addCell(new Cell().add(new Paragraph("$" + totalAmount)));
            document.add(table);

            // Add footer
            Paragraph footer = new Paragraph("Thank you for your business!")
                    .setFontSize(12)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontColor(ColorConstants.GRAY);
            document.add(footer);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            document.close();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }

    public List<UsageResponse> findUsage(int userId) {
        List<UsageResponse> usageResponses = new ArrayList<>();
        Optional<List<UserPlans>> userPlans = userPlansRepository.findByUserId(userId, true);

        if (userPlans.isPresent()) {
            List<UserPlans> userPlansList = userPlans.get();
            long totalUnitsConsumed = 0;

            // First pass to calculate total units consumed
            for (UserPlans userPlan : userPlansList) {
                LocalDate fromDate = userPlan.getRequiredFrom().toInstant().atZone(ZoneId.systemDefault())
                        .toLocalDate();
                LocalDate toDate = LocalDate.now(); // Use today's date
                long daysUsed = ChronoUnit.DAYS.between(fromDate, toDate);
                totalUnitsConsumed += daysUsed;
            }

            // Second pass to create UsageResponse objects with percentage
            for (UserPlans userPlan : userPlansList) {
                String planName = userPlan.getPlans().getPlanName() + " (" + userPlan.getPlans().getLocation() + ")";
                double costPerUnit = userPlan.getPlans().getPrice();
                LocalDate fromDate = userPlan.getRequiredFrom().toInstant().atZone(ZoneId.systemDefault())
                        .toLocalDate();
                LocalDate toDate = LocalDate.now(); // Use today's date
                long daysUsed = ChronoUnit.DAYS.between(fromDate, toDate);
                daysUsed = Math.abs(daysUsed);

                double totalCost = daysUsed * costPerUnit;
                double percentage = (double) daysUsed / totalUnitsConsumed * 100;

                UsageResponse usageResponse = new UsageResponse(planName, daysUsed, costPerUnit, totalCost,
                        totalUnitsConsumed, percentage,
                        Date.from(fromDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
                usageResponses.add(usageResponse);
            }

            // Sort the usageResponses by requiredFrom in descending order
            usageResponses.sort((a, b) -> b.getRequiredFrom().compareTo(a.getRequiredFrom()));
        }

        return usageResponses;
    }
    public Map<String, Map<String, Long>> findUsageMonth(int userId) {
        Map<String, Map<String, Long>> usageByMonth = new TreeMap<>(new MonthYearComparator());
        Optional<List<UserPlans>> userPlans = userPlansRepository.findByUserId(userId, true);

        if (userPlans.isPresent()) {
            List<UserPlans> userPlansList = userPlans.get();
            long totalUnitsConsumed = 0;

            // First pass to calculate total units consumed
            for (UserPlans userPlan : userPlansList) {
                LocalDate fromDate = userPlan.getRequiredFrom().toInstant().atZone(ZoneId.systemDefault())
                        .toLocalDate();
                LocalDate toDate = LocalDate.now(); // Use today's date
                long daysUsed = ChronoUnit.DAYS.between(fromDate, toDate);
                totalUnitsConsumed += daysUsed;
            }

            // Iterate through each month and calculate usage
            for (UserPlans userPlan : userPlansList) {
                String planName = userPlan.getPlans().getPlanName() + " (" + userPlan.getPlans().getLocation() + ")";
                LocalDate fromDate = userPlan.getRequiredFrom().toInstant().atZone(ZoneId.systemDefault())
                        .toLocalDate();
                LocalDate toDate = LocalDate.now(); // Use today's date

                while (!fromDate.isAfter(toDate)) {
                    String monthYear = fromDate.getMonth().toString() + " " + fromDate.getYear();
                    usageByMonth.putIfAbsent(monthYear, new HashMap<>());
                    Map<String, Long> planUsage = usageByMonth.get(monthYear);

                    // Calculate the number of days in the current month
                    long daysInMonth = ChronoUnit.DAYS.between(fromDate,
                            fromDate.withDayOfMonth(fromDate.lengthOfMonth())) + 1;
                    if (fromDate.getMonth() == toDate.getMonth() && fromDate.getYear() == toDate.getYear()) {
                        daysInMonth = ChronoUnit.DAYS.between(fromDate, toDate) + 1;
                    }

                    // Subtract 1 from the usage before displaying
                    planUsage.put(planName, planUsage.getOrDefault(planName, 0L) + daysInMonth - 1);
                    fromDate = fromDate.plusMonths(1).withDayOfMonth(1);
                }
            }
        }

        return usageByMonth;
    }
}
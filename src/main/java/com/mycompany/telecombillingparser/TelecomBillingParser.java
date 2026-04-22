package com.mycompany.telecombillingparser;

import java.io.*;
import java.nio.file.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.math.BigDecimal;

public class TelecomBillingParser {

    // Database Configuration
    private static final String DB_URL = "jdbc:postgresql://ep-cool-heart-anf2054d-pooler.c-6.us-east-1.aws.neon.tech/neondb?sslmode=require&channelBinding=require";
    private static final String USER = "neondb_owner";
    private static final String PASS = "npg_JcoqXx3KHIb5";

    // Directories for processing and archiving
    private static final String INPUT_DIR = "generated_cdrs";
    private static final String ARCHIVE_DIR = "archive_cdrs";

    public static void main(String[] args) {
        
        // Ensure the archive directory exists; create it if it does not
        try {
            Files.createDirectories(Paths.get(ARCHIVE_DIR));
        } catch (IOException e) {
            System.out.println("Failed to create archive directory.");
            e.printStackTrace();
            return;
        }

        File inputDir = new File(INPUT_DIR);
        File[] files = inputDir.listFiles((dir, name) -> name.endsWith(".csv"));

        // Check if there are any files to process
        if (files == null || files.length == 0) {
            System.out.println("No new CDR files found for processing.");
            return;
        }

        // SQL Query updated to insert into 6 columns (dial_a is removed)
        String insertSQL = "INSERT INTO cdr (msisdn, dial_b, service_id, duration_volume, external_fees_amount, cdr_timestamp) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {

            for (File file : files) {
                System.out.println("Processing file: " + file.getName());
                
                // Extract date from filename (e.g., CDR20260416120000.csv -> 20260416)
                String fileName = file.getName();
                String datePart = fileName.substring(3, 11); 
                int recordCount = 0;

                try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        String[] data = line.split(",");
                        
                        // Skip malformed lines
                        if (data.length < 6) continue;

                        String dialA = data[0];
                        String dialB = data[1];
                        int serviceId = Integer.parseInt(data[2]);
                        int durationVolume = Integer.parseInt(data[3]);
                        String timePart = data[4]; 
                        BigDecimal externalFees = new BigDecimal(data[5]);

                        // Combine Date from filename and Time from the row
                        String dateTimeStr = datePart + " " + timePart;
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd HH:mm:ss");
                        LocalDateTime ldt = LocalDateTime.parse(dateTimeStr, formatter);
                        Timestamp cdrTimestamp = Timestamp.valueOf(ldt);

                        // Set SQL parameters sequentially (1 through 6)
                        pstmt.setString(1, dialA);            // msisdn
                        pstmt.setString(2, dialB);            // dial_b
                        pstmt.setInt(3, serviceId);           // service_id
                        pstmt.setInt(4, durationVolume);      // duration_volume
                        pstmt.setBigDecimal(5, externalFees); // external_fees_amount
                        pstmt.setTimestamp(6, cdrTimestamp);  // cdr_timestamp

                        pstmt.addBatch(); 
                        recordCount++;
                    }
                    
                    // Execute batch insert for all rows in the current file
                    pstmt.executeBatch();
                    System.out.println("Successfully inserted " + recordCount + " records from file.");

                } catch (Exception e) {
                    System.out.println("Error reading file: " + file.getName());
                    e.printStackTrace();
                }

                // Move the processed file to the archive directory
                try {
                    Path sourcePath = file.toPath();
                    Path targetPath = Paths.get(ARCHIVE_DIR, file.getName());
                    
                    // Use REPLACE_EXISTING to avoid errors if a file with the same name already exists in the archive
                    Files.move(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
                    System.out.println("Archived file to: " + ARCHIVE_DIR + "/" + file.getName() + "\n------------------");
                } catch (IOException e) {
                    System.out.println("Failed to archive file: " + file.getName());
                    e.printStackTrace();
                }
            }
            
            System.out.println("All files processed successfully.");

        } catch (SQLException e) {
            System.out.println("Database connection error!");
            e.printStackTrace();
        }
    }
}
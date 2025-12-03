package com.school.app.service;

import com.school.app.model.Classroom;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class ClassroomService {

    private final Map<String, Classroom> rooms = new HashMap<>();

    public Map<String, Classroom> getRooms() {
        return rooms;
    }

    /**
     * Instance-style loader (preferred for OOP).
     */
    public void loadFromCsv(String path) throws Exception {
        rooms.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {

            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                // Expected CSV format: RM101,true,true
                String[] parts = line.split(",", 3);
                if (parts.length < 3) continue;

                String room = parts[0].trim();
                boolean hasComp = Boolean.parseBoolean(parts[1].trim());
                boolean hasSmart = Boolean.parseBoolean(parts[2].trim());

                Classroom classroom = new Classroom(room, hasComp, hasSmart);
                rooms.put(room, classroom);
            }

        } catch (Exception e) {
            System.out.println("Error loading Classroom CSV: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Static utility-style loader (optional, from original version).
     */
    public static Map<String, Classroom> loadStatic(String path) {
        Map<String, Classroom> map = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {

            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split(",", 3);
                if (parts.length < 3) continue;

                String room = parts[0].trim();
                boolean hasComp = Boolean.parseBoolean(parts[1].trim());
                boolean hasSmart = Boolean.parseBoolean(parts[2].trim());

                Classroom classroom = new Classroom(room, hasComp, hasSmart);
                map.put(room, classroom);
            }

        } catch (Exception e) {
            System.out.println("Static load error: " + e.getMessage());
        }

        return map;
    }

    public Classroom getByRoom(String id) {
        return rooms.get(id);
    }
}

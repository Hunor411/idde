package edu.bbte.idde.dhim2228.ui;

import edu.bbte.idde.dhim2228.model.EventModel;
import edu.bbte.idde.dhim2228.service.EventService;
import edu.bbte.idde.dhim2228.service.ServiceFactory;
import edu.bbte.idde.dhim2228.service.exceptions.ServiceException;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class UpdateEventUI extends JFrame {
    private final EventService eventService;
    private final EventManager parentUI;
    private final Long eventId;
    JTextField nameField;
    JTextField locationField;
    JTextField dateField;
    JTextArea descriptionArea;
    JTextField attendeesCountField;
    JCheckBox isOnlineCheckBox;

    public UpdateEventUI(EventManager parentUI, Long eventId, String eventName,
                         String eventLocation, String eventDate, boolean isOnline, String eventDescription,
                         int attendeesCount) {
        this.eventService = ServiceFactory.getInstance().getEventService();
        this.parentUI = parentUI;
        this.eventId = eventId;

        this.setTitle("Esemény módosítása");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setSize(400, 400);
        this.setLayout(new GridLayout(8, 2));

        nameField = new JTextField(eventName);
        locationField = new JTextField(eventLocation);
        dateField = new JTextField(eventDate);
        descriptionArea = new JTextArea(eventDescription);
        attendeesCountField = new JTextField(String.valueOf(attendeesCount));
        isOnlineCheckBox = new JCheckBox("Online", isOnline);

        JButton saveButton = new JButton("Mentés");
        saveButton.addActionListener(e -> {
            String name = nameField.getText();
            String location = locationField.getText();
            String dateStr = dateField.getText();
            String description = descriptionArea.getText();
            int attendees;

            try {
                attendees = Integer.parseInt(attendeesCountField.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(
                        this,
                        "Kérlek, adj meg érvényes számot a résztvevők számához.",
                        "Hiba",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            boolean online = isOnlineCheckBox.isSelected();

            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                LocalDateTime date = LocalDateTime.parse(dateStr, formatter);

                EventModel updatedEvent = new EventModel(name, location, date, online, description, attendees);
                eventService.updateEvent(eventId, updatedEvent);

                JOptionPane.showMessageDialog(this, "Esemény sikeresen módosítva!");
                parentUI.fillTableWithEvents();
                this.dispose();
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(
                        this,
                        "Érvénytelen dátum formátum. Helyes formátum: yyyy-MM-dd HH:mm",
                        "Hiba",
                        JOptionPane.ERROR_MESSAGE
                );
            } catch (ServiceException ex) {
                JOptionPane.showMessageDialog(
                        this,
                        ex.getMessage(),
                        "Hiba",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        this.add(new JLabel("Esemény neve:"));
        this.add(nameField);
        this.add(new JLabel("Helyszín:"));
        this.add(locationField);
        this.add(new JLabel("Dátum (yyyy-MM-dd HH:mm):"));
        this.add(dateField);

        this.add(new JLabel("Leírás:"));
        this.add(descriptionArea);

        this.add(new JLabel("Résztvevők száma:"));
        this.add(attendeesCountField);

        this.add(isOnlineCheckBox);
        this.add(saveButton);

        this.setVisible(true);
    }
}

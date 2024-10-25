package edu.bbte.idde.dhim2228.ui;

import edu.bbte.idde.dhim2228.model.EventModel;
import edu.bbte.idde.dhim2228.service.EventService;
import edu.bbte.idde.dhim2228.service.ServiceFactory;

import javax.swing.*;
import java.awt.*;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Locale;

public class AddEventUI extends JFrame {
    private final EventService eventService;

    private final JTextField nameField;
    private final JTextField locationField;
    private final JTextArea descriptionArea;
    private final JTextField attendeesCountField;
    private final JComboBox<Integer> yearComboBox;
    private final JComboBox<String> monthComboBox;
    private final JComboBox<String> dayComboBox;
    private final JTextField hourField;
    private final JTextField minuteField;
    private final JCheckBox isOnlineCheckBox;
    JButton saveButton;

    public AddEventUI(EventManager parentUI) {
        this.eventService = ServiceFactory.getInstance().getEventService();

        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = 400;
        int x = (screenSize.width - width) / 2;
        int height = 400;
        int y = (screenSize.height - height) / 2;
        this.setBounds(x, y, width, height);
        this.setTitle("Új esemény hozzáadása");
        this.setLayout(new GridLayout(10, 2));

        nameField = new JTextField();
        locationField = new JTextField();
        descriptionArea = new JTextArea();
        attendeesCountField = new JTextField();

        yearComboBox = new JComboBox<>();
        monthComboBox = new JComboBox<>();
        dayComboBox = new JComboBox<>();

        for (int year = 2024; year <= 2030; year++) {
            yearComboBox.addItem(year);
        }
        yearComboBox.setSelectedItem(2024);

        for (Month month : Month.values()) {
            monthComboBox.addItem(month.getDisplayName(java.time.format.TextStyle.FULL, Locale.forLanguageTag("hu")));
        }
        monthComboBox.setSelectedIndex(0);

        for (DayOfWeek day : DayOfWeek.values()) {
            dayComboBox.addItem(day.getDisplayName(java.time.format.TextStyle.FULL, Locale.forLanguageTag("hu")));
        }
        dayComboBox.setSelectedIndex(0);

        hourField = new JTextField();
        minuteField = new JTextField();

        isOnlineCheckBox = new JCheckBox("Online esemény");

        saveButton = new JButton("Mentés");
        saveButton.addActionListener(e -> {
            try {
                String name = nameField.getText();
                String location = locationField.getText();
                String description = descriptionArea.getText();
                int attendeesCount = Integer.parseInt(attendeesCountField.getText());

                if (name.isEmpty() || location.isEmpty() || description.isEmpty()
                        || attendeesCountField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Kérlek, tölts ki minden mezőt! ",
                            "Hiba",
                            JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }

                int year = (Integer) yearComboBox.getSelectedItem();
                int month = monthComboBox.getSelectedIndex() + 1;
                int dayOfMonth = dayComboBox.getSelectedIndex() + 1;
                int hour = Integer.parseInt(hourField.getText());
                int minute = Integer.parseInt(minuteField.getText());

                if (hour < 0 || hour > 23 || minute < 0 || minute > 59) {
                    throw new NumberFormatException();
                }

                LocalDateTime date = LocalDateTime.of(year, month, dayOfMonth, hour, minute);
                boolean isOnline = isOnlineCheckBox.isSelected();

                EventModel newEvent = new EventModel(name, location, date, isOnline, description, attendeesCount);
                eventService.createEvent(newEvent);

                JOptionPane.showMessageDialog(this, "Esemény sikeresen hozzáadva!");
                parentUI.fillTableWithEvents();
                this.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(
                        this,
                        "Kérlek, adj meg érvényes számokat az órához, perchez, vagy résztvevők számához.",
                        "Hiba",
                        JOptionPane.ERROR_MESSAGE
                );
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Hiba történt az esemény létrehozásakor: " + ex.getMessage(),
                        "Hiba",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        this.add(new JLabel("Esemény neve:"));
        this.add(nameField);
        this.add(new JLabel("Helyszín:"));
        this.add(locationField);

        this.add(new JLabel("Leírás:"));
        this.add(descriptionArea);

        this.add(new JLabel("Résztvevők száma:"));
        this.add(attendeesCountField);

        this.add(new JLabel("Év:"));
        this.add(yearComboBox);
        this.add(new JLabel("Hónap:"));
        this.add(monthComboBox);
        this.add(new JLabel("Nap:"));
        this.add(dayComboBox);

        this.add(new JLabel("Óra:"));
        this.add(hourField);
        this.add(new JLabel("Perc:"));
        this.add(minuteField);

        this.add(isOnlineCheckBox);
        this.add(saveButton);
        this.setVisible(true);
    }
}

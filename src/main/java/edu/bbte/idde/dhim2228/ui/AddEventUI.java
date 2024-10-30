package edu.bbte.idde.dhim2228.ui;

import edu.bbte.idde.dhim2228.model.EventModel;
import edu.bbte.idde.dhim2228.service.EventService;
import edu.bbte.idde.dhim2228.service.ServiceFactory;
import edu.bbte.idde.dhim2228.service.exceptions.ServiceException;

import javax.swing.*;
import java.awt.*;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Locale;

public class AddEventUI extends JFrame {
    private final EventService eventService;

    private JTextField nameField;
    private JTextField locationField;
    private JTextArea descriptionArea;
    private JTextField attendeesCountField;
    private JComboBox<Integer> yearComboBox;
    private JComboBox<String> monthComboBox;
    private JComboBox<String> dayComboBox;
    private JTextField hourField;
    private JTextField minuteField;
    private JCheckBox onlineEventCheckBox;
    JButton saveButton;

    public AddEventUI(EventManager parentUI) {
        super();
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

        initializeUIComponents();

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

        saveButton.addActionListener(e -> {
            try {
                if (!areFieldsValid()) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Kérlek, tölts ki minden mezőt! ",
                            "Hiba",
                            JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }

                LocalDateTime date = getEventDateTime();
                if (date == null) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Kérlek, adj meg érvényes számokat az órához, perchez, vagy résztvevők számához.",
                            "Hiba",
                            JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }

                EventModel newEvent = createEventModel(date);
                eventService.createEvent(newEvent);

                JOptionPane.showMessageDialog(this, "Esemény sikeresen hozzáadva!");
                parentUI.fillTableWithEvents();
                this.dispose();
            } catch (ServiceException ex) {
                JOptionPane.showMessageDialog(this,
                        "Hiba történt az esemény létrehozásakor: " + ex.getMessage(),
                        "Hiba",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        addUIComponents();
        this.setVisible(true);
    }

    private void initializeUIComponents() {
        nameField = new JTextField();
        locationField = new JTextField();
        descriptionArea = new JTextArea();
        attendeesCountField = new JTextField();

        yearComboBox = new JComboBox<>();
        monthComboBox = new JComboBox<>();
        dayComboBox = new JComboBox<>();

        hourField = new JTextField();
        minuteField = new JTextField();

        onlineEventCheckBox = new JCheckBox("Online esemény");

        saveButton = new JButton("Mentés");
    }

    private void addUIComponents() {
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

        this.add(onlineEventCheckBox);
        this.add(saveButton);
    }

    private boolean areFieldsValid() {
        return !nameField.getText().isEmpty()
                && !locationField.getText().isEmpty()
                && !descriptionArea.getText().isEmpty()
                && !attendeesCountField.getText().isEmpty();
    }

    private LocalDateTime getEventDateTime() {
        try {
            int year = (Integer) yearComboBox.getSelectedItem();
            int month = monthComboBox.getSelectedIndex() + 1;
            int dayOfMonth = dayComboBox.getSelectedIndex() + 1;
            int hour = Integer.parseInt(hourField.getText());
            int minute = Integer.parseInt(minuteField.getText());

            if (hour < 0 || hour > 23 || minute < 0 || minute > 59) {
                return null;
            }

            return LocalDateTime.of(year, month, dayOfMonth, hour, minute);
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    private EventModel createEventModel(LocalDateTime date) {
        String name = nameField.getText();
        String location = locationField.getText();
        String description = descriptionArea.getText();
        int attendeesCount = Integer.parseInt(attendeesCountField.getText());
        boolean isOnline = onlineEventCheckBox.isSelected();

        return new EventModel(name, location, date, isOnline, description, attendeesCount);
    }
}

package edu.bbte.idde.dhim2228.ui;

import edu.bbte.idde.dhim2228.repository.DaoFactory;
import edu.bbte.idde.dhim2228.repository.EventDao;
import edu.bbte.idde.dhim2228.repository.exceptions.NotFoundEventException;
import edu.bbte.idde.dhim2228.repository.memory.InMemoryEventDao;
import edu.bbte.idde.dhim2228.model.EventModel;
import edu.bbte.idde.dhim2228.service.EventService;
import edu.bbte.idde.dhim2228.service.implementation.EventServiceImp;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

public class EventManagerUI extends JFrame {
    EventService eventService;
    JTable eventsTable;
    DefaultTableModel tableModel;

    public EventManagerUI() {
        var eventDao = DaoFactory.getInstance().getEventDao();
        eventService = new EventServiceImp(eventDao);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = 850;
        int x = (screenSize.width - width) / 2;
        int height = 750;
        int y = (screenSize.height - height) / 2;
        this.setBounds(x, y, width, height);
        this.setLayout(new BorderLayout());
        this.setTitle("Esemenykezelő rendszer");

        String[] columnNames = {"Név", "Helyszín", "Dátum", "Online", "Leírás", "Résztvevők száma", "ID"};

        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        eventsTable = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(eventsTable);
        this.add(scrollPane, BorderLayout.CENTER);

        fillTableWithEvents();

        JPanel buttonPanel = getButtonPanel();

        this.add(buttonPanel, BorderLayout.SOUTH);
        this.setVisible(true);
    }

    private JPanel getButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton refreshButton = new JButton("Frissít");
        refreshButton.addActionListener(e -> fillTableWithEvents());
        buttonPanel.add(refreshButton);

        JButton addEventButton = new JButton("Új esemény hozzáadása");
        addEventButton.addActionListener(e -> new AddEventUI(eventService, this));
        buttonPanel.add(addEventButton);

        JButton deleteEvent = new JButton("Törlés");
        deleteEvent.addActionListener(e -> deleteEvent());
        buttonPanel.add(deleteEvent);

        JButton updateEventButton = new JButton("Módosítás");
        updateEventButton.addActionListener(e -> updateEvent());
        buttonPanel.add(updateEventButton);
        return buttonPanel;
    }

    private void updateEvent() {
        int selectedRow = eventsTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Ahhoz, hogy eseményt módosíts, ki kell választanod egy eseményt a táblázatban.",
                    "Információ",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        Long eventId = Long.parseLong(eventsTable.getValueAt(selectedRow, 6).toString());
        String eventName = eventsTable.getValueAt(selectedRow, 0).toString();
        String eventLocation = eventsTable.getValueAt(selectedRow, 1).toString();
        String eventDate = eventsTable.getValueAt(selectedRow, 2).toString();
        boolean isOnline = eventsTable.getValueAt(selectedRow, 3).toString().equals("igen");
        String eventDescription = eventsTable.getValueAt(selectedRow, 4).toString();
        int attendeesCount = Integer.parseInt(eventsTable.getValueAt(selectedRow, 5).toString());


        new UpdateEventUI(eventService, this, eventId, eventName, eventLocation, eventDate, isOnline, eventDescription, attendeesCount);
    }

    public void fillTableWithEvents() {
        Collection<EventModel> events = eventService.getAllEvents();
        tableModel.setRowCount(0);

        if (events.isEmpty()) {
            return;
        }

        for (EventModel event : events) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            String formattedDate = event.getDate().format(formatter);

            Object[] rowData = {
                    event.getName(),
                    event.getLocation(),
                    formattedDate,
                    event.getIsOnline() ? "igen" : "nem",
                    event.getDescription(),
                    event.getAttendeesCount(),
                    event.getId(),
            };
            tableModel.addRow(rowData);
        }
    }

    private void deleteEvent() {
        int[] selectedRows = eventsTable.getSelectedRows();

        if (selectedRows.length == 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "Ahhoz, hogy eseményt törölj ki kell választanod a táblázatban.",
                    "Információ",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }


        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Biztosan törölni szeretnéd a kiválasztott eseményt?",
                "Megerősítés",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.NO_OPTION) {
            return;
        }

        for (int rowIndex : selectedRows) {
            Long eventId = Long.parseLong(eventsTable.getValueAt(rowIndex, 6).toString());
            try {
                eventService.deleteEvent(eventId);
            } catch (NotFoundEventException e) {
                JOptionPane.showMessageDialog(
                        this,
                        e.getMessage(),
                        "Hiba",
                        JOptionPane.ERROR_MESSAGE);
            }
        }

        fillTableWithEvents();
    }
}

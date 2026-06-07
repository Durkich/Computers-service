package com.Kalabekov.Computersservice.service;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class RAGInitializer implements CommandLineRunner {

    @Autowired
    private VectorStore vectorStore;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) {
        System.out.println("========================================");
        System.out.println("RAG INITIALIZER STARTED");
        System.out.println("========================================");

        try {
            jdbcTemplate.execute("DELETE FROM vector_store");
            System.out.println("Cleared existing vector_store");
        } catch (Exception e) {
            System.out.println("Table vector_store not exists yet");
        }

        List<Document> documents = new ArrayList<>();

        List<Map<String, Object>> computers = jdbcTemplate.queryForList(
                "SELECT id, inventory_number, processor, ram, graphic_adapter, rom, room_number FROM infrastructure.computers"
        );

        for (Map<String, Object> pc : computers) {
            String inv = (String) pc.get("inventory_number");
            String text = String.format(
                    "Компьютер %s имеет процессор %s, %d ГБ оперативной памяти, видеокарту %s, диск %d ГБ, он находится в аудитории %s",
                    inv, pc.get("processor"), pc.get("ram"), pc.get("graphic_adapter"), pc.get("rom"), pc.get("room_number")
            );
            documents.add(new Document(text, Map.of("type", "computer", "inventory", inv)));
            System.out.println("  Added: " + text);
        }

        List<Map<String, Object>> laptops = jdbcTemplate.queryForList(
                "SELECT id, inventory_number, processor, ram, graphic_adapter, rom, display FROM infrastructure.laptops"
        );

        for (Map<String, Object> laptop : laptops) {
            String inv = (String) laptop.get("inventory_number");
            String text = String.format(
                    "Ноутбук %s имеет процессор %s, %d ГБ оперативной памяти, видеокарту %s, диск %d ГБ, экран %s",
                    inv, laptop.get("processor"), laptop.get("ram"),
                    laptop.get("graphic_adapter"), laptop.get("rom"), laptop.get("display")
            );
            documents.add(new Document(text, Map.of("type", "laptop", "inventory", inv)));
            System.out.println("  Added: " + text);
        }

        List<Map<String, Object>> software = jdbcTemplate.queryForList(
                "SELECT s.software_name, s.software_version, s.is_licensed, " +
                        "c.inventory_number as computer_inv " +
                        "FROM infrastructure.software s " +
                        "LEFT JOIN infrastructure.computers c ON s.computer_id = c.id"
        );

        for (Map<String, Object> sw : software) {
            String license = Boolean.TRUE.equals(sw.get("is_licensed")) ? "лицензионное" : "нелицензионное";
            String computerInv = (String) sw.get("computer_inv");

            String text;
            Map<String, Object> metadata = new HashMap<>();
            metadata.put("type", "software");

            if (computerInv != null) {
                text = String.format(
                        "На компьютере %s установлено программное обеспечение %s версии %s, которое является %s",
                        computerInv, sw.get("software_name"), sw.get("software_version"), license
                );
                metadata.put("computer_inventory", computerInv);
            } else {
                text = String.format(
                        "Программное обеспечение %s версии %s является %s",
                        sw.get("software_name"), sw.get("software_version"), license
                );
            }

            documents.add(new Document(text, metadata));
            System.out.println("  Added: " + text);
        }

        List<Map<String, Object>> peripherals = jdbcTemplate.queryForList(
                "SELECT p.peripheral_name, p.peripheral_type, c.inventory_number as computer_inv " +
                        "FROM infrastructure.peripherals p " +
                        "LEFT JOIN infrastructure.computers c ON p.computer_id = c.id"
        );

        for (Map<String, Object> peripheral : peripherals) {
            String computerInv = (String) peripheral.get("computer_inv");
            String text;
            Map<String, Object> metadata = new HashMap<>();
            metadata.put("type", "peripheral");

            if (computerInv != null) {
                text = String.format(
                        "К компьютеру %s подключено периферийное устройство %s типа %s",
                        computerInv, peripheral.get("peripheral_name"), peripheral.get("peripheral_type")
                );
                metadata.put("computer_inventory", computerInv);
            } else {
                text = String.format(
                        "Периферийное устройство %s типа %s",
                        peripheral.get("peripheral_name"), peripheral.get("peripheral_type")
                );
            }

            documents.add(new Document(text, metadata));
            System.out.println("  Added: " + text);
        }

        System.out.println("Total documents: " + documents.size());
        System.out.println("Saving to vector store...");

        if (!documents.isEmpty()) {
            vectorStore.add(documents);
            System.out.println("SUCCESS: Saved " + documents.size() + " documents");
        }

        System.out.println("========================================");
        System.out.println("RAG INITIALIZER FINISHED");
        System.out.println("========================================");
    }
}
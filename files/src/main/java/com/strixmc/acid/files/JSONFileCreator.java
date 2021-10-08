package com.strixmc.acid.files;

import com.google.gson.JsonElement;
import org.bukkit.plugin.Plugin;

import java.io.*;
import java.util.logging.Level;

public class JSONFileCreator {

    private final Plugin plugin;
    private final File file;
    private final String fileName;

    public JSONFileCreator(Plugin plugin, String fileName) {
        this(plugin, plugin.getDataFolder(), fileName);
    }

    public JSONFileCreator(Plugin plugin, String folderName, String fileName) {
        this(plugin, new File(plugin.getDataFolder().getAbsolutePath() + File.separator + folderName), fileName);
    }

    public JSONFileCreator(Plugin plugin, File folder, String fileName) {
        this.plugin = plugin;
        this.fileName = fileName;
        if (!folder.exists()) {
            folder.mkdir();
        }
        this.file = new File(folder, fileName + ".json");
        this.create();
    }

    private void create() {
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                plugin.getLogger().log(Level.SEVERE, "An error occurred while trying to create the file", e);
            }
        }
    }

    public BufferedReader getBufferedReaderFile() {
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(file);
        } catch (FileNotFoundException e) {
            plugin.getLogger().log(Level.SEVERE, "An error occurred while trying to find the file", e);
        }
        return new BufferedReader(fileReader);
    }

    public String getText() {
        String line;

        StringBuilder builder = new StringBuilder();
        BufferedReader reader = getBufferedReaderFile();

        try {
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    public void write(String jsonText) {
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(jsonText);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write(JsonElement object) {
        write(object.getAsString());
    }

    public String getFileName() {
        return this.fileName;
    }

    public void destroy() {
        this.file.delete();
    }
}

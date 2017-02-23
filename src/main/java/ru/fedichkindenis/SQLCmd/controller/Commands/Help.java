package ru.fedichkindenis.SQLCmd.controller.Commands;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import ru.fedichkindenis.SQLCmd.util.StringUtil;
import ru.fedichkindenis.SQLCmd.view.ViewDecorator;

import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Команда для вызова справки
 * Формат команды: help
 * Пример команды: help
 */
public class Help implements Command {

    private ViewDecorator view;
    private String textCommand;

    private final String NAME_COMMAND = "nameCommand";
    private final String DESCRIPTION = "description";
    private final String FORMAT = "format";
    private final String EXAMPLE = "example";
    private final String INFO = "info";

    public Help(ViewDecorator view, String textCommand) {
        this.view = view;
        this.textCommand = textCommand;
    }

    @Override
    public void execute() throws Exception {

        if(!validateCommand()) {
            throw new IllegalArgumentException("Указан не верный формат команды");
        }

        JSONObject jsonObject;

        URL url = Thread.currentThread().getContextClassLoader()
                .getResource("descriptionOfTheCommands.json");

        if(url == null) {
            throw new RuntimeException("Не удалось прочитать файл с справкой");
        }

        File jsonFile = new File(url.toURI());
        JSONParser jsonParser = new JSONParser();
        jsonObject = (JSONObject)jsonParser.parse(new FileReader(jsonFile));

        Map<Integer, Map<String, String>> mapCommands = getMapCommands(jsonObject);

        for(Map<String, String> mapCommand : mapCommands.values()) {

            view.write("Команда: " + mapCommand.get(NAME_COMMAND));
            view.write("\tОписание команды: " + mapCommand.get(DESCRIPTION));
            view.write("\tФормат команды: " + mapCommand.get(FORMAT));
            view.write("\tПример команды: " + mapCommand.get(EXAMPLE));

            if(mapCommand.get(INFO).length() > 0) {
                view.write("\tДополнительная информация: " + mapCommand.get(INFO));
            }
        }
    }

    private boolean validateCommand() {

        boolean isValidateCommand;

        isValidateCommand = !StringUtil.isEmpty(textCommand);
        isValidateCommand = isValidateCommand && textCommand.equals("help");

        return isValidateCommand;
    }

    private Map<Integer, Map<String, String>> getMapCommands(JSONObject jsonObject) {

        Map<Integer, Map<String, String>> mapCommands = new TreeMap<>();

        for(Object command : jsonObject.keySet()) {

            JSONObject infoCommand = (JSONObject)jsonObject.get(command);
            Map<String, String> mapCommand = new HashMap<>();

            Integer sort = ((Long) infoCommand.get("sort")).intValue();
            mapCommand.put(NAME_COMMAND, command.toString());
            mapCommand.put(DESCRIPTION, infoCommand.get(DESCRIPTION).toString());
            mapCommand.put(FORMAT, infoCommand.get(FORMAT).toString());
            mapCommand.put(EXAMPLE, infoCommand.get(EXAMPLE).toString());
            mapCommand.put(INFO, infoCommand.get(INFO) == null
                    ? "" : infoCommand.get(INFO).toString());

            mapCommands.put(sort, mapCommand);
        }

        return mapCommands;
    }
}

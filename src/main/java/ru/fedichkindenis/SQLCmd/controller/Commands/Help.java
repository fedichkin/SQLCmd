package ru.fedichkindenis.SQLCmd.controller.Commands;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import ru.fedichkindenis.SQLCmd.util.StringUtil;
import ru.fedichkindenis.SQLCmd.view.ViewDecorator;

import java.io.File;
import java.io.FileReader;

/**
 * Команда для вызова справки
 * Формат команды: help
 * Пример команды: help
 */
public class Help implements Command {

    private ViewDecorator view;
    private String textCommand;

    public Help(ViewDecorator view, String textCommand) {
        this.view = view;
        this.textCommand = textCommand;
    }

    @Override
    public void execute(){

        if(!validateCommand()) {
            throw new IllegalArgumentException("Указан не верный формат команды");
        }

        try {
            File jsonFile = new File(Thread.currentThread().getContextClassLoader()
                    .getResource("descriptionOfTheCommands.json").toURI());
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject)jsonParser.parse(new FileReader(jsonFile));

            for(Object command : jsonObject.keySet()) {

                JSONObject infoCommand = (JSONObject)jsonObject.get(command);

                view.write("Команда: " + command);
                view.write("\tОписание команды: " + infoCommand.get("description"));
                view.write("\tФормат команды: " + infoCommand.get("format"));
                view.write("\tПример команды: " + infoCommand.get("example"));

                if(infoCommand.get("info") != null) {
                    view.write("\tДополнительная информация: " + infoCommand.get("info"));
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Не удалось прочитать файл с справкой");
        }
    }

    private boolean validateCommand() {

        boolean isValidateCommand;

        isValidateCommand = !StringUtil.isEmpty(textCommand);
        isValidateCommand = isValidateCommand && textCommand.equals("help");

        return isValidateCommand;
    }
}

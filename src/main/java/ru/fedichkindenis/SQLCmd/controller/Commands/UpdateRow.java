package ru.fedichkindenis.SQLCmd.controller.Commands;

/**
 * Команда для обновления данных в строках отобраных по условию
 * Формат команды: update-row|наименование таблицы|наименование поля1|значение поля 1| ...
 * |<>|поле условия1|оператор условия1|значение условия1| ...
 * Пример команды: update-row|usr|password|1111|<>|id|=|3
 */
public class UpdateRow implements Command {


    @Override
    public void execute() {

    }
}

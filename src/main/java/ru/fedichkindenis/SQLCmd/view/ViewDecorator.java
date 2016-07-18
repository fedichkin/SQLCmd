package ru.fedichkindenis.SQLCmd.view;

import ru.fedichkindenis.SQLCmd.model.DataMap;

import java.util.List;

/**
 * Created by Денис on 18.07.2016.
 */
public abstract class ViewDecorator implements View {

    protected View view;

    public ViewDecorator(View view) {
        this.view = view;
    }

    @Override
    public void write(String message){
        view.write(message);
    }

    @Override
    public String read() {
        return view.read();
    }

    @Override
    public void close() {
        view.close();
    }

    public abstract void write(List<String> list, AlignWrite alignWrite);
    public abstract void write(DataMap dataMap);
    public abstract void write(List<DataMap> listDataMap);
}
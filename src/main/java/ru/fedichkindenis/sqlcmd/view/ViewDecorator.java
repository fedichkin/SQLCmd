package ru.fedichkindenis.sqlcmd.view;

import ru.fedichkindenis.sqlcmd.model.DataRow;

import java.util.List;

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
    public abstract void write(DataRow dataRow);
    public abstract void write(List<DataRow> listDataRow);
}

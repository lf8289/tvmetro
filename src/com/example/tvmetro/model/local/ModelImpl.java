package com.example.tvmetro.model.local;

import com.google.gson.Gson;

public class ModelImpl implements IModel {

    private Class<?> cls;

    public ModelImpl(Class<?> cls) {
        this.cls = cls;
    }

    public IData doParser(Object obj) {
        Gson gson = new Gson();
        if (obj == null) {
            return null;
        }

        IData data = null;
        try {
            data = (IData) gson.fromJson(obj.toString(), this.cls);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }
}

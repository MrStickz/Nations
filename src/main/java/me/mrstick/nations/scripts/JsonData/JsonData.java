package me.mrstick.nations.scripts.JsonData;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class JsonData {

    public String ToList(String input) {

        List<String> list = List.of(input);

        StringBuilder cListBuilder = new StringBuilder("[");
        for (String value : list) {
            cListBuilder.append('"').append(value).append("\",");
        }

        // Remove the trailing comma if there are elements
        if (cListBuilder.length() > 1) {
            cListBuilder.deleteCharAt(cListBuilder.length() - 1);
        }

        cListBuilder.append("]");

        return cListBuilder.toString();

    }

    public String UpdateList(String listString, String value) {
        // Convert the JSON array string to a list
        Type listType = new TypeToken<List<String>>() {}.getType();
        List<String> list = new Gson().fromJson(listString, listType);

        // Add the value to the list
        list.add(value);

        // Convert the list back to a JSON array string
        StringBuilder updatedJsonArrayBuilder = new StringBuilder("[");
        for (String val : list) {
            updatedJsonArrayBuilder.append('"').append(val).append("\",");
        }

        // Remove the trailing comma if there are elements
        if (updatedJsonArrayBuilder.length() > 1) {
            updatedJsonArrayBuilder.deleteCharAt(updatedJsonArrayBuilder.length() - 1);
        }

        updatedJsonArrayBuilder.append("]");

        return updatedJsonArrayBuilder.toString();
    }

    public Boolean CheckList(String list, String value) {

        return list.contains(value);

    }

}
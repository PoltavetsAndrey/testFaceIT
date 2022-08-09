package com.poltavets;

import java.util.ArrayList;
import java.util.Collection;

public class ResponseJson {

    private final Collection<String> jsons = new ArrayList<>(InstitutionDao.stringsOfJson);

    public ResponseJson() {
    }

    public Collection<String> getJsons() {
        return jsons;
    }
}

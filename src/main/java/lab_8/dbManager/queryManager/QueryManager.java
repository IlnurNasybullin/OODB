package lab_8.dbManager.queryManager;

import lab_8.dbManager.query.Query;

public interface QueryManager {

    Query selectById();
    Query insert();
    Query update();
    Query delete();

}

package me.iscle.paraulgic.data;

public class ParaulogicDatabase {
    private static volatile ParaulogicDatabase instance;

    private ParaulogicDatabase() {
    }

    public void saveWord(String word) {

    }

    public static ParaulogicDatabase getInstance() {
        if (instance == null) {
            synchronized (ParaulogicDatabase.class) {
                if (instance == null) {
                    instance = new ParaulogicDatabase();
                }
            }
        }
        return instance;
    }
}

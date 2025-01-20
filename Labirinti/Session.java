public class Session {
    private static int userId;

    // Metoda për të vendosur ID-në e përdoruesit
    public static void setUserId(int id) {
        userId = id;
    }

    // Metoda për të marrë ID-në e përdoruesit
    public static int getUserId() {
        return userId;
    }
}

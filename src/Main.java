public class Main {
    public static void main(String[] args) {
        ConnectionSQL.query("SELECT * FROM authors");
        boolean rs = ConnectionSQL.update(6, "William", "Shakespeare");
        if (rs) {
            ConnectionSQL.query("SELECT * FROM authors");
        } else {
            System.out.print("No update were executed!");
        }
    }
}

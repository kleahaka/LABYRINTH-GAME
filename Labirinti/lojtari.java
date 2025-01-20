public class lojtari {
    private int pozitaX;
    private int pozitaY;
    private int piket;
    private int thesare;
    public lojtari(int pozitaX, int pozitaY) {
        this.pozitaX = pozitaX;
        this.pozitaY = pozitaY;
        this.piket = 0;
        this.thesare = 0;
    }
    public int getPozitaX()
            {
        return pozitaX;
    }
    public void setPozitaX(int pozitaX) {

        this.pozitaX = pozitaX;
    }
    public int getPozitaY() {

                return pozitaY;
    }
    public void setPozitaY(int pozitaY) {

                this.pozitaY = pozitaY;
    }
    public int getPiket() {

                return piket;
    }
    public void setPiket(int piket) {

        this.piket = piket;
    }
    public int getThesare() {

        return thesare;
    }
    public void setThesare(int thesare) {

        this.thesare = thesare;
    }
    public void shtoPike(int pike) {

        this.piket += pike;
    }
    public void shtoThesar() {

        this.thesare++;
    }
    public boolean kaMbledhurTeGjitheThesaret(int gjatesia, int gjeresia, int[][] laberinto) {
        for (int i = 0; i < gjatesia; i++) {
            for (int j = 0; j < gjeresia; j++) {
                if (laberinto[i][j] == 4 || laberinto[i][j] == 5) {
                    return false;
                }
            }
        }
        return true;
    }
}

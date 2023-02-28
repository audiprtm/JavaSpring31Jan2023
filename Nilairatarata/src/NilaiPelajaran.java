public class NilaiPelajaran {

    public NilaiPelajaran(int nilaiPelajaran, String namaPelajaran) {
        this.NilaiPelajaran = nilaiPelajaran;
        this.namaPelajaran = namaPelajaran;
    }

    private int NilaiPelajaran;

    private String namaPelajaran;

    public int getNilaiPelajaran() {
        return NilaiPelajaran;
    }

    public void setNilaiPelajaran(int nilaiPelajaran) {
        NilaiPelajaran = nilaiPelajaran;
    }

    public String getNamaPelajaran() {
        return namaPelajaran;
    }

    public void setNamaPelajaran(String namaPelajaran) {
        this.namaPelajaran = namaPelajaran;
    }
}

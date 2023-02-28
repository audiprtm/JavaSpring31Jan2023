import java.util.List;

public class Siswa {

    public Siswa(String namaSiswa, List<NilaiPelajaran> listNilaiPelajaran) {
        this.namaSiswa = namaSiswa;
        this.listNilaiPelajaran = listNilaiPelajaran;
    }

    private String namaSiswa;
    private List<NilaiPelajaran> listNilaiPelajaran;

    public List<NilaiPelajaran> getListNilaiPelajaran() {
        return listNilaiPelajaran;
    }

    public void printNilaiPelajaranDanRatarata(){
        int total = 0;
        for (int i=0;i< listNilaiPelajaran.size();i++){
            total = total + listNilaiPelajaran.get(i).getNilaiPelajaran();
            System.out.println("Nilai Pelajaran "+listNilaiPelajaran.get(i).getNilaiPelajaran());
            System.out.println("Nama Pelajaran "+listNilaiPelajaran.get(i).getNamaPelajaran());
        }
        float ratarata = total/listNilaiPelajaran.size();
        System.out.println("Rata-Rata :"+ratarata);
    }

    public void setListNilaiPelajaran(List<NilaiPelajaran> listNilaiPelajaran) {
        this.listNilaiPelajaran = listNilaiPelajaran;
    }

    public String getNamaSiswa() {
        return namaSiswa;
    }

    public void setNamaSiswa(String namaSiswa) {
        this.namaSiswa = namaSiswa;
    }


}
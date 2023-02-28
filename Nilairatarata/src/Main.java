import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        List<Siswa> listSiswa = new ArrayList<>();

        List<NilaiPelajaran> listNilaiPelajaran = new ArrayList<>();
        listNilaiPelajaran.add(new NilaiPelajaran(90,"Matematika"));
        listNilaiPelajaran.add(new NilaiPelajaran(70,"IPA"));

        List<NilaiPelajaran> listNilaiPelajaran2 = new ArrayList<>();
        listNilaiPelajaran2.add(new NilaiPelajaran(50,"IPS"));
        listNilaiPelajaran2.add(new NilaiPelajaran(60,"Kimia"));

        listSiswa.add(new Siswa("Budi",listNilaiPelajaran));
        listSiswa.add(new Siswa("ibu Budi",listNilaiPelajaran2));


        for ( int j = 0; j < listSiswa.size(); j++){
            System.out.println(listSiswa.get(j).getNamaSiswa());
            listSiswa.get(j).printNilaiPelajaranDanRatarata();
        }
    }

}

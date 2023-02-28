import java.math.BigInteger;
import java.util.Scanner;

public class Pertemuan1 {
    public static void main(String[] args) {
        //belajarTipeData();

        //Function
        // Kalo dia umur 21 keatas = Dewasa
        // Kalo dia umur 17 - 20 = Baru Punya KTP
        // Dibawah itu = Masih Dibawah Umur
        System.out.println("Kategori Umur");
        Scanner scan = new Scanner(System.in);
        int umur = scan.nextInt();

        //kategoriUmur(umur);
        String kategoriUmur = kategoriUmurString(umur);
        System.out.println(kategoriUmur);

        //4 Function
        //Penambahan 2 Angka
        int input1 = scan.nextInt();
        int input2= scan.nextInt();
        Integer hasilPenambahan = penambahan(input1,input2);
        System.out.println(hasilPenambahan);

        //Pengurangan
        Integer hasilPengurangan = pengurangan(input1,input2);
        System.out.println(hasilPengurangan);

        //Perkalian
        Integer hasilPerkalian = perkalian(input1,input2);
        System.out.println(hasilPerkalian);

        //Pembagian
        float hasilPembagian = pembagian(input1,input2);
        System.out.println(hasilPembagian);
    }

    public static void belajarTipeData(){
        //Primitive Data Type
        int i= 10;
        long long_i=10000000;

        float f = 0.1f;
        double d= 0.0000000000000001;

        //Wrapper Class
        Integer wrapperInt = 10;
        Long wrapperLong = 10000000000000L;
        System.out.println(wrapperInt.toString() + " unit");

        String units = wrapperInt.toString() + " units";
        System.out.println(units);

        //If Else (Control Flow)
        if (units.contains("10")){
            System.out.println("Ada angka 10");
        }else{
            System.out.println("Tidak ada angka 10");
        }
    }

    public static void kategoriUmur(int umur){
        if (umur>= 21){
            System.out.println("Dewasa");
        }else if(umur>=17 && umur<=20){
            System.out.println("Baru Punya KTP");
        }else{
            System.out.println("Masih dibawah Umur");
        }
    }
                    //return  //nama function  //parameter
    public static String kategoriUmurString(int umur){
        if (umur>= 21){
            return "Dewasa";
        }else if(umur>=17 && umur<=20){
            return "Baru Punya KTP";
        }else{
            return "Masih dibawah Umur";
        }
    }

    public static Integer penambahan(int input1, int input2){
        return input1 + input2;
    }

    public static Integer pengurangan(int input1, int input2){
        return input1 - input2;
    }

    public static Integer perkalian(int input1, int input2){
        return input1 * input2;
    }

    public static float pembagian(int input1, int input2){
        return (float) input1 / input2;
    }
}

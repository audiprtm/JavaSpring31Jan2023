import java.util.HashMap;
import java.util.Map;

public class Pertemuan3 {
    public static void main(String[] args) {
        //Java Collection
        //Map
//        List<String> listOfNama = new ArrayList<>();
//        listOfNama.add("Audi");
//
//

        //OOP
        //Polymorphism
        //Abstraction

        //Sistem Order barang
        //Nama barang
        //Nama Penerima
        //Alamat Penerima
        //Nama Kurir

        Map<Integer, String> mapOfNomorPemain = new HashMap<>();
        mapOfNomorPemain.put(10, "Lionel Messi");
        mapOfNomorPemain.put(9, "Luis Suarez");

        System.out.println(mapOfNomorPemain.get(9));
        System.out.println(mapOfNomorPemain.get(10));

        Order mejaBelajar = new Order("Meja belajar", "Audi", "Tangerang", "JNE");
        System.out.println(mejaBelajar);

        //Comparision 1: using polymorphism (best practice)
        //Open for Extension , Closed for Modification
        //Huruf O dari SOLID Principle

        //Kirim Barang menggunakan Kurir
        ShipperInterface shipper = getMappingShipper(mejaBelajar.getShipperName());
        shipper.sendPackage(mejaBelajar);
        //--------------------------------------------------------------------------------------

        //Comparision 2: using if else (bad practice)
        if (mejaBelajar.getShipperName() == "JNE") {
            JNE jne = new JNE();
            jne.sendPackage(mejaBelajar);
        } else if (mejaBelajar.getShipperName() == "Sicepat") {
            Sicepat sicepat = new Sicepat();
            sicepat.sendPackage(mejaBelajar);
        }
    }

    public static ShipperInterface getMappingShipper(String shipperName){
        Map<String, ShipperInterface> mappingShipper = new HashMap<>();
        mappingShipper.put("JNE", JNE.getInstance());
        mappingShipper.put("Sicepat", new Sicepat());

        return mappingShipper.get(shipperName);
    }
}

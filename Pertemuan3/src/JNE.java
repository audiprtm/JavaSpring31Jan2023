public class JNE extends Shipper implements ShipperInterface{
    public JNE() {
        name = "JNE";
    }

    private static JNE jne;

    public static JNE getInstance(){
        if (jne==null){
            jne = new JNE();
        }
        return jne;
    }

    public void sendPackage(Order order){
        System.out.println("Kurir "+name+" sedang mengirim paket " + order.getGoodsName());
        System.out.println("Kirim request ke www.jne/booking.com");
    }
}

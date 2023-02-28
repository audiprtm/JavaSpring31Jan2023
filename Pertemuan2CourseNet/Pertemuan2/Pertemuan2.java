package Pertemuan2;

public class Pertemuan2 {
  public static void main(String[] args){
    FootballPlayer messi = new FootballPlayer("Messi", 50000000L);
    FootballPlayer neymar = new FootballPlayer("Neymar", 90000000L);
    FootballPlayer mbappe = new FootballPlayer("Mbappe", 200000000L);
  
    System.out.println(messi.toString());
    
    FootballClub barcelona = new FootballClub("Barcelona", 200000000L);
    
    barcelona.buyPlayer(messi);
    barcelona.buyPlayer(neymar);
    barcelona.buyPlayer(mbappe);
  
    System.out.println("Barcelona Players: ");
    showPlayers(barcelona);
    
    barcelona.sellPlayer(mbappe);
    barcelona.sellPlayer(messi);
    barcelona.sellPlayer(messi);
    showPlayers(barcelona);
  
    FootballClubU19 barcelonaU19 = new FootballClubU19("Barcelona U19", 50000000L);
    FootballPlayer bagusKahfi = new FootballPlayer("Bagus Kahfi", 300000L);
    barcelonaU19.buyPlayer(bagusKahfi);
    
    System.out.println("Barcelona U19 Players: ");
    showPlayers(barcelonaU19);
    
    barcelonaU19.promoteToSenior(bagusKahfi);
    showPlayers(barcelonaU19);
  }
  
  private static void showPlayers(FootballClub footballClub) {
    for (int i = 0; i< footballClub.getPlayers().size(); i++){
      System.out.println(footballClub.getPlayers().get(i).getPlayerName());
    }
  }
}

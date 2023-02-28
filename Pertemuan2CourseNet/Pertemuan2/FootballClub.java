package Pertemuan2;

import java.util.ArrayList;
import java.util.List;

public class FootballClub {
  public FootballClub(String clubName, Long fund) {
    this.clubName = clubName;
    this.fund = fund;
    players = new ArrayList<FootballPlayer>();
  }
  
  private String clubName;
  private Long fund;
  private List<FootballPlayer> players;
  
  public List<FootballPlayer> getPlayers() {
    return players;
  }
  
  public String getClubName() {
    return clubName;
  }
  
  public void setClubName(String clubName) {
    this.clubName = clubName;
  }
  
  public Long getFund() {
    return fund;
  }
  
  public void setFund(Long fund) {
    this.fund = fund;
  }
  
  public void buyPlayer(FootballPlayer player){
    if (fund - player.getMarketPrice() > 0){
      fund = fund - player.getMarketPrice();
      players.add(player);
      System.out.println("Success buy "+player.getPlayerName()+" Fund Remaining: "+fund);
    }else{
      System.out.println("Cannot buy "+player.getPlayerName());
    }
  }
  
  public void sellPlayer(FootballPlayer player){
      if (players.contains(player)){
        fund = fund + player.getMarketPrice();
        players.remove(player);
        System.out.println("Success Sell "+ player.getPlayerName()+" Fund Remaining: "+fund);
      }else{
        System.out.println(player.getPlayerName()+" is not in this club");
      }
  }
}

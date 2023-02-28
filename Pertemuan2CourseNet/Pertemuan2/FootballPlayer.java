package Pertemuan2;

public class FootballPlayer {
  public FootballPlayer(String playerName, Long marketPrice) {
    this.playerName = playerName;
    this.marketPrice = marketPrice;
  }
  
  private String playerName;
  private Long marketPrice;
  
  public String getPlayerName() {
    return playerName;
  }
  
  public void setPlayerName(String playerName) {
    this.playerName = playerName;
  }
  
  public Long getMarketPrice() {
    return marketPrice;
  }
  
  public void setMarketPrice(Long marketPrice) {
    this.marketPrice = marketPrice;
  }
  
  public String toString(){
    return "Player Name: "+playerName + ", Market Price: "+marketPrice.toString();
  }
}

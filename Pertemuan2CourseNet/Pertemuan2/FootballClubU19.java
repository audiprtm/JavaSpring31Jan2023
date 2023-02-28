package Pertemuan2;

public class FootballClubU19 extends FootballClub{
  public FootballClubU19(String clubName, Long fund) {
    super(clubName, fund);
  }
  
  public void promoteToSenior(FootballPlayer player){
    getPlayers().remove(player);
  }
}

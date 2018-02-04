package com.notificationtutorial.musicplayerservice;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 2/3/2018.
 */

public class Music {

   Integer songId;
   String artistName;
   String songName;
   String AlbumName;

   public Music(Integer songId, String artistName, String songName, String albumName) {
      this.songId = songId;
      this.artistName = artistName;
      this.songName = songName;
      AlbumName = albumName;
   }

   public Integer getSongId() {
      return songId;
   }

   public void setSongId(Integer songId) {
      this.songId = songId;
   }

   public String getArtistName() {
      return artistName;
   }

   public void setArtistName(String artistName) {
      this.artistName = artistName;
   }

   public String getSongName() {
      return songName;
   }

   public void setSongName(String songName) {
      this.songName = songName;
   }

   public String getAlbumName() {
      return AlbumName;
   }

   public void setAlbumName(String albumName) {
      AlbumName = albumName;
   }


   public static List<Music> PlayList()
   {
      List<Music> playlist = new ArrayList<Music>();

      Integer [] songs = {R.raw.j_boog_lets_do_it_again, R.raw.kranium_can_not_believe, R.raw.matisyahu_one_day, R.raw.rockabye
      ,R.raw.sikah_ft_dezine_stay_with_me, R.raw.tarrus_riley_just_the_way_you_are };

      String [] name = {"J Boog","Kranium","Matisyahu","Bandit","Sikah","Tarrus"};
      for (int i = 0; i <name.length; i++) {

         Music music = new Music(songs[i],name[i],"Unknown","Unknown");
         playlist.add(music);


      }

      return playlist;
   }


}

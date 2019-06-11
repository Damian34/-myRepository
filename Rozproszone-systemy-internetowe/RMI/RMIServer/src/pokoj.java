
    
public class pokoj {
    
    public Room getRoom(String RoomType,int nr,String osoba){
      if(RoomType == null){
         return null;
      }		
        switch (RoomType) {
            case "SmallRoom":
                return new SmallRoom(nr,osoba);
            case "MediumRoom":
                return new MediumRoom(nr,osoba);
            case "LargeRoom":
                return new LargeRoom(nr,osoba);
        }
      
      return null;
   }
}


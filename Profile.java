import java.util.ArrayList;
import java.io.IOException;
import java.io.File;
import java.io.FileWriter;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;

class Profile{
  String personIndex;
  String name;
  String firstPlace;
  boolean[] freeTime;
  String history;

  public Profile(){
    this("9801", "未具名", "這", new boolean[]{true, true}, "無史料");
  }
  //建構子 (編號， 名字， 第一次見面的地方， 空閒時間， 歷史片段
  public Profile(String personIndex, String name, String firstPlace, boolean[] freeTime, String history){
    this.personIndex = personIndex;
    this.name = name;
    this.firstPlace = firstPlace;
    this.freeTime = freeTime;
    this.history = history;
  }

  //建構子:讀檔 自動判斷檔案內的關鍵字 關鍵字有以下[編號:] [姓名:] [初識地點:] [時間表:] [史料:]
  public Profile(File file) throws IOException{
    this.personIndex = "";
    this.name = "";
    this.firstPlace = "";
    this.freeTime = new boolean[0];
    this.history = "";

    InputStreamReader insReader = new InputStreamReader(new FileInputStream(file), "UTF-8");
    BufferedReader bufReader = new BufferedReader(insReader);

    String line = new String();
    while((line = bufReader.readLine()) != null){
      if(line.contains("編號:")){
        if(personIndex.length() == 0){
          personIndex = line.replace("編號:", "");
        }else{
          personIndex = "(沒有資料)" + line.replace("編號:", "");
        }
      }
      else if(line.contains("姓名:")){
        if(name.length() == 0){
          name = line.replace("姓名:", "");
        }else{
          name = "(沒有資料)" + line.replace("姓名:", "");
        }
      }
      else if(line.contains("初識地點:")){
        if(firstPlace.length() == 0){
          firstPlace = line.replace("初識地點:", "");
        }else{
          firstPlace = "(沒有資料)" + line.replace("初識地點:", "");
        }
      }
      else if(line.contains("時間表:")){
        freeTime = new boolean[21];
        String temp = "";
        //逐一讀取
        for(int i = 0; i < freeTime.length; i++){
          String timeBol = bufReader.readLine();
          if(timeBol != null) freeTime[i] = Boolean.parseBoolean(  timeBol.replace(",", "") );
        }
      }
      else if(line.contains("史料:")){
        String history = "";
        //逐一讀取
        while((history = bufReader.readLine()) != null){
          if(history.contains("史料END")) break;
          history += history;
        }
      }
    }

    bufReader.close();
    insReader.close();
  }

  //main方法 目前測試用
  public static void main(String[] args) throws IOException{
    Profile pr = new Profile(new File("nameList/010_大壞蛋"));
  }

  //創建檔案
  public boolean createFile() throws IOException{
    final String NAME_FOLDER = "nameList";

    //若資料夾不存在就建立一個資料夾
    File fileDb = new File(NAME_FOLDER);
    if( !fileDb.exists() || !fileDb.isDirectory() ) fileDb.mkdir();
    String fileName = NAME_FOLDER + "/" + getPersonIndex() + "_" + getName();
    if(new File(fileName).exists()) System.out.print("重複囉");  //TEST  //TODO 這裡寫了一個test 可是我看不懂
    if(new File(fileName).exists()) return false;

    // 寫檔
    FileWriter fileWritter = new FileWriter(fileName, true);
    BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
    bufferWritter.write("編號:" + getPersonIndex() + "\r\n");
    bufferWritter.write("姓名:" + getName() + "\r\n");
    bufferWritter.write("初識:" + getFirstPlace() + "\r\n");
    bufferWritter.write("時間:\r\n");
    for(Boolean bol : getFreeTime()) bufferWritter.write(bol + ",\r\n");
    bufferWritter.write("史料:\r\n");
    bufferWritter.write(getHistory() + "\r\n");
    bufferWritter.write("史料END\r\n");
    bufferWritter.close();
    fileWritter.close();

    return true;
  }

  // get / set
  public String getPersonIndex(){
    return this.personIndex;
  }
  public void setPersonIndex(String personIndex){
    this.personIndex = personIndex;
  }
  public String getName(){
    return this.name;
  }
  public void setName(String name){
    this.name = name;
  }
  public String getFirstPlace(){
    return this.firstPlace;
  }
  public void setFirstPlace(String firstPlace){
    this.firstPlace = firstPlace;
  }
  public boolean[] getFreeTime(){
    return this.freeTime;
  }
  public void setFreeTime(boolean[] freeTime){
    this.freeTime = freeTime;
  }
  public String getHistory(){
    return this.history;
  }
  public void setHistory(String history){
    this.history = history;
  }
}
